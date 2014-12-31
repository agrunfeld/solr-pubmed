package ca.bcgsc.uima.annotators.tmvar;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import types.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;



public class TmVarAnnotator extends JCasAnnotator_ImplBase {
//    public static final String PARAM_TMVAR_BASE_DIR = "tmVarBaseDir";
//
//    @ConfigurationParameter(name = PARAM_TMVAR_BASE_DIR)
//    private String tmVarBaseDir;

    String BASE_INPUT_DIR;
    String BASE_OUTPUT_DIR;
    String BASE_DIR;

    @Override
    public void initialize(UimaContext aContext) throws ResourceInitializationException{
        super.initialize(aContext);
        Properties properties = new Properties();


        URL configURL = TmVarAnnotator.class.getClassLoader().getResource("annotator.properties");
        File configFile = new File(configURL.getFile());

        try{
            properties.load(new FileInputStream(configFile));

            // properties.load(new FileInputStream("resources/config.properties"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BASE_INPUT_DIR = properties.getProperty("in");
        BASE_OUTPUT_DIR = properties.getProperty("out");
        BASE_DIR = properties.getProperty("base");
    }

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException  {
        // Retrieve Document
        String text = aJCas.getDocumentText();

        String sectionText = text;
        String id = "12345678";
        // Create a String which turns the text into the pubtator format.
        String temp = id + "|" + "A" + "|" + sectionText + "\n" + "\n";
        // Next we write this String to a Pubtator document.
        try {
            FileWriter writer = new FileWriter(BASE_INPUT_DIR + File.separator + "TmVarForm" + ".PubTator.txt");
            writer.write(temp);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            tmVarWrapper(aJCas);
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        } catch (InterruptedException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }



    public void tmVarWrapper(JCas aJCas) throws IOException, InterruptedException
    {
        // The Pubtator document will be processed using the command line
        // The results will be written to another document. This result document is parsed to retrieve the
        // corresponding annotations.
        String in = BASE_INPUT_DIR;
        String out = BASE_OUTPUT_DIR;

        String[] command = {"perl", "tmVar.pl", "-i", in,"-o",out,"-s","setup.txt", "-f","PubTator"};
        ProcessBuilder p= new ProcessBuilder( command );

        p.directory(new File(BASE_DIR));


        Process process = p.start();

        int exitValue = process.waitFor();

        // Extract Mutation From Text

        ArrayList<Annotation> mutationList = new ArrayList<Annotation>();

        // Mutations are extracted from the results document and stored as uima annotations.


        String files_folder = BASE_OUTPUT_DIR + File.separator + "TmVarForm.PubTator.txt.PubTator";
        File fileName = new File(files_folder);


        FileReader fileReader = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fileReader);


        String id = "12345678";

        String line = null;
        int lineCount = 0;

        // result[0] : Doc Id
        // result[1] : start offset
        // result[2] : end offset
        // result[3] : Covered Text
        // result[4] : Sequence Type
        // result[5] : Summary
        while ((line = reader.readLine()) != null) {
            if (line.contains(id + "\t")) {
                String[] result = line.split("\t");

                int begining = Integer.parseInt(result[1]);
                int ending = Integer.parseInt(result[2]);
                Mutation annotation;

                if (result[4].contains("SNP")){
                    annotation = new DBSNP(aJCas, begining, ending);
                }
                else if(result[3].contains("r.")){
                    annotation = new RNAMutation(aJCas, begining, ending);
                    annotation.setSequenceType("r.");

                }
                else if(result[3].contains("p.") || result[4].contains("Protein") ){
                    annotation = new ProteinMutation(aJCas, begining, ending);
                    annotation.setReferenceSequence("p.");
                }
                else if(result[3].contains("g.")){
                    annotation = new GenomeMutation(aJCas, begining, ending);
                    annotation.setReferenceSequence("g.");
                }
                else if(result[3].contains("m.")){
                    annotation = new MitochondrialMutation(aJCas,begining, ending);
                    annotation.setReferenceSequence("m.");
                }
                else if (result[3].contains("c.") || result[4].contains("DNA")){
                    annotation = new DNAMutation(aJCas, begining, ending);
                    annotation.setReferenceSequence("c.");
                }
                else{
                    annotation = new AmbiguousMutation(aJCas, begining, ending);
                }

                annotation.setText(result[3]);
                annotation.setNormalized(result[5]);
                annotation.setTool("TmVar");
                annotation.setElementaryChange(extractElementaryChange(result[5]));
                annotation.addToIndexes();
            }
        }
        reader.close();
    }

    public String extractElementaryChange(String summary){
        String change;
        if(summary.contains("SUB")){
            change = "Substitution";
        }
        else if(summary.contains("INS")){
            change = "Insertion";
        }
        else if(summary.contains("DEL")){
            change = "Deletion";
        }
        else if(summary.contains("INDEL")){
            change = "Insertion+Deletion";
        }
        else if(summary.contains("DUP")){
            change = "Duplication";
        }
        else if(summary.contains("FS")){
            change = "Frame Shift";
        }
        else {
            change = "?";
        }
        return change;
    }

}


