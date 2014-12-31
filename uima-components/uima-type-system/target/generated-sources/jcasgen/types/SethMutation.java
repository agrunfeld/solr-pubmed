

/* First created by JCasGen Tue Dec 30 15:14:08 PST 2014 */
package types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Dec 30 15:14:08 PST 2014
 * XML source: /home/alex/dev/btl-java/uima-components/uima-type-system/target/jcasgen/typesystem.xml
 * @generated */
public class SethMutation extends Mutation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SethMutation.class);
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
  protected SethMutation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SethMutation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SethMutation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public SethMutation(JCas jcas, int begin, int end) {
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
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_referenceSequence == null)
      jcasType.jcas.throwFeatMissing("referenceSequence", "types.SethMutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_referenceSequence);}
    
  /** setter for referenceSequence - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setReferenceSequence(String v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_referenceSequence == null)
      jcasType.jcas.throwFeatMissing("referenceSequence", "types.SethMutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_referenceSequence, v);}    
   
    
  //*--------------*
  //* Feature: wildTypeResidue

  /** getter for wildTypeResidue - gets 
   * @generated
   * @return value of the feature 
   */
  public String getWildTypeResidue() {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_wildTypeResidue == null)
      jcasType.jcas.throwFeatMissing("wildTypeResidue", "types.SethMutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_wildTypeResidue);}
    
  /** setter for wildTypeResidue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setWildTypeResidue(String v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_wildTypeResidue == null)
      jcasType.jcas.throwFeatMissing("wildTypeResidue", "types.SethMutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_wildTypeResidue, v);}    
   
    
  //*--------------*
  //* Feature: mutatedResidue

  /** getter for mutatedResidue - gets 
   * @generated
   * @return value of the feature 
   */
  public String getMutatedResidue() {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_mutatedResidue == null)
      jcasType.jcas.throwFeatMissing("mutatedResidue", "types.SethMutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_mutatedResidue);}
    
  /** setter for mutatedResidue - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setMutatedResidue(String v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_mutatedResidue == null)
      jcasType.jcas.throwFeatMissing("mutatedResidue", "types.SethMutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_mutatedResidue, v);}    
   
    
  //*--------------*
  //* Feature: locationWrtResidueOrNucleotide

  /** getter for locationWrtResidueOrNucleotide - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLocationWrtResidueOrNucleotide() {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_locationWrtResidueOrNucleotide == null)
      jcasType.jcas.throwFeatMissing("locationWrtResidueOrNucleotide", "types.SethMutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_locationWrtResidueOrNucleotide);}
    
  /** setter for locationWrtResidueOrNucleotide - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLocationWrtResidueOrNucleotide(String v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_locationWrtResidueOrNucleotide == null)
      jcasType.jcas.throwFeatMissing("locationWrtResidueOrNucleotide", "types.SethMutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_locationWrtResidueOrNucleotide, v);}    
   
    
  //*--------------*
  //* Feature: normalized

  /** getter for normalized - gets 
   * @generated
   * @return value of the feature 
   */
  public String getNormalized() {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "types.SethMutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_normalized);}
    
  /** setter for normalized - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setNormalized(String v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_normalized == null)
      jcasType.jcas.throwFeatMissing("normalized", "types.SethMutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_normalized, v);}    
   
    
  //*--------------*
  //* Feature: tool

  /** getter for tool - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTool() {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_tool == null)
      jcasType.jcas.throwFeatMissing("tool", "types.SethMutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_tool);}
    
  /** setter for tool - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTool(String v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_tool == null)
      jcasType.jcas.throwFeatMissing("tool", "types.SethMutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_tool, v);}    
   
    
  //*--------------*
  //* Feature: HGVS

  /** getter for HGVS - gets 
   * @generated
   * @return value of the feature 
   */
  public String getHGVS() {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_HGVS == null)
      jcasType.jcas.throwFeatMissing("HGVS", "types.SethMutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_HGVS);}
    
  /** setter for HGVS - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setHGVS(String v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_HGVS == null)
      jcasType.jcas.throwFeatMissing("HGVS", "types.SethMutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((SethMutation_Type)jcasType).casFeatCode_HGVS, v);}    
   
    
  //*--------------*
  //* Feature: isSnpAmbiguous

  /** getter for isSnpAmbiguous - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getIsSnpAmbiguous() {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_isSnpAmbiguous == null)
      jcasType.jcas.throwFeatMissing("isSnpAmbiguous", "types.SethMutation");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((SethMutation_Type)jcasType).casFeatCode_isSnpAmbiguous);}
    
  /** setter for isSnpAmbiguous - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setIsSnpAmbiguous(boolean v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_isSnpAmbiguous == null)
      jcasType.jcas.throwFeatMissing("isSnpAmbiguous", "types.SethMutation");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((SethMutation_Type)jcasType).casFeatCode_isSnpAmbiguous, v);}    
   
    
  //*--------------*
  //* Feature: isNucleotideSequenceMutation

  /** getter for isNucleotideSequenceMutation - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getIsNucleotideSequenceMutation() {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_isNucleotideSequenceMutation == null)
      jcasType.jcas.throwFeatMissing("isNucleotideSequenceMutation", "types.SethMutation");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((SethMutation_Type)jcasType).casFeatCode_isNucleotideSequenceMutation);}
    
  /** setter for isNucleotideSequenceMutation - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setIsNucleotideSequenceMutation(boolean v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_isNucleotideSequenceMutation == null)
      jcasType.jcas.throwFeatMissing("isNucleotideSequenceMutation", "types.SethMutation");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((SethMutation_Type)jcasType).casFeatCode_isNucleotideSequenceMutation, v);}    
   
    
  //*--------------*
  //* Feature: isProteinSequenceMutation

  /** getter for isProteinSequenceMutation - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getIsProteinSequenceMutation() {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_isProteinSequenceMutation == null)
      jcasType.jcas.throwFeatMissing("isProteinSequenceMutation", "types.SethMutation");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((SethMutation_Type)jcasType).casFeatCode_isProteinSequenceMutation);}
    
  /** setter for isProteinSequenceMutation - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setIsProteinSequenceMutation(boolean v) {
    if (SethMutation_Type.featOkTst && ((SethMutation_Type)jcasType).casFeat_isProteinSequenceMutation == null)
      jcasType.jcas.throwFeatMissing("isProteinSequenceMutation", "types.SethMutation");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((SethMutation_Type)jcasType).casFeatCode_isProteinSequenceMutation, v);}    
  }

    