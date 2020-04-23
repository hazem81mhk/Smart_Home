package client.controller;

import client.model.Client;
import client.view.ButtonType;
import client.view.ClientLogin;
import client.view.MainFrame;
import client.view.Schedule;

/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class Controller {
    private MainFrame mainFrame;
    private ClientLogin clientLogin;
    private Client client;
    private Schedule schedule;

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

    public void startSchedule() {
        mainFrame.getMainPanel().getGui().setSelectedStarttime();
        mainFrame.getMainPanel().getGui().setSelectedTotime();
        schedule = new Schedule(mainFrame.getMainPanel().getGui().getStarttime(),
                mainFrame.getMainPanel().getGui().getTotime());
        client.sendSchedule(schedule);
    }
    public void setButtonOff(){
        mainFrame.getMainPanel().getGui().setLampButtonOff();
    }

    public void setButtonOn(){
        mainFrame.getMainPanel().getGui().setLampButtonOn();
    }

    public void buttonPressed(ButtonType button) {
        switch (button) {
            case login:
                startClient();
                break;
            case lamp1_On:
                client.sendRequest("on");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //mainFrame.getMainPanel().getGui().setLampButtonOff();
                break;
            case lamp1_off:
                client.sendRequest("off");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //mainFrame.getMainPanel().getGui().setLampButtonOn();
                break;
            case start_schedule:
                startSchedule();
                break;
            case get_consumption:
                client.sendRequest("getcnsumptin");
                break;
        }
    }
}
