#!/usr/bin/python
# -*- coding: utf-8 -*-

"""
    VariO variation ontology annotation tool, the standalone version
    Purpose: Find VariO ontology terms for variant descriptions given as argument
    Variation type terms are returned, variant descriptions are NOT checked
    with Mutalyzer.
    Use (if run locally): python2.6 findVariOTermsSA.py 'variantDescription'
    e.g. python2.6 findVariOTermsSA.py 'NM_000051.2:c.22A>G'
    
    @author: Gerard C.P. Schaafsma <Gerard.Schaafsma@med.lu.se>
    @date: 2014-12-03
    @copyright: Lund University, Dept. of Experimental Medical Science
    @version: v01

"""
# Define a prefix
OBO_PRE = u'http://purl.obolibrary.org/obo/'

import re
import enchant
from variotator.model import VariO, VariationTypes


class Annotator:
    def __init__(self):
        # Initialize
        self.vario = VariO.VariO()
        # Initialize Parse the VariO owl file into a ConjunctiveGraph object
        self.parsed = self.vario.parsed

    def process_token(self, variant):
        var_result = []
        var_type = VariationTypes.VariationTypes()

        # Strip off reference sequence
        variant = variant[variant.find(":") + 1:]

        # Determine the level (DNA, RNA, protein or translocation)
        # of the variant description
        variant_level = var_type.var_level(variant)

        # Set flag to indicate if an RNA description is a prediction (from DNA)
        is_predicted_rna = False

        # Remove brackets, but not for translocation descriptions
        # Set flag to indicate if brackets were removed
        rem_bracket = False
        if variant_level != "translocation":
            new_var = variant.replace("(", "").replace(")", "")
            if new_var != variant:
                # Brackets were removed
                rem_bracket = True
            variant = new_var
            del new_var
        # if

        # Initiate a list for holding the search terms
        search_terms = []

        # Translate the variant description to a variation type search term
        if variant_level == "protein" and var_type.check_prot_type(variant) and \
                not (variant.endswith("?") or variant.endswith("?)")):
            # Check if proper protein description
            # Use proteinType method to determine the aa variation type term
            # Do not process uncertain descriptions
            if variant.endswith("M1*") or variant.endswith("Met1*") or \
                    variant.endswith("M1*)") or variant.endswith("Met1*)"):
                search_terms.append((variant, 'missing protein'))
            elif variant.endswith("*") or variant.endswith("*)") or \
                    variant.endswith("Ter") or variant.endswith("Ter)") or \
                    variant.endswith("fs"):
                # make exemption for nonsense change
                search_terms.append((variant, 'protein truncation'))
            elif variant.find("fs*") != -1 or variant.find("Ter") != -1:
                # amphigoric amino acid indel
                search_terms.append((variant, "amphigoric amino acid indel"))
            elif variant.find("del") != -1 and variant.find("ins") != -1:
                # sequence retaining amino acid indel
                search_terms.append((variant, "sequence retaining amino acid indel"))
            elif variant.find("ext") != -1:
                # exemption for new translation initiation site (upstream)
                # and translation termination codon
                search_terms.append((variant, var_type.AMPH_INS))
            else:
                search_terms.append((variant, var_type.protein_type(variant)))
                # if else
        elif variant_level == "RNA":
            # Capitalize all bases and then repair dUp
            variant = variant.replace("a", "A").replace("c", "C"). \
                replace("g", "G").replace("u", "U"). \
                replace("dUp", "dup").replace("t", "U"). \
                replace("T", "U")
            rna_spec_terms = var_type.rna_specs(variant)
            if rna_spec_terms:
                for term in rna_spec_terms:
                    search_terms.append((variant, term))
                    # if
        elif variant_level == "DNA":
            dna_label = var_type.dna_type(variant)
            search_terms.append((variant, dna_label))
        elif variant_level == "translocation":
            search_terms.append((variant, var_type.trans_type(variant)))
        found_terms = []
        # Now find VariO terms
        if len(search_terms) > 0 and search_terms[0][1]:
            # args.outputFile.write("Length search_terms > 0 and search_terms[0][1] exists\n")
            for term in search_terms:
                # Append the found VariO term as tuple (variant, term) to
                # the found_terms list
                # Add RNA variant description deduced from DNA description
                # TODO check this part!!
                # Use coding DNA description to specify protein description
                if term[1] == "amphigoric" and search_terms[0][1] == "DNA deletion":
                    found_terms.append((term[0], self.vario.get_term_id(var_type.AMPH_DEL, self.parsed)))
                elif term[1] == "amino acid deletion" and search_terms[0][1] == "DNA deletion":
                    found_terms.append((term[0], self.vario.get_term_id(var_type.SEQRET_DEL, self.parsed)))
                elif term[1] == "amphigoric" and search_terms[0][1] == "DNA insertion":
                    # TODO is this one necessary??
                    found_terms.append((term[0], self.vario.get_term_id(var_type.AMPH_INS, self.parsed)))
                elif term[1] == "amino acid insertion" \
                        and (search_terms[0][1] == "DNA insertion" or search_terms[0][1] == "amino acid insertion"):
                    found_terms.append((term[0], self.vario.get_term_id(var_type.SEQRET_INS, self.parsed)))
                else:
                    found_terms.append((term[0], self.vario.get_term_id(term[1], self.parsed)))
        else:
            found_terms = []

        # if else
        # Now go through the found terms
        if len(found_terms) > 0:
            # initiate list to keep track of previous ancestor terms, you
            # do not want to repeat
            prev_ancestors = []
            prev_vars = []
            for queryResult in found_terms:
                for term_tuple in queryResult[1]:
                    # Exclude terms which are not variation type terms
                    if (term_tuple[1].find("pericentric") == -1 and
                            term_tuple[1].find("paracentric") == -1 and
                            term_tuple[1].find("chromosomal") == -1 and
                            term_tuple[1].find("Robertsonian") == -1 and
                            term_tuple[1].find("RNA deletion editing") == -1 and
                            term_tuple[1].find("RNA insertion editing") == -1):
                        var = queryResult[0]
                        variant_level = var_type.var_level(var)
                        # Add brackets if they were removed before or if RNA
                        # description is a prediction from DNA
                        if variant_level == "RNA" and (rem_bracket or is_predicted_rna):
                            var = (var[:var.find(".")] + var[var.find("."):].lower()).replace("r.", "r.(") + ")"
                        elif rem_bracket:
                            var = (var[:var.find(".")] + var[var.find("."):]).replace("p.", "p.(") + ")"
                        # if
                        if variant_level == "RNA":
                            var = var.lower()
                        if var not in prev_vars:
                            var_result.append("%s" % var)
                            prev_vars.append(var)
                        # if
                        ancestors = self.vario.get_ancestors(term_tuple, self.parsed, variant_level)
                        for anc in reversed(ancestors):
                            # Do not print the root term
                            # term_id = anc.bindings[0]["term"].replace(OBO_PRE, "").replace("_", ":")
                            term_id = tuple(anc)[0][0].replace(OBO_PRE, "")
                            # VariO_0313 can be used both for DNA and RNA,
                            # so is allowed more than once
                            if term_id == "VariO_0313" or ((term_id not in prev_ancestors) and term_id != "VariO_0001"):
                                uri = "http://purl.obolibrary.org/obo/%s" % term_id
                                label = tuple(anc)[0][1]
                                var_result.append({'uri': uri, 'label': str(label)})
                                prev_ancestors.append(term_id)

                        uri = term_tuple[0]
                        label = term_tuple[1]
                        var_result.append({'uri': str(uri), 'label': str(label)})

            return var_result[1:]
        else:
            # print "No terms matching your variant description were found;\n" +\
            # "please check your variant description\n"
            return False

    def process_text(self, text):
        annotation_list = []
        en = enchant.Dict("en_US")

        tokens = re.finditer(r'\S+', text)

        filtered_tokens = (i for i in tokens if not (en.check(i.group)))

        for token in filtered_tokens:
            terms = self.process_token(token.group())
            if terms:
                annotation_list.append({'terms': terms, 'begin': token.start(), 'end': token.end()})
        results = {'annotations': annotation_list,
                   'count': len(annotation_list)}
        return results