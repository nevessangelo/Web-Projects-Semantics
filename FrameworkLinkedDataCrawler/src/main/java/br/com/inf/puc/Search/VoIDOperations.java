/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inf.puc.Search;

/**
 *
 * @author angelo
 */
public class VoIDOperations {
    
    public Boolean FindVoID(String uri){
        if(uri.contains(".void")){
            return true;
        }else{
            return false;
        }
    }
    
}
