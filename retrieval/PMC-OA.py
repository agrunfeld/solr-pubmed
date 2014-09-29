import datetime
import urllib
import urllib2
import xml.etree.ElementTree as eT
import os
## More info: http://www.ncbi.nlm.nih.gov/pmc/tools/ftp/
url = "http://www.pubmedcentral.nih.gov/utils/oa/oa.fcgi"

# request parameters
# may use a specific PMC id instead of dates
# format: tgz|pdf
params = urllib.urlencode({
    'from': datetime.date(2014, 1, 1),
    'format': 'tgz'
})
# get xml response
response = urllib2.urlopen(url, params).read()

# parse the xml response
tree = eT.fromstring(response)

# grab all the links to PMC artilces
link_gen = (link.attrib['href'] for link in tree.findall("./records/record/link"))

# download each record from the PMC ftp server
for link in link_gen:
    urllib.urlretrieve(link, os.path.join('ftp', link.split('/')[-1]))

# if response has > 1000 records, use the resumption token to retrieve more results
resumption = tree.findall("./records/resumption/link")
if resumption:
    token = resumption[0].attrib['token']
    print(token)