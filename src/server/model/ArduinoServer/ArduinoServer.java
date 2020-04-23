package server.model.ArduinoServer;

import server.model.MainServer.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 04/04/2020
 *
 * @Mohammed Hazem Kudaimi and Mohammed Amayri
 */

/**
 * The TCP_Server_V0_1 is a class receives and reads of a string message of the Arduino,
 * And sends a string message to Arduino.
 */

public class ArduinoServer extends Thread {

    private ServerSocket serverSocket;
    private int port;
    private Arduino arduino;
    private Server server;

    /**
     * The constructor take the port number shares the values to the class's instance variables,
     * and start a server socket connection with the clients, and start the listening to clients.
     *
     * @param port is the port number.
     */

    public ArduinoServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        this.port=port;
        start();
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ArduinoHandler arduinoHandler = new ArduinoHandler(socket, this);
            } catch (IOException e) {
                //System.out.println("We have a problem in our Arduino sever socket");
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    public void closeArduinoServerSocket() throws IOException {
        serverSocket.close();
    }

    public int getPort() {
        return port;
    }

    public void setArduino(Arduino arduino) {
        this.arduino = arduino;
    }

    public Arduino getArduino() {
        return arduino;
    }
}


