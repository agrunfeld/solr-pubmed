package annotator;


import de.hu.berlin.wbi.objects.MutationMention;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import seth.SETH;
import types.*;

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

@TypeCapability(outputs = {
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


public class SethAnnotator extends JCasAnnotator_ImplBase {

    public static final String PARAM_USE_EXACT_GRAMMAR = "useExactGrammar";
    @ConfigurationParameter(name = "useExactGrammar")
    private boolean useExactGrammar;

    public static final String PARAM_USE_OLD_NOMENCLATURE = "useOldNomenclature";
    @ConfigurationParameter(name = "useOldNomenclature")
    private boolean useOldNomenclature;

    public static final String PARAM_REGEX_FILE_PATH = "regexFilePath";
    @ConfigurationParameter(name = "regexFilePath")
    private String regexFilePath;

    private SETH seth;

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        seth = new SETH(regexFilePath, useExactGrammar, useOldNomenclature);
    }

    /**
     * @see org.apache.uima.fit.component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
     */
    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        String docText = aJCas.getDocumentText();


        java.util.List<MutationMention> result = seth.findMutations(docText);

        for (MutationMention m : result) {
            /**
             System.out.println("----------------------");
             System.out.println("Span = "+ m.getStart() + " - " + m.getEnd());
             System.out.println("text = "+ m.getText());
             System.out.println("Ref = "+ m.getRef());
             System.out.println("MutResidue = "+ m.getMutResidue());
             System.out.println("Location = "+ m.getPosition());
             System.out.println("wtResidue = "+ m.getWtResidue());
             System.out.println("Tool = "+ m.getTool());
             System.out.println("HGVS = "+ m.toHGVS());
             System.out.println("Normalized = "+ m.toNormalized());
             System.out.println("Is Ambiguous = "+ m.isAmbiguous());
             System.out.println("Is NSM  = "+ m.isNsm());
             System.out.println("Is PSM = "+ m.isPsm());
             **/

            String seqType = SequenceIdentifier(m);

            if (m.getType().toString().contains("DBSNP")){

                DBSNP annotation = new DBSNP(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence(m.getRef());
                annotation.setMutatedResidue(m.getMutResidue());
                annotation.setLocation(m.getPosition());
                annotation.setWildTypeResidue(m.getWtResidue());
                annotation.setTool(m.getTool().name());
                annotation.setNormalized(m.toNormalized());
                annotation.setHGVS(m.toHGVS());
                annotation.setElementaryChange(m.getType().name());
                annotation.addToIndexes();
                getContext().getLogger().log(Level.FINEST, "Found: " + annotation);

            }
            else if (seqType.equalsIgnoreCase("DNA")){

                DNAMutation annotation = new DNAMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence(m.getRef());
                annotation.setMutatedResidue(m.getMutResidue());
                annotation.setLocation(m.getPosition());
                annotation.setWildTypeResidue(m.getWtResidue());
                annotation.setTool(m.getTool().name());
                annotation.setNormalized(m.toNormalized());
                annotation.setHGVS(m.toHGVS());
                annotation.setElementaryChange(m.getType().name());
                 annotation.addToIndexes();
                getContext().getLogger().log(Level.FINEST, "Found: " + annotation);

            }
            else if(seqType.equalsIgnoreCase("RNA")){
                RNAMutation annotation = new RNAMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence(m.getRef());
                annotation.setMutatedResidue(m.getMutResidue());
                annotation.setLocation(m.getPosition());
                annotation.setWildTypeResidue(m.getWtResidue());
                annotation.setTool(m.getTool().name());
                annotation.setNormalized(m.toNormalized());
                annotation.setHGVS(m.toHGVS());
                annotation.setElementaryChange(m.getType().name());
                annotation.addToIndexes();
                getContext().getLogger().log(Level.FINEST, "Found: " + annotation);
            }
            else if(seqType.equalsIgnoreCase("Protein")){
                ProteinMutation annotation = new ProteinMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence(m.getRef());
                annotation.setMutatedResidue(m.getMutResidue());
                annotation.setLocation(m.getPosition());
                annotation.setWildTypeResidue(m.getWtResidue());
                annotation.setTool(m.getTool().name());
                annotation.setNormalized(m.toNormalized());
                annotation.setHGVS(m.toHGVS());
                annotation.setElementaryChange(m.getType().name());
                annotation.addToIndexes();
                getContext().getLogger().log(Level.FINEST, "Found: " + annotation);
            }
            else if(seqType.equalsIgnoreCase("Genome")){
                GenomeMutation annotation = new GenomeMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence(m.getRef());
                annotation.setMutatedResidue(m.getMutResidue());
                annotation.setLocation(m.getPosition());
                annotation.setWildTypeResidue(m.getWtResidue());
                annotation.setTool(m.getTool().name());
                annotation.setNormalized(m.toNormalized());
                annotation.setHGVS(m.toHGVS());
                annotation.setElementaryChange(m.getType().name());
                annotation.addToIndexes();
                getContext().getLogger().log(Level.FINEST, "Found: " + annotation);
            }
            else if(seqType.equalsIgnoreCase("Mitochondrial")){
                MitochondrialMutation annotation = new MitochondrialMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence(m.getRef());
                annotation.setMutatedResidue(m.getMutResidue());
                annotation.setLocation(m.getPosition());
                annotation.setWildTypeResidue(m.getWtResidue());
                annotation.setTool(m.getTool().name());
                annotation.setNormalized(m.toNormalized());
                annotation.setHGVS(m.toHGVS());
                annotation.setElementaryChange(m.getType().name());
                annotation.addToIndexes();
                getContext().getLogger().log(Level.FINEST, "Found: " + annotation);
            }
            else {


                AmbiguousMutation annotation = new AmbiguousMutation(aJCas, m.getStart(), m.getEnd());
            }
//            else {
//                MitochondrialMutation annotation = new MitochondrialMutation(aJCas, m.getStart(), m.getEnd());
//               // annotation.setReferenceSequence(m.getRef());
////                annotation.setMutatedResidue(m.getMutResidue());
////                annotation.setLocation(m.getPosition());
////                annotation.setWildTypeResidue(m.getWtResidue());
////                annotation.setTool(m.getTool().name());
////                annotation.setNormalized(m.toNormalized());
////                annotation.setHGVS(m.toHGVS());
////                annotation.setElementaryChange(m.getType().name());
////                annotation.addToIndexes();
////                getContext().getLogger().log(Level.FINEST, "Found: " + annotation);
//            }

        }

    }

    public static String HGVSSequenceTypeIdentifier(MutationMention mute){

        if (mute.toHGVS().substring(0, 2).equalsIgnoreCase("c.")){
            return "DNA";
        }
        else if(mute.toHGVS().substring(0,2).equalsIgnoreCase("r.")){
            return "RNA";
        }
        else if(mute.toHGVS().substring(0,2).equalsIgnoreCase("p.")){
            return "Protein";
        }
        else if(mute.toHGVS().substring(0,2).equalsIgnoreCase("g.")){
            return "Genome";
        }
        else if(mute.toHGVS().substring(0,2).equalsIgnoreCase("m.")){
            return "Mitochondrial";
        }
        else {
            return "Unknown";
        }



    }

    public static String SequenceIdentifier(MutationMention m){
        String HGVS;
        String referenceSeq = m.getRef();
        Boolean Nsm = m.isNsm();
        Boolean Psm = m.isPsm();
        Boolean Amb = m.isAmbiguous();

        if (m.toHGVS().substring(0, 2).equalsIgnoreCase("c.")){
            HGVS = "DNA";
        }
        else if(m.toHGVS().substring(0,2).equalsIgnoreCase("r.")){
            HGVS =  "RNA";
        }
        else if(m.toHGVS().substring(0,2).equalsIgnoreCase("p.")){
            HGVS =  "Protein";
        }
        else if(m.toHGVS().substring(0,2).equalsIgnoreCase("g.")){
            HGVS =  "Genome";
        }
        else if(m.toHGVS().substring(0,2).equalsIgnoreCase("m.")){
            HGVS =  "Mitochondrial";
        }
        else {
            HGVS =  "Unknown";
        }


        if(!(HGVS.equalsIgnoreCase("Unknown"))){
            return HGVS;
        }
        else
        {
            if (Nsm == true && Psm == false && Amb == false) {
                return "DNA";
            } else if (Nsm == false && Psm == true && Amb == false) {
                return "Protein";
            } else {
                return "Unknown";
            }
        }
    }
}
