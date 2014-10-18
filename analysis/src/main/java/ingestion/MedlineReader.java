package ingestion;

import medline.*;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//todo: alternative approach, use an XSLT to transform medline records to XML Formatted Index Updates
public class MedlineReader {

    private final Unmarshaller unmarshaller;
    private final Logger logger;

    public MedlineReader() throws JAXBException {
        logger = LoggerFactory.getLogger(SolrClient.class);
        JAXBContext jaxbContext = JAXBContext.newInstance("medline", ObjectFactory.class.getClassLoader());
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    public MedlineCitationSet unmarshall(File xml) throws FileNotFoundException, JAXBException {
        return unmarshall(new FileInputStream(xml));
    }

    public MedlineCitationSet unmarshall(InputStream inputStream) throws JAXBException {
        return (MedlineCitationSet) unmarshaller.unmarshal(inputStream);
    }

    public Collection<SolrInputDocument> mapToSolrInputDocumentCollection(MedlineCitationSet citationSet) {
        Collection<SolrInputDocument> documentCollection = new ArrayList<>(citationSet.getMedlineCitation().size());
        for(MedlineCitation citation : citationSet.getMedlineCitation()) {
            documentCollection.add(mapToSolrInputDocument(citation));
        }
        return documentCollection;
    }

    public SolrInputDocument mapToSolrInputDocument(MedlineCitation citation) {
        SolrInputDocument document = new SolrInputDocument();
        addField(document, "pmid", citation.getPMID().getvalue());
        addDateField(document, "date_created", citation.getDateCreated());
        addDateField(document, "date_completed", citation.getDateCompleted());
        addDateField(document, "date_revised", citation.getDateRevised());
        addField(document, "article_title", citation.getArticle().getArticleTitle());
        addField(document, "journal_title", citation.getArticle().getJournal().getTitle());
        if (citation.getArticle().getAbstract() != null) {
            addAbstractText(document, "abstract_text", citation.getArticle().getAbstract().getAbstractText());
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

    /*todo: use child documents and section labels*/
    private void addAbstractText(SolrInputDocument document, String name, List<AbstractText> abstractTexts) {
        for (AbstractText abstractText : abstractTexts) {
            document.addField(name, abstractText.getvalue());
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


}
