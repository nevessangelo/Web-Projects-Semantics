/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.fuseki;

/**
 *
 * @author angelo
 */
public class Topicos {
    
    private String topico;
    private Double valor;
    
    public Topicos (){
        
        
    }
    
    public Topicos (String topico, Double valor){
        this.topico = topico;
        this.valor = valor;
    }

    /**
     * @return the topico
     */
    public String getTopico() {
        return topico;
    }

    /**
     * @param topico the topico to set
     */
    public void setTopico(String topico) {
        this.topico = topico;
    }

    /**
     * @return the valor
     */
    public Double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    
}
