package server.controller;

import server.modelArduinoServer.ArduinoServer;
import server.modelServer.Server;
import server.view.ServerGUI;
import server.modelServer.ButtonType;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {
    private Server server;
    private ServerGUI serverGUI;
    private PrintWriter out;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public Controller() throws FileNotFoundException {
        serverGUI = new ServerGUI(this);
        out = new PrintWriter("files/allTraffic.txt");
    }

    public void buttonPressed(ButtonType buttonType) throws IOException, ParseException {
        switch (buttonType) {
            case startStop:
                if (serverGUI.getPort().equals("") || serverGUI.getPort().isEmpty()) {
                    errMessage("Please enter server port number");
                    serverGUI.setPort("");
                } else {
                    if (server != null) {
                        stopServer();
                    } else {
                        try {
                            startServer();
                        } catch (NumberFormatException e) {
                            errMessage("Pleas enter just number");
                            serverGUI.setPort("");
                        }
                    }
                }
                break;
            case searchLog:
                serverGUI.setSearchFrameVisibility();
                break;
            case searchGO:
                getLog();
                serverGUI.appendLog("-----------------------------------------------------------------------------" +
                        "----------------------------------------------------------------------------------------------\n");
                break;
        }
    }

    public void startServer() throws IOException {
        String time = sdf.format(new Date());
        server = new Server(Integer.parseInt(serverGUI.getPort()), this);
        server.start();
        String logStr = time + "   Server Connected, Port: " + serverGUI.getPort();
        serverGUI.appendEvent(logStr);
        System.out.println(logStr);
        out.println(logStr);
        out.flush();
        serverGUI.setConDis(true);//True if server is running
        new ArduinoServer(9000);
    }

    //Stop server if server is running when you close serverGUI
    public void stopServer() {
        String time = sdf.format(new Date());
        if (server != null) {
            try {
                server.closeSocket();
                serverGUI.setConDis(false);
                String logStr = time + "   Server Disconnected";
                serverGUI.appendEvent(logStr);
                System.out.println(logStr);
                out.println(logStr);
                out.flush();
                serverGUI.setPort("");
            } catch (Exception eClose) {
                server = null;
            }
        }
    }

    public void disPlayChat(String logStr) {
        serverGUI.appendChat(logStr);
        out.println(logStr);
        out.flush();
        System.out.println(logStr);
    }

    public void disPlayEvent(String logStr) {
        serverGUI.appendEvent(logStr);
        out.println(logStr);
        out.flush();
        System.out.println(logStr);
    }

    public void errMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public void getLog() throws ParseException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
        String datestr1 = serverGUI.getdateField1();
        Date date1 = sdfDate.parse(datestr1);
        String datestr2 = serverGUI.getdateField2();
        Date date2 = sdfDate.parse(datestr2);

        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        String timeStr1 = serverGUI.gettime1Field();
        Date time1 = sdfTime.parse(timeStr1);
        String timeStr2 = serverGUI.gettime2Field();
        Date time2 = sdfTime.parse(timeStr2);

        String str;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("files/allTraffic.txt"), "UTF-8"))) {
            str = br.readLine();
            while (str != null) {
                str = br.readLine();
                String subDate = str.substring(0, 10);
                Date messageDate = sdfDate.parse(subDate);
                String subTime = str.substring(11, 16);
                Date messageTime = sdfTime.parse(subTime);
                try {
                    if ((date1.compareTo(messageDate) <= 0) && (date2.compareTo(messageDate) >= 0)) {  //tru=0
                        if ((time1.compareTo(messageTime) <= 0) && (time2.compareTo(messageTime) >= 0)) {
                            serverGUI.appendLog(str);
                        }
                    }
                } catch (NullPointerException e) {
                    //  e.printStackTrace();
                }
            }
        } catch (IOException e) {
            //  System.err.println(e);
        } catch (NullPointerException e) {
            //  e.printStackTrace();
        }
    }
}

