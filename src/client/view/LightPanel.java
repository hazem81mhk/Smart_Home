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

/**
 * 011/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class LightPanel extends JPanel {
    private Controller controller;
    private int width = 350;
    private int height = 200;
    private JPanel timePanel1;
    private JPanel timePanel2;
    private JButton lamp1_On,lamp1_off, startTimer;


    public LightPanel (){
        //this.controller=controller;
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
        pnlMain.setBorder(BorderFactory.createTitledBorder(null,"Time table",
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

        startTimer =new JButton("Start timer");
        startTimer.setPreferredSize(new Dimension(150,35));

        timePanel1=new JPanel();
        timePanel2=new JPanel();
        timetablePanelFrom();
        timetablePanelTo();

        JPanel north=new JPanel();
        north.add(timeFrom);
        north.add(timePanel1);
        north.add(timeTo);
        north.add(timePanel2);

        JPanel south=new JPanel();
        south.add(startTimer);

        pnlMain.add(north,BorderLayout.NORTH);
        pnlMain.add(south,BorderLayout.SOUTH);

        add(pnlMain, BorderLayout.SOUTH);
    }

    public void timetablePanelFrom(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        DefaultComboBoxModel<Date> model = new DefaultComboBoxModel<>();
        do {
            model.addElement(calendar.getTime());
            calendar.add(Calendar.MINUTE, 15);
        } while (calendar.getTime().before(end.getTime()));
        JComboBox<Date> cb = new JComboBox<>(model);
        cb.setRenderer(new DateFormattedListCellRenderer(new SimpleDateFormat("HH:mm")));
        timePanel1.add(cb);
    }

    public void timetablePanelTo(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        DefaultComboBoxModel<Date> model = new DefaultComboBoxModel<>();
        do {
            model.addElement(calendar.getTime());
            calendar.add(Calendar.MINUTE, 15);
        } while (calendar.getTime().before(end.getTime()));
        JComboBox<Date> cb = new JComboBox<>(model);
        cb.setRenderer(new DateFormattedListCellRenderer(new SimpleDateFormat("HH:mm")));
        timePanel2.add(cb);
    }

    public class DateFormattedListCellRenderer extends DefaultListCellRenderer {
        private DateFormat format;
        public DateFormattedListCellRenderer(DateFormat format) {
            this.format = format;
        }
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Date) {
                value = format.format((Date) value);
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    private void addListeners() {
        ActionListener listener = new ButtonActionListeners();
        lamp1_On.addActionListener(listener);
        lamp1_off.addActionListener(listener);
        startTimer.addActionListener(listener);
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
                controller.buttonPressed(ButtonType.lamp1_off);
            }
        }
    }
}
