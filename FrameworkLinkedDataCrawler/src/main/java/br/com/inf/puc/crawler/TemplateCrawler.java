/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.crawler;

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

    public final void Search(String uri) {
        Model model = Dereferecing(uri);
        ArrayList<String> subject = GetSubject(model, uri);
        ArrayList<String> object = GetObject(model, uri);
        ArrayList<String> uris = UnionURIs(subject, object);
        TypeSearch(uris);
    }

    final ArrayList<String> UnionURIs(ArrayList<String> subjects, ArrayList<String> objects) {
        ArrayList<String> URIs = new ArrayList<>();
        for (String subject : subjects) {
            URIs.add(subject);
        }

        for (String object : objects) {
            if (!subjects.contains(object)) {
                URIs.add(object);
            }

        }

        return URIs;
    }

    final ArrayList<String> GetObject(Model model, String uri) {
        ArrayList<String> listOject = new ArrayList<>();
        String queryString = "SELECT ?o WHERE {?s ?p ?o}";
        Query query = QueryFactory.create(queryString);
        QueryExecution exec = QueryExecutionFactory.create(query, model);
        ResultSet rs = exec.execSelect();
        while (rs.hasNext()) {
            QuerySolution sol = rs.next();
            if (sol.get("?o").isResource()) {
                String object = sol.getResource("?o").toString();
                if (!uri.equals(object)) {
                    listOject.add(object);
                }
            }

        }

        return listOject;
    }

    final ArrayList<String> GetSubject(Model model, String uri) {
        ArrayList<String> listSubject = new ArrayList<>();
        String queryString = "SELECT ?s WHERE  {?s ?p ?o}";
        Query query = QueryFactory.create(queryString);
        QueryExecution exec = QueryExecutionFactory.create(query, model);
        ResultSet rs = exec.execSelect();
        while (rs.hasNext()) {
            QuerySolution sol = rs.next();
            String subject = sol.getResource("?s").toString();
            if (!uri.equals(subject)) {
                listSubject.add(subject);
            }

        }
        return listSubject;
    }

    final Model Dereferecing(String uri) {
        try {
            Model model = ModelFactory.createDefaultModel();
            URLConnection conn = new URL(uri).openConnection();
            conn.setRequestProperty("Accept", "application/rdf+xml");
            InputStream in = conn.getInputStream();
            model.read(in, uri);

            return model;
        } catch (MalformedURLException ex) {
            Logger.getLogger(TemplateCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TemplateCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public abstract void TypeSearch(ArrayList<String> uris);

}
