package annotator; /**
 * Created by alex on 15/10/14.
 */

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasBuilder;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.apache.uima.jcas.JCas;
import org.junit.Before;
import org.junit.Test;
import types.Section;
import types.SethMutation;
import types.TmVarMutation;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;

public class AnnotationMerge {
    public static final String TYPE_SYSTEM = "/TypeSystem.xml";
    public static final String REGEX_FILE = "/mutations.txt";
    private JCas sampleJCas;
    private File typeSystem;
    private File regexFile;

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
        File typeSystem = new File(url.getFile());

        url = this.getClass().getResource(REGEX_FILE);
        regexFile = new File(url.getFile());


        sampleJCas =  JCasFactory.createJCas(TypeSystemDescriptionFactory.createTypeSystemDescriptionFromPath(typeSystem.getAbsolutePath()));

        JCasBuilder builder = new JCasBuilder(sampleJCas);
        builder.add(
                //"Causative GJB2 mutations were identified in 31 (15.2%) patients, " +
                //        "and two common mutations, c.35delG and L90P (c.269T>C), accounted " +
                //        "for 72.1% and 9.8% of GJB2 disease alleles. \n \n \n",

                "", Section.class);
        builder.add(
               // "Subs:  c.-7A>C , c.3G>T, c.88+2T>G, c.89-1G>T, c.*23T>C , NM_004006.1:c.3G>T, GJB6:c.3G>T, rs2306220:A>G" +
                  //      ", r.67g>u, p.Trp26Cys, (p.W26C), p.Trp26*, (p.W26*)" //+
                "Ins: c.51_52insT, c.51_52insGAGA, r.51_52insu, r.51_52insgaga, p.Lys2_Leu3insGlnSer, (p.K2_L3insQS), c.76_77insT, c.123+54_123+55insAB012345.2:g.76_420"
                , Section.class);
        builder.close();

    }

    // @Ignore("need to package tmVar")

    @Test
    public void testProcess() throws Exception {

        Properties analysisProperties = new Properties();
        FileInputStream in = new FileInputStream("analysis/analysis.properties");
        analysisProperties.load(in);
        in.close();

        AnalysisEngine tmVar =
                createEngine(
                        TmVarAnnotator.class,
                        TmVarAnnotator.PARAM_TMVAR_BASE_DIR, analysisProperties.getProperty("tmVarDirectory"));

        tmVar.process(sampleJCas);


        AnalysisEngine seth =
                AnalysisEngineFactory.createEngine(
                        SethAnnotator.class,
                        SethAnnotator.PARAM_REGEX_FILE_PATH, regexFile.getAbsolutePath(),
                        SethAnnotator.PARAM_USE_EXACT_GRAMMAR, false,
                        SethAnnotator.PARAM_USE_OLD_NOMENCLATURE, false);

        seth.process(sampleJCas);

        int count=0;
        for (TmVarMutation taggedMutation : select(sampleJCas, TmVarMutation.class)) {
           ///System.out.print(taggedMutation.getCoveredText() + ": :" + taggedMutation.getCoveredText()+"\n");
           System.out.print(taggedMutation.toString());

        }
//
        System.out.print("\n");
//        count=0;
        for (SethMutation taggedMutation : select(sampleJCas, SethMutation.class)) {
            // System.out.print(taggedMutation.getCoveredText() + ": :" + taggedMutation.getCoveredText()+"\n");
            System.out.print(taggedMutation.toString());
        }


        System.out.print("\n \n \n \n");

        //summaryParser("c|DEL|35|G");
  //      summaryParser("|DEL|||");


        AnalysisEngine merge =
                AnalysisEngineFactory.createEngine(
                        MergerAnnotator.class);

        merge.process(sampleJCas);

//        for (MutationMerged taggedMutation : select(sampleJCas, MutationMerged.class)) {
//            System.out.print(taggedMutation.toString());
//            System.out.print("   " + taggedMutation.getCoveredText()+"\n");
//        }
    }



    public static void summaryParser(String summary){
        String [] summarySplit = summary.split("\\|", -1);

        if(summary.contains("SUB")){


        }
        if(summary.contains("DEL")){
            //taco = summary.split("\\|");
            //System.out.println(taco.toString());
           // System.out.println(taco.length);
            for(String t:summarySplit){
            System.out.println(t);
            }
            System.out.println(summarySplit.length);
        }



    }


}
