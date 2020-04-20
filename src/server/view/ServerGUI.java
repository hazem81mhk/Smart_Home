package server.view;

import server.controller.Controller;
import server.model.MainServer.ButtonType;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerGUI extends JFrame {
    private Controller controller;

    private JButton start, stop, searchLog, searchGO;
    private JTextArea eventLog, log;
    private JTextField tfPortNbr, dateField1, dateField2, time1Field, time2Field;
    private JFrame searchFrame;

    public ServerGUI(Controller controller) {
        this.controller = controller;
        ServerGUIa();
    }

    public void ServerGUIa() {
        //JFrame setup
        //MainProgramServer Frame (Server)
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(800, 400);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setTitle("                                                            " +
                "                       << Smart Home Server >>");
        mainFrame.setLocationRelativeTo(null);

        //Search Frame (search log)
        searchFrame = new JFrame();
        searchFrame.setSize(600, 600);
        searchFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        searchFrame.setTitle("Search log");
        searchFrame.setLocationRelativeTo(null);

        //initialize components
        //TextAreas with scroll as needed
        //Event
        eventLog = new JTextArea(20, 60);
        eventLog.setEditable(false);
        appendEventLog("Event log\n");
        JScrollPane chatScroll = new JScrollPane(eventLog, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        //Search log
        log = new JTextArea(60, 60);
        log.setEditable(false);
        appendLog("Search log\n");
        JScrollPane logScroll = new JScrollPane(log, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //ButtonType
        start = new JButton("Start Server");
        stop =new JButton("Stop Server");
        stop.setEnabled(false);
        searchLog = new JButton("Search log");
        searchGO = new JButton("Search");

        //Textfields
        tfPortNbr = new JTextField();
        tfPortNbr.setPreferredSize(new Dimension(60, 20));

        //tfPortNbr.setEditable(false);
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
        String date = sdfDate.format(new Date());
        dateField1 = new JTextField(date);
        dateField2 = new JTextField(date);

        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        String time = sdfTime.format(new Date());
        time1Field = new JTextField(time);
        time2Field = new JTextField(time);

        //Labels
        JLabel portLabel = new JLabel("Port number: ", SwingConstants.CENTER);
        JLabel date1Label = new JLabel("Search from this date...", SwingConstants.CENTER);
        JLabel date2Label = new JLabel("...to this date", SwingConstants.CENTER);
        JLabel time1Label = new JLabel("From this time...", SwingConstants.CENTER);
        JLabel time2Label = new JLabel("...to this time", SwingConstants.CENTER);
        JLabel spacing1 = new JLabel("");//used for filling in layout
        JLabel spacing2 = new JLabel("");//used for filling in layout
        JLabel spacing3 = new JLabel("");//used for filling in layout

        //Set up Panels
        //north in MainProgramServer Frame (server)
        JPanel north = new JPanel();
        north.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        north.add(portLabel);
        north.add(tfPortNbr);
        north.add(start);
        north.add(stop);
        north.add(searchLog);

        //south in MainProgramServer Frame (server)
        JPanel south = new JPanel();
        south.setLayout(new GridLayout(1, 2));
        south.add(chatScroll);
        //south.add(eventScroll);

        //southFL in Search Frame (Search log)
        JPanel southFL = new JPanel();
        southFL.setLayout(new GridLayout(1, 1));
        southFL.add(logScroll);

        //northFL in Search Frame (Search log)
        JPanel northFL = new JPanel();
        northFL.setLayout(new GridLayout(3, 4, 0, 10));
        Border borderl = BorderFactory.createLineBorder(Color.BLACK);//Optional border
        northFL.setBorder(borderl);//Optional border
        northFL.add(date1Label);
        northFL.add(dateField1);
        northFL.add(date2Label);
        northFL.add(dateField2);
        northFL.add(time1Label);
        northFL.add(time1Field);
        northFL.add(time2Label);
        northFL.add(time2Field);
        northFL.add(spacing1);//for filling in layout
        northFL.add(spacing2);//for filling in layout
        northFL.add(spacing3);//for filling in layout
        northFL.add(searchGO);//Search log between dates

        //set up layout of the frames
        //MainProgramServer Frame (server)
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(north, BorderLayout.NORTH);//port and startStop server
        mainFrame.add(south, BorderLayout.CENTER);//chat & event textAreas

        // (search log)
        searchFrame.add(northFL, BorderLayout.NORTH);//date & time chooser
        searchFrame.add(southFL, BorderLayout.CENTER);//log textArea

        //set visible
        mainFrame.setVisible(true);
        searchFrame.setVisible(false);
        addListeners();
    }

    //Gets String and writes it in Eveny box
    public void appendEventLog(String str) {
        eventLog.append(str + "\n");
    }


    //Gets String and writes it in Log box
    public void appendLog(String str) {
        log.append(str + "\n");
    }

    private void addListeners() {
        ActionListener listener = new ButtonActionListeners();
        start.addActionListener(listener);
        stop.addActionListener(listener);
        searchLog.addActionListener(listener);
        searchGO.addActionListener(listener);
    }

    class ButtonActionListeners implements ActionListener, KeyListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == start) {
                try {
                    controller.buttonPressed(ButtonType.start);
                } catch (IOException | ParseException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource()==stop){
                try {
                    controller.buttonPressed(ButtonType.stop);
                } catch (IOException | ParseException ex) {
                    ex.printStackTrace();
                }
            }
            else if (e.getSource() == searchLog) {
                try {
                    controller.buttonPressed(ButtonType.searchLog);
                } catch (IOException | ParseException ex) {
                    ex.printStackTrace();
                }
            }
            if (e.getSource() == searchGO) {
                try {
                    controller.buttonPressed(ButtonType.searchGO);
                } catch (IOException | ParseException ex) {
                    ex.printStackTrace();
                }
            }

        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    controller.buttonPressed(ButtonType.start);
                } catch (IOException | ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public void setStart() {
        start.setEnabled(false);
        stop.setEnabled(true);
    }

    public void setStop(){
        stop.setEnabled(false);
        start.setEnabled(true);
    }

    public String getPort() {
        return tfPortNbr.getText();
    }

    public void setPort(String str) {
        tfPortNbr.setText(str);
    }

    public void setSearchFrameVisibility() {
        searchFrame.setVisible(true);
    }

    public String getdateField1() {
        return dateField1.getText();
    }

    public String getdateField2() {
        return dateField2.getText();
    }

    public String gettime1Field() {
        return time1Field.getText();
    }

    public String gettime2Field() {
        return time2Field.getText();
    }
}

