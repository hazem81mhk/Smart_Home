package server.model.MainServer;

import java.io.Serializable;

public class ConsumptionCounter implements Serializable {
    String dateStart;
    String dateEnd;
    int WatsPerHour = 0;
    int costOfOneKilo = 0;
    Server server;
    double cost = 0;
    private int minutes = 0;

    public ConsumptionCounter(String dateStart, String dateEnd, int WatsPerHour, int costOfOneKilo) {
        this.costOfOneKilo = costOfOneKilo;
        this.WatsPerHour = WatsPerHour;
        this.dateEnd = dateEnd;
        this.dateStart = dateStart;

    }

    public void setCost() {

        this.cost = countConsumptionCost(WatsPerHour, costOfOneKilo, minutes);
    }


    public double getCost() {
        return cost;
    }

    public void setConsumedMinuets(int minutes) {
        this.minutes = minutes;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public double countConsumptionCost(int kiloWatsPerHour, int costOfOneKilo, int minutesCons) {
        double hours = (double) minutes / 60;
        double cost = hours * kiloWatsPerHour * costOfOneKilo;
        return cost;
    }

    public ConsumptionCounter getConObject() {
        return this;
    }
}
