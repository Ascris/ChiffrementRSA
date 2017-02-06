/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univangers.m2sili.chiffrement;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author etudiant
 */
public class Reception implements Runnable {
    
    private BufferedReader in;
    private String message = null;
    private String cryptedMessage = null;
    private String name;
    private Key privateKey;
    private Decrypter de;
    
    public Reception(BufferedReader in, Key privateKey, String name) {
        this.in = in;
        this.privateKey = privateKey;
        this.name = name;
    }

    @Override
    public void run() {
        de = new Decrypter();
        boolean exit = false;
        while (!exit) {
            try {
                cryptedMessage = in.readLine();
                System.out.println("Encrypted message receive : "+cryptedMessage);
                message = de.decryption(cryptedMessage, privateKey);
                System.out.println("Decrypted message receive : "+message);
                if (message.equals("bye")) {
                    exit = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
