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
    private LoginPanel loginPanel;
    private Client client;
    private Schedule schedule;
    private CurtainSchedule Cschedule;
    private TempSchedule Tschedul;

    public Controller() {
        loginPanel = new LoginPanel(this);
        mainFrame = new MainFrame(this);
    }

    public void startClient() {
        loginPanel.setFramevisiblity(false);
        client = new Client(loginPanel.getIp(), loginPanel.getPort(), loginPanel.getUserName(), this);
    }

    public void startClientGui() {
        mainFrame.setFramevisiblity(true);
        mainFrame.getMainPanel().getMainMenuPanel().setjButtonLight();
    }

    public void startSchedule() {
        mainFrame.getMainPanel().getLightPanel().setSelectedStarttime();
        mainFrame.getMainPanel().getLightPanel().setSelectedTotime();
        schedule = new Schedule(mainFrame.getMainPanel().getLightPanel().getStarttime(),
                mainFrame.getMainPanel().getLightPanel().getTotime());
        client.sendSchedule(schedule);

    }

    public void getConsumption() {
        mainFrame.getMainPanel().getLightPanel().setConsumptionFor();
        mainFrame.getMainPanel().getLightPanel().setConsumptionTo();
        ConsumptionCounter cons = new ConsumptionCounter((mainFrame.getMainPanel().getLightPanel().getConsfr()),
                (mainFrame.getMainPanel().getLightPanel().getConsto()), Integer.parseInt(mainFrame.getMainPanel().getLightPanel().getKw()),
                Integer.parseInt(mainFrame.getMainPanel().getLightPanel().getKwkr()));
        client.sendCons(cons);
    }

    public void setButtonOff() {
        mainFrame.getMainPanel().getLightPanel().setLampButtonOff();
    }

    public void setButtonOn() {
        mainFrame.getMainPanel().getLightPanel().setLampButtonOn();
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
                //mainFrame.getMainPanel().getLightPanel().setLampButtonOff();
                break;
            case lamp1_off:
                client.sendRequest("off");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //mainFrame.getMainPanel().getLightPanel().setLampButtonOn();
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

                break;
            case curtain_down:
                client.sendRequest("down");

                break;
            case curtain_stop:
                client.sendRequest("stop");
                break;
            case disable:
                client.sendRequest("cancel_Schedule");
                break;
            case curtain_schedule:
                CSchedule();
                break;
            case temp_curtain:
                CTempSchedule();
                break;
            case lightPanel:
                mainFrame.getMainPanel().setLightPanel();
                mainFrame.getMainPanel().getMainMenuPanel().setjButtonLight();
                break;
            case curtainPanel:
                mainFrame.getMainPanel().setCurtainPanel();
                mainFrame.getMainPanel().getMainMenuPanel().setjButtonCurtain();
                break;
            case openDoor:
                client.sendRequest("open");
                break;
            case closeDoor:
                client.sendRequest("close");
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

    public void CButtonTop() {
        mainFrame.getMainPanel().getCurtainGui().curtainOnTop();
    }

    public void CButtonBotoom() {
        mainFrame.getMainPanel().getCurtainGui().curtainBottom();
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
        System.out.println("LOOK WHAT WE GOT!" + mainFrame.getMainPanel().getCurtainGui().getTempsch1() + "" + mainFrame.getMainPanel().getCurtainGui().getTempsch2());
    }

    public void sendUpdate(String str) {
        mainFrame.getMainPanel().getMainMenuPanel().appendLog(str);
    }

    public void setOpenButtonOff() {
        mainFrame.getMainPanel().getMainMenuPanel().setjButtonOpenLock();
    }

    public void setCloseButtonOff() {
        mainFrame.getMainPanel().getMainMenuPanel().setjButtonCloseLock();
    }
}