

/* First created by JCasGen Tue Dec 30 15:14:08 PST 2014 */
package types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** 
 * Updated by JCasGen Tue Dec 30 15:14:08 PST 2014
 * XML source: /home/alex/dev/btl-java/uima-components/uima-type-system/target/jcasgen/typesystem.xml
 * @generated */
public class TmVarMutation extends Mutation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(TmVarMutation.class);
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
  protected TmVarMutation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public TmVarMutation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public TmVarMutation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public TmVarMutation(JCas jcas, int begin, int end) {
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
  //* Feature: sequencetype

  /** getter for sequencetype - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSequencetype() {
    if (TmVarMutation_Type.featOkTst && ((TmVarMutation_Type)jcasType).casFeat_sequencetype == null)
      jcasType.jcas.throwFeatMissing("sequencetype", "types.TmVarMutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TmVarMutation_Type)jcasType).casFeatCode_sequencetype);}
    
  /** setter for sequencetype - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSequencetype(String v) {
    if (TmVarMutation_Type.featOkTst && ((TmVarMutation_Type)jcasType).casFeat_sequencetype == null)
      jcasType.jcas.throwFeatMissing("sequencetype", "types.TmVarMutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TmVarMutation_Type)jcasType).casFeatCode_sequencetype, v);}    
   
    
  //*--------------*
  //* Feature: Summary

  /** getter for Summary - gets 
   * @generated
   * @return value of the feature 
   */
  public String getSummary() {
    if (TmVarMutation_Type.featOkTst && ((TmVarMutation_Type)jcasType).casFeat_Summary == null)
      jcasType.jcas.throwFeatMissing("Summary", "types.TmVarMutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((TmVarMutation_Type)jcasType).casFeatCode_Summary);}
    
  /** setter for Summary - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSummary(String v) {
    if (TmVarMutation_Type.featOkTst && ((TmVarMutation_Type)jcasType).casFeat_Summary == null)
      jcasType.jcas.throwFeatMissing("Summary", "types.TmVarMutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((TmVarMutation_Type)jcasType).casFeatCode_Summary, v);}    
  }

    