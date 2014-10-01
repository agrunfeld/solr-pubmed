import os
from Bio import Entrez
import gzip

# Usage limits Entrez Link
# http://eutils.ncbi.nlm.nih.gov/entrez/query/static/entrezlinks.html#pmc

if __name__ == '__main__':
    Entrez.email = "apurdy@uvic.ca"
    path = '/home/alex/medline/'
    records_per_year = {}
    for year in range(1865, 2017, 1):
        search_handle = Entrez.esearch(db="pubmed",
                                       term="{0:d}[crdt]".format(year),
                                       usehistory='y')
        search_results = Entrez.read(search_handle)
        search_handle.close()

        count = int(search_results['Count'])
        print("Found {0:d} results for year {1:d}".format(count, year))
        records_per_year[year] = count
        if count == 0:
            continue

        batch_size = 1000
        records_dir_path = os.path.join(path, str(year))
        if not os.path.exists(records_dir_path):
            os.makedirs(records_dir_path)

        for start in range(0, count, batch_size):
            end = min(count, start + batch_size)
            with gzip.open(os.path.join(records_dir_path, "{0:d}-{1:d}.xml.gz".format(start + 1, end)),
                           "wt") as raw_handle:
                print("Going to download record %i to %i" % (start + 1, end))
                fetch_handle = Entrez.efetch(db="pubmed",
                                             rettype="medline", retmode="xml",
                                             restart=start, retmax=batch_size,
                                             webenv=search_results["WebEnv"],
                                             query_key=search_results["QueryKey"])
                data = fetch_handle.read()
                fetch_handle.close()
                raw_handle.write(data)

                # batch_size = 5000
                # with open('store/urls.txt', 'w') as url_handle:
                # for start in range(0, count, batch_size):
                # end = min(count, start + batch_size)
                # print("Fetching associated links for record %i to %i" % (start + 1, end))
                # linkout_handle = Entrez.elink(dbfrom="pubmed",
                #                                       retmode="xml",
                #                                       restart=start, retmax=batch_size,
                #                                       cmd="llinks",
                #                                       webenv=search_results["WebEnv"],
                #                                       query_key=search_results["QueryKey"],
                #                                       linkname="pubmed_pubmed")
                #         records = Entrez.read(linkout_handle)
                #         linkout_handle.close()
                #         id_url_set = records[0]['IdUrlList']['IdUrlSet']
                #         for id_url in id_url_set:
                #             pid = id_url['Id']
                #             urls = set()
                #             for obj_url in id_url['ObjUrl']:
                #                 if 'Full Text Sources' in obj_url['Category'] and all(
                #                                 k in obj_url['Attribute'] for k in ('free resource', 'full-text online')):
                #                     urls.add(obj_url['Url'])
                #             if (urls):
                #                 url_handle.write('>' + pid + '\n' + str('\n'.join(urls)) + '\n')
                #
    print(records_per_year)