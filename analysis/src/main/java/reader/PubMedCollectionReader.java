package reader;

import ingestion.PubMedReader;
import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.JCasBuilder;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import pubmed.PubmedArticle;
import pubmed.PubmedArticleSet;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

/**
 * Created by alex on 15/10/14.
 */
public class PubMedCollectionReader extends JCasCollectionReader_ImplBase {

    public static final String PARAM_XML_DIRECTORY = "xmlDirectory";
    @ConfigurationParameter(name = PARAM_XML_DIRECTORY)
    private String xmlDirectory;


    private Iterator<File> fileIterator;
    private Iterator<PubmedArticle> articleIterator;
    private PubMedReader reader;

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        fileIterator = FileUtils.iterateFiles(new File(xmlDirectory), new String[] {"gz"}, true);
        try {
            reader = new PubMedReader();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {
        if (articleIterator != null && articleIterator.hasNext()) {
            initializeCas(jCas, articleIterator.next());
        } else {
            File zippedXml = fileIterator.next();
            InputStream fileStream = new FileInputStream(zippedXml);
            GZIPInputStream gzipStream = new GZIPInputStream(fileStream);

            try {
                PubmedArticleSet articleSet = reader.unmarshall(gzipStream);
                articleIterator = articleSet.getPubmedArticle().iterator();
                if (articleIterator.hasNext()) {
                    initializeCas(jCas, articleIterator.next());
                }
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            gzipStream.close();
            fileStream.close();
        }
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return fileIterator.hasNext() || articleIterator.hasNext();
    }

    @Override
    public Progress[] getProgress() {
        return new Progress[0];
    }

    private void initializeCas(JCas jCas, PubmedArticle article) {
        JCasBuilder builder = new JCasBuilder(jCas);

        if (article.getMedlineCitation().getArticle().getAbstract() != null) {
            String abstractText = article.getMedlineCitation().getArticle().getAbstract().getAbstractText();
            builder.add(abstractText);
        }

        String articleTitle = article.getMedlineCitation().getArticle().getArticleTitle();
        builder.add(articleTitle);
        builder.close();
    }
}
