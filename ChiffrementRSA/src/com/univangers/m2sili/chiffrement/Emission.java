/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univangers.m2sili.chiffrement;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author etudiant
 */
public class Emission extends Thread {
    
    private PrintWriter out;
    private String message;
    private String cryptedMessage;
    private Scanner sc;
    private Key publicKey;
    private Encrypter en;
    private String name;
    private boolean stop;
    
    public Emission(PrintWriter out, Key publicKey, String name) {
        this.out = out;
        this.publicKey = publicKey;
        en = new Encrypter();
        this.name = name;
        this.stop = false;
    }

    @Override
    public void run() {
        sc = new Scanner(System.in);
        System.out.println("Your message (\"bye\" to close) : ");
        while (!this.stop){
            message = sc.nextLine();
            if (message.equals("bye")) {
                this.stop = true;
            }
            cryptedMessage = en.encryption(message, publicKey);
            out.println(cryptedMessage);
            out.flush();
        }
    }
    
}