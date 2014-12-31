

/* First created by JCasGen Tue Dec 30 15:14:08 PST 2014 */
package types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Dec 30 15:14:08 PST 2014
 * XML source: /home/alex/dev/btl-java/uima-components/uima-type-system/target/jcasgen/typesystem.xml
 * @generated */
public class MutationExtended extends Mutation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(MutationExtended.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected MutationExtended() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public MutationExtended(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public MutationExtended(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public MutationExtended(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: referenceSequence

  /** getter for referenceSequence - gets 
   * @generated
   * @return value of the feature 
   */
  public String getReferenceSequence() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_referenceSequence == null)
      jcasType.jcas.throwFeatMissing("referenceSequence", "types.MutationExtended");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_referenceSequence);}
    
  /** setter for referenceSequence - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setReferenceSequence(String v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_referenceSequence == null)
      jcasType.jcas.throwFeatMissing("referenceSequence", "types.MutationExtended");
    jcasType.ll_cas.ll_setStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_referenceSequence, v);}    
   
    
  //*--------------*
  //* Feature: wildTypeResidue

  /** getter for wildTypeResidue - gets 
   * @generated
   * @return value of the feature 
   */
  public String getWildTypeResidue() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_wildTypeResidue == null)
      jcasType.jcas.throwFeatMissing("wildTypeResidue", "types.MutationExtended");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_wildTypeResidue);}
    
  /** setter for wildTypeResidue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setWildTypeResidue(String v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_wildTypeResidue == null)
      jcasType.jcas.throwFeatMissing("wildTypeResidue", "types.MutationExtended");
    jcasType.ll_cas.ll_setStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_wildTypeResidue, v);}    
   
    
  //*--------------*
  //* Feature: mutatedResidue

  /** getter for mutatedResidue - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMutatedResidue() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_mutatedResidue == null)
      jcasType.jcas.throwFeatMissing("mutatedResidue", "types.MutationExtended");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_mutatedResidue);}
    
  /** setter for mutatedResidue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMutatedResidue(String v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_mutatedResidue == null)
      jcasType.jcas.throwFeatMissing("mutatedResidue", "types.MutationExtended");
    jcasType.ll_cas.ll_setStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_mutatedResidue, v);}    
   
    
  //*--------------*
  //* Feature: location

  /** getter for location - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLocation() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_location == null)
      jcasType.jcas.throwFeatMissing("location", "types.MutationExtended");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_location);}
    
  /** setter for location - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLocation(String v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_location == null)
      jcasType.jcas.throwFeatMissing("location", "types.MutationExtended");
    jcasType.ll_cas.ll_setStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_location, v);}    
   
    
  //*--------------*
  //* Feature: normalized

  /** getter for normalized - gets 
   * @generated
   * @return value of the feature 
   */
  public String getNormalized() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "types.MutationExtended");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_normalized);}
    
  /** setter for normalized - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNormalized(String v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "types.MutationExtended");
    jcasType.ll_cas.ll_setStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_normalized, v);}    
   
    
  //*--------------*
  //* Feature: tool

  /** getter for tool - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTool() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_tool == null)
      jcasType.jcas.throwFeatMissing("tool", "types.MutationExtended");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_tool);}
    
  /** setter for tool - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTool(String v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_tool == null)
      jcasType.jcas.throwFeatMissing("tool", "types.MutationExtended");
    jcasType.ll_cas.ll_setStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_tool, v);}    
   
    
  //*--------------*
  //* Feature: HGVS

  /** getter for HGVS - gets 
   * @generated
   * @return value of the feature 
   */
  public String getHGVS() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_HGVS == null)
      jcasType.jcas.throwFeatMissing("HGVS", "types.MutationExtended");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_HGVS);}
    
  /** setter for HGVS - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHGVS(String v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_HGVS == null)
      jcasType.jcas.throwFeatMissing("HGVS", "types.MutationExtended");
    jcasType.ll_cas.ll_setStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_HGVS, v);}    
   
    
  //*--------------*
  //* Feature: SequenceType

  /** getter for SequenceType - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSequenceType() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_SequenceType == null)
      jcasType.jcas.throwFeatMissing("SequenceType", "types.MutationExtended");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_SequenceType);}
    
  /** setter for SequenceType - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSequenceType(String v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_SequenceType == null)
      jcasType.jcas.throwFeatMissing("SequenceType", "types.MutationExtended");
    jcasType.ll_cas.ll_setStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_SequenceType, v);}    
   
    
  //*--------------*
  //* Feature: elementaryChange

  /** getter for elementaryChange - gets 
   * @generated
   * @return value of the feature 
   */
  public String getElementaryChange() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_elementaryChange == null)
      jcasType.jcas.throwFeatMissing("elementaryChange", "types.MutationExtended");
    return jcasType.ll_cas.ll_getStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_elementaryChange);}
    
  /** setter for elementaryChange - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setElementaryChange(String v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_elementaryChange == null)
      jcasType.jcas.throwFeatMissing("elementaryChange", "types.MutationExtended");
    jcasType.ll_cas.ll_setStringValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_elementaryChange, v);}    
   
    
  //*--------------*
  //* Feature: AmbiguousOutput

  /** getter for AmbiguousOutput - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getAmbiguousOutput() {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_AmbiguousOutput == null)
      jcasType.jcas.throwFeatMissing("AmbiguousOutput", "types.MutationExtended");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_AmbiguousOutput);}
    
  /** setter for AmbiguousOutput - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setAmbiguousOutput(boolean v) {
    if (MutationExtended_Type.featOkTst && ((MutationExtended_Type)jcasType).casFeat_AmbiguousOutput == null)
      jcasType.jcas.throwFeatMissing("AmbiguousOutput", "types.MutationExtended");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((MutationExtended_Type)jcasType).casFeatCode_AmbiguousOutput, v);}    
  }

    