package ca.bcgsc.uima.annotators.seth;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasBuilder;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;
import types.Mutation;

import java.net.URI;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;


public class SethAnnotatorTest {
    private static final String TYPE_SYSTEM = "TypeSystem.xml";
    public static final String REGEX_FILE = "/mutations.txt";
    private JCas sampleJCas;

    final private String[] expected = {
            "c.35delG",
            "L90P",
            "c.269T>C",
            "rs2234671",
            "S276T",
            "rs2234671",
            "rs2234671"};

    @Before
    public void setUp() throws Exception {
        URI typeSystem = this.getClass().getClassLoader().getResource(TYPE_SYSTEM).toURI();

        sampleJCas =  JCasFactory.createJCas(TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(typeSystem.toString()));

        String text = "Causative GJB2 mutations were identified in 31 (15.2%) patients, " +
                "and two common mutations, c.35delG and L90P (c.269T>C), accounted " +
                "for 72.1% and 9.8% of GJB2 disease alleles." +
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
                "in the studied Brazilian population.";

        JCasBuilder builder = new JCasBuilder(sampleJCas);
        builder.add(text);
        builder.close();
    }


    @Test
	public void test() throws Exception {

        AnalysisEngine seth =
                AnalysisEngineFactory.createEngine(
                        SethAnnotator.class,
                        SethAnnotator.PARAM_USE_EXACT_GRAMMAR, false,
                        SethAnnotator.PARAM_USE_OLD_NOMENCLATURE, false);

        seth.process(sampleJCas);

    	int count=0; 
        FSIndex timeIndex = sampleJCas.getAnnotationIndex(Mutation.type);
        Iterator timeIter = timeIndex.iterator();   
        while (timeIter.hasNext()) {
          Mutation time = (Mutation)timeIter.next();
          assertEquals(expected[count++],time.getCoveredText());
          System.out.println(time.getCoveredText());          
        }
	}
}
