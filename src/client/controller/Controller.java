package client.controller;

import client.model.Client;
import client.view.ButtonType;
import client.view.MainFrame;
import server.model.MainServer.User;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class Controller {
    private MainFrame gui;
    private Client client;

    public Controller() throws UnknownHostException {
        client = new Client("81.224.148.215", 8000);
        String userName = JOptionPane.showInputDialog("Please Enter Your User Name", null);
        User user = new User(userName);
        client.sendUser(user);
        gui = new MainFrame(this);

    }

    public void buttonPressed(ButtonType button) {
        switch (button) {
            case lamp1_On:
                client.sendRequest("on");
                break;
            case lamp1_off:
                client.sendRequest("off");
                break;
            case statistic:
                client.sendRequest("Total");
                System.out.println("Total");
                break;
        }
    }
}
