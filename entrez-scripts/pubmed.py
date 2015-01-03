from http.client import IncompleteRead, BadStatusLine
from urllib.error import URLError
from Bio import Entrez
import os
import gzip
import logging
import progressbar
from time import sleep

# root logger, append to file
logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s %(name)-12s %(levelname)-8s %(message)s',
                    datefmt='%m-%d %H:%M',
                    filename='fetch.log',
                    filemode='a')
# define a Handler which writes INFO messages or higher to the sys.stderr
console = logging.StreamHandler()
console.setLevel(logging.INFO)
formatter = logging.Formatter('%(name)-12s: %(levelname)-8s %(message)s')
console.setFormatter(formatter)
# add the handler to the root logger
logging.getLogger('').addHandler(console)

BASE_PATH = "/home/alex/parsers.medline"
BATCH_SIZE = 10000

Entrez.email = "apurdy@uvic.ca"

for year in range(1952, 1800, -1):

    search_results = Entrez.read(Entrez.esearch(db="parsers.pubmed",
                                                term="{0:d}[edat]".format(year),
                                                usehistory="y"))
    count = int(search_results["Count"])
    logging.info("Found {0:d} records published in {1:d}".format(count, year))
    if count == 0:
        continue

    records_dir_path = os.path.join(BASE_PATH, str(year))
    if not os.path.exists(records_dir_path):
        os.makedirs(records_dir_path)

    bar = progressbar.ProgressBar(maxval=count - 1,
                                  widgets=[progressbar.Bar('=', '[', ']'), ' ', progressbar.Percentage()])
    bar.start()
    for start in range(0, count, BATCH_SIZE):
        end = min(count - 1, start + BATCH_SIZE)
        fname = os.path.join(records_dir_path, "{0:d}-{1:d}.xml.gz".format(start + 1, end))
        if os.path.exists(fname):
            bar.update(end)
            continue
        with gzip.open(fname, "wt") as out_handle:
            logging.debug("Downloading record {0:d} to {1:d}".format(start + 1, end))
            try:
                fetch_handle = Entrez.efetch(db="parsers.pubmed",
                                             retmode="xml",
                                             retstart=start, retmax=BATCH_SIZE,
                                             webenv=search_results["WebEnv"],
                                             query_key=search_results["QueryKey"])
                try:
                    data = fetch_handle.read()
                    out_handle.write(data)
                except IncompleteRead:
                    logging.error("Fail to read records {0:d} to {1:d}".format(start + 1, end))
                finally:
                    fetch_handle.close()
            except BadStatusLine:
                logging.error("Fail to read records {0:d} to {1:d}".format(start + 1, end))
            except URLError:
                logging.error("Failed to connect to fetch results {0:d} to {1:d}".format(start + 1, end))



        bar.update(end)
