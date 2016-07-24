/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.cluster;

/**
 *
 * @author angelo
 */
public class Cluster {
    private String uri;
    private String grupo;
    
    public Cluster(){
        
    }
    
    public Cluster (String uri, String grupo){
        this.uri = uri;
        this.grupo = grupo;
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
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    
    
    
}
