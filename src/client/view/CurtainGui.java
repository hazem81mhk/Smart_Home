package client.view;


import client.controller.Controller;
//import javafx.scene.layout.Background;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CurtainGui extends JPanel {
    private Controller controller;
    String Curtainschedulefrom, Curtainscheduleto, Tempschedule, Checkbox;
    JPanel jpmain = new JPanel();
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    JPanel jp4 = new JPanel();


    JButton jbup = new JButton("UP ⤊");
    JButton jbdown = new JButton("DOWN ⤋");
    JButton jbstop = new JButton("STOP");
    JButton jbenable1 = new JButton("ENABLE");
    JButton jbdisable1 = new JButton("DISABLE");
    JButton jbenable2 = new JButton("ENABLE");
    JButton jbdisable2 = new JButton("DISABLE");
    JButton jbmm = new JButton("Main Menu");

    JLabel jlf = new JLabel("From: ");
    JLabel jlt = new JLabel("To: ");
    JLabel jlrollup = new JLabel("Roll up when the temperature is higher than:");
    JLabel jlrolldown = new JLabel("The curtain is going to roll down otherwise");
    JLabel jlps = new JLabel("*Note: If the Temperature schedule and the Curtain conflict, ");
    JLabel jlps2 = new JLabel("then the CurtainSchedule will be followed  ");
    JLabel jlspace = new JLabel("");

    ButtonGroup ButtonG = new ButtonGroup();
    JCheckBox jcbup = new JCheckBox("UP");
    JCheckBox jcbdwn = new JCheckBox("DOWN");

    static final int SLMIN = 0;
    static final int SLMAX = 50;
    static final int SLAUTO = 0;

    JSlider jsl1 = new JSlider(JSlider.HORIZONTAL, SLMIN, SLMAX, SLAUTO);

    private JList<String> listschedulestartM;
    private JList<String> listschedulestartD;
    private JList<String> listschedulestartT;
    private JList<String> listscheduletoM;
    private JList<String> listscheduletoD;
    private JList<String> listscheduletoT;

    DefaultListModel<String> lmmonth = new DefaultListModel<String>();
    DefaultListModel<String> lmday = new DefaultListModel<>();
    DefaultListModel<String> lmtime = new DefaultListModel<String>();
    private double tempschedule1 = 0;
    private double tempschedule2 = 0;

    JFrame jf = new JFrame();

    Dimension db = new Dimension(120, 30);

    public CurtainGui(Controller controller) {
        this.controller = controller;
        jf.add(jpmain);
        Buttons();
        CurtainSchedule();
        TemperatureSchedule();
        MainMenu();

        jf.setPreferredSize(new Dimension(360, 670));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setTitle("SmartHome Application");
        jf.pack();
        jf.setVisible(true);
    }

    public void Buttons() {
        jp1.setPreferredSize(new Dimension(360, 120));
        Dimension d = new Dimension(140, 50);
        jbup.setPreferredSize(d);
        jbdown.setPreferredSize(d);
        jbstop.setPreferredSize(d);
        jpmain.add(jp1);
        jp1.add(jbup);
        jp1.add(jbdown);
        jp1.add(jbstop);
        jbup.addActionListener(new ButtonActionListen());
        jbdown.addActionListener(new ButtonActionListen());
        jbstop.addActionListener(new ButtonActionListen());
        jbstop.setEnabled(true);
    }

    public void CurtainSchedule() {
        jp2.setBorder(BorderFactory.createTitledBorder(null, "CurtainSchedule: ",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER, new Font("Arial", Font.PLAIN, 15), Color.BLACK));
        jp2.setPreferredSize(new Dimension(330, 180));

        Dimension djl = new Dimension(80, 25);
        Dimension dcb = new Dimension(100, 40);
        jlf.setPreferredSize(djl);
        jlt.setPreferredSize(djl);
        jcbup.setPreferredSize(dcb);
        jcbdwn.setPreferredSize(dcb);
        jbenable1.setPreferredSize(db);
        jbenable2.setPreferredSize(db);
        jbdisable1.setPreferredSize(db);
        jbdisable2.setPreferredSize(db);
        jpmain.add(jp2);
        ButtonG.add(jcbup);
        ButtonG.add(jcbdwn);

        int y = 0;

        String hour;
        String minute;
        String day;
        String month;
        for (int m = 1; m < 13; m++) {
            if (m < 10) {
                month = ("0" + m);
            } else {
                month = String.valueOf(m);
            }
            lmmonth.addElement(month);
        }
        for (int z = 1; z < 32; z++) {
            if (z < 10) {
                day = ("0" + z);
            } else {
                day = String.valueOf(z);
            }
            lmday.addElement(day);
        }
        for (int x = 0; x < 24; x++) {
            while (y != 60) {
                if (x < 10) {
                    hour = ("0" + x);
                } else {
                    hour = String.valueOf(x);
                }

                if (y < 10) {
                    minute = ("0" + y);
                } else {
                    minute = String.valueOf(y);
                }
                y += 5;
                lmtime.addElement(hour + ":" + minute);
            }
            y = 0;
        }
        listschedulestartT = new JList<String>(lmtime);

        listschedulestartT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listschedulestartT.setSelectedIndex(0);
        listschedulestartT.setVisibleRowCount(1);

        JScrollPane Time = new JScrollPane(listschedulestartT);

        listschedulestartM = new JList<String>(lmmonth);
        listschedulestartM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listschedulestartM.setSelectedIndex(0);
        listschedulestartM.setVisibleRowCount(1);

        JScrollPane Month = new JScrollPane(listschedulestartM);

        listschedulestartD = new JList<>(lmday);
        listschedulestartD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listschedulestartD.setSelectedIndex(0);
        listschedulestartD.setVisibleRowCount(1);

        DefaultListModel<String> listModel2 = new DefaultListModel<String>();
        //DefaultListModel listModel = new DefaultListModel();
        listModel2 = lmtime;

        listscheduletoT = new JList<String>(listModel2);
        listscheduletoT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listscheduletoT.setSelectedIndex(0);
        listscheduletoT.setVisibleRowCount(1);

        JScrollPane Time2 = new JScrollPane(listscheduletoT);
        //JList för Månad i Schedule to panelen
        DefaultListModel<String> lmmonth2 = new DefaultListModel<String>();
        lmmonth2 = lmmonth;

        listscheduletoM = new JList<>(lmmonth);
        listscheduletoM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listscheduletoM.setSelectedIndex(0);
        listscheduletoM.setVisibleRowCount(1);

        JScrollPane Month2 = new JScrollPane(listscheduletoM);
        //JList för dagar i schedule to panelen
        DefaultListModel<String> lmday2 = new DefaultListModel<>();
        lmday2 = lmday;

        listscheduletoD = new JList<>(lmday2);
        listscheduletoD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listscheduletoD.setSelectedIndex(0);
        listscheduletoD.setVisibleRowCount(1);

        JScrollPane Day2 = new JScrollPane(listscheduletoD);

        JScrollPane Day = new JScrollPane(listschedulestartD);
        Month.setPreferredSize(new Dimension(40, 25));
        Day.setPreferredSize(new Dimension(40, 25));
        Time.setPreferredSize(new Dimension(60, 25));
        Month2.setPreferredSize(new Dimension(40, 25));
        Day2.setPreferredSize(new Dimension(40, 25));
        Time2.setPreferredSize(new Dimension(60, 25));

        jp2.add(jlf);
        jp2.add(Month);
        jp2.add(Day);
        jp2.add(Time);
        jp2.add(jlt);
        jp2.add(Month2);
        jp2.add(Day2);
        jp2.add(Time2);

        jp2.add(jcbup, BorderLayout.CENTER);
        jp2.add(jcbdwn, BorderLayout.CENTER);
        jp2.add(jbenable1);
        jp2.add(jbdisable1);
        CurtainScheduleActionListen curtainScheduleActionListen = new CurtainScheduleActionListen();
        jbenable1.addActionListener(curtainScheduleActionListen);
        jbdisable1.addActionListener(curtainScheduleActionListen);
    }

    public void TemperatureSchedule() {
        jsl1.setMajorTickSpacing(10);
        jsl1.setMinorTickSpacing(1);
        jsl1.setPaintTicks(true);
        jsl1.setPaintLabels(true);

        jp3.setBorder(BorderFactory.createTitledBorder(null, "TemperatureSchedule: ",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                new Font("Arial", Font.PLAIN, 15), Color.BLACK));
        jp3.setPreferredSize(new Dimension(330, 245));

        Dimension dslide = new Dimension(250, 50);
        jbenable2.setPreferredSize(db);
        jsl1.setPreferredSize(dslide);
        jlrollup.setPreferredSize(new Dimension(265, 25));
        jlrolldown.setPreferredSize(new Dimension(255, 30));

        Dimension dps = new Dimension(270, 10);
        Font fps = new Font("Arial", Font.PLAIN, 10);
        jlps.setPreferredSize(dps);
        jlps.setFont(fps);
        jlps2.setPreferredSize(dps);
        jlps2.setFont(fps);
        jlps.setForeground(Color.RED);
        jlps2.setForeground(Color.RED);


        jlspace.setPreferredSize(new Dimension(360, 15));

        jpmain.add(jp3);
        jp3.add(jlrollup);
        jp3.add(jsl1);
        jp3.add(jlrolldown);
        jp3.add(jbenable2);
        jp3.add(jbdisable2);
        jp3.add(jlspace);
        jp3.add(jlps);
        jp3.add(jlps2);
        jbenable2.addActionListener(new TemperatureScheduleActionListen());
        jbdisable2.addActionListener(new TemperatureScheduleActionListen());
    }

    public void MainMenu() {
        jp4.setBorder(BorderFactory.createTitledBorder(null, null,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                new Font("Arial", Font.PLAIN, 15), Color.BLACK));
        jp4.setPreferredSize(new Dimension(330, 55));
        jpmain.add(jp4);
        jbmm.setPreferredSize(new Dimension(280, 40));
        jp4.add(jbmm);
        jbmm.addActionListener(new MainMenuActionListen());

    }


    public void setCurtainschedulefrom() {
        String FMonth = listschedulestartM.getSelectedValue();
        String FDay = listschedulestartD.getSelectedValue();
        String FTime = listschedulestartT.getSelectedValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        this.Curtainschedulefrom = currentDate + FMonth + "-" + FDay + " " + FTime + ":00";
    }

    public void setCurtainscheduleto() {
        String TMonth = listscheduletoM.getSelectedValue();
        String TDay = listscheduletoD.getSelectedValue();
        String TTime = listscheduletoT.getSelectedValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        this.Curtainscheduleto = currentDate + TMonth + "-" + TDay + " " + TTime + ":00";
    }

    public String getCurtainschedulefrom() {
        return Curtainschedulefrom;
    }

    public String getCurtainscheduleto() {
        return Curtainscheduleto;
    }

    public void setTempschedule() {
        double High, Low;
        High = jsl1.getValue();
        this.tempschedule1 = High;
    }

    public double getTempsch1() {
        return tempschedule1;
    }

    public double getTempsch() {
        String str = ("Between " + getTempsch1() + " And " + getTempsch2());
        return tempschedule1;
    }

    public double getTempsch2() {
        return tempschedule2;
    }

    public void setCheckbox() {
        if (jcbup.isSelected()) {
            this.Checkbox = "UP";
        } else {
            this.Checkbox = "DOWN";
        }
    }

    public String getCheckbox() {
        return Checkbox;
    }

    public void setCurtainButtonUP() {
        jbup.setEnabled(false);
        jbdown.setEnabled(true);
        jbstop.setEnabled(true);
    }

    public void setAllButtons() {
        jbup.setEnabled(true);
        jbdown.setEnabled(true);
        jbstop.setEnabled(true);
    }

    public void ButtonUPEnable() {
        jbup.setEnabled(true);

    }

    public void setCurtainButtonDownEnable() {

        jbdown.setEnabled(true);

    }

    public void setCurtainButtonDOWN() {
        jbup.setEnabled(true);
        jbdown.setEnabled(false);
        jbstop.setEnabled(true);
    }

    public void setCurtainButtonSTOP() {
        jbup.setEnabled(true);
        jbdown.setEnabled(true);
        jbstop.setEnabled(true);
    }

    public void curtainOnTop() {
        jbup.setEnabled(false);
        jbdown.setEnabled(true);
        jbstop.setEnabled(false);
    }

    public void curtainBottom() {
        jbup.setEnabled(true);
        jbdown.setEnabled(false);
        jbstop.setEnabled(false);
    }

    class ButtonActionListen implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jbup) {
                controller.buttonPressed(ButtonType.curtain_up);
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }


                //jbup.setEnabled(false);
                //jbdown.setEnabled(true);
                //jbstop.setEnabled(true);
                //System.out.println("The curtains are rolling up!");
            } else if (e.getSource() == jbdown) {
                controller.buttonPressed(ButtonType.curtain_down);
                //jbdown.setEnabled(false);
                //jbup.setEnabled(true);
                //jbstop.setEnabled(true);
                //System.out.println("The curtains are rolling down!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();

                }

            } else if (e.getSource() == jbstop) {
                controller.buttonPressed(ButtonType.curtain_stop);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();

                }


                //jbup.setEnabled(true);
                //jbdown.setEnabled(true);
                //jbstop.setEnabled(false);
                //System.out.println("The curtains have stopped rolling!");
            }
        }
    }

    class CurtainScheduleActionListen implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (!jcbup.isSelected() && !jcbdwn.isSelected()) {
                JOptionPane.showMessageDialog(null, "Pick the direction of the curtain");
            }
            /*else if (jcbup.isSelected()){
                controller.buttonPressed(ButtonType.curtain_schedule);
                System.out.println("Curtain goes UP. From: "  + listschedulestartM.getSelectedValue() +", "+
                        listschedulestartD.getSelectedValue() + ", " + listschedulestartT.getSelectedValue() + ", To: "+
                        listscheduletoM.getSelectedValue() +", "+ listscheduletoD.getSelectedValue() +
                        ", " + listscheduletoT.getSelectedValue());
            }*/
            else {
                if (e.getSource() == jbenable1) {
                    controller.buttonPressed(ButtonType.curtain_schedule);

/*
                    System.out.println("Curtain goes DOWN. From: " + listschedulestartM.getSelectedValue() + ", " +
                            listschedulestartD.getSelectedValue() + ", " + listschedulestartT.getSelectedValue() + ", To: " +
                            listscheduletoM.getSelectedValue() + ", " + listscheduletoD.getSelectedValue() +
                            ", " + listscheduletoT.getSelectedValue());
*/
                } else if (e.getSource() == jbdisable1) {
                    controller.buttonPressed(ButtonType.disable);
                    //System.out.println("Disable curtain");
                }
            }
        }
    }

    class TemperatureScheduleActionListen implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jbenable2) {
                controller.buttonPressed(ButtonType.temp_curtain);
                System.out.println("Higher than " + jsl1.getValue());
            } else if (e.getSource() == jbdisable2) {
                controller.buttonPressed(ButtonType.disable);
                System.out.println("Disable temp");
            }
        }
    }

    class MainMenuActionListen implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jbmm) {
                controller.buttonPressed(ButtonType.main_menu);
                jf.dispose();
            }
        }
    }
}