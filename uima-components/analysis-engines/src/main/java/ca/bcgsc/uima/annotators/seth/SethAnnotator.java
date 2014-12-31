package ca.bcgsc.uima.annotators.seth;

import de.hu.berlin.wbi.objects.MutationMention;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import seth.SETH;
import types.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SethAnnotator extends JCasAnnotator_ImplBase {
    public static final String PARAM_USE_EXACT_GRAMMAR = "useExactGrammar";
    @ConfigurationParameter(name = "useExactGrammar")
    private boolean useExactGrammar;

    public static final String PARAM_USE_OLD_NOMENCLATURE = "useOldNomenclature";
    @ConfigurationParameter(name = "useOldNomenclature")
    private boolean useOldNomenclature;

	SETH seth;

	@Override
	public void initialize(UimaContext aContext) throws ResourceInitializationException{
		super.initialize(aContext);
		// Load regular expression file.
		URL mutationURL = SethAnnotator.class.getClassLoader().getResource("mutations.txt");
		File mutationFile = new File(mutationURL.getFile());
		// Initialize Seth
		seth = new SETH(mutationFile.getAbsolutePath(), useExactGrammar, useOldNomenclature);
	} 
	
	@Override
	public void process(JCas aJCas)  {
		// Retrieve Document
		String docText = aJCas.getDocumentText();

		ArrayList<TempAnnotation> tempList = miscMutation(docText);
		
//		Note: 
//			The type MutationMention belongs to the Seth Tool
//		
//		Process Document using Seth and store mutation annotations in result
        java.util.List<MutationMention> result = seth.findMutations(docText);
        
        for (TempAnnotation m : tempList) {
        	Mutation annotation;
        	
			if (m.getHGVS().substring(0, 2).equalsIgnoreCase("c.")){
                annotation = new DNAMutation(aJCas, m.getBegin(), m.getEnd());
                annotation.setReferenceSequence("c.");
                
               
            }
            else if(m.getHGVS().substring(0, 2).equalsIgnoreCase("r.")){
                annotation = new RNAMutation(aJCas, m.getBegin(), m.getEnd());
                annotation.setReferenceSequence("r.");
                
            }
            else if(m.getHGVS().substring(0, 2).equalsIgnoreCase("p.")){
                annotation = new ProteinMutation(aJCas, m.getBegin(), m.getEnd());
                annotation.setReferenceSequence("p.");
                
            }
            else if(m.getHGVS().substring(0, 2).equalsIgnoreCase("g.")){
                annotation = new GenomeMutation(aJCas, m.getBegin(), m.getEnd());
                annotation.setReferenceSequence("g.");
                
            }
            else if(m.getHGVS().substring(0, 2).equalsIgnoreCase("m.")){
                annotation = new MitochondrialMutation(aJCas, m.getBegin(), m.getEnd());
                annotation.setReferenceSequence("m.");
               
            }
            else{
            	annotation = new AmbiguousMutation(aJCas, m.getBegin(), m.getEnd());
            	        	
            }
			annotation.setHGVS(m.getHGVS());
			annotation.setText(m.getDesc());
			annotation.addToIndexes();
        }
        
        
        
        
        
//        Note:
//        	The for loop below stores each found mutation as uima annotation of type DBSNP, DNA, RNA, Protein, Mitochondrial, Genome or Ambiguous
        
        for (MutationMention m : result) {
        	// The sequenceIdentifier function determines the type of found mutation (See below for details)
            String seqType = sequenceIdentifier(m);
            Mutation annotation;

            if (m.getType().toString().contains("DBSNP")){
                annotation = new DBSNP(aJCas, m.getStart(), m.getEnd());                 
            }
            else if (seqType.equalsIgnoreCase("c.")){
                annotation = new DNAMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence("c.");
                annotation.setHGVS(m.toHGVS());
            }
            else if(seqType.equalsIgnoreCase("r.")){
                annotation = new RNAMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence("r.");
                annotation.setHGVS(m.toHGVS());
            }
            else if(seqType.equalsIgnoreCase("p.")){
                annotation = new ProteinMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence("p.");
                annotation.setHGVS(m.toHGVS());
            }
            else if(seqType.equalsIgnoreCase("g.")){
                annotation = new GenomeMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence("g.");
                annotation.setHGVS(m.toHGVS());
            }
            else if(seqType.equalsIgnoreCase("m.")){
                annotation = new MitochondrialMutation(aJCas, m.getStart(), m.getEnd());
                annotation.setReferenceSequence("m.");
                annotation.setHGVS(m.toHGVS());
            }            
            else if(tempList.size()>0){
            	Boolean exit = false;
            	TempAnnotation miscMute = new TempAnnotation();
            	
            	System.out.println(tempList.size());
            	Iterator<TempAnnotation> MiscMutationIterator = tempList.iterator();
            	           	
            	while (MiscMutationIterator.hasNext() && exit == false ) {  
            		miscMute = MiscMutationIterator.next();
            		
            		if(true == mutationCompare(m, miscMute)){
            			exit = true;            			           			
            		}            		
            	}           	               	
    			if (miscMute.getHGVS().substring(0, 2).equalsIgnoreCase("c.")){
                    annotation = new DNAMutation(aJCas, m.getStart(), m.getEnd());
                    annotation.setReferenceSequence("c.");
                    annotation.setHGVS(m.toHGVS().replace("?.","c."));
                } else if(miscMute.getHGVS().substring(0, 2).equalsIgnoreCase("r.")){
                    annotation = new RNAMutation(aJCas, m.getStart(), m.getEnd());
                    annotation.setReferenceSequence("r.");
                    annotation.setHGVS(m.toHGVS().replace("?.","r."));
                } else if(miscMute.getHGVS().substring(0, 2).equalsIgnoreCase("p.")){
                    annotation = new ProteinMutation(aJCas, m.getStart(), m.getEnd());
                    annotation.setReferenceSequence("p.");
                    annotation.setHGVS(m.toHGVS().replace("?.","p."));
                } else if(miscMute.getHGVS().substring(0, 2).equalsIgnoreCase("g.")){
                    annotation = new GenomeMutation(aJCas, m.getStart(), m.getEnd());
                    annotation.setReferenceSequence("g.");
                    annotation.setHGVS(m.toHGVS().replace("?.","g."));
                } else if(miscMute.getHGVS().substring(0, 2).equalsIgnoreCase("m.")){
                    annotation = new MitochondrialMutation(aJCas, m.getStart(), m.getEnd());
                    annotation.setReferenceSequence("m.");
                    annotation.setHGVS(m.toHGVS().replace("?.","m."));
                } else{
                	annotation = new AmbiguousMutation(aJCas, m.getStart(), m.getEnd());
                	annotation.setHGVS(m.toHGVS());
            	}               
            } else{
            	annotation = new AmbiguousMutation(aJCas, m.getStart(), m.getEnd());
            	annotation.setHGVS(m.toHGVS());
            }
            annotation.setReferenceSequence(m.getRef());
            annotation.setMutatedResidue(m.getMutResidue());
            annotation.setLocation(m.getPosition());
            annotation.setWildTypeResidue(m.getWtResidue());
            annotation.setTool(m.getTool().name());
            annotation.setNormalized(m.toNormalized());
            annotation.setElementaryChange(m.getType().name());
            annotation.setSequenceType(ReferanceSequenceMapper(seqType));
            annotation.addToIndexes();         
            }        
	}


//	Note:
//		The following function determines the sequence type as follows:
//			1) Check the generated HGVS, if  a sequence type can be identified, label it accordingly
//			2) Else Check: the boolean features values generated by Seth, if they are not contradictory, label them accordingly
//			3) Else label as unkown
	
    public static String sequenceIdentifier(MutationMention m){
        String HGVS;
        String referenceSeq = m.getRef();
        Boolean Nsm = m.isNsm();
        Boolean Psm = m.isPsm();
        Boolean Amb = m.isAmbiguous();

        if (m.toHGVS().substring(0, 2).equalsIgnoreCase("c.")){
            HGVS = "c.";
        }
        else if(m.toHGVS().substring(0,2).equalsIgnoreCase("r.")){
            HGVS =  "r.";
        }
        else if(m.toHGVS().substring(0,2).equalsIgnoreCase("p.")){
            HGVS =  "p.";
        }
        else if(m.toHGVS().substring(0,2).equalsIgnoreCase("g.")){
            HGVS =  "g.";
        }
        else if(m.toHGVS().substring(0,2).equalsIgnoreCase("m.")){
            HGVS =  "m.";
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
                return "c.";
            } else if (Nsm == false && Psm == true && Amb == false) {
                return "p.";
            } else {
                return "Unknown";
            }
        }
    }

//Give, c., p., g., m. or r; the input will be mapped to one of DNA, RNA, Protein, Genome or Mitochondrial    
    
    public static String ReferanceSequenceMapper(String m){
        String Type;
        
        if (m.equalsIgnoreCase("c.")){
            Type = "DNA";
        }
        else if(m.equalsIgnoreCase("r.")){
            Type =  "RNA";
        }
        else if(m.equalsIgnoreCase("p.")){
            Type =  "Protein";
        }
        else if(m.equalsIgnoreCase("g.")){
            Type =  "Genome";
        }
        else if(m.equalsIgnoreCase("m.")){
            Type =  "Mitochondrial";
        }
        else {
            Type =  "Unknown";
        }
        return Type;
    }    
      
// The following function finds additional mutations (represented with HGVS nomenclature) not found with Seth.   
    
    public static ArrayList<TempAnnotation> miscMutation(String input){
    	// Two variants in one allele
    	String REGEX = "[cg]\\.\\[([^\\(]*?);([^\\)]*?)\\]";
    	Pattern p = Pattern.compile(REGEX);
    	Matcher m = p.matcher(input);
 

    	ArrayList<TempAnnotation> tempList = new ArrayList<TempAnnotation>();
    	
        while(m.find()) {
        	TempAnnotation t = new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Two variants in one allele",m.pattern().toString());
        	tempList.add(t);
        }

        // Two variants in one individual with alleles unknown
       	REGEX = "[cg]\\.\\[[\\sa-zA-Z0-9><-_]*\\(;\\)[\\sa-zA-Z0-9><-_]*\\]";
    	p = Pattern.compile(REGEX);
    	m = p.matcher(input);
    	       
        while(m.find()) {
        	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Two variants in one individual with alleles unknown",m.pattern().toString());	        	
        	tempList.add(t);
        }
        
        
        REGEX = "[cg]\\.\\[(\\d+)[ACGT]>[ACGT]\\];\\[(\\d+)[ACGT]>[ACGT]\\]";
    	p = Pattern.compile(REGEX);
    	m = p.matcher(input);
        
        while(m.find()) {
        	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Recessive disease - (changes in different alleles, both variants identified)",m.pattern().toString());	        	
        	tempList.add(t);
        }

        REGEX = "[cg]\\.\\[(\\d+)[ACGT]>[ACGT]\\];\\[\\?\\]";
    	p = Pattern.compile(REGEX);
    	m = p.matcher(input);
        
        while(m.find()) {
        	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Recessive disease - (changes in different alleles, one variant not yet identified)",m.pattern().toString());	        	
        	tempList.add(t);
        }

        REGEX = "[cg]\\.\\[(\\d+)[ACGT]>[ACGT]\\];\\[\\((\\d+)[ACGT]>[ACGT]\\)\\]";
    	p = Pattern.compile(REGEX);
    	m = p.matcher(input);
        
        while(m.find()) {
        	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Recessive disease - (changes in different alleles, probably homozygous varient)",m.pattern().toString());	        	
        	tempList.add(t);
        }
        
        REGEX = "[cg]\\.\\[(\\d+)[ACGT]>[ACGT]\\];\\[\\=\\]";
    	p = Pattern.compile(REGEX);
    	m = p.matcher(input);
        
        while(m.find()) {
        	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Recessive disease - (changes in different alleles, one variant  alle and one normal allele)",m.pattern().toString());	        	
        	tempList.add(t);
        }
        
        REGEX = "[cg]\\.\\[(\\d+)[ACGTacgt]>[ACGTacgt];(\\d+)[ACGTacgt]>[ACGTacgt];(\\d+)[ACGTacgt]>[ACGTacgt]\\];[cg]\\.\\[(\\d+)[ACGTacgt]>[ACGTacgt];\\=;(\\d+)[ACGTacgt]>[ACGTacgt]\\]";
    	p = Pattern.compile(REGEX);
    	m = p.matcher(input);
        
        while(m.find()) {
        	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Recessive disease - (changes in different alleles, one variant not yet identified)",m.pattern().toString());	        	
        	tempList.add(t);
        }

        REGEX = "[cg]\\.(\\(\\=\\/\\)|\\=\\/)(\\d+)[ACGTacgt]>[ACGTacgt]";
    	p = Pattern.compile(REGEX);
    	m = p.matcher(input);
        
        while(m.find()) {
        	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Mosaicism",m.pattern().toString());	        	
        	tempList.add(t);
        }
        REGEX = "[cg]\\.\\=\\/\\/(\\d+)[ACGTacgt]>[ACGTacgt]";
    	p = Pattern.compile(REGEX);
    	m = p.matcher(input);
        
        while(m.find()) {
        	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Chimerism",m.pattern().toString());	        	
        	tempList.add(t);
        }
        
        REGEX = "[cg]\\.[\\d\\+]+([ACGTacgt]+)\\[(\\d)\\];\\[(\\d)\\]";
    	p = Pattern.compile(REGEX);
    	m = p.matcher(input);
        
        while(m.find()) {
        	String note = "One allele containing " + m.group(1) + " di-nucleotide repeat of length " + m.group(2)+", the other allele containing a repeat of length "+m.group(3);
        	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),note  ,m.pattern().toString());	        	
        	tempList.add(t);
        }
        
        REGEX = "r\\.\\[[=\\s]+,[\\s\\d_ins\\+]+;[\\s\\d\\+ACGTacgt]+>[ACGTacgt]+\\]|[r\\.\\[\\dACGTacgt]+>[ACGTacgt];[\\s\\d_ins\\+]+\\]|[r\\.\\[\\dACGTacgt]+>[ACGTacgt],[\\d_del]+\\]|[r]\\.\\[\\=,[\\d_del]+\\]";
  		p = Pattern.compile(REGEX);
  		m = p.matcher(input);
      
  		while(m.find()) {
      	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"One change, more RNA molecules",m.pattern().toString());	        	
      	tempList.add(t);
  		}
  		
  		REGEX = "[r]\\.[\\s-][\\d-+]+_[\\d-+]+[delins\\s]+(([\\d-+]+_[\\d-+]+)|([ABC\\d.]+:[rg][\\.\\s]+[\\d-+]+_[\\d-+]+))";
  		p = Pattern.compile(REGEX);
  		m = p.matcher(input);
      
  		while(m.find()) {
      	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Large Deletion - promoter deletion",m.pattern().toString());	        	
      	tempList.add(t);
  		}        

  		REGEX = "[r]\\.[\\s]*[\\d]+[+-]*[\\d]*_[\\d]+[delinso]+([\\d]+[+-]*[\\d]+_[\\d]+[+-]*[\\d]*|[AB]+[\\d.]+:[rg\\s]+\\.[\\s]*[\\d]+[+-]*[\\d]*_[\\d]+[-+]*[\\d]*)";
  		p = Pattern.compile(REGEX);
  		m = p.matcher(input);
      
  		while(m.find()) {
  			TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Large Deletion - deletion 3'",m.pattern().toString());	        	
  			tempList.add(t);
  		}
//      REGEX = "";
//  	p = Pattern.compile(REGEX);
//  	m = p.matcher(input);
//      
//      while(m.find()) {
//      	TempAnnotation t =  new TempAnnotation(m.start(),m.end(),m.group(), m.group().substring(0,2),"Recessive disease - (changes in different alleles, one variant not yet identified)",m.pattern().toString());	        	
//      	tempList.add(t);
//      }        
        
    	return tempList;
    }
    
// The following function compares mutation extracted with Seth  vs Mutation extracted using additional regex
    
    public static boolean mutationCompare(MutationMention mutation, TempAnnotation MiscMutation){
    	if(
    			MiscMutation.getBegin() <= mutation.getStart() && 
    			MiscMutation.getEnd() >= mutation.getEnd() && 
    			MiscMutation.getHGVS().contains(mutation.getText())){   		
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    	

    	

    
}
    

