from Bio import Entrez

# Usage limits Entrez Link
# http://eutils.ncbi.nlm.nih.gov/entrez/query/static/entrezlinks.html#pmc


if __name__ == '__main__':
    Entrez.email = "apurdy@uvic.ca"
    search_handle = Entrez.esearch(db="pubmed",
                                   term="((Diagnosis AND genetics) OR (Differential Diagnosis[MeSH] OR Differential Diagnosis[Text Word] AND genetics) OR (Natural History OR Mortality OR Phenotype OR Prevalence OR Penetrance AND genetics) OR (therapy[Subheading] OR treatment[Text Word] OR treatment outcome OR investigational therapies AND genetics) OR (Genetic Counseling OR Inheritance pattern AND genetics) OR (Medical Genetics OR genotype OR genetics[Subheading] AND genetics) OR (DNA Mutational Analysis OR Laboratory techniques and procedures OR Genetic Markers OR diagnosis OR testing OR test OR screening OR mutagenicity tests OR genetic techniques OR molecular diagnostic techniques AND genetics))",
                                   reldate=5, datetype="pdat",
                                   usehistory='y')
    search_results = Entrez.read(search_handle)
    search_handle.close()

    count = int(search_results['Count'])
    print("Found %i results" % count)

    batch_size = 1000
    with open("store/medline.xml", "w") as raw_handle:
        for start in range(0, count, batch_size):
            end = min(count, start + batch_size)
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
            #         end = min(count, start + batch_size)
            #         print("Fetching associated links for record %i to %i" % (start + 1, end))
            #         linkout_handle = Entrez.elink(dbfrom="pubmed",
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
