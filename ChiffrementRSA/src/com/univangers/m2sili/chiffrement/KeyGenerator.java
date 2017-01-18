package com.univangers.m2sili.chiffrement;

import java.math.BigInteger;
import java.util.Random;

/**
 * This class represents a key generator
 */
public class KeyGenerator {
    
    public KeyGenerator(){}
    
    public BigInteger getFirstPrimeNumberWith(BigInteger myBI){
        Random rand= new Random();
        BigInteger bi, PGCD;
        boolean isPrime= false;
        int littleInteger;
        
        littleInteger= new Random().nextInt(50)+1;
        bi= BigInteger.valueOf(littleInteger);
        while(!isPrime){
            //check if the BigInteger is odd
            if(!bi.mod(new BigInteger("2")).equals(BigInteger.ZERO)){
                PGCD= bi.gcd(myBI);
                if(BigInteger.ONE.equals(PGCD) || new BigInteger("-1").equals(PGCD)) {
                    isPrime= true;
                } else {
                    littleInteger= new Random().nextInt(50)+1;
                    bi= BigInteger.valueOf(littleInteger);
                }
            } else {
                littleInteger= new Random().nextInt(50)+1;
                bi= BigInteger.valueOf(littleInteger);
            }
        }
        
        System.out.println("Mon petit entier : " + littleInteger);
        
        return bi;
    }
    
    public Key createPublicKey(){
        BigInteger p, q, n, m, e;

        Random rand= new Random();
        p= BigInteger.probablePrime(500, rand);
//        p= new BigInteger("53");
        System.out.println("p = " + p);
        
        q= BigInteger.probablePrime(500, rand);
//        q= new BigInteger("97");
        System.out.println("q = " + q);

        n= p.multiply(q);
        //m is the Euler indicater
        m= p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        System.out.println("m= " + m);
        
        //e is the public exponent
        e= getFirstPrimeNumberWith(m);
        
        Key my_public_key= new Key(n,e);
        return my_public_key;
    };
    
    public static void main(String[] args){
        KeyGenerator k= new KeyGenerator();
        Key my_key= k.createPublicKey();
        System.out.println("Clé publique = " + my_key.toString());
    }
}