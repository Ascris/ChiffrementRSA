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
    
    public Reception(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        
        while (true) {
            try {
                message = in.readLine();
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
}
