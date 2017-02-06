package com.univangers.m2sili.chiffrement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.*;
import java.util.Vector;

/**
 * Represent an individual who can send a crypted message to another individual thanks to the Server class
 * Use the RSA encryption to communicate safely
 */
public class Individual {
    
    private String _name;
    private Key _publicKey;
    private Key _privateKey;
    private Key _publicKeyReceiper;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    Encrypter en;
    Decrypter de;
    
    public Individual(String name) {
        _name = name;
    }
    
    public Individual(String name, Key publicK, Key privateK) {
        _name = name;
        _publicKey = publicK;
        _privateKey = privateK;
        en = new Encrypter();
        de = new Decrypter();
    }
    
    public void startConnectionToServer(int PortNumber) {
        try {
            System.out.println("Connection to Bob at port " + PortNumber + " ...");
            socket = new Socket("localhost", PortNumber);
            System.out.println("Connection accepted !");
            
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
            
            String messageServer = in.readLine();
            System.out.println("Bob to "+_name+" : "+messageServer);
            
            
            
            System.out.println(_name+" to Bob : "+ _publicKey.getE().toString()); // Envoi de sa clé publique
            out.println(_publicKey.getE().toString());
            System.out.println(_name+" to Bob : "+ _publicKey.getN().toString()); // Envoi de sa clé publique
            out.println(_publicKey.getN().toString());
            
            out.flush();
            
            _publicKeyReceiper = new Key();
            String publicKeyOtherE = in.readLine(); // Récupération de la clé publique de l'autre individu
            System.out.println("Bob to "+_name+" : "+ publicKeyOtherE);
            _publicKeyReceiper.setE(new BigInteger(publicKeyOtherE));
            
            String publicKeyOtherN = in.readLine(); // Récupération de la clé publique de l'autre individu
            System.out.println("Bob to "+_name+" : "+ publicKeyOtherN);
            _publicKeyReceiper.setN(new BigInteger(publicKeyOtherN));
            
            Thread t1 = new Thread(new Emission(out, _publicKeyReceiper, "Alice"));
            t1.start();
            Thread t2 = new Thread(new Reception(in, _privateKey, "Alice"));
            t2.start();
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String message) {
        String cryptedMessage = en.encryption(message, _publicKeyReceiper);
        System.out.println(_name + " to Bob : (non-crypted message) " + message);
        System.out.println(_name + " to Bob : (crypted message) " + cryptedMessage);
        out.println(cryptedMessage);
        out.flush();
    }
    
    public String receiveMessage() throws IOException {
        String messageReceive = in.readLine();
        System.out.println("Bob to "+_name+" : "+ messageReceive);
        return de.decryption(messageReceive, _privateKey);
    }
    
    public void closeConnectionToServer() {
        try {
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        KeyGenerator gen = new KeyGenerator();
        Vector<Key> coupleKey = gen.build_key_couple();
        Individual Alice = new Individual("Alice", coupleKey.firstElement(), coupleKey.lastElement());
        Alice.startConnectionToServer(Server.PORTNUMBER);
        /*Alice.sendMessage("Coucou Bob ! Ca va ?");
        try {
            String messageDecrypted = Alice.receiveMessage();
            System.out.println("Message receive from Bob : " + messageDecrypted);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Alice.closeConnectionToServer();*/
    }
}