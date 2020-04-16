package server.modelServer;

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


