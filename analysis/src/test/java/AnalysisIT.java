/**
 * Created by alex on 15/10/14.
 */

import annotator.SethAnnotator;
import annotator.TmVarAnnotator;
import consumer.SequenceOntologyMappingStrategy;
import org.apache.clerezza.uima.casconsumer.CASMappingStrategiesRepository;
import org.apache.clerezza.uima.casconsumer.CASMappingStrategy;
import org.apache.clerezza.uima.casconsumer.ClerezzaCASConsumer;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.solrcas.SolrCASConsumer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import reader.PubMedCollectionReader;

import java.io.File;
import java.net.URL;

public class AnalysisIT {
    public static final String TEST_COLLECTION = "/collection";
    public static final String TYPE_SYSTEM = "/TypeSystem.xml";
    public static final String REGEX_FILE = "/mutations.txt";
    public static final String SOLR_MAPPING_FILE = "classpath:/solrMapping.xml";
    public static final String SOLR_PATH = "http://localhost:8983/core0/";

    private File collection;
    private File typeSystem;
    private File regexFile;

    private File getResource(String resourcePath) {
        URL url = this.getClass().getResource(resourcePath);
        return new File(url.getFile());
    }

    @Before
    public void setUp() throws Exception {
        collection = getResource(TEST_COLLECTION);
        typeSystem = getResource(TYPE_SYSTEM);
        regexFile = getResource(REGEX_FILE);

        CASMappingStrategy casMappingStrategy = new SequenceOntologyMappingStrategy();
        CASMappingStrategiesRepository.getInstance().register(casMappingStrategy, "so");
    }

    @Ignore("not ready yet")
    @Test
    public void testPipeline() throws Exception {
        CollectionReaderDescription pubMedReader = CollectionReaderFactory.createReaderDescription(
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
                        TmVarAnnotator.PARAM_TMVAR_BASE_DIR, "/home/alex/tools/tmVar");


        AnalysisEngineDescription solrConsumer =
                AnalysisEngineFactory.createEngineDescription(SolrCASConsumer.class,
                        "solrInstanceType", "http",
                        "solrPath", SOLR_PATH,
                        "mappingFile", SOLR_MAPPING_FILE,
                        "autoCommit", true);

        AnalysisEngineDescription rdfConsumer =
                AnalysisEngineFactory.createEngineDescription(
                        ClerezzaCASConsumer.class,
                        "mappingStrategy", "so");

        SimplePipeline.runPipeline(pubMedReader, seth, tmVar, rdfConsumer, solrConsumer);
    }
}
