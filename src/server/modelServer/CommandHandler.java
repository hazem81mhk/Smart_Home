package server.modelServer;

import java.io.Serializable;

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
