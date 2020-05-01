package client.view;

import java.io.Serializable;

public class TempSchedul implements Serializable {
    private String TempSchedule;

    public TempSchedul (String TempSchedule){
        this.TempSchedule=TempSchedule;
    }

    public String getTempSchedule() {
        return TempSchedule;
    }

    public String toString() {
        return "\ngetTempSchedule:\n" + TempSchedule ;
    }
}
