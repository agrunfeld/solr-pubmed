package ingestion;

import com.google.common.io.Resources;
import medline.MedlineCitationSet;
import org.apache.log4j.BasicConfigurator;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class SolrIT {
    public static final String SAMPLE_FILE = "samples/medsamp2014.xml";
    private SolrClient client;

    @Before
    public void setUp() throws Exception {
        BasicConfigurator.configure();
        client = new SolrClient(false);
        client.deleteRecords();
    }

    @After
    public void tearDown() throws Exception {
        client.deleteRecords();
    }

    @Test
    public void testAddRecords() throws Exception {
        MedlineReader medlineReader = new MedlineReader();
        MedlineCitationSet set = medlineReader.unmarshall(Resources.getResource(SAMPLE_FILE).openStream());
        Collection<SolrInputDocument> collection = medlineReader.mapToSolrInputDocumentCollection(set);
        assertEquals(161, collection.size());
        client.update(collection);
        assertEquals(161, client.getCount());
    }

    @Test
    public void testAddRecordsConcurrent() throws Exception {
        client = new SolrClient(true);
        MedlineReader medlineReader = new MedlineReader();
        MedlineCitationSet set = medlineReader.unmarshall(Resources.getResource(SAMPLE_FILE).openStream());
        Collection<SolrInputDocument> collection = medlineReader.mapToSolrInputDocumentCollection(set);
        assertEquals(161, collection.size());
        client.update(collection);
        assertEquals(161, client.getCount());
    }
}
