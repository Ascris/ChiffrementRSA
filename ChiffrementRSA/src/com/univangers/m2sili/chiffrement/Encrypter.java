/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univangers.m2sili.chiffrement;

import java.math.BigInteger;
import java.util.Arrays;

public class Encrypter {
    private String text;
    private Key publicKey;
    
    public Encrypter(String text_to_translate, Key public_key){
        text= text_to_translate;
        publicKey= public_key;
    }
    
    public void setText(String text) { this.text = text; }

    public String getText() { return text; }
    
    public Key getPublicKey() { return publicKey; }
    
    /**
     * Generate the encryption of the text field (ascii style)
     * @return the encrypted text
     */
    public String encryption(){
        String res= "";
        String text= getText();
        
        String part_one= res;
        //text encryption using ascii table
        for(int i= 0; i < text.length(); ++i){
            char c= text.charAt(i);
            int c_ascii= (int) c;
            part_one= part_one + c_ascii + " ";
        }
        
        Key k= getPublicKey();
        BigInteger e= k.getE();
        BigInteger n= k.getN();
        
        //text encryption using modulo and the public key's exponent e
        String[] text_elem= part_one.split(" ");
        String current_elem;
        int current_val;
        BigInteger new_val;
        for(int i= 0; i < text_elem.length; ++i){
            current_elem= text_elem[i];
            new_val= new BigInteger(current_elem);
            new_val= new_val.modPow(e, n);

            res= res + new_val + " ";
        }

        return res;
    }
}
