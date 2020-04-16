package server.modelArduinoServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 04/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

/**
 * The TCP_Server_V0_1 is a class receives and reads of a string message of the Arduino,
 * And sends a string message to Arduino.
 */

public class ArduinoServer extends Thread {

    private ServerSocket serverSocket;
    private Arduino arduino;

    /**
     * The constructor take the port number shares the values to the class's instance variables,
     * and start a server socket connection with the clients, and start the listening to clients.
     *
     * @param port is the port number.
     */

    public ArduinoServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        start();
    }

    public void run() {
        System.out.println("ArduinoServer.ArduinoServer running. Port number is " + serverSocket.getLocalPort() + ".");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ArduinoHandler arduinoHandler = new ArduinoHandler(socket, this);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void setArduino(Arduino arduino) {
        this.arduino = arduino;
    }

    public Arduino getArduino() {
        return arduino;
    }
}


