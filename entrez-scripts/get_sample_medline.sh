#!/bin/bash
readonly PATH_TO_FILES=$HOME/medline-samples

mkdir -p -v ${PATH_TO_FILES}
wget -r -P ${PATH_TO_FILES} -nd -A gz ftp://ftp.nlm.nih.gov/nlmdata/sample/medline/