package ingestion;

import com.google.common.io.Resources;
import medline.MedlineCitationSet;
import org.apache.log4j.BasicConfigurator;
import org.apache.solr.common.SolrInputDocument;

import java.util.Collection;

/**
 * Created by alex on 29/09/14.
 */
public class TestScripts {
    public static final String SAMPLE_FILE = "samples/medsamp2014.xml";

    public static void deleteRecords() throws Exception {
        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();
    }

    public static void importSampleDate() throws Exception {
        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();

        MedlineReader medlineReader = new MedlineReader();
        MedlineCitationSet set = medlineReader.unmarshall(Resources.getResource(SAMPLE_FILE).openStream());
        Collection<SolrInputDocument> collection = medlineReader.mapToSolrInputDocumentCollection(set);
        client.update(collection);
    }
 }
