/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.fuseki;

import br.com.edu.artigo.Artigo;
import br.com.edu.artigo.Autor;
import br.com.edu.artigo.BuscaTopicos;
import br.com.edu.artigo.Topicos;
import java.util.ArrayList;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;

/**
 *
 * @author angelo
 */
public class Fuseki {

    public String buscarTitulo(String uri) {
        String uri_tratado = "<" + uri + ">";
        String serviceURI = "http://localhost:8080/fuseki/TopWikiLakFinal";
        String query = "PREFIX swrc:<http://swrc.ontoware.org/ontology#>\n"
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                + "\n"
                + "SELECT DISTINCT ?titulo WHERE {\n"
                + "" + uri_tratado + " dc:title ?titulo .\n"
                + "" + uri_tratado + "  swrc:year ?year \n"
                + "    FILTER (?year = \"2014\")   }";
        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI, query);
        ResultSet resultSet = q.execSelect();
        //ResultSetFormatter.out(System.out, resultSet); 
        QuerySolution soln = resultSet.nextSolution();
        String titulo = String.valueOf(soln.get("titulo"));
        return titulo;
    }

    public ArrayList<BuscaTopicos> buscarporTopico(String nome_topico) {
        ArrayList<BuscaTopicos> lista_buscatopicos = new ArrayList<BuscaTopicos>();
        String serviceURI = "http://localhost:8080/fuseki/TopWikiLakFinal";
        String query = "PREFIX swrc:<http://swrc.ontoware.org/ontology#>\n"
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                + "PREFIX prov:<http://www.w3.org/ns/prov#>\n"
                + "\n"
                + "SELECT DISTINCT ?uri_artigo ?titulo WHERE {\n"
                + "?uri_artigo dc:subject ?blank_node .\n"
                + "?blank_node  prov:pairKey  ?topico .\n"
                + "?uri_artigo dc:title ?titulo .\n"
                + "?uri_artigo  swrc:year ?year \n"
                + "    FILTER (?year = \"2014\")  FILTER (?topico = \"" + nome_topico + "\") }";
        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI, query);
        ResultSet resultSet = q.execSelect();
        while (resultSet.hasNext()) {
            BuscaTopicos buscatopico = new BuscaTopicos();
            QuerySolution soln = resultSet.nextSolution();
            String uri_paper = String.valueOf(soln.get("uri_artigo"));
            String titulo = String.valueOf(soln.get("titulo"));
            buscatopico.setTitulo(titulo);
            buscatopico.setUri(uri_paper);
            lista_buscatopicos.add(buscatopico);
        }

//        for(BuscaTopicos b: lista_buscatopicos){
//            System.out.println(b.getTitulo()+","+b.getUri());
//        }
        return lista_buscatopicos;

    }

    public Artigo buscarParametrosArtigo(String uri) {
        String uri_tratado = "<" + uri + ">";
        Artigo artigo = new Artigo();
        String serviceURI = "http://localhost:8080/fuseki/TopWikiLakFinal";
        String query = "PREFIX led:<http://data.linkededucation.org/ns/linked-education.rdf#>\n"
                + "PREFIX swrc:<http://swrc.ontoware.org/ontology#>\n"
                + "PREFIX prov:<http://www.w3.org/ns/prov#> \n"
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                + "PREFIX bibo: <http://purl.org/ontology/bibo/>\n"
                + "PREFIX swc:<http://data.semanticweb.org/ns/swc/ontology#>\n"
                + "\n"
                + "SELECT DISTINCT ?title ?resumo ?acronym  WHERE { " + uri_tratado + " swrc:year ?year .  " + uri_tratado + " dc:title ?title .\n"
                + "" + uri_tratado + " bibo:abstract ?resumo .\n"
                + "" + uri_tratado + " dc:subject ?blank_node .\n"
                + "?conference swc:hasAcronym ?acronym . \n"
                + "?proceedings  swc:relatedToEvent ?conference . \n"
                + "?proceedings swc:hasPart " + uri_tratado + " . \n"
                + "  FILTER (?year = \"2014\") }";
        QueryExecution q = QueryExecutionFactory.sparqlService(serviceURI, query);
        ResultSet resultSet = q.execSelect();
        QuerySolution soln = resultSet.nextSolution();
        String titulo = String.valueOf(soln.get("title"));
        String resumo = String.valueOf(soln.get("resumo"));
        String conferencia = String.valueOf(soln.get("acronym"));
        artigo.setTitulo(titulo);
        artigo.setResumo(resumo);
        artigo.setConferencia(conferencia);

        //   ResultSetFormatter.out(System.out, resultSet); 
        ArrayList<Topicos> lista_topicos = new ArrayList<Topicos>();
        String query2 = "PREFIX led:<http://data.linkededucation.org/ns/linked-education.rdf#>\n"
                + "PREFIX swrc:<http://swrc.ontoware.org/ontology#>\n"
                + "PREFIX prov:<http://www.w3.org/ns/prov#> \n"
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                + "PREFIX bibo: <http://purl.org/ontology/bibo/>\n"
                + "PREFIX swc:<http://data.semanticweb.org/ns/swc/ontology#>\n"
                + "\n"
                + "SELECT DISTINCT ?topicos ?valor_confianca WHERE {\n"
                + "" + uri_tratado + " dc:subject ?blank_node .\n"
                + "" + uri_tratado + "  swrc:year ?year . \n"
                + "?blank_node prov:pairKey ?topicos . ?blank_node prov:pairValue ?valor_confianca\n"
                + " FILTER (?year = \"2014\") }";

        QueryExecution q2 = QueryExecutionFactory.sparqlService(serviceURI, query2);
        ResultSet resultSet2 = q2.execSelect();
        while (resultSet2.hasNext()) {
            Topicos topicos = new Topicos();
            QuerySolution soln2 = resultSet2.nextSolution();
            String s_valor_confianca = String.valueOf(soln2.get("valor_confianca"));
            Double valor_confianca = Double.parseDouble(s_valor_confianca.replaceAll("%", ""));
            topicos.setNome(String.valueOf(soln2.get("topicos")));
            topicos.setValor(valor_confianca);
            lista_topicos.add(topicos);
        }
        artigo.setTopicos(lista_topicos);

        ArrayList<Autor> lista_autor = new ArrayList<Autor>();
        String query3 = "PREFIX swrc:<http://swrc.ontoware.org/ontology#>\n"
                + "PREFIX dc: <http://purl.org/dc/elements/1.1/>\n"
                + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"
                + "\n"
                + "SELECT DISTINCT ?Primeiro_nome ?sobrenome WHERE {\n"
                + "" + uri_tratado + " dc:creator ?link_nome .\n"
                + "?link_nome foaf:firstName ?Primeiro_nome . ?link_nome foaf:lastName ?sobrenome .\n"
                + "" + uri_tratado + "  swrc:year ?year \n"
                + " FILTER (?year = \"2014\") }";
        QueryExecution q3 = QueryExecutionFactory.sparqlService(serviceURI, query3);
        ResultSet resultSet3 = q3.execSelect();
        while (resultSet3.hasNext()) {
            Autor autor = new Autor();
            QuerySolution soln3 = resultSet3.nextSolution();
            String primeiro_nome = String.valueOf(soln3.get("Primeiro_nome"));
            String sobrenome = String.valueOf(soln3.get("sobrenome"));
            autor.setPrimeiro_nome(primeiro_nome);
            autor.setUltimo_nome(sobrenome);
            lista_autor.add(autor);
        }

        artigo.setAutor(lista_autor);
        return artigo;

    }

    public ArrayList<String> buscaURIS() {
        ArrayList<String> uris = new ArrayList<String>();
        String serviceURI = "http://localhost:8080/fuseki/TopWikiLakFinal";
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

        return uris;

    }

    public static void main(String[] args) {

        
        

        //ta saindo da jaula o mostro
    }

}
