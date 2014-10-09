package ingestion;

import com.google.common.io.Resources;
import medline.MedlineCitationSet;
import org.apache.log4j.BasicConfigurator;
import org.apache.solr.common.SolrInputDocument;
import org.codehaus.plexus.util.FileUtils;
import pubmed.PubmedArticleSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by alex on 29/09/14.
 */
public class TestScripts {
    public static final String SAMPLE_FILE = "samples/medsamp2014.xml";
    public static final String MEDLINE = "/home/alex/medline/2014";

    public static void deleteRecords() throws Exception {
        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();
    }

    public static void importSampleDate() throws Exception {
        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();

    }


    public static void importLastYear() throws Exception {
//        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();
        PubMedReader pubMedReader = new PubMedReader();

        File medline = FileUtils.getFile(MEDLINE);
        File[] zippedMedline = medline.listFiles();
        System.out.println(zippedMedline.length);
        for (int i = 0; i < 2; i++) {
            System.out.println(zippedMedline[i].getName());
            InputStream fileStream = new FileInputStream(zippedMedline[i]);
            GZIPInputStream gzipStream = new GZIPInputStream(fileStream);
            PubmedArticleSet set = pubMedReader.unmarshall(gzipStream);
            Collection<SolrInputDocument> collection = pubMedReader.mapToSolrInputDocumentCollection(set);
            client.update(collection);
            gzipStream.close();
            fileStream.close();
        }
    }

    public static void main(String[] args) throws Exception {
        importLastYear();
    }

 }
