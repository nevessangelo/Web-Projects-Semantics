/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.Search;

import br.com.inf.puc.crawler.TemplateCrawler;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author angelo
 */
public class VoIDSearch extends TemplateCrawler {

    @Override
    public void TypeSearch(ArrayList<String> uris) {
        Integer counter = 0;
        try {
            ExecutorService pool = Executors.newWorkStealingPool(1);
            for(String uri: uris){
               pool.submit(new SearchVoIDTask(uri));
               System.out.println((++counter) + ": Harvesting task for " + uri + " submitted.");
               break;
            }
            
            pool.shutdown();
            System.out.println("Waiting for remaining tasks...");
            pool.awaitTermination(2, TimeUnit.DAYS);
            System.out.println(String.format("Search VoID end :D"));;
         
            System.out.println("================================================================================================================================");
            System.out.println("");
        } catch (InterruptedException ex) {
            Logger.getLogger(VoIDSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
    

  
    
}
