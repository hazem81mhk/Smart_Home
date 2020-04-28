package server.model.MainServer;

import server.controller.Controller;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 10/04/2020
 *
 * @Mohammed Amayri and Moahmmed Hazem Kudaimi
 */

public class Server extends Thread {
    private int serverPort;
    private ServerSocket serverSocket;
    private Controller controller;

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

    private int minutesForCost = 0;

    private Schema schema = null;
    private boolean lampStatus = false;
    private MessageController messageController;
    private double temp=0;
    private TempSchedule tempSchedule=null;
    private CurtainSchedule curtainSchedule=null;


    public Server(int port, Controller controller) throws IOException {
        this.controller = controller;
        this.serverPort = port;
        serverSocket = new ServerSocket(serverPort);
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
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

    public void sendTrafficMessage(String logStr) {
        controller.disPlayEvent(logStr);
    }

    public void setOnTimer(Date onTimer) {
        this.onTimer = onTimer;
    }

    public void setOffTimer(Date offTimer) {
        this.offTimer = offTimer;
        countTimeOfConsumption(onTimer, offTimer);
        saveTimeToFile();
        String time = sdf.format(new Date());
        String logStr = (time + "    The lampa was on for " + minutesCounted + " minutes.");
        sendTrafficMessage(logStr);
    }

    public void countTimeOfConsumption(Date on, Date off) {
        minutesCounted = (int) getDateDiff(on, off, TimeUnit.MINUTES);
    }

    public static long getDateDiff(java.util.Date date, java.util.Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    // When closing? or when turning the lamp off?
    public void saveTimeToFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateToSave = sdf.format(Calendar.getInstance().getTime());
        try (FileWriter fw = new FileWriter("files/lampLog.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(dateToSave + ":" + minutesCounted);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

    public int printStatics(String startDate, String endDate) throws NumberFormatException, ParseException {
        int result = readAndSort(startDate, endDate);
        return result;
    }


    int readAndSort(String startDate, String endDate) {

        int minutesForCost = 0;
        try {
            FileReader fr = new FileReader("files/lampLog.txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String subString = line.substring(0, 10);
            String lineDate;
            String subString1 = null;
            int x = 0;
            while (line != null) {

                lineDate = line.substring(0, 19);
                if (checkDate(startDate, endDate, lineDate)) {
                    int lineNumb = Integer.parseInt(line.substring(20, line.length()));
                    minutesForCost += lineNumb;

                }
                line = br.readLine();
                x++;
            }
        } catch (NumberFormatException | ParseException | IOException e) {
            System.out.println(e);
        }
        return minutesForCost;

    }

    public boolean checkDate(String start, String end, String line) throws ParseException {
        boolean result = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String lineDate = line.substring(0, 10);
        Date startDate = sdf.parse(start);
        Date endDate = sdf.parse(end);
        Date todaysDate = sdf.parse(lineDate);

        //String currentTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());

        LocalTime current_Time = LocalTime.parse(getTime(line));
        LocalTime startTime = LocalTime.parse(getTime(start));
        LocalTime endTime = LocalTime.parse(getTime(end));

        LocalDateTime localDateTime = LocalDateTime.now();
        //System.out.println(localDateTime);

        if (todaysDate.after(startDate) || todaysDate.equals(startDate)) {
            if (todaysDate.before(endDate) || todaysDate.equals(endDate)) {
                //System.out.println(sdf.format(todaysDate) + " is actually between  " + sdf.format(startDate) + " and " + sdf.format(endDate));
                if (!todaysDate.equals(startDate) && !todaysDate.equals(endDate)) {
                    result = true;

                } else {
                    if (todaysDate.equals(startDate) && !todaysDate.equals(endDate)) {
                        if (current_Time.isAfter(startTime) && startTime.isBefore(LocalTime.parse("23:59:59"))) {
                            result = true;
                        }
                    }
                    if (endTime.isAfter(LocalTime.parse("00:00:00")) && current_Time.isBefore(endTime) && !endDate.equals(startDate)) {
                        result = true;
                    }
                    if (startDate.equals(endDate) && startDate.equals(todaysDate)) {
                        if (current_Time.isAfter(startTime) && startTime.isBefore(LocalTime.parse("23:59:59"))) {
                            if ((endTime.isAfter(LocalTime.parse("00:00:00")) && current_Time.isBefore(endTime))) {
                                result = true;
                            }

                        }
                    }
                }
            }
        } else {
            System.out.println("NO YOUR DATE IS WRONG");
            result = false;
        }
        return result;
    }

    public String getTime(String str) {
        String submitedTime = str.substring(11, str.length());
        return submitedTime;
    }

    public void setSchedule(String startDate, String endDate) {
        if (schema == null) {
            schema = new Schema(startDate, endDate, this);
        } else {
            sendRequest("off");
            schema.setFlag(false);
            //schema.sendRequest("off");
            schema = null;
            schema = new Schema(startDate, endDate, this);
        }
    }

    public boolean getStatus() {
        return lampStatus;
    }

    public void setStatus(boolean status) {
        this.lampStatus = status;
    }

    public void sendRequest(String request) {
        try {
            request = "server" + request;
            Socket arduinoSocket = new Socket(InetAddress.getLocalHost(), 9000);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(arduinoSocket.getOutputStream()));
            bw.write(request);
            bw.newLine();
            bw.flush();
            arduinoSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public void setTemp(String str) {
		this.temp=Double.parseDouble(str);
		
	}
	public double getTemp()
	{
		return temp;
	}
    public void setTempSchedule(TempSchedule tempSchedule1) {
        if (tempSchedule == null) {
        	tempSchedule = tempSchedule1;
        	tempSchedule1.setServer(this);
        	tempSchedule1.startTimer();
        } else {
        	tempSchedule.stopTimer();
        	tempSchedule = tempSchedule1;
        	tempSchedule1.setServer(this);
        	tempSchedule1.startTimer();
        }
    }
    public void setCurtainSchedule(CurtainSchedule curtainSchedule1) {
        if (schema == null) {
        	curtainSchedule = curtainSchedule1;
        	curtainSchedule.setServer(this);
        	curtainSchedule.startTimer();
        } else {
        	curtainSchedule.stopTimer();
        	curtainSchedule = curtainSchedule1;
        	curtainSchedule.setServer(this);
        	curtainSchedule.startTimer();
        }
    }

}

    


