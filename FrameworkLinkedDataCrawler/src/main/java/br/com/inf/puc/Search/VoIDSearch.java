/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.Search;

import br.com.inf.puc.crawler.TemplateCrawler;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author angelo
 */
public class VoIDSearch extends TemplateCrawler {

    @Override
    public void TypeSearch(ArrayList<String> uris, int k) {
        ExecutorService pool = Executors.newWorkStealingPool(4);
        for(String uri: uris){
            pool.submit(new SearchVoIDTask(uri, k));
            
            //perguntas:
            //pool de theards é um agente de software?
            //posso armazenar no banco de dados?
        }
        
        
    }
    
    
    

  
    
}
