package client.model;

import client.controller.Controller;
import client.view.Schedule;
import server.model.ArduinoServer.Statee;
import server.model.MainServer.Request;
import server.model.MainServer.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 16/04/2020
 *
 * @Mohammed Amayri and Mohammed Hazem Kudaimi
 */

public class Client extends Thread {
    private int port;
    private String ip;
    private String userName;
    private User user;
    private ObjectInputStream ois;
    private ObjectOutputStream ous;
    private Socket clientSocket;
    private Controller controller;

    public Client(String ip, int port, String userName, Controller controller) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.user = new User(userName);
        this.controller = controller;
        start();
    }

    @Override
    public void run() {
        clientSocket = null;
        try {
            clientSocket = new Socket(ip, port);
            ous = new ObjectOutputStream(clientSocket.getOutputStream());
            sendUser(user);
            controller.startClientGui();
            ois = new ObjectInputStream(clientSocket.getInputStream());
            try {
                while (true) {
                    Object object = ois.readObject();
                    if (object != null) {
                        sort(object);
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (IOException e) {
            System.out.println("We have a problem connecting to the server");
            JOptionPane.showMessageDialog(null, "You have a problem connecting to the server" +
                    "\n Please make sure that the server is running ");
        }
    }

    private void sort(Object object) {
        if (object instanceof Request) {
            Request request = (Request) object;
            String stateTxt = request.getTextMessage();
            if(stateTxt.toLowerCase().contains("arduino is cnnected"))
                System.out.println("Arduino is Connected");
            if (stateTxt.toLowerCase().contains("on")) {
                System.out.println("Lamp is on");
                controller.setButtonOff();
            }
            if (stateTxt.toLowerCase().contains("off")) {
                System.out.println("lamp is off");
                controller.setButtonOn();
                //System.out.println(request.getTextMessage());

        /*} else if (object instanceof Statee) {
            stateHandler((Statee) object);
        }*/
            }

   /* public void stateHandler(Statee state) {
        String stateTxt = state.getState();
        if (stateTxt.toLowerCase().contains("on")) {
            System.out.println("Lamp is on");
        }
        if (stateTxt.toLowerCase().contains("off")) {
            System.out.println("lamp is off");
        }
    */
        }
    }

    public void sendRequest(String request) {
        Request req = new Request(request);
        System.out.println("Request to the server send: " + request);
        try {
            ous.writeObject(req);
            ous.flush();
        } catch (IOException e) {
            System.out.println("There is a problem to send the request");
        }
    }

    public void sendUser(User user) {
        try {
            System.out.println("User name: " + user.getName() + ", is connected to the server.");
            ous.writeObject(user);
            ous.flush();
        } catch (IOException e) {
            System.out.println("There is a problem to send the user");
        }
    }

    public void sendSchedule(Schedule schedule) {
        try {
            System.out.println("Schedule to the server send: " + schedule);
            ous.writeObject(schedule);
            ous.flush();
        } catch (IOException e) {
            System.out.println("There is a problem to send the schedule");
        }
    }
}


