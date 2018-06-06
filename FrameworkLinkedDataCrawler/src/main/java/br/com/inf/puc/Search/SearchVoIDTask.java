/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.Search;

import java.util.concurrent.Callable;

/**
 *
 * @author angelo
 */
public class SearchVoIDTask implements Runnable {
    
    private String uri;
    private int k;

    public SearchVoIDTask(String uri, int k) {
        this.uri = uri;
        this.k = k;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
