package server.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import client.model.ClientHandler1;


public class Serverr extends Thread {
   
    private ServerSocket serverSocket;

   
    public Serverr(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        start();
    }

    public void run() {
        System.out.println("Server.Server running. Port number is " + serverSocket.getLocalPort()+".");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                
                	ClientHandler1 c=new ClientHandler1(socket);
				
            } catch (IOException e) {
            	System.out.println("OOOObs"+e);
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("class"+e);
			}
        }
    }



    public static void main(String[] args) throws IOException {
        new Server(9000);
    }
}


