__author__ = 'alex'
from flask.ext import restful
from flask.ext.restful import reqparse
from variotator.annotator import Annotator

post_parser = reqparse.RequestParser()
post_parser.add_argument(
    'text', type=str,
    required=True, help='Text to be analyzed',
)


class Variotator(restful.Resource):
    def __init__(self):
        self.annotator = Annotator()

    def post(self):
        args = post_parser.parse_args()
        text = args['text']
        results = self.annotator.process_text(text)

        return results, 201