/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univangers.m2sili.chiffrement;

import java.math.BigInteger;

public class Encrypter {
    
    public Encrypter(){}
    
    /**
     * Generate the encryption of the text field (ascii style)
     * @return the encrypted text
     */
    public String encryption(String text_to_translate, Key public_key){
        String res= "";
        String text= text_to_translate;
        
        String part_one= res;
        //text encryption using ascii table
        for(int i= 0; i < text.length(); ++i){
            char c= text.charAt(i);
            int c_ascii= (int) c;
            part_one= part_one + c_ascii + " ";
        }
        
        BigInteger e= public_key.getE();
        BigInteger n= public_key.getN();
        
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
