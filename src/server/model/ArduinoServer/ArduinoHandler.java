package server.model.ArduinoServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 05/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class ArduinoHandler extends Thread {
    private Socket socket;
    private Socket socket2;
    private BufferedReader inPut;
    private BufferedWriter outPut;
    private ArduinoServer server;

    public ArduinoHandler(Socket socket, ArduinoServer server) throws IOException {
        this.socket = socket;
        this.server = server;
        inPut = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outPut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        new readMessage().start();
    }

    public class readMessage extends Thread {
        public void run() {
            while (true) {
                try {
                    String inputMessage = inPut.readLine();
                    sort(inputMessage);
                    //System.out.println("Received message: " + inputMessage);
                } catch (Exception e) {
                    try {
                        socket.close();
                    } catch (Exception e2) {
                    }
                }
            }
        }
    }

    private void sort(String inputMessage) {
        if (inputMessage.toLowerCase().contains("server")) {
            try {
                //System.out.println("we gor a new request");
                requestHandler(inputMessage);
            } catch (IOException e) {
                System.out.println("We have a problem sending the message to arduino");
            }
        } else {
            StatusUpdate(inputMessage);
        }
    }

    private void requestHandler(String inputMessage) throws IOException {
        String request;
        if (inputMessage.toLowerCase().contains("on")) {
            //System.out.println("SHOULD BE ON");
            request = "lamp1_on";
        } else {
            //System.out.println("SHOULD BE OFF");
            request = "lamp1_off";
        }
        //System.out.println("this is request" + request);
        Arduino arduinoClient = server.getArduino();
        arduinoClient.sendToArduino(request);
    }

    private void StatusUpdate(String inputMessage) {
        //System.out.println("Our input Message is :" + inputMessage);
        switch (inputMessage) {
            case "Connected":
                sendStatus("Arduino is Connected");
                try {
                    Arduino arduino = new Arduino(socket.getOutputStream());
                    server.setArduino(arduino);
                } catch (IOException e) {
                    System.out.println("Cant create a new arduino");
                }
                break;
            case "lamp1_on":
                sendStatus("The lamp is turned on");
                break;
            case "lamp1_off":
                sendStatus("The lamp is turned off");
                break;
            case "disconnected":
                sendStatus("Arduino is now disconnected");
                break;
            default:
                //System.out.print("Arduino said: " + inputMessage);
        }
    }

    private void sendStatus(String status) {
        Statee state = new Statee(status);
        try {
            //System.out.println("We are trying to send this status:" + state.getState());
            Socket clientSocket = new Socket(InetAddress.getLocalHost(), 8000);
            ObjectOutputStream ous = new ObjectOutputStream(clientSocket.getOutputStream());
            ous.writeObject(state);
            clientSocket.close();
        } catch (IOException o) {
            System.out.println("we have a problem connecting to Server");
        }
    }
}
