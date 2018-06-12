/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.Search;

import br.com.inf.puc.crawler.Executor;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author angelo
 */
public class SearchVoIDTask implements Runnable {

    private String uri;
    private VoIDOperations void_tasks;

    public SearchVoIDTask(String uri) {
        this.uri = uri;
        
    }


    @Override
    public void run() {
        // find cadidate to void
        Callable<Boolean> Findvoid_task = () -> {
            void_tasks.FindVoID(uri);
            return null;
        };
        try {
            Executor.execute(Findvoid_task, "Find VoID " + uri, 100000000);
        } catch (InterruptedException ex) {
            Logger.getLogger(SearchVoIDTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(SearchVoIDTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(SearchVoIDTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    }
