package client.controller;

import client.model.Client;
import client.view.ButtonType;
import client.view.ClientLogin;
import client.view.MainFrame;

/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class Controller {
    private MainFrame mainFrame;
    private ClientLogin clientLogin;
    private Client client;

    public Controller() {
        clientLogin = new ClientLogin(this);
    }

    public void startClient() {
        clientLogin.setFramevisiblity(false);
        client = new Client(clientLogin.getIp(), clientLogin.getPort(), clientLogin.getUserName(), this);   //"81.224.148.215"
    }

    public void startClientGui() {
        mainFrame = new MainFrame(this);
    }

    public void buttonPressed(ButtonType button) {
        switch (button) {
            case login:
                startClient();
                break;
            case lamp1_On:
                client.sendRequest("on");
                break;
            case lamp1_off:
                client.sendRequest("off");
                break;
            case start_schedule:
                client.sendRequest("startschedule");
                break;
            case get_consumption:
                client.sendRequest("getcnsumptin");
                break;
        }
    }
}
