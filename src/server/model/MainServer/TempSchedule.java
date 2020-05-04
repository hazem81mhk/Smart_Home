package server.model.MainServer;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class TempSchedule extends TimerTask implements Serializable{
	private double rollUpTemp;
	private double rollDownTemp;
	private Server server;
	private Timer timer;
	private double temp=0;
	public TempSchedule(double rollUpTemp,double rollDownTemp)
	{
		this.rollDownTemp=rollDownTemp;
		this.rollUpTemp=rollUpTemp;
		
		
		
	}
	public TempSchedule(double rollUpTemp,double rollDownTemp,Server server)
	{
		this.rollDownTemp=rollDownTemp;
		this.rollUpTemp=rollUpTemp;
		this.server=server;
		
		
		
	}
	public void setServer(Server server)
	{
		this.server=server;
	}
	public TempSchedule()
	{
		
	}
	public void startTimer() {
		 this.timer = new Timer();
		timer.schedule(new TempSchedule(rollUpTemp, rollDownTemp,server), 0, 60000);
		
	}
	@Override
	public void run() {
		if(!server.getCurtainSchState())
		{
			double tempo=checkTemp();
		if(tempo<=rollUpTemp)
		{	if( checkState("up"))
		{	System.out.println("TEMP SCHED: RUN : SENDING UP");
			server.sendRequest("up");
			server.setTempScheduleState(true);
		}
		}
		if(tempo>=rollUpTemp)
		{ if( checkState("down"))
		{	System.out.println("TEMP SCHED: RUN : SENDING DOWN");
			server.sendRequest("down");
			server.setTempScheduleState(true);
		}
		}
		else
		{
			server.setTempScheduleState(false);
		}
		}
		
		
	}
	private double checkTemp() {
		server.sendRequest("temp");
		
		
		while(server.getTemp()==temp)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 temp=server.getTemp();
		System.out.println("TempSche:"+temp);
		return temp;
		
	}
	public void stopTimer()
	{	server.setTempScheduleState(false);
		this.timer.cancel();
	
		System.out.println("TempSched:timer is canceled");
		
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