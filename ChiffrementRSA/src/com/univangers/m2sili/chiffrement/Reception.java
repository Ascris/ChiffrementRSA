package com.univangers.m2sili.chiffrement;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author etudiant
 */
public class Reception extends Thread {
    
    private BufferedReader in;
    private String message = null;
    private String cryptedMessage = null;
    private String name;
    private Key privateKey;
    private Decrypter de;
    private boolean stop;
    
    public Reception(BufferedReader in, Key privateKey, String name) {
        this.in = in;
        this.privateKey = privateKey;
        this.name = name;
        this.stop = false;
    }

    @Override
    public void run() {
        de = new Decrypter();
        try {
            while (!this.stop) {
                cryptedMessage = in.readLine();
                System.out.println("Encrypted message receive : "+cryptedMessage);
                message = de.decryption(cryptedMessage, privateKey);
                System.out.println("Decrypted message receive : "+message);
                if (message.equals("bye")) {
                    this.stop = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
