package server.model.MainServer;

import server.model.ArduinoServer.Statee;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;


public class MessageController extends Thread {
    private final Server server;
    private final Socket socket;
    private TrafficRegister trafficRegister;
    private User user;
    private SimpleDateFormat sdf;
    private ObjectOutputStream oosm;
    private ArrayList<User> allUsersList;
    private ArrayList<String> onlineUser;
    private HashMap<String, ObjectOutputStream> userSocket = new HashMap<String, ObjectOutputStream>();
    private ConsumptionCounter consObject;

    public MessageController(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.trafficRegister = server.getTrafficRegister();

        allUsersList = new ArrayList<User>();
        onlineUser = new ArrayList<String>();
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }

    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream((socket.getInputStream()));
            while (true) {
                try {
                    Object o = ois.readObject();
                    sortOf(o);
                } catch (ClassNotFoundException e) {
                }
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (Exception e2) {
            }
        }
        //System.out.println("we have a problem sending this Request:");
    }

    public void sortOf(Object messageObject) throws IOException {
        if (messageObject instanceof User) {
            userHandler((User) messageObject);
        } else if (messageObject instanceof Request) {
            requestHandler((Request) messageObject);
        } else if (messageObject instanceof Statee) {
            stateHandler((Statee) messageObject);
        } else if (messageObject instanceof ConsumptionCounter) {
            consHandler((ConsumptionCounter) messageObject);
        }
    }

    public synchronized void userHandler(User user) throws IOException {
        this.user = user;
        allUsersList = server.getUsers();
        if (allUsersList.contains(user)) {
            server.setOnlineUser(user.getName());
        } else {
            server.addUser(user);
            ObjectOutputStream oosm = new ObjectOutputStream(socket.getOutputStream());
            server.setClientSocket(user.getName(), oosm);
            server.setOnlineUser(user.getName());
        }
    }

    public synchronized void requestHandler(Request msg) throws IOException {
        String request = msg.getTextMessage();
        if (request.toLowerCase().contains("getConsumption")) {
            server.printStatics();
        }
        request = "server" + request;
        Socket arduinoSocket = new Socket(InetAddress.getLocalHost(), 9000);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(arduinoSocket.getOutputStream()));
        bw.write(request);
        bw.newLine();
        bw.flush();
        arduinoSocket.close();
    }

    private void stateHandler(Statee state) {
        String stateTxt = state.getState();
        String time = sdf.format(new Date());
        if (stateTxt.toLowerCase().contains("on")) {
            server.sendTrafficMessage(time+"    "+stateTxt);
            server.setOnTimer(Calendar.getInstance().getTime());
        }
        if (stateTxt.toLowerCase().contains("off")) {
            server.sendTrafficMessage(time+"    "+stateTxt);
            server.setOffTimer(Calendar.getInstance().getTime());
        }
        if(stateTxt.toLowerCase().contains("connected")){
            server.sendTrafficMessage(time+"    "+stateTxt);
        }
        Request requestToClient = new Request("State update:" + stateTxt);
        try {
            onlineBroadcast(requestToClient);
        } catch (IOException e) {
            System.out.println("We have a problem with broadcasting the message");
        }
    }

    private void consHandler(ConsumptionCounter consObject) {
        this.consObject = consObject;
        consObject.setServer(server);
        consObject.setCost();
        try {
            oosm.writeObject(consObject);
            oosm.flush();
        } catch (IOException e) {
            System.out.println("We have a problem sending consObject");
        }
    }

    public synchronized void onlineBroadcast(Request cmdHandler) throws IOException {
        User us;
        onlineUser = server.getOnlineList();
        for (int i = 0; i < onlineUser.size(); i++) {
            us = new User(onlineUser.get(i));
            ObjectOutputStream socketToSendTo = getReciverSocket(us);
            socketToSendTo.writeObject(cmdHandler);
            socketToSendTo.flush();
        }
    }


    public synchronized ObjectOutputStream getReciverSocket(User reciver) {
        userSocket = server.getClientSocket();
        Entry<String, ObjectOutputStream> entry = userSocket.entrySet().iterator().next();
        ObjectOutputStream value = entry.getValue();
        if (value == null) {
            System.out.print("EVEN OUR VALUE IS FUCKED UP!");
        }
        String name = reciver.getName();
        ObjectOutputStream oos = userSocket.get(name);
        if (oos == null) {
            System.out.print("OUR SOCKET IS FUCKED UP!");
        }
        return oos;
    }

    private void setsendtime(Request request) {
        String time = sdf.format(new Date());
        request.setSendTime(time);
    }

    private void setreceivedtime(Request request) {
        String time = sdf.format(new Date());
        request.setReciveTime(time);
    }

    public void closeInputStream() {
        try {
            socket.getOutputStream().close();
            socket.getInputStream().close();
            socket.close();
        } catch (IOException e) {
        }
    }
}
