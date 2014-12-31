import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PubMedCollectionReaderTest {
    public static final String TEST_COLLECTION = "/collection";
    public static final String TYPE_SYSTEM = "TypeSystem.xml";
    private PubMedCollectionReader collectionReader;
    private URI typeSystem;


    @Before
    public void setUp() throws Exception {
        URL url = this.getClass().getResource(TEST_COLLECTION);
        File collection = new File(url.getFile());

        typeSystem = this.getClass().getResource(TYPE_SYSTEM).toURI();


        collectionReader = (PubMedCollectionReader) CollectionReaderFactory.createReader(
                PubMedCollectionReader.class,
                PubMedCollectionReader.PARAM_XML_DIRECTORY, collection.getAbsolutePath());
    }

    @After
    public void tearDown() throws Exception {


    }

    @Test
    public void testGetNext() throws Exception {
        JCas jCas = JCasFactory.createJCas(TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(typeSystem.toString()));
        collectionReader.getNext(jCas);
        assertNotNull(jCas.getDocumentText());
    }

    @Test
    public void testHasNext() throws Exception {
        assertTrue(collectionReader.hasNext());

    }

    @Test
    public void testGetProgress() throws Exception {

    }

    @Test
    public void testClose() throws Exception {

    }
}