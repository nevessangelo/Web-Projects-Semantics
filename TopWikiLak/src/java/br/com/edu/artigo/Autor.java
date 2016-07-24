/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.artigo;

/**
 *
 * @author angelo
 */
public class Autor {
    private String primeiro_nome;
    private String ultimo_nome;
    
    public Autor (){
        
    }
    
    public Autor(String primeiro_nome, String ultimo_nome){
        this.primeiro_nome = primeiro_nome;
        this.ultimo_nome = ultimo_nome;
    }

    /**
     * @return the primeiro_nome
     */
    public String getPrimeiro_nome() {
        return primeiro_nome;
    }

    /**
     * @param primeiro_nome the primeiro_nome to set
     */
    public void setPrimeiro_nome(String primeiro_nome) {
        this.primeiro_nome = primeiro_nome;
    }

    /**
     * @return the ultimo_nome
     */
    public String getUltimo_nome() {
        return ultimo_nome;
    }

    /**
     * @param ultimo_nome the ultimo_nome to set
     */
    public void setUltimo_nome(String ultimo_nome) {
        this.ultimo_nome = ultimo_nome;
    }
    
    
    
}
