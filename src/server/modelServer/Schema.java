package server.modelServer;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;

public class Schema extends Thread{
	private String start;
	private String end;
	public Schema(String start,String end) {
		this.start=start;
		this.end=end;
		start();
		
	}
	public boolean checkDate(String start,String end)
	{	boolean result=false;
		int[] todayDateArr;
		int[] dateArrStart;
		int[] dateArrEnd;
		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String currentTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
		todayDateArr=stringToDate(currentDate);
		Date todaysDate= new Date(todayDateArr[0],todayDateArr[1],todayDateArr[2]);
		dateArrStart=stringToDate(start);
		Date startDate=new Date(dateArrStart[0],dateArrStart[1],dateArrStart[2]);
		dateArrEnd=stringToDate(end);
		Date endDate=new Date(dateArrEnd[0],dateArrEnd[1],dateArrEnd[2]);
		LocalTime current_Time = LocalTime.parse( currentTime ) ;
		LocalTime startTime = LocalTime.parse( getTime(start) ) ;
		LocalTime endTime = LocalTime.parse( getTime(end) ) ;
		
		
		System.out.println(currentDate+"our date is:"+startDate+"End date:"+endDate);
	
		if( todaysDate.after(startDate) && todaysDate.before(endDate))
		{
			System.out.println(todaysDate.toString()+ " is actually between "+startDate.toString()+" And "+ endDate.toString());
			
			if(current_Time.isAfter( startTime ) && current_Time.isBefore(LocalTime.parse("23:59:59"))||
					current_Time.isAfter( LocalTime.parse( "00:00:00" ) ) && current_Time.isBefore(endTime)) 
					{
						System.out.println("The time is right");
						result= true;
					}
		}
		else
		{
			System.out.println("NO YOUR DATE IS WRONG");
			result=false;
		}
		return result;

	}
	
	
	
	public int[] stringToDate(String sub) {
		
		String year=sub.substring(0,4);
		int yearInt=Integer.parseInt(year);
		String month=sub.substring(5,7);
		int monthInt=Integer.parseInt(month);
		String day=sub.substring(8,10);
		int dayInt=Integer.parseInt(day);
		int[] retInt= {yearInt,monthInt,dayInt};
		return retInt;
		
	}
	public String getTime(String str)
	{
		String submitedTime=str.substring(11,str.length());
		return submitedTime;
	}
	public void run()
	{
		while(true)
		{
			while(checkDate(start,end))
			{
				System.out.println("TIME IS PERFECT");
				try {
					sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("Sleep problem");
				}	
			}
			System.out.println("SORRY very SORRY");
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("SleepProblem");
			}
		}
	}
	
	public static void main (String[]args)
	{
		String date1="2020-04-16 17:34:00";
		String date2="2020-04-18 17:40:00";
		Schema t=new Schema(date1,date2);
		

}
}
