/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.artigo;

import br.com.edu.cluster.Cluster;
import java.util.ArrayList;

/**
 *
 * @author angelo
 */
public class Artigo {
    
    private String titulo; 
    private String conferencia;
    private String resumo;
    private ArrayList<Autor> autor;
    private ArrayList<Topicos> topicos;
    
    public Artigo(){
        
    }
    
    public Artigo(String titulo, String conferencia, String resumo, ArrayList<Autor> autor, ArrayList<Topicos> topicos){
        this.titulo = titulo;
        this.conferencia = conferencia;
        this.resumo = resumo;
        this.autor = autor;
        this.topicos = topicos;
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

    /**
     * @return the conferencia
     */
    public String getConferencia() {
        return conferencia;
    }

    /**
     * @param conferencia the conferencia to set
     */
    public void setConferencia(String conferencia) {
        this.conferencia = conferencia;
    }

    /**
     * @return the resumo
     */
    public String getResumo() {
        return resumo;
    }

    /**
     * @param resumo the resumo to set
     */
    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    /**
     * @return the autor
     */
    public ArrayList<Autor> getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(ArrayList<Autor> autor) {
        this.autor = autor;
    }

    /**
     * @return the topicos
     */
    public ArrayList<Topicos> getTopicos() {
        return topicos;
    }

    /**
     * @param topicos the topicos to set
     */
    public void setTopicos(ArrayList<Topicos> topicos) {
        this.topicos = topicos;
    }

    
}
