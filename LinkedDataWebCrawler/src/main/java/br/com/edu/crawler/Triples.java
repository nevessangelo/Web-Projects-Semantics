/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.crawler;

/**
 *
 * @author angelo
 */
public class Triples {
    
    private String subject;
    private String predicate;
    private String object;
    
    public Triples(){
        
    }
    
    public Triples(String subject, String predicate, String object){
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the predicate
     */
    public String getPredicate() {
        return predicate;
    }

    /**
     * @param predicate the predicate to set
     */
    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    /**
     * @return the object
     */
    public String getObject() {
        return object;
    }

    /**
     * @param object the object to set
     */
    public void setObject(String object) {
        this.object = object;
    }
    
    @Override
    public String toString(){
        String triple = "";
        return triple="<"+subject+">"+"\t"+"<"+predicate+">"+"\t"+"<"+object+">"+"\n";
   }
    
    
    
    
    
    
}
