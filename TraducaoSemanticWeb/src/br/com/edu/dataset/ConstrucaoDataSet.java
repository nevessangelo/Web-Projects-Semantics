/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.dataset;

import com.github.jsonldjava.utils.JsonUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.vocabulary.DC;
import org.json.JSONArray;
import org.wikipedia.miner.annotation.Disambiguator;
import org.wikipedia.miner.annotation.Topic;
import org.wikipedia.miner.annotation.TopicDetector;
import org.wikipedia.miner.annotation.preprocessing.DocumentPreprocessor;
import org.wikipedia.miner.annotation.preprocessing.PreprocessedDocument;
import org.wikipedia.miner.annotation.preprocessing.WikiPreprocessor;
import org.wikipedia.miner.annotation.tagging.DocumentTagger;
import org.wikipedia.miner.annotation.tagging.WikiTagger;
import org.wikipedia.miner.annotation.weighting.LinkDetector;
import org.wikipedia.miner.model.Wikipedia;
import org.wikipedia.miner.util.WikipediaConfiguration;

/**
 *
 * @author angelo
 */
public class ConstrucaoDataSet {

    static DecimalFormat _df = new DecimalFormat("#0%");

    DocumentPreprocessor _preprocessor;
    Disambiguator _disambiguator;
    TopicDetector _topicDetector;
    LinkDetector _linkDetector;
    DocumentTagger _tagger;

    public ConstrucaoDataSet(Wikipedia wikipedia) throws Exception {
        _preprocessor = new WikiPreprocessor(wikipedia);
        _disambiguator = new Disambiguator(wikipedia);
        _topicDetector = new TopicDetector(wikipedia, _disambiguator);
        _linkDetector = new LinkDetector(wikipedia);
        _tagger = new WikiTagger();

    }

    public ArrayList<Topic> annotate(String originalMarkup) throws Exception {

        PreprocessedDocument doc = _preprocessor.preprocess(originalMarkup);
        Collection<Topic> allTopics = _topicDetector.getTopics(doc, null);

        ArrayList<Topic> bestTopics = _linkDetector.getBestTopics(allTopics, 0.5);

        return bestTopics;

    }

    public static void main(String args[]) throws Exception {

        WikipediaConfiguration conf = new WikipediaConfiguration(new File("/home/angelo/workspace/wikipedia-miner-1.2.0/configs/wikipedia.xml"));
        Wikipedia wikipedia = new Wikipedia(conf, false);
        ConstrucaoDataSet annotator = new ConstrucaoDataSet(wikipedia);
        String assemblerFile = "/home/angelo/Web Semântica/apache-jena-fuseki-2.3.1/run/configuration/lakFinal.ttl";
        
        Dataset dataset = TDBFactory.assembleDataset(assemblerFile);
        Model model = dataset.getDefaultModel();
        
        
          
        Model modelProv = ModelFactory.createDefaultModel();
        modelProv.read("http://www.w3.org/ns/prov");
        modelProv.setNsPrefix("prov", "http://www.w3.org/ns/prov#");

        Resource keyValueProperty = modelProv.getResource("http://www.w3.org/ns/prov#KeyValuePair");
        Property key = modelProv.getProperty("http://www.w3.org/ns/prov#pairKey");
        Property value = modelProv.getProperty("http://www.w3.org/ns/prov#pairValue");
        String filtro = "2014";
        Property ano = model.getProperty("http://swrc.ontoware.org/ontology#year");
        StmtIterator iter = model.listStatements(new SimpleSelector(null, ano, filtro));
        ArrayList<String> uris = new ArrayList<String>();
        if (iter.hasNext()) {
            while (iter.hasNext()) {
                Statement stmt = iter.nextStatement();  
                Resource subject = stmt.getSubject();   
                String uri = subject.toString();
                uris.add(uri);
            }

        }
       
       
        Property body = model.getProperty("http://data.linkededucation.org/ns/linked-education.rdf#body");
        for (int i = 0; i < uris.size(); i++) {
            Resource URIlakPaper = model.createResource(uris.get(i));
            NodeIterator iter2 = model.listObjectsOfProperty(URIlakPaper, body);
            while (iter2.hasNext()){
                RDFNode node = iter2.nextNode();
                String lakPaper = node.toString();
                ArrayList<Topic> bestTopics = annotator.annotate(lakPaper);
                for (Topic t : bestTopics) {
                     URIlakPaper.addProperty(DC.subject, model.createResource(keyValueProperty)
                                .addProperty(key, t.getTitle())
                                .addProperty(value, _df.format(t.getWeight())));
                }
              
            }
        }
       
        
        FileOutputStream out = new FileOutputStream("/home/angelo/Web Semântica/TopWikiLak.rdf",false);
       
        model.write(out, "RDF/XML");
        dataset.end();
        

   
    }

}
