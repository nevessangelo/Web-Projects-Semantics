/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.arff;

import br.com.edu.cluster.Cluster;
import br.com.edu.fuseki.Fuseki;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader.ArffReader;

/**
 *
 * @author angelo
 */
public class LoadArff {
    
    public ArrayList<Cluster> carregarArff(String caminho) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(caminho));
        ArffReader arff = new ArffReader(reader);
        Instances data = arff.getData();
        data.setClassIndex(data.numAttributes() - 1);
        Instance inst = null;
        Attribute att=data.attribute("Cluster");
        
        ArrayList<String> uris;
        ArrayList<Cluster> lista = new ArrayList<Cluster>();
        
        Fuseki fuseki = new Fuseki();
        
        
        uris = fuseki.buscaURIS();
    
        for (int i = 0; i <= data.numInstances() - 1; i++) {
             Cluster cluster = new Cluster();
             String clusters = String.valueOf(data.get(i).stringValue(att));
             cluster.setUri(uris.get(i));
             cluster.setGrupo(clusters);
             lista.add(cluster);
       }
        
//        for (Cluster c : lista) {
//              System.out.println(c.getUri());
//              System.out.println(c.getGrupo());
//        } 
        return lista;
    }

}
