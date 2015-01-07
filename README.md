[![Build Status](https://travis-ci.org/apurdy/solr-pubmed.svg?branch=master)](https://travis-ci.org/apurdy/solr-pubmed)
=========

An [Apache Solr](http://lucene.apache.org/solr/) based tool for fetching, indexing and searching PubMed/MEDLINE records. 

Requirements
---------
* [Apache Maven](http://maven.apache.org/download.cgi)
* [Oracle JDK 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
* Python 2.7
* [pip](https://pip.pypa.io/en/latest/index.html)

[Anaconda](https://store.continuum.io/cshop/anaconda/) is an easy to install Python distribution that doesn’t require root or local admin privileges.

If you use the default Python, make sure you have the python-dev package installed. It contains headers needed for building some of the Python packages.

[virtualenv](https://virtualenv.pypa.io/en/latest/) is recommended for create isolated Python development environments.

Get the code
------------
This repo contains submodules, so use the following command to clone the repository and it's dependencies:

    git clone --recursive https://github.com/apurdy/solr-pubmed.git
    
If you've already cloned the repository and you need to initialize the submodules you can run:

    git submodule init
    git submodule update

Building
---------
To build the project, run the following: 

    cd solr-pubmed
    mvn install
    
The build artifacts will be installed to your local maven repository (typically `~/.m2` on Unix systems or `C:\Documents and Settings\{your-username}\.m2` on Windows). 

Running Tests
-------------
To run all unit tests, run the following command in the project root directory:

    mvn test

Starting the Solr Server
-------------
To run the configured Solr instance on a local Jetty server, use:

    cd solr-pubmed/solr-config
    mvn jetty:run-war

The Solr instance will run on [http://localhost:8983/core0/](http://localhost:8983/core0/) by default.

Fetching PubMed and PMC records
-------------
The retrieval directory contains python scripts for downloading PubMed records using the Entrez E-Utils and PMC Open Access Web services. 

[virtualenv](http://docs.python-guide.org/en/latest/dev/virtualenvs/) is recommended for managing your python development environments. 

If you haven't set up a virtualenv environment already you can run:

    cd solr-pubmed/entrez-scripts
    virtualenv .
    source bin/activate

To ensure you have the neccesary python packages needed to run the scripts, run the following:

    pip install -r requirements.txt
    
Indexing the sample records
-------------
A collection of utility methods for indexing MEDLINE records can be found in the `ingestion.SolrUtils` class in the `entrez-parsers` module.

Running the Solr frontend
-------------
The `solr-frontend` module consists of a lightweight [Flask](http://flask.pocoo.org/) application, which is essentially a thin presentation layer built on the Solr REST interface. The UI provides several features including keyword search, faceted searching, dynamic faceting using document clustering and highlighting. 

If you haven't set up a virtualenv environment already you can run:

    cd solr-pubmed/solr-frontend
    virtualenv .
    source bin/activate
    
Then install the python packages:

    pip install -r requirements.txt
    
If you're on Windows using the Anaconda python distribution, you may run into issues installing the MarkupSafe package. If so, you can solve this issue by modifying `cygwinccompiler.py` as desribed here: [http://bugs.python.org/issue21821](http://bugs.python.org/issue21821)

To run the app:

    python app.py

The Flask server will run on [http://localhost:5000](http://localhost:5000) by default.
