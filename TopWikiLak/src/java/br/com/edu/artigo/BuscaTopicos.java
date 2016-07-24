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
public class BuscaTopicos {
    private String uri;
    private String titulo;
    
    public BuscaTopicos(){
        
    }
    
    public BuscaTopicos(String uri, String titulo){
        this.uri = uri;
        this.titulo = titulo;
        
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
}
