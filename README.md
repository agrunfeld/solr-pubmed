[![Build Status](https://travis-ci.org/apurdy/solr-project.svg?branch=master)](https://travis-ci.org/apurdy/solr-project)
=========

An [Apache Solr](http://lucene.apache.org/solr/) based tool for fetching, indexing and searching PubMed/MEDLINE records. 
Requirements
---------

[Apache Maven](http://maven.apache.org/download.cgi)

Building
---------

    mvn compile

Running Tests
-------------
To execute all unit tests, use:

    mvn test

To execute integration tests, use:

    mvn verify

Starting the Solr Server
-------------
To run the configured Solr instance on a local Jetty server, use:

    mvn jetty:run-war

The Solr instance will run on [http://localhost:8983/core0/](http://localhost:8983/core0/) by default.

Indexing the sample records
-------------
To index a small collection MEDLINE records run the following script in the ingestion module:

    TestScripts#ImportSampleData()

Fetching PubMed and PMC records
-------------
The retrieval directory contains python scripts for downloading PubMed records using the Entrez E-Utils and PMC Open Access Web services. 

We recommend using [virtualenv](http://docs.python-guide.org/en/latest/dev/virtualenvs/) to manage your python development environments. 

To ensure you have the neccesary python dependencies needed to run the scripts, run the following command in the root directory of the retrieval module:

    pip install -r requirements.txt

Running the Flask application
-------------
The webapp directory contains a [Flask](http://flask.pocoo.org/)-based search UI, which provides several features including faceted searching and highlighting. Again make sure the required python packages are installed using pip:

    pip install -r requirements.txt

To run the app:

    python app.py

The Flask server will run on [http://localhost:5000](http://localhost:5000) by default.
