import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.junit.After;
import reader.PubMedCollectionReader;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

public class PubMedCollectionReaderTest {
    public static final String TEST_COLLECTION = "/collection";
    public static final String TYPE_SYSTEM = "/TypeSystem.xml";
    private PubMedCollectionReader collectionReader;
    private File typeSystem;


    @org.junit.Before
    public void setUp() throws Exception {
        URL url = this.getClass().getResource(TEST_COLLECTION);
        File collection = new File(url.getFile());

        url = this.getClass().getResource(TYPE_SYSTEM);
        typeSystem = new File(url.getFile());

        collectionReader = (PubMedCollectionReader) CollectionReaderFactory.createReader(
                PubMedCollectionReader.class,
                PubMedCollectionReader.PARAM_XML_DIRECTORY, collection.getAbsolutePath());
    }

    @After
    public void tearDown() throws Exception {


    }

    @org.junit.Test
    public void testGetNext() throws Exception {
        JCas jCas = JCasFactory.createJCas(TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(typeSystem.getAbsolutePath()));
        collectionReader.getNext(jCas);
        assertNotNull(jCas.getDocumentText());
    }

    @org.junit.Test
    public void testHasNext() throws Exception {
        assertTrue(collectionReader.hasNext());

    }

    @org.junit.Test
    public void testGetProgress() throws Exception {

    }

    @org.junit.Test
    public void testClose() throws Exception {

    }
}