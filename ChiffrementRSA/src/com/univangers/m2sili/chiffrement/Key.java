package com.univangers.m2sili.chiffrement;

import java.math.BigInteger;
import java.util.Random;

/**
 * Class that represents a key
 */
public class Key {
    private BigInteger p, q, n, m, e;

    
    public Key(){
        p= new BigInteger("100000000000000");
        q= new BigInteger("100000000000000");
    }
    public Key(BigInteger BI_p, BigInteger BI_q){
        p= BI_p;
        q= BI_q;
    };
    
    public void setP(BigInteger p) { this.p= p; }

    public void setQ(BigInteger q) { this.q= q; }

    public void setN(BigInteger n) { this.n= n; }

    public void setM(BigInteger m) { this.m= m; }

    public void setE(BigInteger e) { this.e= e; }

    public BigInteger getP() { return p; }

    public BigInteger getQ() { return q; }

    public BigInteger getN() { return n; }

    public BigInteger getM() { return m; }

    public BigInteger getE() { return e; }
    
    public void computeN(){ setN(p.multiply(q)); }
    
    public void computeM(){ setM(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))); }
    
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
    
    public void computeE(){ setE(getFirstPrimeNumberWith(m)); }
    
    @Override
    public String toString(){
        String res;
        res= "(" + n + " ; " + e + ")";
        return res;
    }
}
	