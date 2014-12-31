

/* First created by JCasGen Tue Dec 30 15:14:08 PST 2014 */
package types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Dec 30 15:14:08 PST 2014
 * XML source: /home/alex/dev/btl-java/uima-components/uima-type-system/target/jcasgen/typesystem.xml
 * @generated */
public class Mutation extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Mutation.class);
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
  protected Mutation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Mutation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Mutation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Mutation(JCas jcas, int begin, int end) {
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
  //* Feature: text

  /** getter for text - gets 
   * @generated
   * @return value of the feature 
   */
  public String getText() {
    if (Mutation_Type.featOkTst && ((Mutation_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "types.Mutation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Mutation_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setText(String v) {
    if (Mutation_Type.featOkTst && ((Mutation_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "types.Mutation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Mutation_Type)jcasType).casFeatCode_text, v);}    
  }

    