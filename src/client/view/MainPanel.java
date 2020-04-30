package client.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

import client.controller.Controller;

/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi and Mohammed Amayri
 */

public class MainPanel extends JPanel {
    private Controller controller;
    //private LightPanel lightPanel;
    private GUI gui;
    private CurtainGui cgui;
    private BorderLayout layout;

    public MainPanel(Controller controller) {
        this.controller = controller;
        setupPanel();
    }

    private void setupPanel() {
        layout = new BorderLayout();
        setLayout(layout);
        Border border = this.getBorder();
        Border margin = BorderFactory.createEmptyBorder(12, 12, 12, 12);
        setBorder(new CompoundBorder(border, margin));
        //lightPanel = new LightPanel(controller);
        gui=new GUI(controller);
        cgui = new CurtainGui(controller);
        //add(lightPanel, BorderLayout.NORTH);
        add(gui);
        add(cgui);
        //add(curtainPanel, BorderLayout.CENTER);
        //add(lockPanel, BorderLayout.SOUTH);
    }

    public GUI getGui(){
        return gui;
    }

    public CurtainGui getCurtainGui(){
        return cgui;
    }
}
