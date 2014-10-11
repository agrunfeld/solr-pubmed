package ingestion;

import com.google.common.io.Resources;
import medline.MedlineCitationSet;
import org.apache.log4j.BasicConfigurator;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.codehaus.plexus.util.FileUtils;
import pubmed.PubmedArticleSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by alex on 29/09/14.
 */
public class TestScripts {
    public static final String SAMPLE_FILE = "samples/medsamp2014.xml";
    public static final String MEDLINE = "/home/alex/medline/2014";

    public static void deleteRecords() throws Exception {
        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();
    }

    public static void importSampleDate() throws Exception {
        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();

        MedlineReader medlineReader = new MedlineReader();
        MedlineCitationSet set = medlineReader.unmarshall(Resources.getResource(SAMPLE_FILE).openStream());
        Collection<SolrInputDocument> collection = medlineReader.mapToSolrInputDocumentCollection(set);
        client.update(collection);

    }


    public static void importLastYear(boolean concurrent) throws Exception {
//        BasicConfigurator.configure();
        SolrClient client = new SolrClient(concurrent);
        client.deleteRecords();
        PubMedReader pubMedReader = new PubMedReader();

        File medline = FileUtils.getFile(MEDLINE);
        File[] zippedMedline = medline.listFiles();
        System.out.println(zippedMedline.length);
        for (int i = 0; i < zippedMedline.length; i++) {
            System.out.println(zippedMedline[i].getName());
            InputStream fileStream = new FileInputStream(zippedMedline[i]);
            GZIPInputStream gzipStream = new GZIPInputStream(fileStream);
            PubmedArticleSet set = pubMedReader.unmarshall(gzipStream);
            Collection<SolrInputDocument> collection = pubMedReader.mapToSolrInputDocumentCollection(set);
            client.update(collection);
            gzipStream.close();
            fileStream.close();
        }
    }

    public static void testQueryUnicode() throws Exception {
        SolrClient client = new SolrClient(true);
        SolrDocumentList list = client.getRecords("Adiponectin SNP11377 and SNP276 gene-gene interactions are associated with the increased risk of", 1);
        SolrDocument document = list.get(0);
        String title = (String) document.get("article_title");
        System.out.println(title);
        PrintStream out = new PrintStream(System.out, true, "UTF-8");
        out.println(title);
        String newString = new String(title.getBytes(), "UTF8");
        System.out.println(newString);
        out.println(newString);


    }

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();
        importLastYear(false);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("Regular update of 820411 records took " + duration / 1E9 + " seconds");

        startTime = System.nanoTime();
        importLastYear(true);
        endTime = System.nanoTime();
        duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        System.out.println("Concurrent update of 820411 records took " + duration / 1E9 + " seconds");


    }

 }
