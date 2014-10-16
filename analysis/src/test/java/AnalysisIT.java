/**
 * Created by alex on 15/10/14.
 */

import annotator.EchoAE;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.junit.Before;
import org.junit.Test;
import reader.PubMedCollectionReader;

import java.io.File;
import java.net.URL;

public class AnalysisIT {
    public static final String TEST_COLLECTION = "/collection";
    private File collection;

    @Before
    public void setUp() throws Exception {
        URL url = this.getClass().getResource(TEST_COLLECTION);
        collection = new File(url.getFile());

    }

    @Test
    public void testPipeline() throws Exception {

        CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                PubMedCollectionReader.class,
                PubMedCollectionReader.PARAM_XML_DIRECTORY, collection.getAbsolutePath());

        AnalysisEngineDescription tokenizer =
                AnalysisEngineFactory.createEngineDescription(
                        EchoAE.class);

        SimplePipeline.runPipeline(reader, tokenizer);
    }
}
