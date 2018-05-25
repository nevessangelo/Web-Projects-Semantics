/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.example;

/**
 *
 * @author angelo
 */
public class Triplas {
    
    private String subject;
    private String predicate;
    private String object;
    
    public Triplas(){
        
    }
    
    public Triplas(String subject, String predicate, String object){
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;             
    }
    
    
    
    
   @Override
   public String toString(){
        String triple = "";
        return triple="<"+subject+">"+"\t"+"<"+predicate+">"+"\t"+"<"+object+">"+"\n";
   }
    
}
