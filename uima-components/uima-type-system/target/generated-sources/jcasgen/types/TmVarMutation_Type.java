
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
public class TmVarMutation_Type extends Mutation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (TmVarMutation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = TmVarMutation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new TmVarMutation(addr, TmVarMutation_Type.this);
  			   TmVarMutation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new TmVarMutation(addr, TmVarMutation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = TmVarMutation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("types.TmVarMutation");
 
  /** @generated */
  final Feature casFeat_sequencetype;
  /** @generated */
  final int     casFeatCode_sequencetype;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSequencetype(int addr) {
        if (featOkTst && casFeat_sequencetype == null)
      jcas.throwFeatMissing("sequencetype", "types.TmVarMutation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sequencetype);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSequencetype(int addr, String v) {
        if (featOkTst && casFeat_sequencetype == null)
      jcas.throwFeatMissing("sequencetype", "types.TmVarMutation");
    ll_cas.ll_setStringValue(addr, casFeatCode_sequencetype, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Summary;
  /** @generated */
  final int     casFeatCode_Summary;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSummary(int addr) {
        if (featOkTst && casFeat_Summary == null)
      jcas.throwFeatMissing("Summary", "types.TmVarMutation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Summary);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSummary(int addr, String v) {
        if (featOkTst && casFeat_Summary == null)
      jcas.throwFeatMissing("Summary", "types.TmVarMutation");
    ll_cas.ll_setStringValue(addr, casFeatCode_Summary, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public TmVarMutation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_sequencetype = jcas.getRequiredFeatureDE(casType, "sequencetype", "uima.cas.String", featOkTst);
    casFeatCode_sequencetype  = (null == casFeat_sequencetype) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sequencetype).getCode();

 
    casFeat_Summary = jcas.getRequiredFeatureDE(casType, "Summary", "uima.cas.String", featOkTst);
    casFeatCode_Summary  = (null == casFeat_Summary) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Summary).getCode();

  }
}



    