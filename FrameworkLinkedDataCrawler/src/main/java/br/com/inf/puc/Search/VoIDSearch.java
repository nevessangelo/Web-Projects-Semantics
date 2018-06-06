/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.Search;

import br.com.inf.puc.crawler.TemplateCrawler;
import br.com.inf.puc.crawler.Triples;
import java.util.ArrayList;

/**
 *
 * @author angelo
 */
public class VoIDSearch extends TemplateCrawler {

    @Override
    public void TypeSearch(ArrayList<String> subjects, ArrayList<String> objects, int k) {
      
        System.out.println("VoID busca");
        for(String subject: subjects){
            System.out.println(subject);
        }
        
        for(String object: objects){
            VoIDSearch voidsearch = new VoIDSearch();
            voidsearch.Search(object, 4);
        }
    }
    
    
    

  
    
}
