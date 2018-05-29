/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.crawler;

import br.com.inf.puc.example.Dereferencing;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 *
 * @author angelo
 */
public abstract class TemplateCrawler {

    public final void Search(String uri, int k) {
        Model model = Dereferecing(uri);
        ArrayList<Triples> triples = GetTriples(model, uri);
        TypeSearch(triples, k);
    }

    final ArrayList<Triples> GetTriples(Model model, String uri) {
        ArrayList<Triples> list_triples = new ArrayList<>();
        String queryString = "SELECT ?s ?p ?o WHERE  {?s ?p ?o}";
        Query query = QueryFactory.create(queryString);
        QueryExecution exec = QueryExecutionFactory.create(query, model);
        ResultSet rs = exec.execSelect();
        while (rs.hasNext()) {
            QuerySolution sol = rs.next();
            Triples triples = new Triples();
            triples.setSubject(sol.getResource("?s").toString());
            triples.setPredicate(sol.getResource("?p").toString());
            if(sol.get("?o").isResource())
                triples.setObject(sol.getResource("?o").toString());
            
            list_triples.add(triples);

        }
        return list_triples;
    }

    final Model Dereferecing(String uri) {
        try {
            ArrayList<Triples> list_triples = new ArrayList<>();
            Model model = ModelFactory.createDefaultModel();
            URLConnection conn = new URL(uri).openConnection();
            conn.setRequestProperty("Accept", "application/rdf+xml");
            InputStream in = conn.getInputStream();
            model.read(in, null);
            return model;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Dereferencing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Dereferencing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public abstract void TypeSearch(ArrayList<Triples> triples, int k);

}
