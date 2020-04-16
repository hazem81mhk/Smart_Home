package server.model;

import server.controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread {
    private int serverPort;
    private ServerSocket serverSocket;
    private Controller controller;
    private TrafficRegister trafficRegister;

    private HashMap<String, ObjectOutputStream> clientSocket = new HashMap<String, ObjectOutputStream>();
    private ArrayList<MessageController> messageControllerList = new ArrayList<MessageController>();
    private ArrayList<String> userList = new ArrayList<String>();
    private ArrayList<String> onlineUser = new ArrayList<>();
    private Queue<Message> unsendMessage = new LinkedList();
    private HashMap<String, Queue> unsendMessageList = new HashMap<String, Queue>();

    public Server(int port, Controller controller) throws IOException {
        this.controller = controller;
        this.serverPort = port;
        serverSocket = new ServerSocket(serverPort);
        trafficRegister = new TrafficRegister(this);
    }

    public void run() {
        deleteFile();
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                MessageController msgController = new MessageController(this, clientSocket);
                messageControllerList.add(msgController);
                msgController.start();
            } catch (IOException e) {
                //System.err.println(e);
                break;
            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    serverSocket.close();
                    System.out.println("The server is shut down!");
                } catch (IOException e) { /* failed */ }
            }
        });
    }

    public void closeSocket() throws IOException {
        for (MessageController messageController : messageControllerList) {
            messageController.closeInputStream();
        }
        serverSocket.close();
    }

    public void setClientSocket(String user, ObjectOutputStream socket) {
        clientSocket.put(user, socket);
    }

    public HashMap getClientSocket() {
        return clientSocket;
    }

    public void setOnlineUser(String user) {
        onlineUser.add(user);
    }

    public void setOfflinUser(User user) {
        int index = onlineUser.indexOf(user.getName());
        onlineUser.remove(index);
    }

    public ArrayList<String> getOnlineList() {
        return onlineUser;
    }

    public void addUser(User user) {
        userList.add(user.getName());
    }

    public ArrayList getUsers() {
        return userList;
    }


    public void addOflinObject(String name, Queue line) {
        unsendMessageList.put(name, line);
    }

    public void addToQueue(String user, Message msg) {
        if (unsendMessageList.get(user) == null) {
            addOflinObject(user, unsendMessage);
            unsendMessageList.get(user).add(msg);
            //System.out.println("Have saved " + unsendMessageList.get(user).size() + " Offline Msgs");
        } else {
            unsendMessageList.get(user).add(msg);
            //System.out.println("Have saved " + unsendMessageList.get(user).size() + " Offline Msgs");
        }
    }

    public boolean hasOfLinMessage(String user) {
        Queue ourQueue = unsendMessageList.get(user);
        if (ourQueue == null || ourQueue.isEmpty()) {
            return false;
        } else
            return true;
    }

    public Queue getoflinQueu(String user) {
        return unsendMessageList.get(user);
    }

    public TrafficRegister getTrafficRegister() {
        return trafficRegister;
    }

    public void sendTrafficMessage(String logStr) {
        controller.disPlayChat(logStr);
    }

    public void sendTrafficUser(String logStr) {
        controller.disPlayEvent(logStr);
    }

    private void deleteFile() {
        File file = new File("files/allTraffic.txt");
        file.delete();
    }
}


