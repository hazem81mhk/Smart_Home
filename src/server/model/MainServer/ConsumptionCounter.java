package server.model.MainServer;

import java.io.Serializable;

public class ConsumptionCounter implements Serializable {
    String dateStart;
    String dateEnd;
	int WatsPerHour = 0;
    int costOfOneKilo = 0;
    Server server;
    int cost = 0;
	private int minutes=0;

    public ConsumptionCounter(String dateStart,String dateEnd,int WatsPerHour, int costOfOneKilo) {
        this.costOfOneKilo = costOfOneKilo;
        this.WatsPerHour = WatsPerHour;
        this.dateEnd=dateEnd;
        this.dateStart=dateStart;

    }

    public void setCost() {
        cost = countConsumptionCost(WatsPerHour, costOfOneKilo,minutes);
    }


    public int getCost() {
        return cost;
    }
    public void setConsumedMinuets(int minutes)
    {
    	this.minutes=minutes;
    }
    public String getDateStart() {
		return dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public int countConsumptionCost(int kiloWatsPerHour, int costOfOneKilo, int minutesCons) {
        int hours = minutes / 60;
        int cost = hours * kiloWatsPerHour * costOfOneKilo;
        return cost;
    }
}
