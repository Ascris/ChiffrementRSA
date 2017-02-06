/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univangers.m2sili.chiffrement;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author etudiant
 */
public class Emission implements Runnable {
    
    private PrintWriter out;
    private String message;
    private String cryptedMessage;
    private Scanner sc;
    private Key publicKey;
    private Encrypter en;
    private String name;
    
    public Emission(PrintWriter out, Key publicKey, String name) {
        this.out = out;
        this.publicKey = publicKey;
        en = new Encrypter();
        this.name = name;
    }

    @Override
    public void run() {
        sc = new Scanner(System.in);
        boolean exit = false;
        System.out.println("Your message (\"bye\" to close) : ");
        while(!exit){
            message = sc.nextLine();
            if (message.equals("bye")) {
                exit = true;
            }
            cryptedMessage = en.encryption(message, publicKey);
            out.println(cryptedMessage);
            out.flush();
        }
    }
    
}