
/* First created by JCasGen Tue Dec 30 15:14:08 PST 2014 */
package types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** 
 * Updated by JCasGen Tue Dec 30 15:14:08 PST 2014
 * @generated */
public class SethMutation_Type extends Mutation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SethMutation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SethMutation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SethMutation(addr, SethMutation_Type.this);
  			   SethMutation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SethMutation(addr, SethMutation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SethMutation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("types.SethMutation");
 
  /** @generated */
  final Feature casFeat_referenceSequence;
  /** @generated */
  final int     casFeatCode_referenceSequence;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getReferenceSequence(int addr) {
        if (featOkTst && casFeat_referenceSequence == null)
      jcas.throwFeatMissing("referenceSequence", "types.SethMutation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_referenceSequence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setReferenceSequence(int addr, String v) {
        if (featOkTst && casFeat_referenceSequence == null)
      jcas.throwFeatMissing("referenceSequence", "types.SethMutation");
    ll_cas.ll_setStringValue(addr, casFeatCode_referenceSequence, v);}
    
  
 
  /** @generated */
  final Feature casFeat_wildTypeResidue;
  /** @generated */
  final int     casFeatCode_wildTypeResidue;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getWildTypeResidue(int addr) {
        if (featOkTst && casFeat_wildTypeResidue == null)
      jcas.throwFeatMissing("wildTypeResidue", "types.SethMutation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_wildTypeResidue);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setWildTypeResidue(int addr, String v) {
        if (featOkTst && casFeat_wildTypeResidue == null)
      jcas.throwFeatMissing("wildTypeResidue", "types.SethMutation");
    ll_cas.ll_setStringValue(addr, casFeatCode_wildTypeResidue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_mutatedResidue;
  /** @generated */
  final int     casFeatCode_mutatedResidue;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getMutatedResidue(int addr) {
        if (featOkTst && casFeat_mutatedResidue == null)
      jcas.throwFeatMissing("mutatedResidue", "types.SethMutation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mutatedResidue);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMutatedResidue(int addr, String v) {
        if (featOkTst && casFeat_mutatedResidue == null)
      jcas.throwFeatMissing("mutatedResidue", "types.SethMutation");
    ll_cas.ll_setStringValue(addr, casFeatCode_mutatedResidue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_locationWrtResidueOrNucleotide;
  /** @generated */
  final int     casFeatCode_locationWrtResidueOrNucleotide;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLocationWrtResidueOrNucleotide(int addr) {
        if (featOkTst && casFeat_locationWrtResidueOrNucleotide == null)
      jcas.throwFeatMissing("locationWrtResidueOrNucleotide", "types.SethMutation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_locationWrtResidueOrNucleotide);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLocationWrtResidueOrNucleotide(int addr, String v) {
        if (featOkTst && casFeat_locationWrtResidueOrNucleotide == null)
      jcas.throwFeatMissing("locationWrtResidueOrNucleotide", "types.SethMutation");
    ll_cas.ll_setStringValue(addr, casFeatCode_locationWrtResidueOrNucleotide, v);}
    
  
 
  /** @generated */
  final Feature casFeat_normalized;
  /** @generated */
  final int     casFeatCode_normalized;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getNormalized(int addr) {
        if (featOkTst && casFeat_normalized == null)
      jcas.throwFeatMissing("normalized", "types.SethMutation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_normalized);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNormalized(int addr, String v) {
        if (featOkTst && casFeat_normalized == null)
      jcas.throwFeatMissing("normalized", "types.SethMutation");
    ll_cas.ll_setStringValue(addr, casFeatCode_normalized, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tool;
  /** @generated */
  final int     casFeatCode_tool;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTool(int addr) {
        if (featOkTst && casFeat_tool == null)
      jcas.throwFeatMissing("tool", "types.SethMutation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tool);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTool(int addr, String v) {
        if (featOkTst && casFeat_tool == null)
      jcas.throwFeatMissing("tool", "types.SethMutation");
    ll_cas.ll_setStringValue(addr, casFeatCode_tool, v);}
    
  
 
  /** @generated */
  final Feature casFeat_HGVS;
  /** @generated */
  final int     casFeatCode_HGVS;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getHGVS(int addr) {
        if (featOkTst && casFeat_HGVS == null)
      jcas.throwFeatMissing("HGVS", "types.SethMutation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_HGVS);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHGVS(int addr, String v) {
        if (featOkTst && casFeat_HGVS == null)
      jcas.throwFeatMissing("HGVS", "types.SethMutation");
    ll_cas.ll_setStringValue(addr, casFeatCode_HGVS, v);}
    
  
 
  /** @generated */
  final Feature casFeat_isSnpAmbiguous;
  /** @generated */
  final int     casFeatCode_isSnpAmbiguous;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getIsSnpAmbiguous(int addr) {
        if (featOkTst && casFeat_isSnpAmbiguous == null)
      jcas.throwFeatMissing("isSnpAmbiguous", "types.SethMutation");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isSnpAmbiguous);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIsSnpAmbiguous(int addr, boolean v) {
        if (featOkTst && casFeat_isSnpAmbiguous == null)
      jcas.throwFeatMissing("isSnpAmbiguous", "types.SethMutation");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isSnpAmbiguous, v);}
    
  
 
  /** @generated */
  final Feature casFeat_isNucleotideSequenceMutation;
  /** @generated */
  final int     casFeatCode_isNucleotideSequenceMutation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getIsNucleotideSequenceMutation(int addr) {
        if (featOkTst && casFeat_isNucleotideSequenceMutation == null)
      jcas.throwFeatMissing("isNucleotideSequenceMutation", "types.SethMutation");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isNucleotideSequenceMutation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIsNucleotideSequenceMutation(int addr, boolean v) {
        if (featOkTst && casFeat_isNucleotideSequenceMutation == null)
      jcas.throwFeatMissing("isNucleotideSequenceMutation", "types.SethMutation");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isNucleotideSequenceMutation, v);}
    
  
 
  /** @generated */
  final Feature casFeat_isProteinSequenceMutation;
  /** @generated */
  final int     casFeatCode_isProteinSequenceMutation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getIsProteinSequenceMutation(int addr) {
        if (featOkTst && casFeat_isProteinSequenceMutation == null)
      jcas.throwFeatMissing("isProteinSequenceMutation", "types.SethMutation");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isProteinSequenceMutation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIsProteinSequenceMutation(int addr, boolean v) {
        if (featOkTst && casFeat_isProteinSequenceMutation == null)
      jcas.throwFeatMissing("isProteinSequenceMutation", "types.SethMutation");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isProteinSequenceMutation, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public SethMutation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_referenceSequence = jcas.getRequiredFeatureDE(casType, "referenceSequence", "uima.cas.String", featOkTst);
    casFeatCode_referenceSequence  = (null == casFeat_referenceSequence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_referenceSequence).getCode();

 
    casFeat_wildTypeResidue = jcas.getRequiredFeatureDE(casType, "wildTypeResidue", "uima.cas.String", featOkTst);
    casFeatCode_wildTypeResidue  = (null == casFeat_wildTypeResidue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_wildTypeResidue).getCode();

 
    casFeat_mutatedResidue = jcas.getRequiredFeatureDE(casType, "mutatedResidue", "uima.cas.String", featOkTst);
    casFeatCode_mutatedResidue  = (null == casFeat_mutatedResidue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mutatedResidue).getCode();

 
    casFeat_locationWrtResidueOrNucleotide = jcas.getRequiredFeatureDE(casType, "locationWrtResidueOrNucleotide", "uima.cas.String", featOkTst);
    casFeatCode_locationWrtResidueOrNucleotide  = (null == casFeat_locationWrtResidueOrNucleotide) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_locationWrtResidueOrNucleotide).getCode();

 
    casFeat_normalized = jcas.getRequiredFeatureDE(casType, "normalized", "uima.cas.String", featOkTst);
    casFeatCode_normalized  = (null == casFeat_normalized) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_normalized).getCode();

 
    casFeat_tool = jcas.getRequiredFeatureDE(casType, "tool", "uima.cas.String", featOkTst);
    casFeatCode_tool  = (null == casFeat_tool) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tool).getCode();

 
    casFeat_HGVS = jcas.getRequiredFeatureDE(casType, "HGVS", "uima.cas.String", featOkTst);
    casFeatCode_HGVS  = (null == casFeat_HGVS) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_HGVS).getCode();

 
    casFeat_isSnpAmbiguous = jcas.getRequiredFeatureDE(casType, "isSnpAmbiguous", "uima.cas.Boolean", featOkTst);
    casFeatCode_isSnpAmbiguous  = (null == casFeat_isSnpAmbiguous) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isSnpAmbiguous).getCode();

 
    casFeat_isNucleotideSequenceMutation = jcas.getRequiredFeatureDE(casType, "isNucleotideSequenceMutation", "uima.cas.Boolean", featOkTst);
    casFeatCode_isNucleotideSequenceMutation  = (null == casFeat_isNucleotideSequenceMutation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isNucleotideSequenceMutation).getCode();

 
    casFeat_isProteinSequenceMutation = jcas.getRequiredFeatureDE(casType, "isProteinSequenceMutation", "uima.cas.Boolean", featOkTst);
    casFeatCode_isProteinSequenceMutation  = (null == casFeat_isProteinSequenceMutation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isProteinSequenceMutation).getCode();

  }
}



    