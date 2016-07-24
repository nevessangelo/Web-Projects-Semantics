/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.edu.arff;

import br.com.edu.fuseki.ConnectionLAK;
import br.com.edu.fuseki.Topicos;
import java.util.ArrayList;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author angelo
 */
public class ConstructArff {

    public static void main(String[] args) throws Exception {
        FastVector atts;
        FastVector attsRel;
        FastVector attVals;
        FastVector attValsRel;
        Instances data;
        Instances dataRel;
        double[] vals;
        double[] valsRel;
        int i, j, cont;

        ConnectionLAK conexao = new ConnectionLAK();
        ArrayList<String> uris;
        ArrayList<String> todos_topicos;
        ArrayList<Topicos> valores;

        uris = conexao.buscaURIS();
        todos_topicos = conexao.BuscarTodosTopicos();

        atts = new FastVector();
        for (String topicos : todos_topicos) {
            atts.addElement(new Attribute(topicos));
        }

        data = new Instances("Relacao", atts, 0);


        for (String uri : uris) {
            i = 0;
            vals = new double[data.numAttributes()];
            valores = conexao.BuscarTopicos(uri);
            for (int t = 0; t < data.numAttributes(); t++) {
                for (Topicos topic : valores) {
                    String comparacao = topic.getTopico();
                    String atributo = data.attribute(t).name();
                      if (comparacao.equals(atributo)) {
                        vals[t] = topic.getValor();
                        break;
                      }
                }               
            }
             
            data.add(new Instance(1.0, vals));
        }

        System.out.println(data);
    }
}
