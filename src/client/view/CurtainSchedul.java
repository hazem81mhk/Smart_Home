package client.view;

import java.io.Serializable;

public class CurtainSchedul implements Serializable {
    private String startSchedule;
    private String endSchedule;
    private String Direction;

    public CurtainSchedul (String startSchedule,String endSchedule, String Direction){
        this.startSchedule=startSchedule;
        this.endSchedule=endSchedule;
        this.Direction=Direction;
    }

    public String getStartSchedule() {
        return startSchedule;
    }

    public String getEndSchedule() {
        return endSchedule;
    }

    public String getDirection(){return Direction;}

    @Override
    public String toString() {
        return "\nstartSchedule: " + startSchedule +  "\nendSchedule: " + endSchedule + "\nDirection: "+ Direction;
    }
}

