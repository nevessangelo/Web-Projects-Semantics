/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.Search;

import br.com.inf.puc.crawler.TemplateCrawler;
import java.util.ArrayList;

/**
 *
 * @author angelo
 */
public class VoIDSearch extends TemplateCrawler {

    @Override
    public void TypeSearch(ArrayList<String> uris, int k) {
        
        for(String subject: uris){
            System.out.println(subject);
        }
        
        
    }
    
    
    

  
    
}
