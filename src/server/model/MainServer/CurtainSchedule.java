package server.model.MainServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

/**
 * 10/04/2020
 *
 * @Mohammed Amayri and Moahmmed Hazem Kudaimi
 */

public class CurtainSchedule  extends TimerTask {
    private String start;
    private String end;
    private boolean flag = true;
    private Server server;
    private String order;
    private Timer timer;

    public CurtainSchedule(String start, String end,String order) {
        this.start = start;
        this.end = end;
        
        this.order=order;
        
    }
    public void setServer(Server server)
    {
    	this.server=server;
    }
   
    public void startTimer()
    {
    	 timer = new Timer();
 		timer.schedule(new CurtainSchedule(start,end,order), 0, 300000);
    }

    public boolean checkDate(String start, String end) throws ParseException {
        boolean result = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(Calendar.getInstance().getTime());//readLine();
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
        
            while (true) {
                
                    try {
						if (checkDate(start, end))
						{
							server.sendRequest(order);
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
              
            }
        }
    
    public void stopTimer()
	{
		timer.cancel();
		
	}

}
