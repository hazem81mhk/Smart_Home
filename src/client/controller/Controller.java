package client.controller;

import client.model.Client;
import client.view.ButtonType;
import client.view.MainFrame;

/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class Controller {
    private MainFrame gui;
    private Client client;

    public Controller() {
        gui =new MainFrame(this);
        client=new Client();
    }

    public void buttonPressed(ButtonType button) {
        switch (button){
            case lamp1_On:
                System.out.println("Lamp1: ON");
                break;
            case lamp1_off:
                System.out.println("Lamp1: OFF");
                break;
            case stratTimer:
                System.out.println("Start the lamp");
                break;
        }
    }
}
