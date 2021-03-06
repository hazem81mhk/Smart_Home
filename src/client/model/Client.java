package client.model;

import client.controller.Controller;
import client.view.Schedule;
import server.model.ArduinoServer.Statee;
import server.model.MainServer.*;

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
            checkCurtainState();
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

    private void checkCurtainState() {
        sendRequest("initiate");
    }

    private void sort(Object object) {
        if (object instanceof Statee) {
            Statee state = (Statee) object;
            String stateTxt = state.getState();
            if (stateTxt.toLowerCase().contains("arduino is cnnected")) {
                System.out.println("Message receive from the server: Arduino is Connected");
                controller.sendUpdate("Arduino is Connected");
            }
            if (stateTxt.toLowerCase().contains("on")) {
                System.out.println("Message receive from the server: Now, The lamp is on");
                controller.setButtonOff();
                controller.sendUpdate("Lamp is on");
            }
            if (stateTxt.toLowerCase().contains("off")) {
                System.out.println("Message receive from the server: Now, the lamp is off");
                controller.setButtonOn();
                controller.sendUpdate("Lamp is off");
            }

            if (stateTxt.toLowerCase().contains("up") && !stateTxt.toLowerCase().contains("stoop") && !stateTxt.toLowerCase().contains("down")) { //CHECK IT OUT!
                System.out.println("Message receive from the server: Now, The curtain is rolling ups");
                controller.CButtonup();
                controller.sendUpdate("Curtain is rolling up");
            }
            if (stateTxt.toLowerCase().contains("down") && !stateTxt.toLowerCase().contains("stoop")) {
                System.out.println("Message receive from the server: Now, The curtain is rolling down");
                controller.CButtondown();
                controller.sendUpdate("Curtain is rolling down");
            }
            if (stateTxt.toLowerCase().contains("stoop")) {
                System.out.println("Message receive from the server: Now, The curtain has stopped rolling");
                controller.CButtonstop();
                controller.sendUpdate("Curtain has stopped rolling");
            }
            if (stateTxt.toLowerCase().contains("err")) {
                int x = JOptionPane.showConfirmDialog(null, "There is an onging schedule would you like to cancel it?", "ERROR ", JOptionPane.YES_NO_OPTION);
                if (x == JOptionPane.YES_OPTION) {
                    sendRequest("cancel_Schedule");
                }
            }

            if (stateTxt.toLowerCase().contains("canceled")) {
                System.out.println("ALL SCHEDULE IS CANCELED NOW");
                controller.CButtonstop();
            }

            if (stateTxt.toLowerCase().contains("top")) {
                System.out.println("Message receive from the server: Now, The curtain is on top");
                controller.CButtonTop();
                controller.sendUpdate("Curtain is on top");
            }

            if (stateTxt.toLowerCase().contains("bottom")) {
                System.out.println("Message receiveThe curtain is rolling up from the server: Now, The curtain reached the bottom");
                controller.CButtonBotoom();
                controller.sendUpdate("Curtain reached the bottom");
            }

            if (stateTxt.toLowerCase().contains("open")) {
                System.out.println("Message receive from the server: Now, The door is opened");
                controller.setOpenButtonOff();
                controller.sendUpdate("door is opened");
            }

            if (stateTxt.toLowerCase().contains("close")) {
                System.out.println("Message receive from the server: Now, The door is closed");
                controller.setCloseButtonOff();
                controller.sendUpdate("door is closed");
            }

            if (stateTxt.toLowerCase().contains("guest")) {
                System.out.println("Message receive from the server: Now, We have a guest");
                int x = JOptionPane.showConfirmDialog(null, "A Guest Is Standing Out Side, would You Like to Let Them In?", "ERROR ", JOptionPane.YES_NO_OPTION);
                if (x == JOptionPane.YES_OPTION) {
                    sendRequest("open");
                }
            }
        }
        if (object instanceof ConsumptionCounter) {
            consumptionHandler((ConsumptionCounter) object);
        }
    }

    public void stateHandler(Statee state) {
        String stateTxt = state.getState();
        if (stateTxt.toLowerCase().contains("on")) {
            System.out.println("Lamp is on");
        }
        if (stateTxt.toLowerCase().contains("off")) {
            System.out.println("lamp is off");
        }
        if (stateTxt.toLowerCase().contains("off")) {
            System.out.println("lamp is off");
        }
    }

    private void consumptionHandler(ConsumptionCounter object) {
        String str = String.format(" You have spent :%1.2fkr On the lamp", object.getCost());
        System.out.println(str);
        controller.sendUpdate(str);
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

    public void sendCons(ConsumptionCounter cons) {
        System.out.println("Get consumption is send to server");
        try {
            ous.writeObject(cons);
            ous.flush();
        } catch (IOException e) {
            System.out.println("There is a problem to send Get consumption");
        }
    }

    public void sendUser(User user) {
        try {
            String str = "User name: " + user.getName() + ", is connected to the server.";
            System.out.println(str);
            //controller.sendUpdate(str);
            ous.writeObject(user);
            ous.flush();
        } catch (IOException e) {
            System.out.println("There is a problem to send the user");
        }
    }

    public void sendSchedule(Schedule schedule) {
        try {
            System.out.println("Schedule to the server send:: " + schedule);
            ous.writeObject(schedule);
            ous.flush();
        } catch (IOException e) {
            System.out.println("There is a problem to send the schedule");
        }
    }

    public void sendCurtainSchedule(CurtainSchedule cschedule) {
        try {
            System.out.println("Curtain schedule sent to the server: ");
            ous.writeObject(cschedule);
            ous.flush();
        } catch (IOException e) {
            System.out.println("Theres a problem sending the Curtain schedule");
        }
    }

    public void sendTempSchedule(TempSchedule tschedul) {
        try {
            System.out.println("Temperature schedule sent to the server: " + tschedul);
            ous.writeObject(tschedul);
            ous.flush();
        } catch (IOException e) {
            System.out.println("Theres a problem sending the Temperature schedule");
        }
    }
}


