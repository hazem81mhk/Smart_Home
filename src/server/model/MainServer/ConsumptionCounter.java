package server.model.MainServer;


public class ConsumptionCounter {
	int kiloWatsPerHour=0;
	int costOfOneKilo=0;
	Server server;
	int cost=0;
	public ConsumptionCounter(int kiloWatsPerHour,int costOfOneKilo)
	{
		this.costOfOneKilo= costOfOneKilo;
		this.kiloWatsPerHour=kiloWatsPerHour;
		
	}
	public void setCost()
	{
		
		cost= server.countConsumptionCost(kiloWatsPerHour, costOfOneKilo);
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
