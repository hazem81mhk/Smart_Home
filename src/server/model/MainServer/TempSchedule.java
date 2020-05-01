package server.model.MainServer;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class TempSchedule extends TimerTask implements Serializable{
	private double rollUpTemp;
	private double rollDownTemp;
	private Server server;
	private Timer timer;
	public TempSchedule(double rollUpTemp,double rollDownTemp)
	{
		this.rollDownTemp=rollDownTemp;
		this.rollUpTemp=rollUpTemp;
		
		
		
	}
	public void setServer(Server server)
	{
		this.server=server;
	}
	public TempSchedule()
	{
		
	}
	public void startTimer() {
		 timer = new Timer();
		timer.schedule(new TempSchedule(rollUpTemp, rollDownTemp), 0, 300000);
		
	}
	@Override
	public void run() {
		
		if(checkTemp()<=rollUpTemp)
		{
			server.sendRequest("up");
			server.setTempScheduleState(true);
		}
		if(checkTemp()>=rollDownTemp)
		{
			server.sendRequest("down");
			server.setTempScheduleState(true);
		}
		else
		{
			server.setTempScheduleState(false);
		}
		
		
	}
	private double checkTemp() {
		server.sendRequest("temp");
		try {
			wait(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double temp=server.getTemp();
		return temp;
	}
	public void stopTimer()
	{
		timer.cancel();
		
	}
	

}
