from http.client import IncompleteRead
from Bio import Entrez
import os
import gzip

BASE_PATH = "/home/alex/medline"

Entrez.email = "apurdy@uvic.ca"

for year in range(2015, 1800, -1):
    records_dir_path = os.path.join(BASE_PATH, str(year))
    if not os.path.exists(records_dir_path):
        os.makedirs(records_dir_path)

    search_results = Entrez.read(Entrez.esearch(db="pubmed",
                                                term="{0:d}[edat]".format(year),
                                                usehistory="y"))
    count = int(search_results["Count"])
    print("Found %i results" % count)
    if count == 0:
        continue

    batch_size = 10000
    for start in range(0, count, batch_size):
        end = min(count, start + batch_size)
        fname = os.path.join(records_dir_path, "{0:d}-{1:d}.xml.gz".format(start + 1, end))

        with gzip.open(fname, "wt") as out_handle:
            print("Going to download record %i to %i" % (start + 1, end))
            fetch_handle = Entrez.efetch(db="pubmed",
                                         retmode="xml",
                                         retstart=start, retmax=batch_size,
                                         webenv=search_results["WebEnv"],
                                         query_key=search_results["QueryKey"])
            try:
                data = fetch_handle.read()
                out_handle.write(data)
            except IncompleteRead:
                print("Fail to read records %i to %i" % (start + 1, end))
            finally:
                fetch_handle.close()