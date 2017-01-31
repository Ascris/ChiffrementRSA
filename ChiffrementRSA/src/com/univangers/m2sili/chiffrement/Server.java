package com.univangers.m2sili.chiffrement;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.*;


public class Server {

    public static final int PORTNUMBER = 17047;
    
    public static void main(String[] args){

        ServerSocket socket;
        Key privateKey;
        Key publicKey;
        try {
            privateKey = new Key();
            publicKey = new Key();
            socket = new ServerSocket(PORTNUMBER);
            System.out.println("Serveur : Serveur à l'écoute du port "+socket.getLocalPort());
            Thread t = new Thread(new IndividualAccept(socket, publicKey, privateKey));
            t.start();
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class IndividualAccept implements Runnable {

    private ServerSocket socketserver;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    Key publicKey;
    Key privateKey;
    Key publicKeyReceiper;
    
    public IndividualAccept(ServerSocket s, Key publicK, Key privateK){
       socketserver = s;
       publicKey = publicK;
       privateKey = privateK;
    }

    public void run() {

        try {
            while(true){
                socket = socketserver.accept(); // Un individu se connecte et est accepté
                
                System.out.println("Serveur : Acceptation d'une connexion !");
                
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
                
                out.println("Vous êtes connecté au serveur. Clé publique ?");
                out.flush();
                
                String cleIndividuE = in.readLine();
                String cleIndividuN = in.readLine();
                
                publicKeyReceiper = new Key();
                
                publicKeyReceiper.setE(new BigInteger(cleIndividuE));
                publicKeyReceiper.setN(new BigInteger(cleIndividuN));
                
                out.println(publicKey.getE().toString());
                out.println(publicKey.getN().toString());
                out.flush();
                
                
                // Gestion des échanges cryptés

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}