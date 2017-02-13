package com.univangers.m2sili.chiffrement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;
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
    private Encrypter en;
    private Decrypter de;
    
    
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
    
    public void startConnectionToServer(InetAddress IP, int PortNumber) {
        try {
            System.out.println("Connection to Bob at port " + PortNumber + " ...");
            socket = new Socket(IP, PortNumber);
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
            
            Emission e = new Emission(out, _publicKeyReceiper, "Alice");
            Reception r = new Reception(in, _privateKey, "Alice");
            e.start();
            r.start();
            
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

    public static void main(String[] args) throws UnknownHostException{
        KeyGenerator gen = new KeyGenerator();
        Vector<Key> coupleKey = gen.build_key_couple();
        System.out.println("localhost IP : " + InetAddress.getLocalHost().toString());
        System.out.println("Saisissez IP sur laquelle se connecter : ");
        Scanner sc = new Scanner(System.in);
        String IP = sc.nextLine();
        System.out.println();
        
        System.out.println("Saisissez le port sur lequel se connecter : ");
        int portNb = sc.nextInt();
        System.out.println();
        
        Individual Alice = new Individual("Alice", coupleKey.firstElement(), coupleKey.lastElement());

        Alice.startConnectionToServer(InetAddress.getByName(IP), portNb);
        
        //Alice.closeConnectionToServer();
    }
}
