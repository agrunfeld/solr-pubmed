package ingestion;

import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pubmed.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.Object;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//todo: alternative approach, use an XSLT to transform medline records to XML Formatted Index Updates
public class PubMedReader {

    private final Unmarshaller unmarshaller;
    private final Logger logger;

    public PubMedReader() throws JAXBException {
        logger = LoggerFactory.getLogger(SolrClient.class);
        JAXBContext jaxbContext = JAXBContext.newInstance("pubmed", ObjectFactory.class.getClassLoader());
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    public PubmedArticleSet unmarshall(File xml) throws FileNotFoundException, JAXBException {
        return unmarshall(new FileInputStream(xml));
    }

    public PubmedArticleSet unmarshall(InputStream inputStream) throws JAXBException {
        return (PubmedArticleSet) unmarshaller.unmarshal(inputStream);
    }

    public Collection<SolrInputDocument> mapToSolrInputDocumentCollection(PubmedArticleSet articleSet) {
        Collection<SolrInputDocument> documentCollection = new ArrayList<>(articleSet.getPubmedArticle().size());
        for(PubmedArticle article : articleSet.getPubmedArticle()) {
            documentCollection.add(mapToSolrInputDocument(article));
        }
        return documentCollection;
    }

    public SolrInputDocument mapToSolrInputDocument(PubmedArticle article) {
        SolrInputDocument document = new SolrInputDocument();
        MedlineCitation citation = article.getMedlineCitation();
        addField(document, "pmid", citation.getPMID().getvalue());
        addDateField(document, "date_created", citation.getDateCreated());
        addDateField(document, "date_completed", citation.getDateCompleted());
        addDateField(document, "date_revised", citation.getDateRevised());
        addField(document, "article_title", citation.getArticle().getArticleTitle());
        addField(document, "journal_title", citation.getArticle().getJournal().getTitle());
        if (citation.getArticle().getAbstract() != null) {
            addField(document, "abstract_text", citation.getArticle().getAbstract().getAbstractText());
        }
        if (citation.getArticle().getAuthorList() != null) {
            addAuthors(document, "author_list", citation.getArticle().getAuthorList().getAuthor());
        }
        if (citation.getMeshHeadingList() != null) {
            addMeshHeadings(document, "mesh_heading_list", citation.getMeshHeadingList().getMeshHeading());
        }
        if (citation.getGeneSymbolList() != null) {
            addGeneSymbols(document, "gene_symbol_list", citation.getGeneSymbolList().getGeneSymbol());
        }
        if (citation.getChemicalList() != null) {
            addChemicals(document, "gene_symbol_list", citation.getChemicalList().getChemical());
        }
        return document;
    }

    private void addAuthors(SolrInputDocument document, String name, List<Author> authors) {
        for (Author author : authors) {
            try {
                document.addField(name, formatAuthorName(author));
            } catch (NullPointerException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void addChemicals(SolrInputDocument document, String name, List<Chemical> chemicals) {
        for (Chemical chemical : chemicals) {
            /*todo: incorporate registry number*/
            document.addField(name, chemical.getNameOfSubstance());
        }
    }

    private void addGeneSymbols(SolrInputDocument document, String name, List<GeneSymbol> geneSymbols) {
        for (GeneSymbol geneSymbol : geneSymbols) {
            document.addField(name, geneSymbol.getvalue());
        }
    }

    private void addMeshHeadings(SolrInputDocument document, String name, Collection<MeshHeading> meshHeadings) {
        for (MeshHeading meshHeading : meshHeadings) {
            /*todo: incorporate qualifiers and minor topic headings*/
            if (meshHeading.getDescriptorName().getMajorTopicYN().equals("Y")) {
                document.addField(name, meshHeading.getDescriptorName().getvalue());
            }
        }
    }

    private void addDateField(SolrInputDocument document, String name, Date date) {
        if (date != null) {
            try {
                document.addField(name, date.getConvertedDate());
            } catch (ParseException e) {
                logger.error(String.format("Failed to add date to field %s", name), e);
            }
        }
    }

    private void addField(SolrInputDocument document, String name, Object object) {
        if (object != null)
            document.addField(name, object);
    }

    /* todo: ugh so messy, need to use XSLT */
    private String formatAuthorName(Author author) {
        String lastName = null, foreName = null, initials = null, suffix = null, collectiveName = null;
        for (Object o : author.getLastNameOrForeNameOrInitialsOrSuffixOrNameIDOrCollectiveName()) {
            if (o instanceof LastName) {
                lastName = ((LastName) o).getvalue();
            }
            if (o instanceof Initials) {
                initials = ((Initials) o).getvalue();
            }
            if (o instanceof Suffix) {
                suffix = ((Suffix) o).getvalue();
            }
            if (o instanceof CollectiveName) {
                collectiveName = ((CollectiveName) o).getvalue();
            }
        }
        if (lastName != null && initials != null)
            return String.format("%s %s", initials, lastName);
        else
            throw new NullPointerException("Author must contain at least last and initials");
    }
}
