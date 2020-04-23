package server.model.MainServer;

import java.io.Serializable;

/**
 * 10/04/2020
 *
 * @Mohammed Amayri
 */

public class CommandHandler implements Serializable {
    User user;
    String command;

    public CommandHandler(String command, User user) {
        this.user = user;
        this.command = command;
    }

    public User getUser() {
        return user;
    }

    public String getCommand() {
        return command;
    }
}
