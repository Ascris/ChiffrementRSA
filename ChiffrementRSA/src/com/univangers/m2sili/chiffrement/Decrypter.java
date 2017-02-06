/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univangers.m2sili.chiffrement;

import java.math.BigInteger;

public class Decrypter {
    
    public Decrypter(){}
    
    public String decryption(String text_to_translate, Key private_key){
        String res= "";
        String text= text_to_translate;
        
        Key k= private_key;
        BigInteger n= k.getN();
        BigInteger u= k.getU();
        
        //text decryption using modulo and the private key's Bézout coefficient u
        String[] text_elem= text.split(" ");
        BigInteger current_val;
        for(int i= 0; i < text_elem.length; ++i){
            current_val= new BigInteger(text_elem[i]);
            current_val= current_val.modPow(u, n);
            text_elem[i]= current_val.toString();
        }
        //text decryption using ascii table
        for(int i= 0; i < text_elem.length; ++i){
            int val= Integer.parseInt(text_elem[i]);
            char c= (char) val;
            res= res + c;
        }
        
        return res;
    }
    
}
