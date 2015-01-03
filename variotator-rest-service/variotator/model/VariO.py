#!/usr/bin/python
# -*- coding: utf-8 -*-
import pkgutil
from rdflib import ConjunctiveGraph

"""
    Class VariO for the VariO variation ontology annotation tool
    Purpose: Provide methods for loading and processing VariO ontology terms
    
    @author: Gerard C.P. Schaafsma <Gerard.Schaafsma@med.lu.se>
    @date: 2013-08-08
    @copyright: Lund University, Dept. of Experimental Medical Science
    @version: v01.r123

"""

# Define a prefix
OBO_PRE = u'http://purl.obolibrary.org/obo/'


class VariO:
    """
    Holds methods for loading and processing VariO ontology terms. The whole
    ontology is loaded in a ConjunctiveGraph object
    
    @requires: rdflib.graph
    """

    def __init__(self):
        """
        Initiate a ConjunctiveGraph object
        """
        owl_data = pkgutil.get_data("variotator", "vario.owl")

        # for line in owl_data:
        # print "line in owl_data is: ", line
        self.parsed = ConjunctiveGraph().parse(data=owl_data)

    @staticmethod
    def get_term_id(term, graph):
        """
        Find the termId's with label for a given search term
        return type is a SPARQLQueryResult class instance. The search term
        is compared to VariO term's label.
        
        @type term: string
        @arg term: search term, which is compared to term labels
        @type graph: Conjunctive Graph
        @arg graph: stored graph of the VariO ontology
        
        @rtype: rdfextras.sparql.query.SPARQLQueryResult object
        @return: query result, VariO terms id and label
        """

        result = ""
        query = """
        PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>
        SELECT ?class ?label 
        WHERE {
            ?class rdfs:label ?label .
            FILTER (?label=\"%s\"^^<http://www.w3.org/2001/XMLSchema#string>) .
        }
        """ % term
        try:
            result = graph.query(query)
        except:
            # TODO this is not a good exception, I guess
            # This way the execution of the script just stops
            print "No VariO terms found in VariO.getTermID for your description"
        del query

        if result:
            return result
        else:
            return None

    @staticmethod
    def get_full_term(term_id, graph):
        """
        Find full VariO for a given search term
        (e.g. "http://purl.obolibrary.org/obo/VariO_0084")
        return type is a SPARQLQueryResult class instance.
        
        @type term_id: string
        @arg term_id: search term (VariO id with purl prefix
        @type graph: Conjunctive Graph
        @arg graph: stored graph of the VariO ontology
        
        @rtype: rdfextras.sparql.query.SPARQLQueryResult object
        @return: query result, VariO terms id and label
        """

        result = ""
        id_query = """
        PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>
        SELECT ?class ?label
        WHERE {
            ?class rdfs:label ?label .
            FILTER (?class= <%s>)
        }
        """ % term_id
        try:
            result = graph.query(id_query)
        except:
            # TODO this is not a good exception, I guess
            # This way the execution of the script just stops
            print "No VariO terms found for your description"
        del id_query

        if result:
            return result
        else:
            return None

    @staticmethod
    def get_part_of_term(graph, level=None):
        """
        Find 'part of' terms
        TODO query should be adapted to find terms only for the given term
        
        @type graph: Conjunctive Graph
        @arg graph: stored graph of the VariO ontology
        @type level: string
        @arg level: variant level, either DNA, RNA or protein
        
        @rtype: list of rdfextras.sparql.query.SPARQLQueryResult objects
        @return: list of parent terms
        
        """

        result = ""
        part_of_query = """
        PREFIX owl:<http://www.w3.org/2002/07/owl#>
        PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>
        SELECT DISTINCT ?term ?label
        WHERE { ?id owl:someValuesFrom ?term .
                ?term rdfs:label ?label
        FILTER regex(?label, \"%s\") }""" % level
        try:
            result = graph.query(part_of_query)
        except:
            # TODO this is not a good exception
            print "No result for a 'part of' term"
        del part_of_query

        if result:
            return result
        else:
            return None

    def get_ancestors(self, term, graph, level=None,
                      prev_parent="", parents_list=None):
        """
        Find all ancestors for a VariO term
        argument can be a tuple or a SPARQLQueryResult class instance
        
        @type term: string, tuple or SPARQLQueryResult object
        @arg term: VariO term (id and label)
        @type graph: Conjunctive Graph
        @arg graph: stored graph of the VariO ontology
        @type level: string
        @arg level: variant level, either DNA, RNA or protein
        @type prev_parent: rdfextras.sparql.query.SPARQLQueryResult object
        @arg prev_parent: previous parent
        @type parents_list: list
        @arg parents_list: list of rdfextras.sparql.query.SPARQLQueryResult
        objects
        
        @rtype: list of rdfextras.sparql.query.SPARQLQueryResult objects
        @return: list of parent terms
        """
        if term.__class__.__name__ == "SPARQLQueryResult":
            search_term = tuple(term)[0][0]
        elif term.__class__.__name__ == "ResultRow":
            search_term = term[0]
        elif term.__class__.__name__ == "SPARQLResult":
            search_term = tuple(term)[0][0]
        elif type(term) == tuple:
            search_term = term[0]
        elif type(term) == str:
            search_term = term
        # Initiate an ancestorsList only when there is none
        if parents_list is None:
            parents_list = []
        parent_query = """
        PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>
        SELECT ?term ?label
        WHERE {
            ?id rdfs:subClassOf ?term .
            ?term rdfs:label ?label .
            FILTER (?id= <%s>)
        }""" % search_term
        parent = graph.query(parent_query)
        del parent_query

        # Now if you found a parent go find the parent of that parent
        if parent:
            # Find the parent of this parent
            parents_list.append(parent)
            prev_parent = parent
            return self.get_ancestors(parent, graph, level,
                                      prev_parent, parents_list)
        else:
            # No parent found, you are at the root term
            # OR you are at a 'part of' relationship
            # in case of a transversion, there is no prevParent
            if (prev_parent and tuple(prev_parent)[0][0].replace(OBO_PRE, "") != "VariO_0001") \
                    or str(search_term) == "http://purl.obolibrary.org/obo/VariO_0316":
                # TODO do this once in VariO.__init__ or somewhere else
                # See if it concerns a part of term
                part_of = self.get_part_of_term(graph, level)
                # Append to the parents list when found
                if part_of:
                    parents_list.append(part_of)
                return self.get_ancestors(part_of, graph, level,
                                          prev_parent, parents_list)
            else:
                return parents_list

    def get_ancestors_attr(self, term, graph, prev_terms=None, level=None, prev_parent="", parents_list=None):
        """
        Find all ancestors for a ECO term
        argument can be a tuple or a SPARQLQueryResult class instance
        
        @type term: string, tuple or SPARQLQueryResult object
        @arg term: ECO term (id and label)
        @type prev_terms: string, tuple or SPARQLQueryResult object
        @arg prev_terms: ECO term (id and label)
        @type graph: Conjunctive Graph
        @arg graph: stored graph of the ECO ontology
        @type level: string
        @arg level: variant level, either DNA, RNA or protein
        @type prev_parent: rdfextras.sparql.query.SPARQLQueryResult object
        @arg prev_parent: previous parent
        @type parents_list: list
        @arg parents_list: list of rdfextras.sparql.query.SPARQLQueryResult
        objects
        
        @rtype: list of rdfextras.sparql.query.SPARQLQueryResult objects
        @return: list of parent terms
        """
        if not prev_terms:
            prev_terms = []
        i = 0
        for prevTerm in prev_terms:
            prev_terms[i] = prevTerm.replace("VariO:", "VariO_").strip()
            i += 1
        # for
        if term.__class__.__name__ == "SPARQLQueryResult":
            search_term = tuple(term)[0][0]
        elif term.__class__.__name__ == "ResultRow":
            search_term = term[0]
        elif type(term) == tuple:
            search_term = tuple(term)[0]
        elif type(term) == dict:
            search_term = term['term']
        elif type(term) == str:
            search_term = term
        # Initiate an ancestorsList only when there is none
        if parents_list is None:
            parents_list = []
        parent_query = """
        PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>
        SELECT ?term ?label
        WHERE {
            ?id rdfs:subClassOf ?term .
            ?term rdfs:label ?label .
            FILTER (?id= <%s>)
        }""" % search_term
        parents = graph.query(parent_query)
        del parent_query

        # Now if you found a parent go find the parent of that parent
        if parents:
            # Find the parent of this parent
            if len(parents) > 1:
                # for entry in parents.bindings:
                for entry in parents:
                    for prev in prev_terms:
                        # if str(entry["term"]) == prev:
                        if str(entry[0]) == prev:
                            parent = entry
            else:
                parent = tuple(parents)[0]
            parents_list.append(parent)
            prev_parent = parent
            return self.get_ancestors_attr(parent, graph, prev_terms, level,
                                           prev_parent, parents_list)
        else:
            # No parent found, you are at the root term
            # OR you are at a 'part of' relationship
            # in case of a transversion, there is no prevParent
            if prev_parent and prev_parent[0].replace(OBO_PRE, "") != "VariO_0001":
                # TODO do this once in VariO.__init__ or somewhere else
                # See if it concerns a part of term
                part_of = self.get_part_of_term(graph, level)
                # Append to the parents list when found
                if part_of:
                    parents_list.append(part_of)
                return self.get_ancestors_attr(part_of, graph, prev_terms, level,
                                               prev_parent, parents_list)
            else:
                return parents_list

    @staticmethod
    def get_child_terms(term, graph):
        """
        Find child terms for a given term
        
        @type term: string
        @arg term: search term
        @type graph: Conjunctive Graph
        @arg graph: stored graph of the VariO ontology
        
        @rtype: rdfextras.sparql.query.SPARQLQueryResult object
        @return: query result, VariO terms id and label
        """
        if term.__class__.__name__ == "ResultRow":
            term = tuple(term)[0]
        # print "term in line %d is now: %s" % (lineno(), term)
        result = ""
        query = """
        PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>
        SELECT ?class ?label
        WHERE {
            ?class rdfs:subClassOf <%s> .
            ?class rdfs:label ?label .
        }""" % term
        try:
            result = graph.query(query)
        except:
            # TODO this is not a good exception, I guess
            # This way the execution of the script just stops
            print "No child VariO terms found for your description"
        del query

        if result:
            return result
        else:
            return None

    def get_all_child_terms(self, term, graph):
        """
        @type term: string
        @arg term: search term, format http://purl.obolibrary.org/obo/VariO_0001    
        @type graph: Conjunctive Graph
        @arg graph: stored graph of the VariO ontology
        
        """
        sp_pos = term.find(' ')
        if sp_pos != -1:
            term = term[:sp_pos]
        del sp_pos
        desc_list = [term.replace('VariO:', 'VariO_')]
        for entry in desc_list:
            children = self.get_child_terms((entry[0] if type(entry) == tuple else entry), graph)
            if children:
                for child in children:
                    if child not in desc_list:
                        # Can you do this, changing an array while looping through it?
                        desc_list.append(child)
                        # for
                        # TODO what if you do not find children
                        # else :
                        # print "No children found for: ", entry
                        # if
        # for
        return desc_list  # first element is original search term!