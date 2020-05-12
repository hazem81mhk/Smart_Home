package client.view;
/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi and Mohammed Amayri
 */

import client.controller.Controller;
import javafx.geometry.HorizontalDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    private int width = 750;
    private int height = 700;

    private Controller controller;
    private MainPanel mainPanel = new MainPanel(controller);
    private JFrame jf = new JFrame();
    private GUI gui = new GUI(controller);

    public MainFrame(Controller controller){
        this.controller = controller;
        setupFrame();
    }

    private void setupFrame() {
        jf.setPreferredSize(new Dimension(width, height));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setTitle("SmartHome Application");
        jf.pack();
        jf.setVisible(true);
        jf.getContentPane().setLayout(new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS));
        jf.getContentPane().add(mainPanel);
    }
    class ButtonActionListeners implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //if (mainPanel.){}
        }}


    public MainPanel getMainPanel(){
        return mainPanel;
    }
}