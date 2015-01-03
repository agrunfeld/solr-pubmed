from unittest import TestCase
from variotator.annotator import Annotator

__author__ = 'alex'


class TestAnnotate(TestCase):
    def test_process_text(self):
        annotator = Annotator()
        text = "Causative GJB2 mutations were identified in 31 (15.2%) patients, and two common mutations, c.35delG " \
               "and L90P (c.269T>C), accounted for 72.1% and 9.8% of GJB2 disease alleles."
        result = annotator.process_text(text)
        print result
        self.assertEqual(result['count'], 2)