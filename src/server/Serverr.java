package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Serverr extends Thread {
   
    private ServerSocket serverSocket;

   
    public Serverr(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        start();
    }

    public void run() {
        System.out.println("ArduinoServer.ArduinoServer running. Port number is " + serverSocket.getLocalPort()+".");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                
                	ClientHandler c=new ClientHandler(socket);
				
            } catch (IOException e) {
            	System.out.println("OOOObs"+e);
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("class"+e);
			}
        }
    }



    public static void main(String[] args) throws IOException {
        new Serverr(8000);
    }
}


