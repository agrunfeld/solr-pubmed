import datetime
import urllib
from urllib.request import urlopen, urlretrieve, Request
from urllib.parse import urlencode
import xml.etree.ElementTree as eT
import os
import logging


# todo: module refactoring, cli
logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s %(name)-12s %(levelname)-8s %(message)s',
                    datefmt='%m-%d %H:%M',
                    filename='pmc-oa.log',
                    filemode='a')
# define a Handler which writes INFO messages or higher to the sys.stderr
console = logging.StreamHandler()
console.setLevel(logging.INFO)
formatter = logging.Formatter('%(name)-12s: %(levelname)-8s %(message)s')
console.setFormatter(formatter)
# add the handler to the root logger
logging.getLogger('').addHandler(console)


## More info: http://www.ncbi.nlm.nih.gov/pmc/tools/oa-service/
OA_WEB_SERVICE = "http://www.pubmedcentral.nih.gov/utils/oa/oa.fcgi"
OUTPUT_DIR = "/home/alex/pmc"

# TODO: error handling, timeouts
def fetch_links(xml, path):
    # parse the xml response
    tree = eT.fromstring(xml)

    # grab all the links to PMC articles
    links = (link.attrib['href'] for link in tree.findall(".//record/link"))
    # download each record from the PMC ftp server
    for link in links:
        urlretrieve(link, os.path.join(path, link.split('/')[-1]))

    # if response has > 1000 records, use the resumption token to retrieve more results
    resumption = tree.findall("./records/resumption/link")
    if resumption:
        return resumption.attrib['href']

if __name__ == '__main__':

    for year in range(2000, 2015):

        # request parameters
        # 'id': 'PMC13901',
        # 'from': YYYY-MM-DD HH:MM:SS 
        # 'until': YYYY-MM-DD HH:MM:SS 
        # 'format': 'tgz|pdf'
        params = {
            'from': datetime.date(year=year, month=1, day=1),
            'until': datetime.date(year=year, month=12, day=31),
            'format': 'tgz'
        }

        data = urlencode(params)
        data = data.encode('utf-8')  # data should be bytes
        req = urllib.request.Request(OA_WEB_SERVICE, data)
        response = urlopen(req)
        records = response.read()
        tree = eT.fromstring(records)
        count = int(tree.find("records").attrib['total-count'])
        logging.info("Found {0:d} records published in {1:d}".format(count, year))
        if count == 0:
            continue


        records_dir_path = os.path.join(OUTPUT_DIR, str(year))
        if not os.path.exists(records_dir_path):
            os.makedirs(records_dir_path)

        resumption_url = fetch_links(records, records_dir_path)
        while resumption_url:
            req = urllib.request.Request(resumption_url)
            response = urlopen(req)
            records = response.read()
            resumption_url = fetch_links(records, records_dir_path)







