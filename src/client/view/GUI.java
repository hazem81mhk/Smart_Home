package client.view;

import client.controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 18/04/2020
 *
 * @Agon Beqa
 */

public class GUI extends JPanel {
    private Controller controller;
    private int width = 340;
    private int height = 740;
    private JFrame jf = new JFrame();
    private JPanel jpmain = new JPanel();

    private String kwkr, kw, cost, strttime, totime, consfr, consto;
    //JPanel
    private JPanel jplmp;
    private JPanel jplampst = new JPanel();
    private JPanel jpsch = new JPanel();
    private JPanel jpstart = new JPanel();
    private JPanel jpto = new JPanel();
    private JPanel jpcons = new JPanel();
    private JPanel jpconsfr = new JPanel();
    private JPanel jpconsto = new JPanel();
    private JPanel jptextArra =new JPanel();

    private JTextArea textArea;
    //JButton
    private JButton jbon = new JButton("ON");
    private JButton jboff = new JButton("OFF");
    private JButton jbsch = new JButton("Start Schedule");
    private JButton jbcons = new JButton("Get consumption");
    //JLabel
    private JLabel jlkwkr = new JLabel("KW/Kr");
    private JLabel jlkw = new JLabel("KW");
    //private JLabel jlcst = new JLabel("Cost");

    private JLabel jlSM = new JLabel("Month");
    private JLabel jlSD = new JLabel("Day");
    private JLabel jlST = new JLabel("Time");

    private JLabel jlStM = new JLabel("Month");
    private JLabel jlStD = new JLabel("Day");
    private JLabel jlStT = new JLabel("Time");

    private JLabel jlcfm = new JLabel("Month");
    private JLabel jlcfd = new JLabel("Day");
    private JLabel jlcft = new JLabel("Time");

    private JLabel jlctm = new JLabel("Month");
    private JLabel jlctd = new JLabel("Day");
    private JLabel jlctt = new JLabel("Time");
    //JTextfield
    private JTextField jtkwkr = new JTextField();
    private JTextField jtkw = new JTextField();
    //private JTextField jtcst = new JTextField();
    //JList
    private JList<String> listschedulestartM;
    private JList<String> listschedulestartD;
    private JList<String> listschedulestartT;
    private JList<String> listscheduletoM;
    private JList<String> listscheduletoD;
    private JList<String> listscheduletoT;
    private JList<String> listconsfromM;
    private JList<String> listconsfromD;
    private JList<String> listconsfromT;
    private JList<String> listconstoM;
    private JList<String> listconstoD;
    private JList<String> listconstoT;

    DefaultListModel<String> lmmonth = new DefaultListModel<String>();
    DefaultListModel<String> lmday = new DefaultListModel<>();
    DefaultListModel<String> listModel = new DefaultListModel<String>();


    Font F1 = new Font("Arial", Font.PLAIN, 13);
    Font F2 = new Font("Arial", Font.BOLD, 11);

    public GUI(Controller controller) {
        this.controller = controller;
        //setLayout(new BorderLayout());
        //setPreferredSize(new Dimension(width, height));
        jpmain.setLayout(new BoxLayout(jpmain, BoxLayout.Y_AXIS));
        add(jpmain);

        Lampstate();
        Schedule();
        Consumption();
        textArea();
        Lamp();
        ActionListener listener = new ButtonActionListeners();
        jbon.addActionListener(listener);
        jboff.addActionListener(listener);
        jbsch.addActionListener(listener);
        jbcons.addActionListener(listener);

    }

    public void Lamp() {
        //jplmp.setPreferredSize(new Dimension(360, 680));
        jplmp = new JPanel();
        jplmp.setBorder(BorderFactory.createTitledBorder(null, "Lamp 1:",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                new Font("Arial", Font.PLAIN, 15), Color.BLACK));
        jpmain.add(jplmp);
        jplmp.add(jplampst, BorderLayout.NORTH);
        jplmp.add(jpsch, BorderLayout.CENTER);
        jplmp.add(jpcons, BorderLayout.SOUTH);
        jplmp.add(jptextArra,BorderLayout.SOUTH);
       // add(jplmp, BorderLayout.CENTER);
    }

    public void textArea(){
        jptextArra.setPreferredSize(new Dimension(300,90));
        jptextArra.setBorder(BorderFactory.createTitledBorder(null, "Update:",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                new Font("Arial", Font.PLAIN, 15), Color.BLACK));
        textArea=new JTextArea(3,27);
        textArea.setEditable(false);
        jpmain.add(jptextArra);
        //JScrollPane Scroll = new JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane Scroll=new JScrollPane(textArea);
        Scroll.setHorizontalScrollBar(null);
        //appendLog("");
        jptextArra.add(Scroll);
    }

    public void appendLog(String str) {
        textArea.append(str + "\n");
    }

    public void Lampstate() {
        jplampst.setPreferredSize(new Dimension(300, 60));
        jplampst.setBorder(BorderFactory.createTitledBorder(null, "Lamp State", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                F1, Color.BLUE));
        //JButton on&off i Lampstate panelen
        Dimension dimb = new Dimension(120, 28);
        jpmain.add(jplampst);
        jbon.setPreferredSize(dimb);
        jboff.setPreferredSize(dimb);
        jplampst.add(jbon);
        jplampst.add(jboff);
    }

    public void Schedule() {
        jpsch.setPreferredSize(new Dimension(300, 210));
        jpsch.setBorder(BorderFactory.createTitledBorder(null, "Schedule", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                F1, Color.BLUE));
        //JList fÃ¶r tiden
        int y = 0;
        String mystring;
        String hour;
        String minute;
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
                listModel.addElement(hour + ":" + minute);
            }
            y = 0;
        }

        listschedulestartT = new JList<String>(listModel);

        listschedulestartT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listschedulestartT.setSelectedIndex(0);
        listschedulestartT.setVisibleRowCount(8);

        JScrollPane Time = new JScrollPane(listschedulestartT);

        //JList fÃ¶r Month
        lmmonth.addElement("01");
        lmmonth.addElement("02");
        lmmonth.addElement("03");
        lmmonth.addElement("04");
        lmmonth.addElement("05");
        lmmonth.addElement("06");
        lmmonth.addElement("07");
        lmmonth.addElement("08");
        lmmonth.addElement("09");
        lmmonth.addElement("10");
        lmmonth.addElement("11");
        lmmonth.addElement("12");

        listschedulestartM = new JList<String>(lmmonth);
        listschedulestartM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listschedulestartM.setSelectedIndex(0);
        listschedulestartM.setVisibleRowCount(9);

        JScrollPane Month = new JScrollPane(listschedulestartM);

        //JList fÃ¶r dagar
        lmday.addElement("01");
        lmday.addElement("02");
        lmday.addElement("03");
        lmday.addElement("04");
        lmday.addElement("05");
        lmday.addElement("06");
        lmday.addElement("07");
        lmday.addElement("08");
        lmday.addElement("09");
        lmday.addElement("10");
        lmday.addElement("11");
        lmday.addElement("12");
        lmday.addElement("13");
        lmday.addElement("14");
        lmday.addElement("15");
        lmday.addElement("16");
        lmday.addElement("17");
        lmday.addElement("18");
        lmday.addElement("19");
        lmday.addElement("20");
        lmday.addElement("21");
        lmday.addElement("22");
        lmday.addElement("23");
        lmday.addElement("24");
        lmday.addElement("25");
        lmday.addElement("26");
        lmday.addElement("27");
        lmday.addElement("28");
        lmday.addElement("29");
        lmday.addElement("30");
        lmday.addElement("31");

        listschedulestartD = new JList<>(lmday);
        listschedulestartD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listschedulestartD.setSelectedIndex(0);
        listschedulestartD.setVisibleRowCount(1);

        JScrollPane Day = new JScrollPane(listschedulestartD);
        //JPanel start i Schedule panelen
        jpstart.setPreferredSize(new Dimension(280, 70));
        jpstart.setBorder(BorderFactory.createTitledBorder(null, "Start lamp from:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                F2, Color.BLACK));
        jpstart.setLayout(new GridLayout(2, 3));

        //JPanel to i schedule panelen
        jpto.setPreferredSize(new Dimension(280, 70));
        jpto.setLayout(new GridLayout(2, 3));
        jpto.setBorder(BorderFactory.createTitledBorder(null, "To:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                F2, Color.BLACK));

        jpmain.add(jpsch);
        jpsch.add(jpstart, BorderLayout.NORTH);
        jpsch.add(jpto, BorderLayout.SOUTH);
        jpsch.add(jbsch);

        jpstart.add(jlSM);
        jpstart.add(jlSD);
        jpstart.add(jlST);
        jpstart.add(Month);
        jpstart.add(Day);
        jpstart.add(Time);
        //JList fÃ¶r tiden i Schedule to panelen
        DefaultListModel<String> listModel2 = new DefaultListModel<String>();
        //DefaultListModel listModel = new DefaultListModel();
        listModel2 = listModel;

        listscheduletoT = new JList<String>(listModel2);
        listscheduletoT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listscheduletoT.setSelectedIndex(0);
        listscheduletoT.setVisibleRowCount(8);

        JScrollPane Time2 = new JScrollPane(listscheduletoT);
        //JList fÃ¶r MÃ¥nad i Schedule to panelen
        DefaultListModel<String> lmmonth2 = new DefaultListModel<String>();
        lmmonth2 = lmmonth;

        listscheduletoM = new JList<>(lmmonth);
        listscheduletoM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listscheduletoM.setSelectedIndex(0);
        listscheduletoM.setVisibleRowCount(9);

        JScrollPane Month2 = new JScrollPane(listscheduletoM);
        //JList fÃ¶r dagar i schedule to panelen
        DefaultListModel<String> lmday2 = new DefaultListModel<>();
        lmday2 = lmday;

        listscheduletoD = new JList<>(lmday2);
        listscheduletoD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listscheduletoD.setSelectedIndex(0);
        listscheduletoD.setVisibleRowCount(1);

        JScrollPane Day2 = new JScrollPane(listscheduletoD);

        jpto.add(jlStM, BorderLayout.NORTH);
        jpto.add(jlStD, BorderLayout.NORTH);
        jpto.add(jlStT, BorderLayout.NORTH);
        jpto.add(Month2);
        jpto.add(Day2);
        jpto.add(Time2);
    }

    public void Consumption() {
        jpcons.setPreferredSize(new Dimension(300, 270));
        jpcons.setBorder(BorderFactory.createTitledBorder(null, "Consumption", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                F1, Color.BLUE));
        jpmain.add(jpcons);
        jpcons.add(jpconsfr);
        jpcons.add(jpconsto);
        //Jpanel from i consumption
        Dimension dim1 = new Dimension(280, 70);
        jpconsfr.setPreferredSize(dim1);
        jpconsfr.setBorder(BorderFactory.createTitledBorder(null, "From:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                F2, Color.BLACK));
        jpconsfr.setLayout(new GridLayout(2, 3));

        //JPanel to i consumption
        jpconsto.setPreferredSize(dim1);
        jpconsto.setBorder(BorderFactory.createTitledBorder(null, "To:", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER,
                F2, Color.BLACK));
        jpconsto.setLayout(new GridLayout(2, 3));

        //Button setenable
        jbon.setEnabled(true);
        jboff.setEnabled(false);

        //JList i Consumption From Panelen
        DefaultListModel<String> listModel3 = new DefaultListModel<String>();
        listModel3 = listModel;

        listconsfromT = new JList<>(listModel3);
        listconsfromT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listconsfromT.setSelectedIndex(0);
        listconsfromT.setVisibleRowCount(8);

        JScrollPane Time3 = new JScrollPane(listconsfromT);

        //JList fÃ¶r Month i Consumption from panelen
        DefaultListModel<String> lmmonth3 = new DefaultListModel<String>();
        lmmonth3 = lmmonth;

        listconsfromM = new JList<>(lmmonth3);
        listconsfromM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listconsfromM.setSelectedIndex(0);
        listconsfromM.setVisibleRowCount(9);

        JScrollPane Month3 = new JScrollPane(listconsfromM);

        //JList fÃ¶r dagar i Consuption from panelen
        DefaultListModel<String> lmday3 = new DefaultListModel<>();
        lmday3 = lmday;

        listconsfromD = new JList<String>(lmday3);
        listconsfromD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listconsfromD.setSelectedIndex(0);
        listconsfromD.setVisibleRowCount(1);

        JScrollPane Day3 = new JScrollPane(listconsfromD);

        jpconsfr.add(jlcfm);
        jpconsfr.add(jlcfd);
        jpconsfr.add(jlcft);
        jpconsfr.add(Month3);
        jpconsfr.add(Day3);
        jpconsfr.add(Time3);
        //JList fÃ¶r tiden i Consumption To panelen
        DefaultListModel<String> listModel4 = new DefaultListModel<>();
        listModel4 = listModel;

        listconstoT = new JList<String>(listModel4);
        listconstoT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listconstoT.setSelectedIndex(0);
        listconstoT.setVisibleRowCount(8);

        JScrollPane Time4 = new JScrollPane(listconstoT);
        //Jlist fÃ¶r mÃ¥nader i consumption to panelen
        DefaultListModel<String> lmmonth4 = new DefaultListModel<>();
        lmmonth4 = lmmonth;
        listconstoM = new JList<String>(lmmonth4);
        listconstoM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listconstoM.setSelectedIndex(0);
        listconstoM.setVisibleRowCount(9);

        JScrollPane Month4 = new JScrollPane(listconstoM);
        //JList fÃ¶r dagar i consumption to panelen
        DefaultListModel<String> lmday4 = new DefaultListModel<>();
        lmday4 = lmday;
        listconstoD = new JList<>(lmday4);
        listconstoD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listconstoD.setSelectedIndex(0);
        listconstoD.setVisibleRowCount(1);

        JScrollPane Day4 = new JScrollPane(listconstoD);
        jpconsto.add(jlctm);
        jpconsto.add(jlctd);
        jpconsto.add(jlctt);
        jpconsto.add(Month4);
        jpconsto.add(Day4);
        jpconsto.add(Time4);
        //JPanel dÃ¤r jlabel finns och jtextfield
        JPanel jpcon1 = new JPanel();
        jpcons.add(jpcon1);
        jpcon1.setPreferredSize(new Dimension(280, 50));
        jpcon1.setLayout(new GridLayout(2, 2));
        jpcon1.add(jlkwkr);
        jpcon1.add(jtkwkr);
        jpcon1.add(jlkw);
        jpcon1.add(jtkw);
        //jpcon1.add(jlcst);
        //jpcon1.add(jtcst);
        jpcons.add(jbcons);

    }


    public void setStarttime(String Month, String Day, String Time) {
        Month = listschedulestartM.getSelectedValue();
        Day = listschedulestartD.getSelectedValue();
        Time = listschedulestartT.getSelectedValue();
        this.strttime = "Time start from: " + Month + ", " + Day + ", " + Time;
    }

    public void setSelectedStarttime() {
        String Month = listschedulestartM.getSelectedValue();
        String Day = listschedulestartD.getSelectedValue();
        String Time = listschedulestartT.getSelectedValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        this.strttime = currentDate + Month + "-" + Day + " " + Time + ":00";
    }

    public String getStarttime() {
        return strttime;
    }

    public void setTotime(String Month, String Day, String Time) {
        Month = listscheduletoD.getSelectedValue();
        Day = listscheduletoD.getSelectedValue();
        Time = listscheduletoT.getSelectedValue();
        this.totime = "to: " + Month + ", " + Day + ", " + Time;
    }

    public void setSelectedTotime() {
        String Month = listscheduletoM.getSelectedValue();
        String Day = listscheduletoD.getSelectedValue();
        String Time = listscheduletoT.getSelectedValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        this.totime = currentDate + Month + "-" + Day + " " + Time + ":00";
    }

    public String getTotime() {
        return totime;
    }

    public void setConsfr(String Month, String Day, String Time) {
        Month = listconsfromM.getSelectedValue();
        Day = listconsfromD.getSelectedValue();
        Time = listconsfromT.getSelectedValue();
        this.consfr = "Time start from: " + Month + ", " + Day + ", " + Time;
    }

    public void setConsumptionFor(){
        String Month = listconsfromM.getSelectedValue();
        String Day = listconsfromD.getSelectedValue();
        String Time = listconsfromT.getSelectedValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        this.consfr = currentDate + Month + "-" + Day + " " + Time + ":00";
    }

    public String getConsfr() {
        return consfr;
    }

    public void setConsto(String Month, String Day, String Time) {
        Month = listconstoM.getSelectedValue();
        Day = listconstoD.getSelectedValue();
        Time = listconstoT.getSelectedValue();
        this.consto = "to: " + Month + ", " + Day + ", " + Time;
    }

    public void setConsumptionTo(){
        String Month = listconstoM.getSelectedValue();
        String Day = listconstoD.getSelectedValue();
        String Time = listconstoT.getSelectedValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-");
        String currentDate = sdf.format(Calendar.getInstance().getTime());
        this.consto = currentDate + Month + "-" + Day + " " + Time + ":00";
    }

    public String getConsto() {
        return consto;
    }

    public String getKwkr() {
        return jtkwkr.getText();
    }

    public String getKw() {
        return jtkw.getText();
    }

    public void setLampButtonOn() {
        jbon.setEnabled(true);
        jboff.setEnabled(false);
    }

    public void setLampButtonOff() {
        jbon.setEnabled(false);
        jboff.setEnabled(true);
    }

    class ButtonActionListeners implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == jbon) {
                controller.buttonPressed(ButtonType.lamp1_On);
                //setLampButtonOff();
                //System.out.println("Gui: ON");
            } else if (e.getSource() == jboff) {
                controller.buttonPressed(ButtonType.lamp1_off);
                //setLampButtonOn();
                //System.out.println("Gui: OFF");
            } else if (e.getSource() == jbsch) {
                //System.out.println("Gui: start schedule");
                controller.buttonPressed(ButtonType.start_schedule);
                //setSelectedStarttime();
                //System.out.println(getStarttime());
                //setSelectedTotime();
                //System.out.println(getTotime());
            } else if (e.getSource() == jbcons) {
                try {
                    if(jtkw.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Please enter your lamp kW");
                    }
                    else if(jtkwkr.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please enter the cost per kW");
                    }else {
                        controller.buttonPressed(ButtonType.get_consumption);
                    }
                }catch (NumberFormatException ee){
                    JOptionPane.showMessageDialog(null, "Please enter just numbers");
                }

                //System.out.println("Gui: get consumption");
            }
        }
    }

    public static void main(String[] args) {
        GUI gui=new GUI(null);
    }
}
