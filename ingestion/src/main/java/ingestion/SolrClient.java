package ingestion;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public class SolrClient {
    private static final String BASE_URL = "http://localhost:8983/core0";
    private SolrServer solrServer;
    private final Logger logger;

    public SolrClient(boolean useConcurrentUpdate) {
        if (useConcurrentUpdate) {
            /*TODO: determine parameters from environment*/
            this.solrServer = new ConcurrentUpdateSolrServer(BASE_URL, 30000, 4);
        } else {
            this.solrServer = new HttpSolrServer(BASE_URL);
        }
        logger = LoggerFactory.getLogger(SolrClient.class);
    }

    public void update(Collection<SolrInputDocument> documents) {
        try {
            solrServer.add(documents);
            solrServer.commit();
        } catch (SolrServerException | IOException e) {
            logger.error("Failed to update solr server", e);
        }
    }

    public void deleteRecords() throws IOException, SolrServerException {
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

    public long getCount() throws SolrServerException {
        SolrQuery q = new SolrQuery("*:*");
        q.setRows(0);  // don't actually request any data
        return solrServer.query(q).getResults().getNumFound();
    }
}
