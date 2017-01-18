package com.univangers.m2sili.chiffrement;

import java.math.BigInteger;

/**
 * Class that represents a key
 */
public class Key {
    private BigInteger n;
    private BigInteger e;
    
    public Key(){
        n= new BigInteger("100000000000000");
        e= new BigInteger("100000000000000");
    }
    public Key(BigInteger a, BigInteger b){
        n= a;
        e= b;
    };
    
    @Override
    public String toString(){
        String res;
        res= "(" + n + " ; " + e + ")";
        return res;
    }
}
	