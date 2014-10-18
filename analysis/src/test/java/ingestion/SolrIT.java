package ingestion;

import com.google.common.io.Resources;
import medline.MedlineCitationSet;
import org.apache.log4j.BasicConfigurator;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pubmed.PubmedArticleSet;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SolrIT {
    public static final String MEDLINE_SAMPLE = "samples/medsamp2014.xml";
    public static final String PUBMED_SAMPLE = "samples/pubmed_sample_2009.xml";
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
    public void testAddMedlineRecords() throws Exception {
        MedlineReader medlineReader = new MedlineReader();
        MedlineCitationSet set = medlineReader.unmarshall(Resources.getResource(MEDLINE_SAMPLE).openStream());
        Collection<SolrInputDocument> collection = medlineReader.mapToSolrInputDocumentCollection(set);
        assertEquals(161, collection.size());
        client.update(collection);
        assertEquals(161, client.getCount());
    }

    @Test
    public void testAddMedlineRecordsConcurrent() throws Exception {
        client = new SolrClient(true);
        MedlineReader medlineReader = new MedlineReader();
        MedlineCitationSet set = medlineReader.unmarshall(Resources.getResource(MEDLINE_SAMPLE).openStream());
        Collection<SolrInputDocument> collection = medlineReader.mapToSolrInputDocumentCollection(set);
        assertEquals(161, collection.size());
        client.update(collection);
        assertEquals(161, client.getCount());
    }

    @Test
    public void testAddPubmedRecords() throws Exception {
        PubMedReader pubMedReader = new PubMedReader();
        PubmedArticleSet set = pubMedReader.unmarshall(Resources.getResource(PUBMED_SAMPLE).openStream());
        Collection<SolrInputDocument> collection = pubMedReader.mapToSolrInputDocumentCollection(set);
        System.out.println(collection.size());
        assertEquals(4, collection.size());
        client.update(collection);
        assertEquals(4, client.getCount());
    }

    @Test
    public void testAddPubmedRecordsConcurrent() throws Exception {
        client = new SolrClient(true);
        PubMedReader pubMedReader = new PubMedReader();
        PubmedArticleSet set = pubMedReader.unmarshall(Resources.getResource(PUBMED_SAMPLE).openStream());
        Collection<SolrInputDocument> collection = pubMedReader.mapToSolrInputDocumentCollection(set);
        assertEquals(4, collection.size());
        client.update(collection);
        assertEquals(4, client.getCount());
    }

    @Test
    public void testAddPubmedRecordsConcurrentAllDifferent() throws Exception {
        client = new SolrClient(true);
        PubMedReader pubMedReader = new PubMedReader();
        PubmedArticleSet set = pubMedReader.unmarshall(Resources.getResource(PUBMED_SAMPLE).openStream());
        Collection<SolrInputDocument> collection = pubMedReader.mapToSolrInputDocumentCollection(set);
        assertEquals(4, collection.size());
        client.update(collection);
        SolrDocumentList documentList = client.getRecords("*:*", 10);
        assertEquals(4, documentList.getNumFound());
        for (int i = 0; i < documentList.getNumFound(); i++) {
            for (int j = i + 1; j < documentList.getNumFound(); j++) {
                assertNotEquals(documentList.get(i).get("article_title"), documentList.get(j).get("article_title"));
            }
        }
    }
}
