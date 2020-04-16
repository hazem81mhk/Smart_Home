package server.model;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientTest2 {
    private Socket socket;
    private ObjectOutputStream ous;
    private ArrayList<User> users;

    User mohammed = new User("Mohammed", null);
    User hazem = new User("Hazem", null);
    //   User ali = new User("Ali");

    public void statrClient() throws IOException {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 9001);
            ous = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e) {
            System.out.println("Your server is not connected");
        }

        users = new ArrayList<>();
        //users.add(mohammed);
        users.add(hazem);
        //users.add(ali);

        ImageIcon imageIcon = new ImageIcon("files/orkanen.jpg");

        Message message = new Message(users, mohammed, "Hello Hazem this is Mohammed!"
                , imageIcon, null, null);
        //ous.writeObject(message);

        try {
            ous.writeObject(mohammed);
            ous.flush();
        } catch (NullPointerException e) {
        //    System.out.println("Your server is not connected");
        }
        new SendMsg().start();
        new Connect().start();
    }

    public class SendMsg extends Thread {
        public synchronized void run() {
            try {
                while (true) {
                    String txt = JOptionPane.showInputDialog(null, "Enter your text");
                    Message mes = new Message(users, mohammed, txt, null, null, null);
                    try {
                        ous.writeObject((Object) mes);
                    } catch (SocketException |NullPointerException s) {
                        System.out.println("our connection has came to an end");
                    }
                }
            } catch (IOException e) {
                System.out.print(e);
            }
        }
    }

    public class Connect extends Thread {
        @Override
        public void run() {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Object us;
                while (true) {
                    us = ois.readObject();
                    if (us instanceof CommandHandler)
                        commandHandler((CommandHandler) us);
                    else if (us instanceof Message) {
                        Message msg = (Message) us;
                        System.out.println("Send at: " + msg.getSendTime() + " Recieved at: " + msg.getReciveTime() + ". Message text: " + msg.getTextMessage());
                    }
                }
            } catch (IOException | ClassNotFoundException | NullPointerException e) {
                //System.out.println("WE HAVE A REAL PROBLEM" + e);
            }
        }
    }

    private ArrayList<String> onlineUser = new ArrayList<String>();  //Change place Hazem

    private void commandHandler(CommandHandler us) {
        String command = us.getCommand();
        if (command.equals("add")) {
            if (!onlineUser.contains(us.getUser().getName())) {
                onlineUser.add(us.getUser().getName());
            }
            System.out.print("Those who are online:   ");
            for (int x = 0; x < onlineUser.size(); x++) {
                System.out.print(onlineUser.get(x) + ",   ");
            }
        } else if (command.equals("remove")) {
            System.out.println(us.getUser().getName() + " is offline"); // Add Hazem
            onlineUser.remove(us.getUser().getName());
            System.out.print("Those who are online:");
            for (int x = 0; x < onlineUser.size(); x++) {
                System.out.print(onlineUser.get(x) + ",   ");
            }
        } else if (command.equals("disconnect")) {
            System.out.println("OUR CONNECTION IS LOST");
        }
        System.out.println("");
    }

    public static void main(String[] args) throws IOException {
        ClientTest2 clientTest = new ClientTest2();
        clientTest.statrClient();
    }
}
