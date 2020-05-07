package server.model.ArduinoServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 05/04/2020
 *
 * @Mohammed Hazem Kudaimi and Mohammed Amayri
 */

public class ArduinoHandler extends Thread {
    private Socket socket;
    private Socket socket2;
    private BufferedReader inPut;
    private BufferedWriter outPut;
    private ArduinoServer server;
    private Socket clientSocket;
    private ObjectOutputStream ous;

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
                    ////System.out.println("Received message: " + inputMessage);
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
                ////System.out.println("we gor a new request");
                requestHandler(inputMessage);
            } catch (IOException e) {
                //System.out.println("We have a problem sending the message to arduino");
            }
        } else {
            StatusUpdate(inputMessage);
        }
    }

    private void requestHandler(String inputMessage) throws IOException {
        String request = null;
        if (inputMessage.toLowerCase().contains("on")) {
            ////System.out.println("SHOULD BE ON");
            request = "lamp1_on";
        }
        else if  (inputMessage.toLowerCase().contains("off")) {
            ////System.out.println("SHOULD BE OFF");
            request = "lamp1_off";
        }
        else if (inputMessage.toLowerCase().contains("up")) {
            ////System.out.println("SHOULD BE OFF");
            request = "up";
        }
        else if (inputMessage.toLowerCase().contains("down")) {
            ////System.out.println("SHOULD BE OFF");
            request = "down";
        }
        else if (inputMessage.toLowerCase().contains("stop")) {
            ////System.out.println("SHOULD BE OFF");
            request = "stop";
        }
        else if (inputMessage.toLowerCase().contains("temp")) {
            ////System.out.println("SHOULD BE OFF");
            request = "temp";
        }
        else if (inputMessage.toLowerCase().contains("initiate")) {
            ////System.out.println("SHOULD BE OFF");
            request = "initiate";
        }
        ////System.out.println("this is request" + request);
        Arduino arduinoClient = server.getArduino();
        arduinoClient.sendToArduino(request);
    }

    private void StatusUpdate(String inputMessage) {
        //System.out.println("Our input Message is :" + inputMessage);
        switch (inputMessage) {
            case "Cnnected":
                sendStatus("Arduino is Cnnected");
                try {
                    Arduino arduino = new Arduino(socket.getOutputStream());
                    server.setArduino(arduino);
                } catch (IOException e) {
                    //System.out.println("Cant create a new arduino");
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
            case "up":
                sendStatus("up");
                //System.out.println("ARDUINO HANDLER: up");
                break;
            case "down":
                sendStatus("down");
                //System.out.println("ARDUINO HANDLER: down");
                break;
            case "top":
                sendStatus("top");
                //System.out.println("ARDUINO HANDLER: top");
                break;
            case "bottom":
                sendStatus("bottom");
                //System.out.println("ARDUINO HANDLER: bottom");
                break;
            case "stoop":
                sendStatus("stoop");
                //System.out.println("ARDUINO HANDLER: STOP");
                break;
                
            default:
            	sendStatus(inputMessage);
                //System.out.println("Arduino said: " + inputMessage);
        }
    }

    private void sendStatus(String status) {
        Statee state = new Statee(status);
        server.sendToServer(state);
    }
}
