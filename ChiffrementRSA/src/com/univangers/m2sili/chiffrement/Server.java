package com.univangers.m2sili.chiffrement;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;


public class Server {

    public static final int PORTNUMBER = 17047;
    
    public static void main(String[] args){

        ServerSocket socket;
        Key privateKey;
        Key publicKey;
        try {
            KeyGenerator gen = new KeyGenerator();
            Vector<Key> coupleKey = gen.build_key_couple();
            
            publicKey = coupleKey.firstElement();
            privateKey = coupleKey.lastElement();
            
            System.out.print("Saisissez le port sur lequel ouvrir une connexion : ");
            Scanner scan = new Scanner(System.in);
            int portNb= scan.nextInt();
            System.out.println();
            
            socket = new ServerSocket(portNb);
            System.out.print("Serveur : Serveur à l'écoute du port "+socket.getLocalPort());
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
    Encrypter en;
    Decrypter de;
    
    public IndividualAccept(ServerSocket s, Key publicK, Key privateK){
       socketserver = s;
       publicKey = publicK;
       privateKey = privateK;
       en = new Encrypter();
       de = new Decrypter();
    }
    
    
    private void initConnection() throws IOException {
        
        socket = socketserver.accept(); // Un individu se connecte et est accepté
        System.out.println("Bob : Connection accepted !");

        out = new PrintWriter(socket.getOutputStream());
        in = new BufferedReader (new InputStreamReader(socket.getInputStream()));

        out.println("You are connect to Bob. Public key ?");
        out.flush();

        String cleIndividuE = in.readLine();
        String cleIndividuN = in.readLine();

        publicKeyReceiper = new Key();

        publicKeyReceiper.setE(new BigInteger(cleIndividuE));
        publicKeyReceiper.setN(new BigInteger(cleIndividuN));

        out.println(publicKey.getE().toString());
        out.println(publicKey.getN().toString());
        out.flush();
    }
    
    
    
    

    public void run() {

        try {
            while(true){
                initConnection();
                
                String message = in.readLine();
                System.out.println("Bob receive : " + message);
                String messageDecrypted = de.decryption(message, privateKey);
                System.out.println("Bob decrypted : " + messageDecrypted);
                
                String messageEncrypted = en.encryption(messageDecrypted, publicKeyReceiper);
                System.out.println("Bob encrypted : " + messageEncrypted);
                out.println(messageEncrypted);
                out.flush();

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}