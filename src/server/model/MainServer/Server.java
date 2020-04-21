package server.model.MainServer;

import server.controller.Controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
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
    private int minutesCounted = 0;
    private SimpleDateFormat sdf;
    private HashMap<String, Integer> ourMap = new HashMap<String, Integer>();
    private ArrayList<Integer> indexArr = new ArrayList<Integer>();
    private ArrayList<String> indexStr = new ArrayList<String>();


    public void setOnTimer(Date onTimer) {
        this.onTimer = onTimer;
    }

    public void setOffTimer(Date offTimer) {
        this.offTimer = offTimer;
        countTimeOfConsumption(onTimer, offTimer);
        saveTimeToFile();
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String time = sdf.format(new Date());
        String logStr = (time+"    The lampa was on for " + minutesCounted);
        sendTrafficMessage(logStr);
    }

    public void countTimeOfConsumption(Date on, Date off) {
        minutesCounted = (int) getDateDiff(on, off, TimeUnit.MINUTES);
    }

    public int countConsumptionCost(int kiloWatsPerHour, int costOfOneKilo) {
        int hours = minutesCounted / 60;
        int cost = hours * kiloWatsPerHour * costOfOneKilo;
        return cost;
    }

    public Server(int port, Controller controller) throws IOException {
        this.controller = controller;
        this.serverPort = port;
        serverSocket = new ServerSocket(serverPort);
        trafficRegister = new TrafficRegister(this);
    }

    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                MessageController msgController = new MessageController(this, clientSocket);
                messageControllerList.add(msgController);
                msgController.start();
            } catch (IOException e) {
                //System.out.println("We have a problem in our sever socket");
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    serverSocket.close();
                    System.out.println("The server is shut down!");
                } catch (IOException e) {
                    System.out.println("The server is still running ");
                }
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
        controller.disPlayEvent(logStr);
    }

    public void sendTrafficUser(String logStr) {
        controller.disPlayEvent(logStr);
    }

    public static long getDateDiff(java.util.Date date, java.util.Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    // When closing? or when turning the lamp off?
    public void saveTimeToFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateToSave = sdf.format(Calendar.getInstance().getTime());
        try (FileWriter fw = new FileWriter("files/lampLog.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(dateToSave + ":" + minutesCounted);

            //more code
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public void printStatics() {
        readAndSort("files/lampLog.txt");
        System.out.println(Arrays.asList(ourMap)); // method 1
        System.out.println("printStatics");

    }


    void readAndSort(String filename) {
        int index = -1;
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String subString = line.substring(0, 10);

            String subString1 = null;
            while (line != null) {
                if (subString.equals(subString1)) {
                    int number = Integer.parseInt(line.substring(11, line.length()));
                    {
                        int toAddTo = indexArr.get(index) + number;
                        //System.out.println("Arr index"+index+"we found the value "+indexArr.get(index)+" and added "+number+" we got ");
                        indexArr.add(index, toAddTo);
                        //System.out.println(indexArr.get(index));
                    }
                } else {
                    index++;

                    //System.out.println("We are increasing the index now ");
                    subString1 = subString;
                    int number = Integer.parseInt(line.substring(11, line.length()));
                    //System.out.println(number);

                    indexArr.add(0);
                    indexArr.add(index, number);
                    indexStr.add("");
                    indexStr.add(index, subString);
                    //System.out.println("att index"+index+"we added"+number);

                }
                line = br.readLine();
                if (br.equals(null)) {
                    break;
                } else {
                    try {
                        subString = line.substring(0, 10);
                    } catch (NullPointerException e) {

                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Hello,sorry to bother you but there is an error");
        }
        for (int x = 0; x <= index; x++) {
            ourMap.put(indexStr.get(x), indexArr.get(x));
        }

    }
}

    


