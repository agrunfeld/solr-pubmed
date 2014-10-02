package ingestion;

import com.google.common.io.Resources;
import medline.*;
import org.apache.commons.io.FileUtils;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class MedlineReaderTest {

    private static final String TEST_FILE= "medsamp2014_small.xml";
    private MedlineReader ingestion;

    @Before
    public void setUp() throws Exception {
        ingestion = new MedlineReader();
    }

    @Test
    public void testUnmarshallFromInputStream() throws Exception {
        InputStream inputStream = Resources.getResource(TEST_FILE).openStream();
        MedlineCitationSet set = ingestion.unmarshall(inputStream);
        assertNotNull(set);
    }

    @Test
    public void testUnmarshallFromFile() throws Exception {
        URL url = Resources.getResource(TEST_FILE);
        File testFile = new File(url.getFile());
        assertTrue(testFile.exists());

        MedlineCitationSet set = ingestion.unmarshall(testFile);
        assertNotNull(set);
    }

    @Test
    public void testUnmarshallSetsJournalTitle() throws Exception {
        URL url = Resources.getResource(TEST_FILE);
        File testFile = new File(url.getFile());
        MedlineCitationSet set = ingestion.unmarshall(testFile);
        MedlineCitation citation = set.getMedlineCitation().get(0);
        assertEquals("Molecular microbiology", citation.getArticle().getJournal().getTitle());
    }

    @Test
    public void testToInputDocument() throws Exception {
        SolrInputDocument inputDocument = ingestion.mapToSolrInputDocument(generateMockCitation());

        SolrInputField field = inputDocument.getField("date_created");
        assertTrue(field.getValueCount() > 0);

        field = inputDocument.getField("journal_title");
        assertEquals("test", field.getValue());
    }

    @Test
    public void testToInputDocumentCollection() throws Exception {
        int collectionSize = 5;
        List<MedlineCitation> medlineCitations = new ArrayList<>(collectionSize);
        for (int i = 0; i < collectionSize; i++) {
            medlineCitations.add(generateMockCitation());
        }
        MedlineCitationSet citationSet = mock(MedlineCitationSet.class);
        when(citationSet.getMedlineCitation()).thenReturn(medlineCitations);
        Collection<SolrInputDocument> inputDocuments = ingestion.mapToSolrInputDocumentCollection(citationSet);
        assertEquals(5, inputDocuments.size());
    }

    private MedlineCitation generateMockCitation() {
        MedlineCitation citation = new MedlineCitation();
        PMID pmid = new PMID();
        pmid.setvalue("1");
        citation.setPMID(pmid);
        Day day = new Day();
        day.setvalue("1");
        Month month = new Month();
        month.setvalue("1");
        Year year = new Year();
        year.setvalue("2000");
        DateCreated mockDate = new DateCreated();
        mockDate.setDay(day);
        mockDate.setMonth(month);
        mockDate.setYear(year);
        citation.setDateCreated(mockDate);

        Article mockArticle = new Article();
        Abstract mockAbstract = new Abstract();
        mockArticle.setAbstract(mockAbstract);
        citation.setArticle(mockArticle);
        Journal mockJournal = new Journal();
        mockJournal.setTitle("test");
        mockArticle.setJournal(mockJournal);
        return citation;
    }
}