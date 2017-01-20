package com.univangers.m2sili.chiffrement;

import java.math.BigInteger;
import java.util.Random;

/**
 * This class represents a key generator
 */
public class KeyGenerator {
    
    public KeyGenerator(){}
    
    public Key createPublicKey(){
        BigInteger p, q, n, m, e;
        
        Random rand= new Random();
//        p= BigInteger.probablePrime(500, rand);
        p= new BigInteger("53");
        System.out.println("p = " + p);
        
//        q= BigInteger.probablePrime(500, rand);
        q= new BigInteger("97");
        System.out.println("q = " + q);

        n= p.multiply(q);
        //m is the Euler indicater
        m= p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        System.out.println("m= " + m);
        
        Key my_public_key= new Key(p,q);
        my_public_key.computeN();//product of p and q
        my_public_key.computeM();//Euler indicator
        my_public_key.computeE();//public exponent
        return my_public_key;
    };
    
    public Key createPrivateKey(){
        Key res_key= new Key();
        
        //TODO : Euclide's algorithm to implement
        
        return res_key;
    };
    
    public static void main(String[] args){
        KeyGenerator k= new KeyGenerator();
        Key my_public_key= k.createPublicKey();
        System.out.println("Clé publique = " + my_public_key.toString());
        
        Encrypter encr= new Encrypter("Bonjour !", my_public_key);
        System.out.println("Message chiffré : " + encr.encryption());
    }
}