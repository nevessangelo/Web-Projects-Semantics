package br.com.edu.wikiminer;


import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
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


public class WikMiner {

    DecimalFormat _df = new DecimalFormat("#0%") ;
    DocumentPreprocessor _preprocessor ; 
    Disambiguator _disambiguator ;
    TopicDetector _topicDetector ;
    LinkDetector _linkDetector ;
    DocumentTagger _tagger ;
   

    public WikMiner(Wikipedia wikipedia) throws Exception {
        _preprocessor = new WikiPreprocessor(wikipedia) ;
        _disambiguator = new Disambiguator(wikipedia) ;
        _topicDetector = new TopicDetector(wikipedia, _disambiguator) ;
        _linkDetector = new LinkDetector(wikipedia) ;
        _tagger = new WikiTagger() ;
       
    }

    public void annotate(String originalMarkup, Wikipedia wikipedia) throws Exception {
        PreprocessedDocument doc = _preprocessor.preprocess(originalMarkup) ;
        Collection<Topic> allTopics = _topicDetector.getTopics(doc, null) ;
        System.out.println("\nAll detected topics:") ;
        for (Topic t:allTopics)
            System.out.println(" - " + t.getTitle()) ;
        
        ArrayList<Topic> bestTopics = _linkDetector.getBestTopics(allTopics, 0.5) ;
        System.out.println("\nTopics that are probably good links:") ;
        for (Topic t:bestTopics){
            System.out.println(" - " + t.getTitle() + "[" + _df.format(t.getWeight()) + "]" ) ;
            
        }
   }
   
    

    public static void main(String args[]) throws Exception {

        WikipediaConfiguration conf = new WikipediaConfiguration(new File("/home/angelo/WebSemantica/wikipedia-miner-code-236-trunk/configs/wikipedia.xml")) ;
        Wikipedia wikipedia = new Wikipedia(conf, false) ;

        WikMiner annotator = new WikMiner(wikipedia) ;
     
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)) ;
        while (true) {
            System.out.println("Enter snippet to annotate (or ENTER to quit):") ;
            String line = reader.readLine();

            if (line.trim().length() == 0)
                break ;

            annotator.annotate(line,wikipedia) ;
        }
        
    }
  
}

