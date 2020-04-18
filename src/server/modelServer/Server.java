package server.modelServer;

import server.controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {
    private int serverPort;
    private ServerSocket serverSocket;
    private Controller controller;
    private TrafficRegister trafficRegister;

    private HashMap<String, ObjectOutputStream> clientSocket = new HashMap<String, ObjectOutputStream>();
    private ArrayList<MessageController> messageControllerList = new ArrayList<MessageController>();
    private ArrayList<String> userList = new ArrayList<String>();
    private ArrayList<String> onlineUser = new ArrayList<>();
    private Date onTimer;
    private Date offTimer;
    private int minutesCounted=0;
   

	public void setOnTimer(Date onTimer) {
		this.onTimer = onTimer;
	}

	public void setOffTimer(Date offTimer) {
		this.offTimer = offTimer;
		countTimeOfConsumption(onTimer,offTimer);
		System.out.println();
	}
	public void countTimeOfConsumption(Date on,Date off)
	{
		minutesCounted+=(int)getDateDiff(on, off, TimeUnit.MINUTES);
	}
	public int countConsumptionCost(int kiloWatsPerHour,int costOfOneKilo)
	{
		int hours=minutesCounted/60;
		int cost=hours*kiloWatsPerHour*costOfOneKilo;
		return cost;
	}
	

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
    public static long getDateDiff(java.util.Date date, java.util.Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}


