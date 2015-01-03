#!/usr/bin/python
# -*- coding: utf-8 -*-
import re

"""
    Class VariationTypes for the VariO variation ontology annotation tool
    Purpose: Methods for translating variant descriptions to variation types
    
    @author: Gerard C.P. Schaafsma <Gerard.Schaafsma@med.lu.se>
    @date: 2013-06-25
    @copyright: Lund University, Dept. of Experimental Medical Science
    @version: v01.r100

"""

# Define a prefix
# OBO_PRE = u'http://purl.obolibrary.org/obo/'


class VariationTypes:
    """
    Holds methods for translating variant descriptions to variation types

    @requires: re
    """

    def __init__(self):
        """
        Initiates regular expression patterns
        """

        # Constants
        self.AMPH_INS = "amphigoric amino acid insertion"
        self.AMPH_DEL = "amphigoric amino acid deletion"
        self.SEQRET_DEL = "sequence retaining amino acid deletion"
        self.SEQRET_INS = "sequence retaining protein chain insertion"

        # Regex patterns
        # amino acids
        self.aaPattern = re.compile(
            "Ala(\d+|ext)|A(\d+|ext)|Arg(\d+|ext)|R(\d+|ext)|Asn(\d+|ext)|N(\d+|ext)|Asp(\d+|ext)|D(\d+|ext)|"
            "Cys(\d+|ext)|C(\d+|ext)|Gln(\d+|ext)|Q(\d+|ext)|Glu(\d+|ext)|E(\d+|ext)|Gly(\d+|ext)|G(\d+|ext)|"
            "His(\d+|ext)|H(\d+|ext)|Leu(\d+|ext)|L(\d+|ext)|Ile(\d+|ext)|I(\d+|ext)|Lys(\d+|ext)|K(\d+|ext)|"
            "Met(\d+|ext)|M(\d+|ext)|Phe(\d+|ext)|F(\d+|ext)|Pro(\d+|ext)|P(\d+|ext)|Ser(\d+|ext)|S(\d+|ext)|"
            "Thr(\d+|ext)|T(\d+|ext)|Trp(\d+|ext)|W(\d+|ext)|Tyr(\d+|ext)|Y(\d+|ext)|Val(\d+|ext)|V(\d+|ext)|0|=")
        # protein substitution
        self.protSubPattern = re.compile(
            "^p\.\(?\[?((\*|[A-Z]([a-z]{2})?)(ext\*)?\d+_?(\*|[A-Z]([a-z]{2})?))\]?\)?")
        # missing protein
        # self.protMissPattern = re.compile("^p\.0")
        self.protMissPattern = re.compile("^p\.(0|M1\*\)?|Met1\*\)?)")
        # missing RNA
        self.rnaMissPattern = re.compile("^r\.0")
        # deletion
        self.delPattern = re.compile("^[crpg]\..*(del)")
        # duplication
        self.dupPattern = re.compile("^[crpg]\..*(dup)")
        # insertion
        self.insPattern = re.compile("^[crpg]\..*(ins).*")
        # inversion
        self.invPattern = re.compile("^[crpg]\..*(inv).*")
        # # indel
        # self.delinsPattern =         # re.compile("^[crg]\.(.*)(del[ACGTU]*ins)(.*)")
        # DNA indel
        self.dnaDelinsPattern = re.compile("^[cg]\.(.*)(del[ACGT]*ins)(.*)")
        # RNA indel
        self.rnaDelinsPattern = re.compile("^r\.(.*)(del[ACGU]*ins)(.*)")
        # protein indel
        self.protDelinsPattern = re.compile("^p\.(.*)(del([A-Z]([a-z]{2})?)*ins)(.*)")
        # frameshift
        self.fsPattern = re.compile("^p\.\(?[A-Z]([a-z]{2})?\d+([A-Z]([a-z]{2})?)?fs(\*\d+)?\)?")
        # purine transition
        self.subPurPattern = re.compile("^[cg]\.[\*\-]?\d+([\+\-]\d+)?((A>G)|(G>A))")
        # pyrimidine transition
        self.subPyrPattern = re.compile("^[cg]\.[\*\-]?\d+([\+\-]\d+)?((C>T)|(T>C))")
        # transversions
        self.subTransPattern = re.compile(
            "^[cg]\.[\*\-]?\d+([\+\-]\d+)?((A>C)|(C>A)|(G>T)|(T>G)|(A>T)|(T>A)|(G>C)|(C>G))")
        # RNA purine transition
        self.subPurRNAPattern = re.compile("^r\.[\*\-]?\d+([\+\-]\d+)?((A>G)|(G>A))")
        # RNA pyrimidine transition
        self.subPyrRNAPattern = re.compile("^r\.[\*\-]?\d+([\+\-]\d+)?((C>U)|(U>C))")
        # RNA transversions
        self.subTransRNAPattern = re.compile(
            "^r\.[\*\-]?\d+([\+\-]\d+)?((A>C)|(C>A)|(G>U)|(U>G)|(A>U)|(U>A)|(G>C)|(C>G))")
        # Added \[ to regular expression
        # DNA translocation
        self.transDnaPattern = re.compile("^([\[\(])?(t\().*c\..*")
        # Added \[ to regular expression
        # RNA translocation
        self.transRnaPattern = re.compile("^([\[\(])?(t\().*r\..*")
        # RNA descriptions should be one of these
        self.allVarsPattern = re.compile("ins|inv|del|delins|>|dup|spl|0")
        # RNA descriptions are not allowed to contain one of these
        # self.notRnaPattern = re.compile("[BDEFHIJKLMNOPQRSTVWXYZ]")
        # a t or a T is accepted and replaced in main script annotator.py
        self.notRnaPattern = re.compile("[BDEFHIJKLMNOPQRSVWXYZ]")
        # initiation codon
        self.iccPattern = re.compile(
            "(^r\.([123])((_)(\d*))?([ACGU]|del|dup|inv).*)|(^r\.([12])_(\d+)ins[ACGU]+)")
        # RNA deletion
        self.delRNApattern = re.compile("^r\.(\d+)_?(\d*)(del)")
        # RNA insertion
        self.insRNApattern = re.compile("^r\.(\d+)_(\d+)ins([ACGU]+)")
        # RNA duplication
        self.dupRNApattern = re.compile("^r\.(\d+)_?(\d*)(dup)")
        # RNA inversion
        self.invRNApattern = re.compile("^r\.(\d+)_(\d+)(inv)$")

    # __init__

    @staticmethod
    def var_level(var):
        """
        Determine the level (DNA, RNA, protein) of a variant description

        @type var: string
        @arg var: variant description according to HGVS guidelines (without
        reference)

        @rtype: string
        @return: level of the variant (DNA, RNA or protein)
        """
        # Added "(t", "[t"
        if var.startswith(("t", "(t", "[t")):
            level = "translocation"
        elif var.startswith("p."):
            level = "protein"
        elif var.startswith("r."):
            level = "RNA"
        elif var.find("c.") != -1 or var.find("g.") != -1:
            level = "DNA"
        else:
            level = ""
        # if elif

        return level

    # varLevel

    def check_rna_type(self, var):
        """
        Checks a RNA variant description

        @type var: string
        @arg var: RNA variant description (HGVS notation
        without reference)
        
        @rtype: boolean
        @return: True if variant is a proper RNA description
        """

        if self.allVarsPattern.search(var) and not self.notRnaPattern.search(var):
            # print "checkRnaType is true"
            return True
        else:
            return False
            # if else

    # checkRnaType

    def check_prot_type(self, var):
        """
        Checks a protein variant description
        
        @type var: string
        @arg var: protein variant description (HGVS notation
        without reference)
        
        @rtype: boolean
        @return: True if variant is a proper protein description
        """

        if self.aaPattern.search(var):
            # if var contains an underscore it must also contain one
            # of ins, del, dup or delins
            if var.find("_") != -1 and var.find("ins") == -1 and var.find("del") == -1 and var.find(
                    "dup") == -1 and var.find("delins") == -1:
                return False
            else:
                return True
                # if else
        else:
            return False
            # if else

    # checkProtType

    def dna_type(self, var):
        """
        Translates a coding DNA variant description (c.) to a DNA variation
        type

        @type var: string
        @arg var: coding DNA variant description (HGVS notation
        without reference)
        
        @rtype: string
        @return: DNA variant description as in the VariO ontology
        """

        if self.subPurPattern.match(var):
            result = "purine transition"
        elif self.subPyrPattern.match(var):
            result = "pyrimidine transition"
        elif self.subTransPattern.match(var):
            result = "transversion"
        elif self.dnaDelinsPattern.match(var):
            result = "DNA indel"
        elif self.delPattern.match(var):
            result = "DNA deletion"
        elif self.dupPattern.match(var) or self.insPattern.match(var):
            result = "DNA insertion"
        elif self.invPattern.match(var):
            result = "DNA inversion"
        else:
            result = ""
        # if elif else

        return result

    # dnaType

    def rna_type(self, var):
        """
        Translates an RNA variant description (r.) to an RNA variation type
        
        @type var: string
        @arg var: RNA variant description (HGVS notation
        without reference)
        
        @rtype: string
        @return: RNA variant description as in the VariO ontology
        """

        if self.subPurRNAPattern.match(var):
            result = "purine transition"
        elif self.subPyrRNAPattern.match(var):
            result = "pyrimidine transition"
        elif self.subTransRNAPattern.match(var):
            result = "transversion"
        elif self.rnaDelinsPattern.match(var):
            # print "rnaDelinsPattern"
            result = "RNA indel"
        elif self.delPattern.match(var):
            result = "RNA deletion"
        elif self.invRNApattern.match(var):
            result = "RNA inversion"
        elif self.dupPattern.match(var) or self.insPattern.match(var):
            result = "RNA insertion"
        elif self.rnaMissPattern.match(var):
            result = "missing RNA"
        else:
            result = ""
        # if elif else

        return result

    # rnaType

    def protein_type(self, var):
        """
        Translates a protein variant description (p.) to a (protein) variation
        type using regular expressions
        
        @type var: string
        @arg var: protein variant description (3-letter HGVS notation
        without reference)
        
        @rtype: string
        @return: protein variant description as in the VariO ontology
        """

        if self.protMissPattern.match(var):
            result = "missing protein"
        elif (var.endswith("*") or var.endswith("*)") or var.endswith("Ter") or var.endswith("Ter)")) and var.find(
                "fs") == -1:
            # make exemption for nonsense change
            result = "protein truncation"
        elif var.find("fs*") != -1 or var.find("Ter") != -1 or var.endswith("fs") or var.find("ext*") != -1:
            # amphigoric amino acid indel
            result = "amphigoric amino acid indel"
        elif self.protDelinsPattern.match(var):
            result = "sequence retaining amino acid indel"
        elif var.find("del") != -1 and var.find("ins") != -1:
            # sequence retaining amino acid indel
            result = "sequence retaining amino acid indel"
        elif var.find("ext") != -1 and not self.protSubPattern.match(var):
            # make exemption for new translation initiation site (upstream)
            # and translation termination codon
            result = "amphigoric amino acid insertion"
        elif self.fsPattern.match(var):
            result = "amphigoric"
        elif self.delPattern.match(var):
            result = "sequence retaining amino acid deletion"
        elif self.dupPattern.match(var) or self.insPattern.match(var):
            # duplication is seen as an insertion
            result = "amino acid insertion"
        elif self.protSubPattern.match(var):
            if self.protSubPattern.match(var).group(2) != self.protSubPattern.match(var).group(5):
                # amino acids not allowed to be the same
                result = "amino acid substitution"
            else:
                result = ""
        else:
            result = ""
        # if elif .. else

        return result

    # proteinType

    def trans_type(self, var):
        """
        Translates a translocation variant description (t(.,.)) to a 
        translocatin variation type using regular expressions
        
        @type var: string
        @arg var: translocation description
        
        @rtype: string
        @return: translocation variant description as in the VariO ontology
        """

        if self.transDnaPattern.match(var):
            result = "DNA translocation"
        elif self.transRnaPattern.search(var):
            result = "RNA translocation"
        else:
            result = ""
        # if elif else

        return result

    # transType

    def rna_specs(self, var):
        """
        Provide specific VariO terms for an RNA variant description. This
        method does not use protein (variant) information.
        
        @type var: string
        @arg var: RNA variant description according to HGVS guidelines
        (without reference)
        
        @rtype: list
        @return: VariO terms
        """

        vario_terms = []
        icc_match = self.iccPattern.search(var)  # initiation codon
        ins_match = self.insRNApattern.search(var)  # insertion
        del_match = self.delRNApattern.search(var)  # deletion
        dup_match = self.dupRNApattern.search(var)  # duplication
        inv_match = self.invRNApattern.search(var)  # inversion
        delins_match = self.rnaDelinsPattern.search(var)  # indel added 2013-06-25
        # initiation codon change
        if icc_match and ((icc_match.group(2) != icc_match.group(5)) ^ (icc_match.group(8) != icc_match.group(9))):
            # TODO simplify this one?
            if ins_match and ins_match.group(1) != ins_match.group(2) and int(ins_match.group(2)) - int(
                    ins_match.group(1)) == 1:
                vario_terms.append("initiation codon change")
            elif not ins_match:
                vario_terms.append("initiation codon change")
            # if elif
            if not (del_match or ins_match or dup_match or inv_match):
                vario_terms.append("missense variation")
        # if
        del icc_match
        # RNA deletion or insertion
        # corrected this on 2013-06-25
        if var.find("del") != -1 and var.find("ins") == -1:
            if del_match and del_match.group(2) != "" \
                    and (int(del_match.group(2)) - int(del_match.group(1)) + 1) % 3 == 0:
                vario_terms.append("in-frame deletion")
            elif del_match and del_match.group(1) != del_match.group(2):
                vario_terms.append("out-of-frame deletion")
            # if elif
            del del_match
        elif ins_match:
            if len(ins_match.group(3)) % 3 == 0 and ins_match.group(1) != ins_match.group(2) and int(
                    ins_match.group(2)) - int(ins_match.group(1)) == 1:
                vario_terms.append("in-frame insertion")
            elif ins_match.group(1) != ins_match.group(2) and int(ins_match.group(2)) - int(ins_match.group(1)) == 1:
                vario_terms.append("out-of-frame insertion")
            # if elif
            del ins_match
        elif dup_match:
            if dup_match.group(1) and dup_match.group(2) \
                    and ((int(dup_match.group(2)) - int(dup_match.group(1))) + 1) % 3 == 0 \
                    and dup_match.group(1) != dup_match.group(2):
                vario_terms.append("in-frame insertion")
            elif dup_match.group(1) != dup_match.group(2):
                vario_terms.append("out-of-frame insertion")
            # if elif
            del dup_match
        elif delins_match:  # added on 2013-06-25
            del_pos = delins_match.group(1)
            if del_pos.find("_") == -1:
                del_len = 1
            else:
                del_len = int(del_pos[del_pos.find("_") + 1:]) - int(del_pos[:del_pos.find("_")]) + 1
            ins_len = len(delins_match.group(3))
            delins_len = ins_len - del_len
            if delins_len % 3 == 0:
                vario_terms.append("in-frame indel")
            else:
                vario_terms.append("out-of-frame indel")
            del delins_match
            del del_pos, del_len, ins_len, delins_len
        elif inv_match:
            if inv_match.group(1) == inv_match.group(2):
                # Do nothing
                del inv_match
        else:
            vario_terms.append(self.rna_type(var))

        return vario_terms