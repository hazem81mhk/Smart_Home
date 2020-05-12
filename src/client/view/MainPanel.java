package client.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.controller.Controller;
import javafx.geometry.HorizontalDirection;
import server.model.MainServer.ArduinoCard;

/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi and Mohammed Amayri
 */

public class MainPanel extends JPanel {
    private Controller controller;
    //private LightPanel lightPanel;
    private GUI gui = new GUI(controller);
    private CurtainGui cgui = new CurtainGui(controller);
    private MainListGUI mainListGUI = new MainListGUI(controller);
    private ArduinoCard arduinoCard = new ArduinoCard(controller);
    private JPanel jp = new JPanel();

    public MainPanel(Controller controller) {
        this.controller = controller;
        setupPanel();
    }

    private void setupPanel() {
       add(jp);
       mainListGUI.setAlignmentX(Component.LEFT_ALIGNMENT);

        //lightPanel = new LightPanel(controller);
        jp.add(mainListGUI);
        jp.add(gui);
        jp.add(cgui);
        //jp.add(arduinoCard);
        gui.setVisible(false);
        cgui.setVisible(true);
        //add(lightPanel, BorderLayout.NORTH);
        //add(curtainPanel, BorderLayout.CENTER);
        //add(lockPanel, BorderLayout.SOUTH);
    }

    private ArduinoCard getArduinoCard() {
        return arduinoCard;
    }

    public GUI getGui(){
        return gui;
    }

    public CurtainGui getCurtainGui(){
        return cgui;
    }
}

