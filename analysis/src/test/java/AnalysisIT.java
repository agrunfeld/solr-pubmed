/**
 * Created by alex on 15/10/14.
 */

import annotator.SethAnnotator;
import annotator.TmVarAnnotator;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.junit.Before;
import org.junit.Test;
import reader.PubMedCollectionReader;

import java.io.File;
import java.net.URL;

public class AnalysisIT {
    public static final String TEST_COLLECTION = "/collection";
    public static final String TYPE_SYSTEM = "/TypeSystem.xml";
    public static final String REGEX_FILE = "/mutations.txt";
    private File collection;
    private File typeSystem;
    private File regexFile;

    @Before
    public void setUp() throws Exception {
        URL url = this.getClass().getResource(TEST_COLLECTION);
        collection = new File(url.getFile());

        url = this.getClass().getResource(TYPE_SYSTEM);
        typeSystem = new File(url.getFile());

        url = this.getClass().getResource(REGEX_FILE);
        regexFile = new File(url.getFile());

    }

    @Test
    public void testPipeline() throws Exception {

        CollectionReaderDescription reader = CollectionReaderFactory.createReaderDescription(
                PubMedCollectionReader.class,
                TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(typeSystem.getAbsolutePath()),
                PubMedCollectionReader.PARAM_XML_DIRECTORY, collection.getAbsolutePath());

        AnalysisEngineDescription seth =
                AnalysisEngineFactory.createEngineDescription(
                        SethAnnotator.class,
                        SethAnnotator.PARAM_REGEX_FILE_PATH, regexFile.getAbsolutePath(),
                        SethAnnotator.PARAM_USE_EXACT_GRAMMAR, false,
                        SethAnnotator.PARAM_USE_OLD_NOMENCLATURE, false);
        AnalysisEngineDescription tmVar =
                AnalysisEngineFactory.createEngineDescription(
                        TmVarAnnotator.class,
                        TmVarAnnotator.PARAM_TMVAR_BASE_DIR, "/home/alex/tools/tmVar",
                        TmVarAnnotator.PARAM_TMVAR_BASE_INPUT_DIR, "/home/alex/tools/tmVar/in",
                        TmVarAnnotator.PARAM_TMVAR_BASE_OUTPUT_DIR, "/home/alex/tools/tmVar/out");

        SimplePipeline.runPipeline(reader, seth, tmVar);
    }
}
