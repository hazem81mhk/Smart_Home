package client.view;

import client.controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainListGUI extends JPanel {
    private Controller controller;
    private JFrame jFrame;
    private JPanel jPanelMain;
    private JButton jButtonLight;
    private JButton jButtonCurtain;
    private JButton jButtonLock;
    private Dimension frameDimention= new Dimension(380, 270);
    private Dimension panelDimention= new Dimension(360, 70);
    private Dimension buttonDimension= new Dimension(340, 60);


    public MainListGUI(Controller controller) {
        this.controller = controller;
        setjFrame();
        lightPanel();
        curtainPanel();
        lockPanel();
        setFrame();
        addListener();
    }

    public void setjFrame(){
        jFrame = new JFrame();
        jPanelMain = new JPanel();
        jFrame.add(jPanelMain);
    }
    public void setFrame(){
        jFrame.setPreferredSize(frameDimention);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocation(430, 150);
        jFrame.setTitle("Main List");
        jFrame.pack();
        jFrame.setVisible(true);
    }

    public void lightPanel() {
        JPanel jPanelLight = new JPanel();
        jPanelLight.setPreferredSize(panelDimention);
        jPanelLight.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));

        jButtonLight = new JButton("Light Room 1");
        jButtonLight.setPreferredSize(buttonDimension);
        jPanelLight.add(jButtonLight);
        jPanelMain.add(jPanelLight, BorderLayout.NORTH);

    }

    public void curtainPanel() {
        JPanel jPanelCurtain = new JPanel();
        jPanelCurtain.setPreferredSize(panelDimention);
        jPanelCurtain.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));

        jButtonCurtain = new JButton("Curtain Room 1");
        jButtonCurtain.setPreferredSize(buttonDimension);
        jPanelCurtain.add(jButtonCurtain);
        jPanelMain.add(jPanelCurtain, BorderLayout.CENTER);
    }


    public void lockPanel() {
        JPanel jPanelLock = new JPanel();
        jPanelLock.setPreferredSize(panelDimention);
        jPanelLock.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));

        jButtonLock = new JButton("Home Lock");
        jButtonLock.setPreferredSize(buttonDimension);
        jPanelLock.add(jButtonLock);
        jPanelMain.add(jPanelLock, BorderLayout.SOUTH);
    }

    public void setFramevisiblity(boolean b) {
        jFrame.setVisible(b);
    }

    public void addListener() {
        ActionListener listener = new ButtonActionListeners();
        jButtonLight.addActionListener(listener);
        jButtonCurtain.addActionListener(listener);
        jButtonLock.addActionListener(listener);
    }

    class ButtonActionListeners implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButtonLight) {
                System.out.println(1);
            } else if (e.getSource() == jButtonCurtain) {
                System.out.println(2);
            } else if (e.getSource() == jButtonLock) {
                System.out.println(3);
            }
        }
    }

    public static void main(String[] args) {
        MainListGUI test = new MainListGUI(null);
    }
}

