package client.view;
/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

import client.controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame {
    private int width=400;
    private int height=0;

    private Controller controller;
    private MainPanel mainPanel;

    public MainFrame(Controller controller){
        this.controller= controller;
        setupFrame();
    }

    private void setupFrame() {
        final int offsetX = width;
        final int offsetY = height;
        setSize(width, height);
        setTitle("                  <<<< Smart Home >>>>");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(offsetX, offsetY);
        mainPanel = new MainPanel(controller);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }

    public MainPanel getMainPanel(){
        return mainPanel;
    }
}