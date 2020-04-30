package client.model;

import client.controller.Controller;
import client.view.Schedule;
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
                //System.out.println(request.getTextMessage());
            }
            //AGON
            if (stateTxt.toLowerCase().contains("up")) {
                System.out.println("Message receive from the server: Now, The curtain is rolling up");
                controller.CButtonup();
                controller.sendUpdate("Curtain is rolling up");
            }
            if (stateTxt.toLowerCase().contains("down")) {
                System.out.println("Message receive from the server: Now, The curtain is rolling down");
                controller.CButtondown();
                controller.sendUpdate("Curtain is rolling down");
            }
            if (stateTxt.toLowerCase().contains("stop")) {
                System.out.println("Message receive from the server: Now, The curtain has stopped rolling");
                controller.CButtonstop();
                controller.sendUpdate("Curtain has stopped rolling");
            }
        /* if (object instanceof Statee) {
            stateHandler((Statee) object);
        }*/

        }
        if (object instanceof ConsumptionCounter) {
            consumptionHandler((ConsumptionCounter) object);
        }
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
    private void consumptionHandler(ConsumptionCounter object) {
        //String str = ("From" + object.getDateStart() + " To: " + object.getDateEnd() + " You have spent :" + object.getCost() + " On the lamp");
        String str = String.format(" You have spent :%1.2fkr On the lamp", object.getCost() );
        System.out.println(str);
        //JOptionPane.showMessageDialog(null, str);
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
            System.out.println("Curtain schedule sent to the server: " +cschedule);
            ous.writeObject(cschedule);
            ous.flush();
        } catch (IOException e) {
            System.out.println("Theres a problem sending the Curtain schedule");
        }
    }

    public void sendTempSchedule(TempSchedule tempschedule) {
        try {
            System.out.println("Temperature schedule sent to the server: " + tempschedule);
            ous.writeObject(tempschedule);
            ous.flush();
        } catch (IOException e) {
            System.out.println("Theres a problem sending the Temperature schedule");
        }
    }
}


