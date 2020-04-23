package server.model.ArduinoServer;

import java.io.Serializable;

/**
 * 10/04/2020
 *
 * @Mohammed Amayri
 */

public class Statee implements Serializable {
    private String state;

    public Statee(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
