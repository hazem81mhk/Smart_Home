package server.model.MainServer;

import java.io.Serializable;

/**
 * 10/04/2020
 *
 * @Mohammed Amayri
 */

public class User implements Serializable {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
