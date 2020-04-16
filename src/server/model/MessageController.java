package server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Queue;

public class MessageController extends Thread {
    private final Server server;
    private final Socket socket;
    private TrafficRegister trafficRegister;
    private CommandHandler cmdHandler;
    private User user;
    private SimpleDateFormat simpleDateFormat;

    private ArrayList<User> allUsersList;
    private ArrayList<String> onlineUser;
    private HashMap<String, ObjectOutputStream> userSocket = new HashMap<String, ObjectOutputStream>();

    public MessageController(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        this.trafficRegister = server.getTrafficRegister();

        allUsersList = new ArrayList<User>();
        onlineUser = new ArrayList<String>();
        simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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
                //ous.close();
                //ois.close();
                trafficRegister.userIsOffline(user);
                server.setOfflinUser(user);
                userOfflineHandler(user);           //Add Hazem
            } catch (Exception e2) {
            }
        }
        //System.out.println("we have a problem sending this Message:");
    }

    public void sortOf(Object messageObject) throws IOException {
        if (messageObject instanceof User) {
            userHandler((User) messageObject);
        } else if (messageObject instanceof Message) {
            messageHandler((Message) messageObject);
        }
    }

    public synchronized void userHandler(User user) throws IOException {
        this.user = user;
        allUsersList = server.getUsers();
        if (allUsersList.contains(user)) {
            server.setOnlineUser(user.getName());
            cmdHandler = new CommandHandler("add", user);        /////ADDED
            onlineBroadcast(cmdHandler);                                /////ADDED
            updateUserOnlineList(user);                                    /////ADDED
            trafficRegister.userIsOnline(user);
            if (server.hasOfLinMessage(user.getName())) {
                sendOflinMsg(user.getName());
            }
            System.out.println(user.getName() + "already exist");
        } else {
            server.addUser(user);
            ObjectOutputStream oosm = new ObjectOutputStream(socket.getOutputStream());
            server.setClientSocket(user.getName(), oosm);
            server.setOnlineUser(user.getName());

            cmdHandler = new CommandHandler("add", user);        /////ADDED
            onlineBroadcast(cmdHandler);                                /////ADDED
            updateUserOnlineList(user);                                    /////ADDED
            trafficRegister.userIsOnline(user);
            if (server.hasOfLinMessage(user.getName())) {
                sendOflinMsg(user.getName());
            }
        }
    }

    // Add Hazem
    public void userOfflineHandler(User user) throws IOException {
        cmdHandler = new CommandHandler("remove", user);
        onlineBroadcast(cmdHandler);
    }

    private void sendOflinMsg(String name) throws IOException {
        Queue offMssg = server.getoflinQueu(name);
        if (offMssg == null) {
            System.out.print("this Queue is empty");
        }
        while (!server.getoflinQueu(name).isEmpty()) {
            messageofflineHandler((Message) offMssg.remove());
        }
    }

    public synchronized void onlineBroadcast(CommandHandler cmdHandler) throws IOException {
        User us;
        onlineUser = server.getOnlineList();
        for (int i = 0; i < onlineUser.size(); i++) {
            us = new User(onlineUser.get(i), null);
            if (!us.getName().equals(cmdHandler.getUser().getName())) {                    ////CHANGE
                us = new User(onlineUser.get(i), null);
                ObjectOutputStream socketToSendTo = getReciverSocket(us);                ////CHANGE
                socketToSendTo.writeObject(cmdHandler);                                    ////CHANGE
                socketToSendTo.flush();
            }
        }
    }


    public synchronized void messageHandler(Message msg) throws IOException {
        ArrayList<User> rec = msg.getReciverList();
        ArrayList<String> onlineUsers = server.getOnlineList();
        for (int a = 0; a < rec.size(); a++) {
            if (onlineUsers.contains(rec.get(a).getName())) {
                setsendtime(msg);
                setreceivedtime(msg);
                trafficRegister.msgOnline(rec.get(a).getName(), msg.getSender().getName(), msg.getSendTime(), msg.getReciveTime());
                ObjectOutputStream socketToSendTo = getReciverSocket(rec.get(a));
                socketToSendTo.writeObject(msg);
                socketToSendTo.flush();
            } else {
                setsendtime(msg);
                server.addToQueue(rec.get(a).getName(), msg);
                trafficRegister.msgOffline(rec.get(a).getName(), msg.getSender().getName(), msg.getSendTime());
            }
        }
    }

    public synchronized void messageofflineHandler(Message msg) throws IOException {
        ArrayList<User> rec = msg.getReciverList();
        for (int a = 0; a < rec.size(); a++) {
            setreceivedtime(msg);
            trafficRegister.msgOnlineSend(rec.get(a).getName(), msg.getSender().getName(), msg.getSendTime(), msg.getReciveTime());
            ObjectOutputStream socketToSendTo = getReciverSocket(rec.get(a));
            socketToSendTo.writeObject(msg);
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

    private void setsendtime(Message message) {
        String time = simpleDateFormat.format(new Date());
        message.setSendTime(time);
    }

    private void setreceivedtime(Message message) {
        String time = simpleDateFormat.format(new Date());
        message.setReciveTime(time);
    }

    public void closeInputStream() {
        try {                                                                //new
            User server = new User("server", null);
            cmdHandler = new CommandHandler("disconnect", server);
            onlineBroadcast(cmdHandler);
            socket.getOutputStream().close();
            socket.getInputStream().close();
            socket.close();

        } catch (IOException e) {

        }
    }

    public void updateUserOnlineList(User user) throws IOException {           ////ADDED
        User us;                                                               ////CHANGE IN THE OBJECT SENT
        onlineUser = server.getOnlineList();
        for (int i = 0; i < onlineUser.size(); i++) {
            us = new User(onlineUser.get(i), null);
            if (!us.getName().equals(user.getName())) {
                us = new User(onlineUser.get(i), null);
                ObjectOutputStream socketToSendTo = getReciverSocket(user);
                cmdHandler = new CommandHandler("add", us);
                socketToSendTo.writeObject(cmdHandler);
                socketToSendTo.flush();
            }
        }
    }
}
