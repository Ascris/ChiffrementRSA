package com.univangers.m2sili.chiffrement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;

/**
 * Represent an individual who can send a crypted message to another individual thanks to the Server class
 * Use the RSA encryption to communicate safely
 */
public class Individual {
    
    private String _name;
    private Key _publicKey;
    private Key _privateKey;
    private BufferedReader in;
    private PrintWriter out;
    
    public Individual(String name, Key publicK, Key privateK) {
        _name = name;
        _publicKey = publicK;
        _privateKey = privateK;
    }

    public static void main(String[] args){
        
        Socket socket;
        try {
            System.out.println("Connexion au serveur au port " + Server.PORTNUMBER + " ...");
            socket = new Socket("localhost", Server.PORTNUMBER);
            System.out.println("Connexion accepté !");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
