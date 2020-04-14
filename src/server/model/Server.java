package server.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import client.model.ClientHandler1;

/**
 * 04/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

/**
 * The TCP_Server_V0_1 is a class receives and reads of a string message of the Arduino,
 * And sends a string message to Arduino.
 */

public class Server extends Thread {
   
    private ServerSocket serverSocket;

    /**
     * The constructor take the port number shares the values to the class's instance variables,
     * and start a server socket connection with the clients, and start the listening to clients.
     *
     * @param port is the port number.
     */

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        start();
    }

    public void run() {
        System.out.println("Server.Server running. Port number is " + serverSocket.getLocalPort()+".");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler c=new ClientHandler(socket);
            } catch (IOException e) {
            	System.out.println(e);
            }
        }
    }



    public static void main(String[] args) throws IOException {
        new Server(9000);
    }
}


