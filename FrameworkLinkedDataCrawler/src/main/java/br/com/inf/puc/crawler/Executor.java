/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.crawler;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author angelo
 */
public abstract class Executor {
    
    public static <T> T execute(Callable<T> task, String description, long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        FutureTask<T> future = new FutureTask<>(task);
        Thread thread = new Thread(future);
        try {
            thread.setDaemon(true);
            thread.start();
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            Logger.getLogger("timeout").log(Level.ERROR, "Timeout while executing \"" + description + "\".");
            Logger.getLogger("error").log(Level.ERROR, "Timeout while executing \"" + description + "\".");
            throw e;
        } finally {
            future.cancel(true);
            thread.stop();
        }
    }
    
}
