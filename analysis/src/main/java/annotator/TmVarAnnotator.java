package annotator;


import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import types.Section;
import types.TmVarMutation;

import java.io.*;
import java.util.ArrayList;

import static org.apache.uima.fit.util.JCasUtil.select;

/**
 * This class demonstrates annotating member variables with the @ConfigurationParameter annotation.
 * Defining configuration parameters in this way in combination with using the
 * {@link org.apache.uima.fit.component.JCasAnnotator_ImplBase uimaFIT's JCasAnnotator_ImplBase} class obviates the need for an
 * initialize method at all because the super class initialize method calls
 * {@link org.apache.uima.fit.component.initialize.ConfigurationParameterInitializer#initialize}. This method
 * initializes member variables annotated as configuration parameters using the configuration
 * parameter information provided in the UimaContext.
 * <p/>
 * This class was copied from the uimaj-examples project and modified in following ways:
 * <ul>
 * <li>The package name was changed to org.apache.uima.fit.tutorial.ex2</li>
 * <li>The super class was changed to org.apache.uima.fit.component.JCasAnnotator_ImplBase</li>
 * <li>The class is annotated with org.apache.uima.fit.descriptor.TypeCapability</li>
 * <li>mPatterns and mLocations is annotated with @ConfigurationParameters</li>
 * <li>the initialize method was removed</li>
 * </ul>
 */

@TypeCapability(inputs = {"NER.Section"}, outputs = {
        "NER.Mutation",
        "Mutation:text",
        "Mutation:referenceSequence",
        "Mutation:wildTypeResidue",
        "Mutation:mutatedResidue",
        "Mutation:locationWrtResidueOrNucleotide",
        "Mutation:normalized",
        "Mutation:tool",
        "Mutation:HGVS",
        "Mutation:isNucleotideSequenceMutation",
        "Mutation:isProteinSequenceMutation",
        "Mutation:isSnpAmbiguous"})

//todo: generate temp directory?
public class TmVarAnnotator extends JCasAnnotator_ImplBase {
    public static final String PARAM_TMVAR_BASE_DIR = "tmVarBaseDir";
    @ConfigurationParameter(name = PARAM_TMVAR_BASE_DIR)
    private String tmVarBaseDir;

    public static final String PARAM_TMVAR_BASE_INPUT_DIR = "tmVarBaseInputDir";
    @ConfigurationParameter(name = PARAM_TMVAR_BASE_INPUT_DIR)
    private String tmVarBaseInputDir;

    public static final String PARAM_TMVAR_BASE_OUTPUT_DIR = "tmVarBaseOutputDir";
    @ConfigurationParameter(name = PARAM_TMVAR_BASE_OUTPUT_DIR)
    private String tmVarBaseOutputDir;


    /**
     * @see org.apache.uima.fit.component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
     */

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {

        String text = aJCas.getDocumentText();

        for (Section part : select(aJCas, Section.class)) {
            // get span of text within 50 chars on either side of meeting
            // (window size should probably be a configuration parameter)
            int begin = part.getBegin();
            int end = part.getEnd();

            String sectionText = text.substring(begin, end);
            String id = "12345678";

            String temp = id + "|" + "A" + "|" + sectionText + "\n" + "\n";


            try {
                FileWriter writer = new FileWriter(tmVarBaseInputDir + File.separator + "TmVarForm" + ".PubTator.txt");
                writer.write(temp);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            tmVarWrapper(aJCas, part);
        }
    }


    public void tmVarWrapper(JCas aJCas, Section passage) {
        String in = tmVarBaseInputDir;
        String out = tmVarBaseOutputDir;

        String[] command = {"perl", "tmVar.pl", "-i", in, "-o", out, "-s", "setup.txt", "-f", "PubTator"};
        ProcessBuilder p = new ProcessBuilder(command);

        p.directory(new File(tmVarBaseDir));

        try {
            Process process = p.start();
            try {
                int exitValue = process.waitFor();

                // System.out.println("\n\nExit Value is " + exitValue);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Extract Mutation From Text

        ArrayList<TmVarMutation> mutationList = new ArrayList<>();

        try {

            String files_folder = tmVarBaseOutputDir + File.separator + "TmVarForm.PubTator.txt.PubTator";
            File fileName = new File(files_folder);


            FileReader fileReader = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fileReader);

            // System.out.println(file.getName());
            String id = "12345678";

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains(id + "\t")) {
                    String[] result = line.split("\t");

                    int begining = Integer.parseInt(result[1]) + passage.getBegin();
                    int ending = Integer.parseInt(result[2]) + passage.getBegin();

                    TmVarMutation mutation = new TmVarMutation(aJCas, begining, ending);

                    mutation.setText(result[3]);
                    mutation.setSequencetype(result[4]);
                    mutation.setSummary(result[5]);

                    mutationList.add(mutation);
                }
            }
            reader.close();

            for (TmVarMutation mutation : mutationList) {
                mutation.addToIndexes();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
