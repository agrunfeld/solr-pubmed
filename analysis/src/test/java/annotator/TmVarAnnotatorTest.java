package annotator;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.JCasBuilder;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;
import types.Section;
import types.TmVarMutation;

import java.io.File;
import java.net.URL;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

public class TmVarAnnotatorTest {
    public static final String TYPE_SYSTEM = "/TypeSystem.xml";
    private File typeSystem;
    private JCas sampleJCas;
    private static final String[] expected = {
            "c.35delG",
            "L90P",
            "c.269T>C",
            "rs2234671",
            "Ex2+860G>C",
            "S276T",
            "rs2234671",
            "rs2234671"};

    @Before
    public void setUp() throws Exception {
        URL url = this.getClass().getResource(TYPE_SYSTEM);
        typeSystem = new File(url.getFile());

        sampleJCas =  JCasFactory.createJCas(TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(typeSystem.getAbsolutePath()));

        JCasBuilder builder = new JCasBuilder(sampleJCas);
        builder.add(
                "Causative GJB2 mutations were identified in 31 (15.2%) patients, " +
                        "and two common mutations, c.35delG and L90P (c.269T>C), accounted " +
                        "for 72.1% and 9.8% of GJB2 disease alleles. \n \n \n", Section.class);
        builder.add(
                "BACKGROUND: The chemokine receptor 1 CXCR-1 (or IL8R-alpha) is a specific " +
                        "receptor for the interleukin 8 (IL-8), which is chemoattractant for neutrophils " +
                        "and has an important role in the inflammatory response. The polymorphism rs2234671 " +
                        "at position Ex2+860G>C of the CXCR1 gene causes a conservative amino acid substitution" +
                        " (S276T). This single nucleotide polymorphism (SNP) seemed to be functional as it was " +
                        "associated with decreased lung cancer risk. Previous studies of our group found " +
                        "association of haplotypes in the IL8 and in the CXCR2 genes with the multifactorial disease" +
                        " chronic periodontitis. In this study we investigated the polymorphism rs2234671 in 395 Brazilian" +
                        " subjects with and without chronic periodontitis. FINDINGS: Similar distribution of the allelic " +
                        "and genotypic frequencies were observed between the groups (p>0.05). CONCLUSIONS: The polymorphism " +
                        "rs2234671 in the CXCR1 gene was not associated with the susceptibility to chronic periodontitis " +
                        "in the studied Brazilian population.", Section.class);
        builder.close();

    }

    @Test
    public void testProcess() throws Exception {
        AnalysisEngine tmVar =
                createEngine(
                        TmVarAnnotator.class,
                        TmVarAnnotator.PARAM_TMVAR_BASE_DIR, "/home/alex/tools/tmVar",
                        TmVarAnnotator.PARAM_TMVAR_BASE_INPUT_DIR, "/home/alex/tools/tmVar/in",
                        TmVarAnnotator.PARAM_TMVAR_BASE_OUTPUT_DIR, "/home/alex/tools/tmVar/out");

        tmVar.process(sampleJCas);

        int count=0;
        for (TmVarMutation taggedMutation : select(sampleJCas, TmVarMutation.class)) {
            assertEquals(expected[count++],taggedMutation.getCoveredText());
        }
    }


}