package uff.ic.swlab;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.LangBuilder;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFLanguages;

public class Id extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String ns = "http://swlab.ic.uff.br/id/";

	private ServletContext ctx = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ctx = config.getServletContext();
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
		Lang lang = LangBuilder.create("RDFa", "text/html").addFileExtensions("htm", "html", "xhtml", "xhtm").build();
		RDFLanguages.register(lang);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String uri = ns + request.getParameter("id");
		String accept = request.getHeader("Accept");
		Lang lang = detectLang(accept);

		Model model = getDescription(request, uri);
		OutputStream output = response.getOutputStream();

		if (lang == null) {
			response.setContentType("text/html");
			RDFTranslator.write(output, model, lang);
		} else {
			response.setContentType(lang.getContentType().getContentType());
			RDFDataMgr.write(output, model, lang);
		}

		output.flush();
		output.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().write("Error: POST method not implemented.");
	}

	private Lang detectLang(String accept) {
		Lang[] langs = { Lang.TURTLE, Lang.TTL, Lang.TRIG, Lang.TRIX, Lang.JSONLD, Lang.RDFJSON, Lang.RDFXML,
				Lang.RDFTHRIFT, Lang.NQUADS, Lang.NQ, Lang.NTRIPLES, Lang.N3, Lang.NT };
		for (Lang lang : langs)
			if (accept.toLowerCase().contains(lang.getHeaderString().toLowerCase()))
				return lang;
		return null;
	}

	private static Model getDescription(HttpServletRequest request, String uri) {
		String serviceURIBase = "http://localhost:3030/%1s";
		Model model = ModelFactory.createDefaultModel();

		for (String service : listFusekiServices(request))
			try {
				String serviceURI = String.format(serviceURIBase, service);
				model.setNsPrefix("my", "http://swlab.ic.uff.br/id/");
				model.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
				model.setNsPrefix("dcterms", "http://purl.org/dc/terms/");
				model.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
				model.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");

				String query = ""
						+ "construct {?s ?p ?o.} "
						+ "where { " + "{?s ?p ?o. filter(?s = <%1s>)} "
						+ "UNION {?s ?p ?o. filter(?o = <%2s>)} "
						+ "UNION {GRAPH ?g {?s ?p ?o. filter(?s = <%3s>) }} "
						+ "UNION {GRAPH ?g {?s ?p ?o. filter(?o = <%4s>) }} "
						+ "}";
				query = String.format(query, uri, uri, uri, uri);
				QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI, query);
				q.execConstruct(model);
			} catch (Exception e) {
				System.out.println(e);
			}
		return model;
	}

	private static List<String> listFusekiServices(HttpServletRequest request) {
		List<String> services = new ArrayList<>();
		List<String> configFiles = new ArrayList<>();
		String fuseki_home = System.getenv("FUSEKI_HOME");
		File dir = new File(fuseki_home + "/run/configuration");

		try {
			for (File file : dir.listFiles())
				if (file.isFile())
					configFiles.add(file.getName());
		} catch (Exception e) {
		}
		for (String configFile : configFiles)
			try {
				Model model = ModelFactory.createDefaultModel();
				model.read("file:///" + fuseki_home + "/run/configuration/" + configFile);
				String query = ""
						+ "prefix fuseki: <http://jena.apache.org/fuseki#> "
						+ "select ?name "
						+ "where {[] a fuseki:Service; "
						+ "fuseki:name ?name. "
						+ "}";
				QueryExecution q = QueryExecutionFactory.create(query, model);
				ResultSet result = q.execSelect();
				while (result.hasNext())
					services.add(result.next().get("name").toString());
			} catch (Exception e) {
			}
		return services;
	}

}
