package client.controller;

import client.model.Client;
import client.view.*;
import server.model.MainServer.ConsumptionCounter;
import server.model.MainServer.CurtainSchedule;
import server.model.MainServer.TempSchedule;

/**
 * 11/04/2020
 *
 * @Mohammed Amayri and Mohammed Hazem Kudaimi
 */

public class Controller {
    private MainFrame mainFrame;
    private ClientLogin clientLogin;
    private Client client;
    private Schedule schedule;
    private CurtainSchedule Cschedule;
    private TempSchedule Tschedul;

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

    public void getConsumption() {
        mainFrame.getMainPanel().getGui().setConsumptionFor();
        mainFrame.getMainPanel().getGui().setConsumptionTo();
        ConsumptionCounter cons = new ConsumptionCounter((mainFrame.getMainPanel().getGui().getConsfr()),
                (mainFrame.getMainPanel().getGui().getConsto()), Integer.parseInt(mainFrame.getMainPanel().getGui().getKw()),
                Integer.parseInt(mainFrame.getMainPanel().getGui().getKwkr()));
        client.sendCons(cons);
    }

    public void setButtonOff() {
        mainFrame.getMainPanel().getGui().setLampButtonOff();
    }

    public void setButtonOn() {
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
                getConsumption();
                break;

            //AGON
            case curtain_up:
                client.sendRequest("up");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //CButtonup();
                break;
            case curtain_down:
                client.sendRequest("down");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //CButtondown();
                break;
            case curtain_stop:
                client.sendRequest("stop");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //CButtonstop();
                break;
            case curtain_schedule:
                CSchedule();
                break;
            case temp_curtain:
                CTempSchedule();
                break;
        }
    }

    public void CButtonup() {
        mainFrame.getMainPanel().getCurtainGui().setCurtainButtonUP();
    }

    public void CButtondown() {
        mainFrame.getMainPanel().getCurtainGui().setCurtainButtonDOWN();
    }

    public void CButtonstop() {
        mainFrame.getMainPanel().getCurtainGui().setCurtainButtonSTOP();
    }

    public void CSchedule() {
        mainFrame.getMainPanel().getCurtainGui().setCurtainschedulefrom();
        mainFrame.getMainPanel().getCurtainGui().setCurtainscheduleto();
        mainFrame.getMainPanel().getCurtainGui().setCheckbox();
        //System.out.println("From: " + mainFrame.getMainPanel().getCurtainGui().getCurtainschedulefrom() + "\nTo: " +
        //      mainFrame.getMainPanel().getCurtainGui().getCurtainscheduleto() + "\nDirection: " + mainFrame.getMainPanel().getCurtainGui().getCheckbox());
        Cschedule = new CurtainSchedule(mainFrame.getMainPanel().getCurtainGui().getCurtainschedulefrom(),
                mainFrame.getMainPanel().getCurtainGui().getCurtainscheduleto(), mainFrame.getMainPanel().getCurtainGui().getCheckbox());
        client.sendCurtainSchedule(Cschedule);
    }

    public void CTempSchedule() {
        mainFrame.getMainPanel().getCurtainGui().setTempschedule();
        System.out.println(mainFrame.getMainPanel().getCurtainGui().getTempsch());
        Tschedul = new TempSchedule(mainFrame.getMainPanel().getCurtainGui().getTempsch1(), mainFrame.getMainPanel().getCurtainGui().getTempsch2());
        client.sendTempSchedule(Tschedul);
    }

    public void sendUpdate(String str) {
        mainFrame.getMainPanel().getGui().appendLog(str);
    }
}