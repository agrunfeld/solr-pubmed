from http.client import IncompleteRead
import os
from Bio import Entrez
import gzip
import logging

logging.basicConfig(filename='fetch.log', level=logging.DEBUG)


# todo: fix messy parameters
# todo: elink queries are expensive, returns unnecesary data
def fetch_urls(outdir, webenv, query_key, count, batch_size=100):
    """Retrieves associated urls for a set of medline records.

    Retrieves the associated full-text source urls using the Entrez Link
    service. Server request limits are enforced by the Entrez module.

    Args:
        webenv: Web environment string returned from a previous ESearch, EPost or
        ELink call. When provided, ESearch will post the results of the search
        operation to this pre-existing WebEnv, thereby appending the results to
        the existing environment.
        query_key: Integer query key returned by a previous ESearch, EPost or ELink
        call. When provided, ESearch will find the intersection of the set specified
        by query_key and the set retrieved by the query in term (i.e. joins the two
        with AND).
        batch_size: Total number of DocSums from the input set to be retrieved,
        up to a maximum of 10,000.
    Returns:
        Handle containing response in XML format.

    Raises:
        IncompleteRead: An error occurred reading data through the HTTP connection.
    """
    for start in range(0, count, batch_size):
        end = min(count, start + batch_size)
        with gzip.open(os.join(outdir, "{0:d}-{1:d}.links.xml.gz".format(start + 1, end)), 'w') as out:
            logging.info("Fetching associated links for record %i to %i" % (start + 1, end))
            linkout_handle = Entrez.elink(dbfrom="pubmed",
                                          retmode="xml",
                                          restart=start, retmax=batch_size,
                                          cmd="llinks",
                                          webenv=webenv,
                                          query_key=query_key,
                                          linkname="pubmed_pubmed")
            if int(linkout_handle['Count']) == 0:
                continue
            try:
                data = linkout_handle.read()
            except IncompleteRead:
                logging.error("Fail to read records %i to %i" % (start + 1, end))
            else:
                out.write(data)
                linkout_handle.close()


def fetch_records_by_year(start, stop, fetch_links=False):
    for year in range(start, stop, 1):
        search_handle = Entrez.esearch(db="pubmed",
                                       term="{0:d}[crdt]".format(year),
                                       usehistory='y')
        search_results = Entrez.read(search_handle)
        search_handle.close()

        count = int(search_results['Count'])
        logging.info("Found {0:d} results for year {1:d}".format(count, year))
        if count == 0:
            continue

        batch_size = 1000
        records_dir_path = os.path.join(path, str(year))
        if not os.path.exists(records_dir_path):
            os.makedirs(records_dir_path)

        for start in range(0, count, batch_size):
            end = min(count, start + batch_size)
            with gzip.open(os.path.join(records_dir_path, "{0:d}-{1:d}.xml.gz".format(start + 1, end)), "wt") as out:
                logging.info("Fetching medline records %i to %i" % (start + 1, end))
                fetch_handle = Entrez.efetch(db="pubmed",
                                             rettype="medline", retmode="xml",
                                             restart=start, retmax=batch_size,
                                             webenv=search_results["WebEnv"],
                                             query_key=search_results["QueryKey"])
                try:
                    data = fetch_handle.read()
                except IncompleteRead:
                    logging.error("Fail to read records %i to %i" % (start + 1, end))
                else:
                    out.write(data)
                    fetch_handle.close()
        if fetch_links:
            fetch_urls(records_dir_path, webenv=search_results["WebEnv"], query_key=search_results["QueryKey"], count=count)


if __name__ == '__main__':
    Entrez.email = "apurdy@uvic.ca"
    path = '/home/alex/medline/'
    fetch_records_by_year(1952, 2017, fetch_links=False)

