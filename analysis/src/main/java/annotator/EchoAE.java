package annotator;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

public class EchoAE extends org.apache.uima.fit.component.JCasAnnotator_ImplBase {

  @Override
  public void process(JCas jCas) throws AnalysisEngineProcessException {
    System.out.println(jCas.getDocumentText());
  }
}
