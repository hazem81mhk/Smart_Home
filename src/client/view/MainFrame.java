package client.view;
/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi and Mohammed Amayri
 */

import client.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private int width = 750;
    private int height = 620;

    private Controller controller;
    private MainPanel mainPanel;
    private JFrame jf = new JFrame();

    public MainFrame(Controller controller) {
        this.controller = controller;
        setupFrame();
    }

    private void setupFrame() {
        jf.setPreferredSize(new Dimension(width, height));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setTitle("SmartHome Application");
        jf.pack();
        mainPanel = new MainPanel(controller);
        jf.setLocation(230, 40);
        jf.getContentPane().setLayout(new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS));
        jf.getContentPane().add(mainPanel);
        jf.setVisible(false);
    }

    public void setFramevisiblity(boolean b) {
        jf.setVisible(b);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame(null);
    }
}