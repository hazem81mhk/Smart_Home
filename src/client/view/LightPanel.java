/*
package client.view;

import client.controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

*/
/**
 * 011/04/2020
 *
 * @Mohammed Hazem Kudaimi
 *//*


public class LightPanel extends JPanel {
    private Controller controller;
    private int width = 400;
    private int height = 250;
    private JPanel timePanel1;
    private JPanel timePanel2;
    private JButton lamp1_On,lamp1_off, startTimer, stopTimer;
    private JList list;
    private DefaultListModel listModel;
    private JList list1;
    private DefaultListModel listModel1;

    public LightPanel (Controller controller){
        this.controller=controller;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));
        lampPanel();
        timetable();
        addListeners();
    }

    public void lampPanel(){
        Font font = new Font("Ink Free", Font.BOLD, 20);
        GridLayout layout = new GridLayout(1, 0, 20, 20);
        JPanel pnlButtons = new JPanel(layout);
        //pnlButtons.setBackground(Color.LIGHT_GRAY);
        pnlButtons.setBorder(BorderFactory.createTitledBorder(null,"Lamp 1:",
                TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.CENTER,
                new Font("Ink free",Font.BOLD,20),Color.BLACK));
        Dimension dim = new Dimension(150, 35);
        pnlButtons.setPreferredSize(new Dimension(width, height/3));
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

        add(pnlButtons, BorderLayout.NORTH);
    }

    public void timetable(){
        GridLayout layout = new GridLayout(2, 1, 5, 5);
        JPanel pnlMain = new JPanel(layout);
        //pnlMain.setBackground(Color.LIGHT_GRAY);
        pnlMain.setBorder(BorderFactory.createTitledBorder(null,"Lamp time table",
                TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.CENTER,
                new Font("Ink free",Font.BOLD,20),Color.BLACK));
        Border border = this.getBorder();
        Border margin = BorderFactory.createEmptyBorder(0, 0, 6, 0);
        setBorder(new CompoundBorder(border, margin));
        //pnlMain.setPreferredSize(new Dimension(width, height));

        Dimension dim = new Dimension(50, 35);
        JLabel timeFrom=new JLabel("  From:");
        timeFrom.setPreferredSize(dim);
        JLabel timeTo =new JLabel("  To:");
        timeTo.setPreferredSize(dim);

        startTimer =new JButton("Turn ON");
        startTimer.setPreferredSize(new Dimension(125,35));
        stopTimer =new JButton("Turn OFF");
        stopTimer.setPreferredSize(new Dimension(125,35));

        timePanel1=new JPanel();
        timePanel2=new JPanel();

        JPanel north=new JPanel();
        north.add(timeFrom);
        north.add(timePanel1);
        north.add(timeTo);
        north.add(timePanel2);

        JPanel south=new JPanel();
        JPanel southleft=new JPanel();
        JPanel southcent=new JPanel();
        JPanel southrigh=new JPanel(new GridLayout(3,2));

        southleft.setBorder(BorderFactory.createTitledBorder(null,"From: ",
                TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.CENTER,
                new Font("Ink free",Font.BOLD,20),Color.BLACK));
        pnlMain.add(north,BorderLayout.NORTH);
        pnlMain.add(south,BorderLayout.SOUTH);

        add(pnlMain, BorderLayout.SOUTH);

        south.add(southleft, BorderLayout.WEST);
        south.add(southcent,BorderLayout.CENTER);
        south.add(southrigh,BorderLayout.EAST);
        listModel = new DefaultListModel();
        listModel.addElement("00:00");
        listModel.addElement("00:15");
        listModel.addElement("00:30");
        listModel.addElement("00:45");
        listModel.addElement("01:00");
        listModel.addElement("01:15");
        listModel.addElement("01:30");
        listModel.addElement("01:45");
        listModel.addElement("02:00");
        listModel.addElement("02:15");
        listModel.addElement("02:30");
        listModel.addElement("02:45");
        listModel.addElement("03:00");
        listModel.addElement("03:15");
        listModel.addElement("03:30");
        listModel.addElement("03:45");
        listModel.addElement("04:00");
        listModel.addElement("04:15");
        listModel.addElement("04:30");
        listModel.addElement("04:45");
        listModel.addElement("05:00");
        listModel.addElement("05:15");
        listModel.addElement("05:30");
        listModel.addElement("05:45");
        listModel.addElement("06:00");
        listModel.addElement("06:15");
        listModel.addElement("06:30");
        listModel.addElement("06:45");
        listModel.addElement("07:00");
        listModel.addElement("07:15");
        listModel.addElement("07:30");
        listModel.addElement("07:45");
        listModel.addElement("08:00");
        listModel.addElement("08:15");
        listModel.addElement("08:30");
        listModel.addElement("08:45");
        listModel.addElement("09:00");
        listModel.addElement("09:15");
        listModel.addElement("09:30");
        listModel.addElement("09:45");
        listModel.addElement("10:00");
        listModel.addElement("10:15");
        listModel.addElement("10:30");
        listModel.addElement("10:45");
        listModel.addElement("11:00");
        listModel.addElement("11:15");
        listModel.addElement("11:30");
        listModel.addElement("11:45");
        listModel.addElement("12:00");
        listModel.addElement("12:15");
        listModel.addElement("12:30");
        listModel.addElement("12:45");
        listModel.addElement("13:00");
        listModel.addElement("13:15");
        listModel.addElement("13:30");
        listModel.addElement("13:45");
        listModel.addElement("14:00");
        listModel.addElement("14:15");
        listModel.addElement("14:30");
        listModel.addElement("14:45");
        listModel.addElement("15:00");
        listModel.addElement("15:15");
        listModel.addElement("15:30");
        listModel.addElement("15:45");
        listModel.addElement("16:00");
        listModel.addElement("16:15");
        listModel.addElement("16:30");
        listModel.addElement("16:45");
        listModel.addElement("17:00");
        listModel.addElement("17:15");
        listModel.addElement("17:30");
        listModel.addElement("17:45");
        listModel.addElement("18:00");
        listModel.addElement("18:15");
        listModel.addElement("18:30");
        listModel.addElement("18:45");
        listModel.addElement("19:00");
        listModel.addElement("19:15");
        listModel.addElement("19:30");
        listModel.addElement("19:45");
        listModel.addElement("20:00");
        listModel.addElement("20:15");
        listModel.addElement("20:30");
        listModel.addElement("20:45");
        listModel.addElement("21:00");
        listModel.addElement("21:15");
        listModel.addElement("21:30");
        listModel.addElement("21:45");
        listModel.addElement("22:00");
        listModel.addElement("22:15");
        listModel.addElement("22:30");
        listModel.addElement("22:45");
        listModel.addElement("23:00");
        listModel.addElement("23:15");
        listModel.addElement("23:30");
        listModel.addElement("23:45");

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        //Create a panel that uses BoxLayout.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));

        southleft.add(listScrollPane, BorderLayout.SOUTH);
        southleft.add(buttonPane, BorderLayout.SOUTH);

        southcent.setBorder(BorderFactory.createTitledBorder(null,"Untill: ",
                TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.CENTER,
                new Font("Ink free",Font.BOLD,20),Color.BLACK));
        listModel1 = new DefaultListModel();
        listModel1.addElement("00:00");
        listModel1.addElement("00:15");
        listModel1.addElement("00:30");
        listModel1.addElement("00:45");
        listModel1.addElement("01:00");
        listModel1.addElement("01:15");
        listModel1.addElement("01:30");
        listModel1.addElement("01:45");
        listModel1.addElement("02:00");
        listModel1.addElement("02:15");
        listModel1.addElement("02:30");
        listModel1.addElement("02:45");
        listModel1.addElement("03:00");
        listModel1.addElement("03:15");
        listModel1.addElement("03:30");
        listModel1.addElement("03:45");
        listModel1.addElement("04:00");
        listModel1.addElement("04:15");
        listModel1.addElement("04:30");
        listModel1.addElement("04:45");
        listModel1.addElement("05:00");
        listModel1.addElement("05:15");
        listModel1.addElement("05:30");
        listModel1.addElement("05:45");
        listModel1.addElement("06:00");
        listModel1.addElement("06:15");
        listModel1.addElement("06:30");
        listModel1.addElement("06:45");
        listModel1.addElement("07:00");
        listModel1.addElement("07:15");
        listModel1.addElement("07:30");
        listModel1.addElement("07:45");
        listModel1.addElement("08:00");
        listModel1.addElement("08:15");
        listModel1.addElement("08:30");
        listModel1.addElement("08:45");
        listModel1.addElement("09:00");
        listModel1.addElement("09:15");
        listModel1.addElement("09:30");
        listModel1.addElement("09:45");
        listModel1.addElement("10:00");
        listModel1.addElement("10:15");
        listModel1.addElement("10:30");
        listModel1.addElement("10:45");
        listModel1.addElement("11:00");
        listModel1.addElement("11:15");
        listModel1.addElement("11:30");
        listModel1.addElement("11:45");
        listModel1.addElement("12:00");
        listModel1.addElement("12:15");
        listModel1.addElement("12:30");
        listModel1.addElement("12:45");
        listModel1.addElement("13:00");
        listModel1.addElement("13:15");
        listModel1.addElement("13:30");
        listModel1.addElement("13:45");
        listModel1.addElement("14:00");
        listModel1.addElement("14:15");
        listModel1.addElement("14:30");
        listModel1.addElement("14:45");
        listModel1.addElement("15:00");
        listModel1.addElement("15:15");
        listModel1.addElement("15:30");
        listModel1.addElement("15:45");
        listModel1.addElement("16:00");
        listModel1.addElement("16:15");
        listModel1.addElement("16:30");
        listModel1.addElement("16:45");
        listModel1.addElement("17:00");
        listModel1.addElement("17:15");
        listModel1.addElement("17:30");
        listModel1.addElement("17:45");
        listModel1.addElement("18:00");
        listModel1.addElement("18:15");
        listModel1.addElement("18:30");
        listModel1.addElement("18:45");
        listModel1.addElement("19:00");
        listModel1.addElement("19:15");
        listModel1.addElement("19:30");
        listModel1.addElement("19:45");
        listModel1.addElement("20:00");
        listModel1.addElement("20:15");
        listModel1.addElement("20:30");
        listModel1.addElement("20:45");
        listModel1.addElement("21:00");
        listModel1.addElement("21:15");
        listModel1.addElement("21:30");
        listModel1.addElement("21:45");
        listModel1.addElement("22:00");
        listModel1.addElement("22:15");
        listModel1.addElement("22:30");
        listModel1.addElement("22:45");
        listModel1.addElement("23:00");
        listModel1.addElement("23:15");
        listModel1.addElement("23:30");
        listModel1.addElement("23:45");

        //Create the list and put it in a scroll pane.
        list1 = new JList(listModel1);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setSelectedIndex(0);
        list1.setVisibleRowCount(5);
        JScrollPane listScrollPane1 = new JScrollPane(list1);

        //Create a panel that uses BoxLayout.
        JPanel buttonPane1 = new JPanel();
        buttonPane1.setLayout(new BoxLayout(buttonPane1,
                BoxLayout.LINE_AXIS));


        southcent.add(listScrollPane1, BorderLayout.SOUTH);
        southcent.add(buttonPane1, BorderLayout.SOUTH);

        southrigh.add(startTimer, BorderLayout.NORTH);
        southrigh.add(stopTimer, BorderLayout.SOUTH);
    }

    private void addListeners() {
        ActionListener listener = new ButtonActionListeners();
        lamp1_On.addActionListener(listener);
        lamp1_off.addActionListener(listener);
        startTimer.addActionListener(listener);
        stopTimer.addActionListener(listener);
    }

    public void setLamp1_On(){
        lamp1_On.setEnabled(true);
        lamp1_off.setEnabled(false);
    }

    public void setLamp1_off(){
        lamp1_On.setEnabled(false);
        lamp1_off.setEnabled(true);
    }

    class ButtonActionListeners implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == lamp1_On) {
                controller.buttonPressed(ButtonType.lamp1_On);
                lamp1_On.setEnabled(false);
                lamp1_off.setEnabled(true);
            }
            else if (e.getSource() == lamp1_off) {
                controller.buttonPressed(ButtonType.lamp1_off);
                lamp1_On.setEnabled(true);
                lamp1_off.setEnabled(false);
            }
            else if (e.getSource() == startTimer) {
               // System.out.println("Turn the light on \nfrom: "+list.getSelectedValue()+
                 //       "\ntill: "+list1.getSelectedValue());
                controller.buttonPressed(ButtonType.statistic);
            }
            else if (e.getSource() == stopTimer) {
                System.out.println("Turn the light off \nfrom: "+list.getSelectedValue()+
                        "\ntill: "+list1.getSelectedValue());
            }
        }
    }
}
*/
