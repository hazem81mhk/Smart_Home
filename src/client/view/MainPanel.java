package client.view;

import javax.swing.*;

import client.controller.Controller;

/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi and Mohammed Amayri
 */

public class MainPanel extends JPanel {
    private Controller controller;
    private MainMenuPanel mainMenuPanel;
    private LightPanel lightPanel;
    private CurtainPanel curtainPanel;

    private JPanel jp = new JPanel();

    public MainPanel(Controller controller) {
        this.controller = controller;
        setupPanel();
    }

    private void setupPanel() {
        add(jp);
        mainMenuPanel = new MainMenuPanel(controller);
        lightPanel = new LightPanel(controller);
        curtainPanel = new CurtainPanel(controller);

        jp.add(mainMenuPanel);
        jp.add(lightPanel);
        jp.add(curtainPanel);
        setLightPanel();
    }

    public void setLightPanel() {
        this.lightPanel.setVisible(true);
        this.curtainPanel.setVisible(false);
    }

    public void setCurtainPanel() {
        this.lightPanel.setVisible(false);
        this.curtainPanel.setVisible(true);
    }

    public LightPanel getLightPanel() {
        return lightPanel;
    }

    public CurtainPanel getCurtainGui() {
        return curtainPanel;
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }
}

