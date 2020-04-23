package server.model.MainServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

public class Schema extends Thread {
    private String start;
    private String end;
    private boolean flag = true;
    private Server server;

    public Schema(String start, String end, Server server) {
        this.start = start;
        this.end = end;
        this.server=server;
        start();
    }

    public boolean checkDate(String start, String end) throws ParseException {
        boolean result = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        Date startDate = sdf.parse(start);
        Date endDate = sdf.parse(end);
        Date todaysDate = sdf.parse(currentDate);

        String currentTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        LocalTime current_Time = LocalTime.parse(currentTime);
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
                            if (current_Time.isAfter(endTime)){
                                System.out.println("Time is off");
                                if(server.getStatus())
                                {
                                    sendRequest("off");
                                    setFlag(false);
                                }

                                //this.isInterrupted();
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
        //System.out.println(submitedTime);
        return submitedTime;
    }

    public void run() {
        while (flag) {
            while (flag) {
                try {
                    if (!checkDate(start, end)) break;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("TIME IS PERFECT");
                try {
                    if(!server.getStatus()){
                        sendRequest("on");
                    }
                    sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("Sleep problem");
                }
            }
            System.out.println("SORRY very SORRY");
            try {
                //sendRequest("off");
                sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("SleepProblem");
            }
        }
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

    public void setFlag(boolean bool) {
        this.flag = bool;
    }

    public static void main(String[] args) {
        String sta="2020-04-23 12:27:00";
        String end="2020-04-23 12:28:00";
        //Schema sc=new Schema(sta,end);

    }
}
