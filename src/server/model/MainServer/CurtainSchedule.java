package server.model.MainServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
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

public class CurtainSchedule  extends TimerTask implements Serializable{
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
    public CurtainSchedule(String start, String end,String order,Server server) {
        this.start = start;
        this.end = end;
        this.order=order;
        this.server=server;
        
        
    }
   
    public void setServer(Server server)
    {
    	this.server=server;
    	//System.out.println("WHOOOOPI VI HAR EN SERVER!");
    }
    
   
    public void startTimer()
    {
    	 this.timer = new Timer();
 		timer.schedule(this, 0, 60000);
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
        ////System.out.println(localDateTime);

        if (todaysDate.after(startDate) || todaysDate.equals(startDate)) {
            if (todaysDate.before(endDate) || todaysDate.equals(endDate)) {
                ////System.out.println(sdf.format(todaysDate) + " is actually between  " + sdf.format(startDate) + " and " + sdf.format(endDate));
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
                               
                            	server.sendRequest(antiOrder());
                            	this.stopTimer();
                            	
                            }
                        }
                    }
                }
            }
        } else {
            //System.out.println("NO YOUR DATE IS WRONG");
            result = false;
        }
        return result;
    }

    public String getTime(String str) {
        String submitedTime = str.substring(11, str.length());
        ////System.out.println(submitedTime);
        return submitedTime;
    }

    public void run() {
        
            
                
                    try {
						if (checkDate(start, end))
						{//System.out.println("ACTUALLY REMMEBER THIS DATE");
							//server.printForSure();

						{
							server.sendRequest(order);
							server.setScheduleState(true);
						}
							
						}

							

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
              
            }
        
    
    private String antiOrder() {
		String antiOrder;
		if(order.toLowerCase().contains("up"))
		{
			antiOrder="down";
		}
		else
		{
			antiOrder="up";
		}
		return antiOrder;
	}
	public void stopTimer()
	{	server.setScheduleState(false);
	System.out.println(server.getCurtainSchState());
		if(this.timer!=null)
		{
		
			this.timer.cancel();
		}
		else
		{
			System.out.println("TIMER IS NULL");
		}
		
	}
	public boolean checkState(String str)
	{ boolean result=false;
	if(server.getCurtainState().equals(""))
	{
		result=true;
	}
	else
	{
		if(str.equals("up"))
		{
			if(server.getCurtainState().equals("top"))
			{
				result=false;
			}
			else
			{
				result=true;
			}
		}
		if(str.equals("down"))
		{
			if(server.getCurtainState().equals("bottom"))
			{
				result=false;
			}
			else
			{
				result=true;
			}
		}
		
	}
	return result;
	}

}
