package ingestion;

import com.google.common.io.Resources;
import parsers.medline.MedlineCitationSet;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import parsers.MedlineParser;
import parsers.PubMedParser;
import parsers.pubmed.PubmedArticleSet;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.GZIPInputStream;

/**
 * Created by alex on 29/09/14.
 */
public class SolrUtils {
    // TODO: automate this, and make it portable
    public static final String SAMPLE_FILE = "samples/medsamp2014.xml";
    public static final String MEDLINE_2014 = "/home/alex/medline/2014";
    public static final String MEDLINE = "/home/alex/medline";

    public static void deleteRecords() throws Exception {
        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();
    }

    public static void importSampleResourceData() throws Exception {
        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();

        MedlineParser medlineParser = new MedlineParser();
        MedlineCitationSet set = medlineParser.unmarshall(Resources.getResource(SAMPLE_FILE).openStream());
        Collection<SolrInputDocument> collection = medlineParser.mapToSolrInputDocumentCollection(set);
        client.update(collection);

    }

    public static void importSampleData() throws Exception {
        BasicConfigurator.configure();
        SolrClient client = new SolrClient(false);
        client.deleteRecords();

        MedlineParser medlineParser = new MedlineParser();

        Iterator it = FileUtils.iterateFiles(new File(MEDLINE), new String[]{"xml"}, false);
        while(it.hasNext()){
            MedlineCitationSet set = medlineParser.unmarshall((File) it.next());
            Collection<SolrInputDocument> collection = medlineParser.mapToSolrInputDocumentCollection(set);
            client.update(collection);
        }



    }
    public static void importEverything(boolean concurrent) throws JAXBException, IOException, SolrServerException {

        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("/home/alex/import.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);


        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }


//        BasicConfigurator.configure();
        SolrClient client = new SolrClient(concurrent);
        client.deleteRecords();
        PubMedParser pubMedParser = new PubMedParser();

        File medline = FileUtils.getFile(MEDLINE);
        File[] directories = medline.listFiles();

        Arrays.sort(directories, new Comparator() {
            @Override
            public int compare(Object f1, Object f2) {
                String fileName1 = ((File) f1).getName();
                String fileName2 = ((File) f2).getName();

                int fileId1 = Integer.parseInt(fileName1);
                int fileId2 = Integer.parseInt(fileName2);

                return fileId2 - fileId1;
            }
        });


        for (File directory : directories) {
            if (directory.isDirectory()) {
                File[] zippedMedline = directory.listFiles();
                for (File aZippedMedline : zippedMedline) {
                    try {
                        InputStream fileStream = new FileInputStream(aZippedMedline);
                        GZIPInputStream gzipStream = new GZIPInputStream(fileStream);
                        PubmedArticleSet set = pubMedParser.unmarshall(gzipStream);
                        Collection<SolrInputDocument> collection = pubMedParser.mapToSolrInputDocumentCollection(set);
                        client.update(collection);
                        gzipStream.close();
                        fileStream.close();
                    } catch (JAXBException | IOException e) {
                        logger.severe("error indexing" +  aZippedMedline +  "for year " + directory.getName());
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static void importLastYear(boolean concurrent) throws Exception {
//        BasicConfigurator.configure();
        SolrClient client = new SolrClient(concurrent);
        client.deleteRecords();
        PubMedParser pubMedParser = new PubMedParser();

        File medline = FileUtils.getFile(MEDLINE_2014);
        File[] zippedMedline = medline.listFiles();
        System.out.println(zippedMedline.length);
        for (int i = 0; i < zippedMedline.length; i++) {
            System.out.println(zippedMedline[i].getName());
            InputStream fileStream = new FileInputStream(zippedMedline[i]);
            GZIPInputStream gzipStream = new GZIPInputStream(fileStream);
            PubmedArticleSet set = pubMedParser.unmarshall(gzipStream);
            Collection<SolrInputDocument> collection = pubMedParser.mapToSolrInputDocumentCollection(set);
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

    public static void timedUpdate() throws Exception {
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

    public static void main(String[] args) throws Exception {
        importSampleData();
    }

 }
