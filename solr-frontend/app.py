from flask import Flask, render_template, request, url_for
import pysolr
import math

facet_names = {
    'journal_title': 'Journal',
    'mesh_heading_list': 'MeSH Terms',
    'gene_symbol_list': 'Gene Symbol'
}
results_per_page = 20


# generate facet query for stock Solr request handler
# see http://wiki.apache.org/solr/SolrFacetingOverview#line-9
def get_facet_query(facet_field, facet_value):
    return u"{0}:\"{1}\"".format(facet_field, facet_value)


# TODO: Flask-Appconfig
def create_app():
    app = Flask(__name__)
    # app.config['SERVER_NAME'] = 'localhost:5002'
    solr = pysolr.Solr('http://localhost:8984/core0/', timeout=10)


    @app.template_filter('get_facet_name')
    def get_facet_name(key):
        return facet_names[key]


    @app.template_filter('pluralize')
    def pluralize(number, singular='', plural='s'):
        if number == 1:
            return singular
        else:
            return plural


    @app.route('/', methods=['GET'])
    def index():
        q = request.args.get('q', '')
        fq = request.args.get('fq', '')
        page = int(request.args.get('page', 0))
        if q:
            results = solr.search(q, **{
                'qt': 'select',
                'rows': results_per_page,
                'start': page,
                'fq': fq,
            })
            clusters = solr.search(q, **{
                'qt': 'clustering',
                'fq': fq,
            })
            return render_template('index.html',
                                   query=q,
                                   fq=fq,
                                   results=results,
                                   facets=results.facets,
                                   results_per_page=results_per_page,
                                   page=page,
                                   clusters=clusters.clusters
                                   )
        else:
            return render_template('landing.html')

    app.jinja_env.globals.update(max=max)
    app.jinja_env.globals.update(min=min)
    app.jinja_env.globals.update(ceil=math.ceil)
    app.jinja_env.globals.update(get_fq=get_facet_query)

    return app


if __name__ == '__main__':
    create_app().run(debug=True)