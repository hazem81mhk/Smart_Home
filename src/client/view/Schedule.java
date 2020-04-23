package client.view;

import java.io.Serializable;

public class Schedule implements Serializable {
    private String startSchedule;
    private String endSchedule;

    public Schedule (String startSchedule,String endSchedule){
        this.startSchedule=startSchedule;
        this.endSchedule=endSchedule;
    }

    public String getStartSchedule() {
        return startSchedule;
    }

    public String getEndSchedule() {
        return endSchedule;
    }


    @Override
    public String toString() {
        return "{" + "startSchedule= " + startSchedule +  ", endSchedule= " + endSchedule + "}";
    }
}
