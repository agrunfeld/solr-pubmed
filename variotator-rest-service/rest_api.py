from flask import Flask
from flask.ext.restful import reqparse, Api
from resources.vario_resource import Variotator

app = Flask(__name__)
api = Api(app)

api.add_resource(Variotator, '/vario')

if __name__ == '__main__':
    app.run(debug=True)
