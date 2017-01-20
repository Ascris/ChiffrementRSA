/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univangers.m2sili.chiffrement;

import java.math.BigInteger;

public class Decrypter {
    private String text;
    private Key privateKey;
    
    public Decrypter(String text_to_translate, Key private_key){
        text= text_to_translate;
        privateKey= private_key;
    }
    
    public void setText(String text) { this.text = text; }

    public String getText() { return text; }
    
    public Key getPrivateKey() { return privateKey; }
    
    public String decryption(){
        String res= "";
        String text= getText();
        Key k= getPrivateKey();
        BigInteger n= k.getN();
//        BigInteger u= k.getE();//used for tests
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
