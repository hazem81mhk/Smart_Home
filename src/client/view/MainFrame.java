package client.view;
/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

import client.controller.Controller;

import javax.swing.*;

public class MainFrame extends JFrame {
    private int width=300;
    private int height=700;

    private Controller controller;
    private MainPanel mainPanel;

    public MainFrame(){
        this.controller=controller;
        setupFrame();
    }

    private void setupFrame() {
        final int offsetX = width / 5;
        final int offsetY = height / 5;
        setSize(width, height);
        setTitle("                  <<<< Smart Home >>>>");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(offsetX, offsetY);
        mainPanel = new MainPanel();
        setContentPane(mainPanel);
        pack();
        setVisible(true);
    }
}
