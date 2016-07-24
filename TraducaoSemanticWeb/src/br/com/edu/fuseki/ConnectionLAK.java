/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.fuseki;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

/**
 *
 * @author angelo
 */
public class ConnectionLAK {

    public ArrayList<String> buscaURIS() {
        ArrayList<String> uris = new ArrayList<String>();
        String serviceURI = "http://localhost:3030/TopWikiLakFinal";
        String query = "PREFIX led:<http://data.linkededucation.org/ns/linked-education.rdf#>\n"
                + "PREFIX swrc:<http://swrc.ontoware.org/ontology#>\n"
                + "SELECT ?paper ?fulltext \n"
                + "  WHERE { ?paper led:body ?fulltext . ?paper swrc:year ?year . \n"
                + "           FILTER (?year = \"2014\") }";

        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI, query);
        ResultSet resultSet = q.execSelect();
        while (resultSet.hasNext()) {
            QuerySolution soln = resultSet.nextSolution();
            String uri_paper = String.valueOf(soln.get("paper"));
            uris.add(uri_paper);
        }

//        for(int i = 0; i < uris.size(); i++){
//            System.out.println(uris.get(i));
//        }
        return uris;

    }

    public ArrayList<String> BuscarTodosTopicos() {
        ArrayList<String> todas_uris = new ArrayList<String>();
        String serviceURI = "http://localhost:3030/TopWikiLakFinal";
        String query = "PREFIX led:<http://data.linkededucation.org/ns/linked-education.rdf#>\n"
                + "PREFIX swrc:<http://swrc.ontoware.org/ontology#>\n"
                + "PREFIX prov:<http://www.w3.org/ns/prov#> \n"
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                + "\n"
                + "SELECT DISTINCT  ?topicos\n"
                + "  WHERE { ?uripaper swrc:year ?year . ?uripaper dc:subject ?blank_node . ?blank_node prov:pairKey ?topicos FILTER (?year = \"2014\") }";

        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI, query);
        ResultSet resultSet = q.execSelect();
        while (resultSet.hasNext()) {
            QuerySolution soln = resultSet.nextSolution();
            String todos_topicos = String.valueOf(soln.getLiteral("topicos"));
            todas_uris.add(todos_topicos);
            //  System.out.println(topicos + " " + valor_confianca);
        }
        return todas_uris;

    }

    public ArrayList<Topicos> BuscarTopicos(String uri) {
        ArrayList<Topicos> valores = new ArrayList<Topicos>();
        String uri_tratado = "<" + uri + ">";
        //uri = "<http://data.linkededucation.org/resource/lak/conference/edm2014/paper/494>";
        String serviceURI = "http://localhost:3030/TopWikiLakFinal";
        String query = "PREFIX led:<http://data.linkededucation.org/ns/linked-education.rdf#>\n"
                + "PREFIX swrc:<http://swrc.ontoware.org/ontology#>\n"
                + "PREFIX prov:<http://www.w3.org/ns/prov#> \n"
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                + "\n"
                + "SELECT DISTINCT ?topicos ?valor_confianca\n"
                + "  WHERE { " + uri_tratado + " swrc:year ?year . " + uri_tratado + " dc:subject ?blank_node . ?blank_node prov:pairKey ?topicos .?blank_node prov:pairValue ?valor_confianca FILTER (?year = \"2014\") }";

        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI, query);
        ResultSet resultSet = q.execSelect();
        while (resultSet.hasNext()) {
            Topicos topic = new Topicos();
            QuerySolution soln = resultSet.nextSolution();
            String s_valor_confianca = String.valueOf(soln.get("valor_confianca"));
            Double valor_confianca = Double.parseDouble(s_valor_confianca.replaceAll("%", ""));
            String topicos = String.valueOf(soln.get("topicos"));
            topic.setTopico(topicos);
            topic.setValor(valor_confianca);
            valores.add(topic);
          //  valores.add(valor_confianca);
            //  System.out.println(valor_confianca);
        }
        return valores;

        //  ResultSetFormatter.out(System.out, resultSet); 
    }

        //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // ResultSetFormatter.outputAsJSON(outputStream, resultSet);
    // String json = new String(outputStream.toByteArray());
    // return json;
}
