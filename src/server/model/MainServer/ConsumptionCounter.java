package server.model.MainServer;

import java.io.Serializable;

public class ConsumptionCounter implements Serializable{
	int WatsPerHour=0;
	int costOfOneKilo=0;
	Server server;
	int cost=0;
	public ConsumptionCounter(int WatsPerHour,int costOfOneKilo)
	{
		this.costOfOneKilo= costOfOneKilo;
		this.WatsPerHour=WatsPerHour;
		
	}
	public void setCost()
	{
		
		cost= server.countConsumptionCost(WatsPerHour, costOfOneKilo);
	}
	public void setServer(Server server)
	{
		this.server=server;
	}
	public int getCost()
	{
		return cost;
	}

}
