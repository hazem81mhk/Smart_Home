package client.controller;

import client.model.Client;
import client.view.ButtonType;
import server.model.ClientHandler;
import client.view.MainFrame;

import java.io.IOException;

/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class Controller {
    private MainFrame gui;
    private Client client;

    public Controller() throws IOException {
        gui =new MainFrame();
        client=new Client();
    }

    public void buttonPressed(ButtonType button) {
        switch (button){
            case lamp1_On:

                break;
            case lamp1_off:

                break;
        }
    }
}
