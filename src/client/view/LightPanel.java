package client.view;

import client.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 011/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class LightPanel extends JPanel {
    private Controller controller;
    private JButton lamp1_On,lamp1_off;

    public LightPanel (){
        //this.controller=controller;
        Font font = new Font("Ink Free", Font.BOLD, 15);
        GridLayout layout = new GridLayout(1, 0, 90, 10);
        JPanel pnlButtons = new JPanel(layout);
        pnlButtons.setBorder(BorderFactory.createTitledBorder(""));
        Dimension dim = new Dimension(90, 20);
        lamp1_On = new JButton("ON");
        lamp1_On.setPreferredSize(dim);
        lamp1_On.setFont(font);
        lamp1_off = new JButton("OFF");
        lamp1_off.setPreferredSize(dim);
        lamp1_off.setFont(font);

        pnlButtons.add(lamp1_On);
        pnlButtons.add(lamp1_off);
        lamp1_On.setEnabled(true);
        lamp1_off.setEnabled(false);
        add(pnlButtons);
        addListeners();
    }

    private void addListeners() {
        ActionListener listener = new ButtonActionListeners();
        lamp1_On.addActionListener(listener);
        lamp1_off.addActionListener(listener);
    }

    class ButtonActionListeners implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == lamp1_On) {
                //controller.buttonPressed(ButtonType.lamp1_On);
                lamp1_On.setEnabled(false);
                lamp1_off.setEnabled(true);
            }
            else if (e.getSource() == lamp1_off) {
                //controller.buttonPressed(ButtonType.lamp1_off);
                lamp1_On.setEnabled(true);
                lamp1_off.setEnabled(false);
            }
        }
    }
}
