package client.view;

import client.controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 21/05/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class MainMenuPanel extends JPanel {
    private Controller controller;
    private JPanel jPanelMain = new JPanel();
    private JButton jButtonLight;
    private JButton jButtonCurtain;
    private JButton jButtonOpenLock;
    private JButton jButtonCloseLock;

    private JPanel jptextArra = new JPanel();
    private JTextArea textArea;

    private Dimension panelDimention = new Dimension(350, 70);
    private Dimension buttonDimension = new Dimension(340, 60);

    public MainMenuPanel(Controller controller) {
        this.controller = controller;
        jPanelMain.setLayout(new BoxLayout(jPanelMain, BoxLayout.Y_AXIS));
        add(jPanelMain);
        lightPanel();
        curtainPanel();
        lockPanel();
        textArea();
        setjButtonCloseLock();
        addListener();
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
        jPanelMain.setVisible(true);
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
        Dimension newbuttonDimension = new Dimension(165, 60);
        jButtonOpenLock = new JButton("Open The Home Lock");
        jButtonCloseLock = new JButton("Close The Home Lock");
        jButtonOpenLock.setPreferredSize(newbuttonDimension);
        jButtonCloseLock.setPreferredSize(newbuttonDimension);
        jPanelLock.add(jButtonOpenLock);
        jPanelLock.add(jButtonCloseLock);
        jPanelMain.add(jPanelLock, BorderLayout.SOUTH);
    }

    public void textArea() {
        jptextArra.setPreferredSize(new Dimension(350, 330));
        jptextArra.setBorder(BorderFactory.createTitledBorder(null, "Update:",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                new Font("Arial", Font.PLAIN, 15), Color.BLACK));
        textArea = new JTextArea(18, 32);
        textArea.setEditable(false);
        jPanelMain.add(jptextArra);
        //JScrollPane Scroll = new JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane Scroll = new JScrollPane(textArea);
        Scroll.setHorizontalScrollBar(null);
        jptextArra.add(Scroll);
    }

    public void appendLog(String str) {
        textArea.append(str + "\n");
    }

    public void setjButtonLight() {
        jButtonLight.setEnabled(false);
        jButtonCurtain.setEnabled(true);
    }

    public void setjButtonCurtain() {
        jButtonLight.setEnabled(true);
        jButtonCurtain.setEnabled(false);
    }

    public void setjButtonOpenLock() {
        jButtonOpenLock.setEnabled(false);
        jButtonCloseLock.setEnabled(true);
    }

    public void setjButtonCloseLock() {
        jButtonOpenLock.setEnabled(true);
        jButtonCloseLock.setEnabled(false);
    }

    public void addListener() {
        ActionListener listener = new ButtonActionListeners();
        jButtonLight.addActionListener(listener);
        jButtonCurtain.addActionListener(listener);
        jButtonOpenLock.addActionListener(listener);
        jButtonCloseLock.addActionListener(listener);
    }

    class ButtonActionListeners implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jButtonLight) {
                controller.buttonPressed(ButtonType.lightPanel);
            } else if (e.getSource() == jButtonCurtain) {
                controller.buttonPressed(ButtonType.curtainPanel);
            } else if (e.getSource() == jButtonOpenLock) {
                controller.buttonPressed(ButtonType.openDoor);
            } else if (e.getSource() == jButtonCloseLock) {
                controller.buttonPressed(ButtonType.closeDoor);
            }
        }
    }
}

