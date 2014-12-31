package ca.bcgsc.uima.annotators;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import types.MutationExtended;
import types.SethMutation;
import types.TmVarMutation;

import java.util.ArrayList;
import java.util.Collection;

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

@TypeCapability(outputs = {
        "Type.MutationMerge",
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


public class MergerAnnotator extends JCasAnnotator_ImplBase {

//    public static final String PARAM_USE_EXACT_GRAMMAR = "useExactGrammar";
//    @ConfigurationParameter(name = "useExactGrammar")
//    private boolean useExactGrammar;
//
//    public static final String PARAM_USE_OLD_NOMENCLATURE = "useOldNomenclature";
//    @ConfigurationParameter(name = "useOldNomenclature")
//    private boolean useOldNomenclature;
//
//    public static final String PARAM_REGEX_FILE_PATH = "regexFilePath";
//    @ConfigurationParameter(name = "regexFilePath")
//    private String regexFilePath;
//
//    private SETH seth;

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        //seth = new SETH(regexFilePath, useExactGrammar, useOldNomenclature);
    }

    /**
     * @see org.apache.uima.fit.component.JCasAnnotator_ImplBase#process(org.apache.uima.jcas.JCas)
     */


    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
        String docText = aJCas.getDocumentText();

        System.out.println(docText);

        merge(aJCas);

//        int count = 1;
//        boolean flag = true;
//
//        count = 0;
//
//        Mutation sethAn = sethIt.next();
//        Mutation tmVarAn = tmVarIt.next();
//
//        while (sethIt.hasNext() || tmVarIt.hasNext()) {
//
//
//            if (sethAn.getBegin() == tmVarAn.getBegin()) {
//                // Merge Both Annotations into one
//
//                // Then Take two
//            } else if (sethAn.getBegin() > tmVarAn.getBegin()) {
//                // Store the smallest page number tmVar into a new annotation.
//                // PROCESS current tmVar
//                tmVarAn = tmVarIt.next();
//            } else if (sethAn.getBegin() < tmVarAn.getBegin()) {
//                // Store the smallest page number seth into a new annotation.
//                // PROCESS current seth annotation
//                sethAn = sethIt.next();
//            }
//        }


        // java.util.List<MutationMention> result = seth.findMutations(docText);

//        for (MutationMention m : result) {
//            /**
//             System.out.println("----------------------");
//             System.out.println("Span = "+ m.getStart() + " - " + m.getEnd());
//             System.out.println("text = "+ m.getText());
//             System.out.println("Ref = "+ m.getRef());
//             System.out.println("MutResidue = "+ m.getMutResidue());
//             System.out.println("Location = "+ m.getPosition());
//             System.out.println("wtResidue = "+ m.getWtResidue());
//             System.out.println("Tool = "+ m.getTool());
//             System.out.println("HGVS = "+ m.toHGVS());
//             System.out.println("Normalized = "+ m.toNormalized());
//             System.out.println("Is Ambiguous = "+ m.isAmbiguous());
//             System.out.println("Is NSM  = "+ m.isNsm());
//             System.out.println("Is PSM = "+ m.isPsm());
//             **/
//
//            SethMutation annotation = new SethMutation(aJCas, m.getStart(), m.getEnd());
//            annotation.setReferenceSequence(m.getRef());
//            annotation.setMutatedResidue(m.getMutResidue());
//            annotation.setLocationWrtResidueOrNucleotide(m.getPosition());
//            annotation.setWildTypeResidue(m.getWtResidue());
//            annotation.setTool(m.getTool().name());
//            annotation.setNormalized(m.toNormalized());
//            annotation.setHGVS(m.toHGVS());
//            //annotation.setSequenceType(SNPSimpleClassifier(m.isAmbiguous(), m.isNsm(),m.isPsm()));
//            annotation.setIsSnpAmbiguous(m.isAmbiguous());
//            annotation.setIsNucleotideSequenceMutation(m.isNsm());
//            annotation.setIsProteinSequenceMutation(m.isPsm());
//
//
//            annotation.addToIndexes();
//            getContext().getLogger().log(Level.FINEST, "Found: " + annotation);
//
//        }

    }

    public static void merge(JCas aJCas) {

        Collection<SethMutation> sethMut =  select(aJCas, SethMutation.class);
        ArrayList<SethMutation> sethM = new ArrayList<SethMutation>();

        for(SethMutation m:sethMut){
            sethM.add(m);
        }

        Collection<TmVarMutation> tmVarMut = select(aJCas, TmVarMutation.class);
        ArrayList<TmVarMutation> tmVarM = new ArrayList<TmVarMutation>();

        for(TmVarMutation m:tmVarMut){
            tmVarM.add(m);
        }

        ArrayList<MutationExtended> answer = new ArrayList<MutationExtended>();

        int i = 0, j = 0, k = 0;

        while (i < sethM.size() && j < tmVarM.size()) {
            if (sethM.get(i).getBegin() == tmVarM.get(j).getBegin() ) {
                // answer.add(a.get(i++));
                processMutation(aJCas, sethM.get(i++) , tmVarM.get(j++));

                // answer.add(m);

                // Add Mutation Increase i and j

                // j++;

            } else {
                if (sethM.get(i).getBegin() < tmVarM.get(j).getBegin()) {
                    // answer.add(a.get(i++));
                    // Add Mutation
                    // Increase i
                    i++;
                } else {
                    //  answer.add(b.get(j++));
                    // Add Mutation
                    // Increase j
                    j++;
                }
            }
        }
        while (i < sethM.size()) {
            //  answer.add(a.get(i++));
            i++;
        }

        while (j < tmVarM.size()) {
            //   answer.add( b.get(j++));
            j++;
        }

    }




    public enum Sequence {
        DNA, RNA, GENOME, Protein, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }
//
    public static void processMutation(JCas aJCas, SethMutation sm, TmVarMutation tm) {

            String[] summarySplit = tm.getSummary().split("\\|", -1);

            MutationExtended annotation = new MutationExtended(aJCas, sm.getBegin(), sm.getEnd());

            if (tm.getSummary().contains("SUB")) {
                // summarySplit = {SequenceType, ElementaryChange, WildType, MutationPosition, MutatedResidue}


                if(sm.getReferenceSequence()== null){
                    annotation.setReferenceSequence(summarySplit[0]);
                }
                else {
                    annotation.setReferenceSequence(sm.getReferenceSequence());
                }

                if(sm.getReferenceSequence()== null){
                    annotation.setMutatedResidue(summarySplit[4]);
                }
                else {
                    annotation.setMutatedResidue(sm.getMutatedResidue());
                }

                if(sm.getLocationWrtResidueOrNucleotide() == null){
                    annotation.setLocation(summarySplit[3]);
                }
                else {
                    annotation.setLocation(sm.getLocationWrtResidueOrNucleotide());
                }

                if(sm.getWildTypeResidue() == null){
                    annotation.setWildTypeResidue(summarySplit[2]);
                }
                else {
                    annotation.setWildTypeResidue(sm.getWildTypeResidue());
                }

                annotation.setElementaryChange("Substitution");
                annotation.setTool(sm.getTool());
                annotation.setNormalized(sm.getNormalized());
                annotation.setHGVS(sm.getHGVS());
                annotation.setSequenceType(tm.getSequencetype());
                annotation.addToIndexes();
            }
            //
            // else other ElementaryChanges

    }
//
//    public static String extractElementaryChange(TmVarMutation tm){
//        tm.getSummary();
//
//    }
//
//    public static void processMutation(SethMutation sm) {
//    }
//
//    public static void processMutation(TmVarMutation tm) {
//    }
//
//    public static void summaryParser(String summary) {
//        String[] summarySplit = summary.split("\\|", -1);
//
//        if (summary.contains("SUB")) {
//
//
//        }
//
//
//    }

}