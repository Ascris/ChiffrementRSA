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
    
    public BigInteger correctU(BigInteger u, BigInteger m){
        BigInteger res= BigInteger.ZERO;
        BigInteger k= new BigInteger("-1");
        BigInteger tmp= u.subtract(k.multiply(m));
        BigInteger two= new BigInteger("2");
        /*
         When two BigInteger u1 and u2 are compared with compareTo (u1.compareTo(u2), we can have 3 values :
            0 : u1 and u2 are equal
            1 : u1 is greater than u2
            -1: u1 is lower than u2
        */
        while(!((tmp.compareTo(two) == 1) && (m.compareTo(tmp) == 1))){
            tmp= u.subtract(k.multiply(m));
            k= k.subtract(BigInteger.ONE);
        }
        res= tmp;
        
        return res;
    }
    
    public Key createPrivateKey(){
        //BigInteger used for the private key
        BigInteger p, q, n, m, e;
        
        p= new BigInteger("53");
        q= new BigInteger("97");
        
        Key res_key= new Key(p,q);
        res_key.computeN();
        res_key.computeE();
        res_key.computeM();
        n= res_key.getN();
        e= res_key.getE();
        m= res_key.getM();
                
        //BigInteger used for the loop
        BigInteger r0, r1, r, u0, u1, u, v0, v1, v, quo;
        r0= e;
//        r0= new BigInteger("7");
        r1= m;
//        r1= new BigInteger("4992");
        u0= new BigInteger("1");
        u1= new BigInteger("0");
        v0= new BigInteger("0");
        v1= new BigInteger("1");
        u= u0;
        v= v0;
        
        //Euclide's algorithm
        while(!r1.equals(BigInteger.ZERO)){
            quo= r0.divide(r1);
            r= r0;
            u= u0;
            v= v0;
            r0= r1;
            u0= u1;
            v0= v1;
            r1= r.subtract(quo.multiply(r1));
            u1= u.subtract(quo.multiply(u1));
            v1= v.subtract(quo.multiply(v1));
        }
        
        if(u.compareTo(BigInteger.ZERO) == -1){ u= correctU(u,m); };
        
        res_key.setU(u);
        res_key.setV(v);
        
        return res_key;
    };
    
    public static void main(String[] args){
        KeyGenerator k= new KeyGenerator();
        Key my_public_key= k.createPublicKey();
        Key my_private_key= k.createPrivateKey();
        System.out.println("Clé publique = " + my_public_key.display_public());
        System.out.println("Clé privée = " + my_private_key.display_private());
        
        String my_message= "Bonjour !";
        Encrypter encr= new Encrypter(my_message, my_public_key);
        String encrypted_message= encr.encryption();
        System.out.println("Message basique : " + my_message);
        System.out.println("Message chiffré : " + encrypted_message);   
        
        String test_str= "386 737 970 204 1858";
//        my_private_key.setU(new BigInteger("5141"));
        my_private_key.setN(new BigInteger("5141"));
        
        
//        Decrypter decr= new Decrypter(test_str, my_private_key);
        Decrypter decr= new Decrypter(encrypted_message, my_private_key);
        String decrypted_message= decr.decryption();
        System.out.println("Message déchiffré : " + decrypted_message);

    }
}