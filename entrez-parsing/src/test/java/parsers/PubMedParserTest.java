package parsers;

import org.apache.commons.io.FileUtils;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.junit.Before;
import org.junit.Test;
import parsers.pubmed.*;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PubMedParserTest {

    private static final String TEST_FILE= "/samples/pubmed_sample_2009.xml";
    private PubMedParser ingestion;
    private File testFile;

    @Before
    public void setUp() throws Exception {
        ingestion = new PubMedParser();
        testFile = getTestFile();
    }

    private File getTestFile() {
        URL url = PubMedParser.class.getResource(TEST_FILE);
        return new File(url.getFile());
    }

    @Test
    public void testUnmarshallFromInputStream() throws Exception {
        InputStream inputStream = FileUtils.openInputStream(getTestFile());
        PubmedArticleSet set = ingestion.unmarshall(inputStream);
        assertNotNull(set);
    }

    @Test
    public void testUnmarshallFromFile() throws Exception {
        PubmedArticleSet set = ingestion.unmarshall(testFile);
        assertNotNull(set);
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
    public void testUnmarshallSetsJournalTitle() throws Exception {
        PubmedArticleSet set = ingestion.unmarshall(testFile);
        PubmedArticle article = set.getPubmedArticle().get(0);
        assertEquals("Journal of the National Comprehensive Cancer Network : JNCCN", article.getMedlineCitation().getArticle().getJournal().getTitle());
    }

    @Test
    public void testToInputSetsAuthorList() throws Exception {
        PubmedArticleSet set = ingestion.unmarshall(testFile);
        PubmedArticle article = set.getPubmedArticle().get(0);
        SolrInputDocument document = ingestion.mapToSolrInputDocument(article);
        SolrInputField field = document.getField("author_list");
        Collection<java.lang.Object> values = field.getValues();
        ArrayList expected = new ArrayList<>();
        Collections.addAll(expected, new String[]{"A Lipton", "R Uzzo", "RJ Amato", "GK Ellis", "B Hakimian", "GD Roodman", "MR Smith"});
        assertTrue(values.containsAll(expected));
    }


    @Test
    public void testUnmarshallCollection() throws Exception {
        PubmedArticleSet set = ingestion.unmarshall(testFile);
        PubmedArticle article = set.getPubmedArticle().get(0);
        PubmedArticle anotherArticle = set.getPubmedArticle().get(1);
        assertEquals("Journal of the National Comprehensive Cancer Network : JNCCN", article.getMedlineCitation().getArticle().getJournal().getTitle());
        assertEquals("International journal of clinical practice. Supplement", anotherArticle.getMedlineCitation().getArticle().getJournal().getTitle());
    }

    @Test
    public void testToInputDocumentCollection() throws Exception {
        int collectionSize = 5;
        List<PubmedArticle> pubmedArticles = new ArrayList<>(collectionSize);
        for (int i = 0; i < collectionSize; i++) {
            pubmedArticles.add(generateMockCitation());
        }
        PubmedArticleSet articleSet = mock(PubmedArticleSet.class);
        when(articleSet.getPubmedArticle()).thenReturn(pubmedArticles);
        Collection<SolrInputDocument> inputDocuments = ingestion.mapToSolrInputDocumentCollection(articleSet);
        assertEquals(5, inputDocuments.size());
    }

    private PubmedArticle generateMockCitation() {
        PubmedArticle mockPubmedArticle = new PubmedArticle();
        MedlineCitation citation = new MedlineCitation();
        mockPubmedArticle.setMedlineCitation(citation);
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
        AuthorList authorList = new AuthorList();
        mockArticle.setAuthorList(authorList);
        Abstract mockAbstract = new Abstract();
        mockArticle.setAbstract(mockAbstract);
        citation.setArticle(mockArticle);
        Journal mockJournal = new Journal();
        mockJournal.setTitle("test");
        mockArticle.setJournal(mockJournal);
        return mockPubmedArticle;
    }
}