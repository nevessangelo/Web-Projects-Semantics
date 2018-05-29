/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.Main;

import br.com.inf.puc.Search.VoIDSearch;
import br.com.inf.puc.crawler.Triples;
import java.util.ArrayList;

/**
 *
 * @author angelo
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("Starting Crawler....");
        VoIDSearch voidsearch = new VoIDSearch();
        voidsearch.Search("http://swlab.ic.uff.br/resource/id-6fbbb516-38a9-4a2b-bfbb-19d0ceffc1aa", 2);
        
        
    }
    
}
