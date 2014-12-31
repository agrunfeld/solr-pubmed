
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
public class MutationExtended_Type extends Mutation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (MutationExtended_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = MutationExtended_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new MutationExtended(addr, MutationExtended_Type.this);
  			   MutationExtended_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new MutationExtended(addr, MutationExtended_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = MutationExtended.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("types.MutationExtended");
 
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
      jcas.throwFeatMissing("referenceSequence", "types.MutationExtended");
    return ll_cas.ll_getStringValue(addr, casFeatCode_referenceSequence);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setReferenceSequence(int addr, String v) {
        if (featOkTst && casFeat_referenceSequence == null)
      jcas.throwFeatMissing("referenceSequence", "types.MutationExtended");
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
      jcas.throwFeatMissing("wildTypeResidue", "types.MutationExtended");
    return ll_cas.ll_getStringValue(addr, casFeatCode_wildTypeResidue);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setWildTypeResidue(int addr, String v) {
        if (featOkTst && casFeat_wildTypeResidue == null)
      jcas.throwFeatMissing("wildTypeResidue", "types.MutationExtended");
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
      jcas.throwFeatMissing("mutatedResidue", "types.MutationExtended");
    return ll_cas.ll_getStringValue(addr, casFeatCode_mutatedResidue);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMutatedResidue(int addr, String v) {
        if (featOkTst && casFeat_mutatedResidue == null)
      jcas.throwFeatMissing("mutatedResidue", "types.MutationExtended");
    ll_cas.ll_setStringValue(addr, casFeatCode_mutatedResidue, v);}
    
  
 
  /** @generated */
  final Feature casFeat_location;
  /** @generated */
  final int     casFeatCode_location;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLocation(int addr) {
        if (featOkTst && casFeat_location == null)
      jcas.throwFeatMissing("location", "types.MutationExtended");
    return ll_cas.ll_getStringValue(addr, casFeatCode_location);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLocation(int addr, String v) {
        if (featOkTst && casFeat_location == null)
      jcas.throwFeatMissing("location", "types.MutationExtended");
    ll_cas.ll_setStringValue(addr, casFeatCode_location, v);}
    
  
 
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
      jcas.throwFeatMissing("normalized", "types.MutationExtended");
    return ll_cas.ll_getStringValue(addr, casFeatCode_normalized);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNormalized(int addr, String v) {
        if (featOkTst && casFeat_normalized == null)
      jcas.throwFeatMissing("normalized", "types.MutationExtended");
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
      jcas.throwFeatMissing("tool", "types.MutationExtended");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tool);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTool(int addr, String v) {
        if (featOkTst && casFeat_tool == null)
      jcas.throwFeatMissing("tool", "types.MutationExtended");
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
      jcas.throwFeatMissing("HGVS", "types.MutationExtended");
    return ll_cas.ll_getStringValue(addr, casFeatCode_HGVS);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHGVS(int addr, String v) {
        if (featOkTst && casFeat_HGVS == null)
      jcas.throwFeatMissing("HGVS", "types.MutationExtended");
    ll_cas.ll_setStringValue(addr, casFeatCode_HGVS, v);}
    
  
 
  /** @generated */
  final Feature casFeat_SequenceType;
  /** @generated */
  final int     casFeatCode_SequenceType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSequenceType(int addr) {
        if (featOkTst && casFeat_SequenceType == null)
      jcas.throwFeatMissing("SequenceType", "types.MutationExtended");
    return ll_cas.ll_getStringValue(addr, casFeatCode_SequenceType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSequenceType(int addr, String v) {
        if (featOkTst && casFeat_SequenceType == null)
      jcas.throwFeatMissing("SequenceType", "types.MutationExtended");
    ll_cas.ll_setStringValue(addr, casFeatCode_SequenceType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_elementaryChange;
  /** @generated */
  final int     casFeatCode_elementaryChange;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getElementaryChange(int addr) {
        if (featOkTst && casFeat_elementaryChange == null)
      jcas.throwFeatMissing("elementaryChange", "types.MutationExtended");
    return ll_cas.ll_getStringValue(addr, casFeatCode_elementaryChange);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setElementaryChange(int addr, String v) {
        if (featOkTst && casFeat_elementaryChange == null)
      jcas.throwFeatMissing("elementaryChange", "types.MutationExtended");
    ll_cas.ll_setStringValue(addr, casFeatCode_elementaryChange, v);}
    
  
 
  /** @generated */
  final Feature casFeat_AmbiguousOutput;
  /** @generated */
  final int     casFeatCode_AmbiguousOutput;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getAmbiguousOutput(int addr) {
        if (featOkTst && casFeat_AmbiguousOutput == null)
      jcas.throwFeatMissing("AmbiguousOutput", "types.MutationExtended");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_AmbiguousOutput);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setAmbiguousOutput(int addr, boolean v) {
        if (featOkTst && casFeat_AmbiguousOutput == null)
      jcas.throwFeatMissing("AmbiguousOutput", "types.MutationExtended");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_AmbiguousOutput, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public MutationExtended_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_referenceSequence = jcas.getRequiredFeatureDE(casType, "referenceSequence", "uima.cas.String", featOkTst);
    casFeatCode_referenceSequence  = (null == casFeat_referenceSequence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_referenceSequence).getCode();

 
    casFeat_wildTypeResidue = jcas.getRequiredFeatureDE(casType, "wildTypeResidue", "uima.cas.String", featOkTst);
    casFeatCode_wildTypeResidue  = (null == casFeat_wildTypeResidue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_wildTypeResidue).getCode();

 
    casFeat_mutatedResidue = jcas.getRequiredFeatureDE(casType, "mutatedResidue", "uima.cas.String", featOkTst);
    casFeatCode_mutatedResidue  = (null == casFeat_mutatedResidue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_mutatedResidue).getCode();

 
    casFeat_location = jcas.getRequiredFeatureDE(casType, "location", "uima.cas.String", featOkTst);
    casFeatCode_location  = (null == casFeat_location) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_location).getCode();

 
    casFeat_normalized = jcas.getRequiredFeatureDE(casType, "normalized", "uima.cas.String", featOkTst);
    casFeatCode_normalized  = (null == casFeat_normalized) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_normalized).getCode();

 
    casFeat_tool = jcas.getRequiredFeatureDE(casType, "tool", "uima.cas.String", featOkTst);
    casFeatCode_tool  = (null == casFeat_tool) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tool).getCode();

 
    casFeat_HGVS = jcas.getRequiredFeatureDE(casType, "HGVS", "uima.cas.String", featOkTst);
    casFeatCode_HGVS  = (null == casFeat_HGVS) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_HGVS).getCode();

 
    casFeat_SequenceType = jcas.getRequiredFeatureDE(casType, "SequenceType", "uima.cas.String", featOkTst);
    casFeatCode_SequenceType  = (null == casFeat_SequenceType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_SequenceType).getCode();

 
    casFeat_elementaryChange = jcas.getRequiredFeatureDE(casType, "elementaryChange", "uima.cas.String", featOkTst);
    casFeatCode_elementaryChange  = (null == casFeat_elementaryChange) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_elementaryChange).getCode();

 
    casFeat_AmbiguousOutput = jcas.getRequiredFeatureDE(casType, "AmbiguousOutput", "uima.cas.Boolean", featOkTst);
    casFeatCode_AmbiguousOutput  = (null == casFeat_AmbiguousOutput) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_AmbiguousOutput).getCode();

  }
}



    