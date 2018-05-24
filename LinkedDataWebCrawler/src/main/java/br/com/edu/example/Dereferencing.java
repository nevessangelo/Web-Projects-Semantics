/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
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
public class Dereferencing {
    
    public static Model run(String uri){
        try {
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
//    
//    public static List<Triplas> getObjects(Model model){
//        List<Triplas> objectsURIs = new ArrayList<Triplas>(); 
//        String queryString =  "SELECT ?s ?p ?o WHERE  { ?s ?p ?o . FILTER isURI(?o)}";
//        Query query = QueryFactory.create(queryString);
//        QueryExecution exec = QueryExecutionFactory.create(query, model);
//        ResultSet rs = exec.execSelect();
//        while(rs.hasNext()){
//            QuerySolution sol = rs.next();
//            String s= sol.get("?s").toString();
//            String p=sol.get("?p").toString();
//            String o=sol.get("?o").toString();
//            Triplas triplas= new Triplas(s,p,o);
//            objectsURIs.add(triplas);
//        }
//        return objectsURIs;
//    }
    
    public static void displayModelTriples(Model model){
        
	String queryString =  "SELECT ?s ?p ?o WHERE  { ?s ?p ?o}";
    	Query query = QueryFactory.create(queryString);
    	QueryExecution exec = QueryExecutionFactory.create(query, model);
    	ResultSet rs = exec.execSelect();
    	while(rs.hasNext()){
    		QuerySolution sol = rs.next();
    		System.out.print(("<"+sol.getResource("?s"))+"> ");
    		System.out.print(("<"+sol.getResource("?p"))+"> ");
    		if(sol.get("?o").isResource())
    			System.out.println(("<"+sol.get("?o"))+"> ");
    		else
    			System.out.println(("\""+sol.get("?o"))+"\"");

    	}
    
	}
    
    public static void main(String[] args) {
        String uri = "http://swlab.ic.uff.br/resource/id-6fbbb516-38a9-4a2b-bfbb-19d0ceffc1aa";
        Model model = run(uri);
        displayModelTriples(model);
//        ArrayList<Triplas> list_triplas = (ArrayList<Triplas>) getObjects(model);
//        for(Triplas tripla: list_triplas){
//            System.out.println(tripla.toString());
//        }
        
    }
    
}
