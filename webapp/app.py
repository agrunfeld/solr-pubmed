from flask import Flask, render_template, request
from flask_bootstrap import Bootstrap
import pysolr


# TODO: Flask-Appconfig
def create_app():
    app = Flask(__name__)
    Bootstrap(app)
    solr = pysolr.Solr('http://localhost:8983/core0/', timeout=10)

    # in a real app, these should be configured through Flask-Appconfig
    app.config['SECRET_KEY'] = 'devkey'

    @app.template_filter('pluralize')
    def pluralize(number, singular='', plural='s'):
        if number == 1:
            return singular
        else:
            return plural

    @app.route('/', methods=['GET'])
    def index():
        q = request.args.get('q', '')
        if q:
            results = solr.search(q, **{
                'hl': 'true',
            })
            return render_template('index.html', results=results, results_per_page=10)
        else:
            return render_template('index.html', results=None, results_per_page=10)
    return app


if __name__ == '__main__':
    create_app().run(debug=True)