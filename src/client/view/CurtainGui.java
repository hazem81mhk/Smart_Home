package client.view;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CurtainGui extends JPanel{
    String Curtainsch, Tempsch;
    JPanel jpmain = new JPanel();
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();

    JButton jbup = new JButton("UP ⤊");
    JButton jbdown = new JButton("DOWN ⤋");
    JButton jbstop = new JButton("STOP");
    JButton jbapply1 = new JButton("APPLY");
    JButton jbapply2 = new JButton("APPLY");

    JLabel jlf = new JLabel("From: ");
    JLabel jlt = new JLabel("To: ");
    JLabel jlhr = new JLabel("Higher than ____ to roll down");
    JLabel jllr = new JLabel("Lower than ____ to roll up");

    JTextField jtf1 = new JTextField();
    JTextField jtf2 = new JTextField();

    ButtonGroup ButtonG = new ButtonGroup();
    JCheckBox jcbup = new JCheckBox("UP");
    JCheckBox jcbdwn = new JCheckBox("DOWN");

    static final int SLMIN = 0;
    static final int SLMAX = 50;
    static final int SLAUTO = 0;

    JSlider jsl1 = new JSlider(JSlider.HORIZONTAL, SLMIN, SLMAX, SLAUTO);
    JSlider jsl2 = new JSlider(JSlider.HORIZONTAL, SLMIN, SLMAX, SLAUTO);

    public CurtainGui() {
        //JFRAME
        JFrame jf = new JFrame();
        jf.add(jpmain);
        Buttons();
        CurtainSchedule();
        TemperatureSchedule();

        jf.setPreferredSize(new Dimension(360, 580));
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setTitle("SmartHome Application");
        jf.pack();
        jf.setVisible(true);
    }
    public void Buttons(){
        jp1.setPreferredSize(new Dimension(360, 120));
        Dimension d = new Dimension(140,50);
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
        jbstop.setEnabled(false);
    }

        public void CurtainSchedule(){
        jp2.setBorder(BorderFactory.createTitledBorder(null,"CurtainSchedule: ",
                    TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.CENTER,new Font("Arial",Font.PLAIN,15),Color.BLACK));
        jp2.setPreferredSize(new Dimension(330,180));
        Dimension djt = new Dimension(170,25);
        Dimension djl = new Dimension(80,25);
        Dimension dcb = new Dimension(100,40);
        jlf.setPreferredSize(djl);
        jtf1.setPreferredSize(djt);
        jlt.setPreferredSize(djl);
        jtf2.setPreferredSize(djt);
        jcbup.setPreferredSize(dcb);
        jcbdwn.setPreferredSize(dcb);
        jbapply1.setPreferredSize(new Dimension(120,30));
        jpmain.add(jp2);
        ButtonG.add(jcbup);
        ButtonG.add(jcbdwn);
        jp2.add(jlf);
        jp2.add(jtf1);
        jp2.add(jlt);
        jp2.add(jtf2);
        jp2.add(jcbup, BorderLayout.CENTER);
        jp2.add(jcbdwn, BorderLayout.CENTER);
        jp2.add(jbapply1);
        jbapply1.addActionListener(new CurtainScheduleActionListen());
        }
        public void TemperatureSchedule(){
            jsl2.setMajorTickSpacing(10);
            jsl2.setMinorTickSpacing(1);
            jsl2.setPaintTicks(true);
            jsl2.setPaintLabels(true);

            jsl1.setMajorTickSpacing(10);
            jsl1.setMinorTickSpacing(1);
            jsl1.setPaintTicks(true);
            jsl1.setPaintLabels(true);

            jp3.setBorder(BorderFactory.createTitledBorder(null,"TemperatureSchedule: ",
                    TitledBorder.DEFAULT_JUSTIFICATION,TitledBorder.CENTER,
                    new Font("Arial",Font.PLAIN,15),Color.BLACK));
            jp3.setPreferredSize(new Dimension(330,218));

            Dimension dslide = new Dimension(250,50);
            jbapply2.setPreferredSize(new Dimension(120,30));
            jsl1.setPreferredSize(dslide);
            jsl2.setPreferredSize(dslide);

            jpmain.add(jp3);
            jp3.add(jlhr);
            jp3.add(jsl1);
            jp3.add(jllr);
            jp3.add(jsl2);
            jp3.add(jbapply2);
            jbapply2.addActionListener(new TemperatureScheduleActionListen());
    }
    public void setCurtainsch(){
        String from, to, direction;
        from = jtf1.getText();
        to = jtf2.getText();
        this.Curtainsch ="From: "+ from+ ",to: " + to;
    }
    public String getCurtainsch(){
        return  this.Curtainsch;
    }

    public void setTempsch(){
        int High,Low;
        High = jsl1.getValue();
        Low = jsl2.getValue();
        this.Tempsch = "Higher than " + High + " to roll down\n" + "Lower than " + Low + " to roll up";
    }

    public String getTempsch(){
        return this.Tempsch;
    }


    class ButtonActionListen implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           if (e.getSource() == jbup) {
                jbup.setEnabled(false);
                jbdown.setEnabled(true);
                jbstop.setEnabled(true);
                System.out.println("The curtains are rolling up!");

            }
            else if (e.getSource() == jbdown) {
                jbdown.setEnabled(false);
                jbup.setEnabled(true);
                jbstop.setEnabled(true);
                System.out.println("The curtains are rolling down!");
            }
            else if (e.getSource()== jbstop){
                jbup.setEnabled(true);
                jbdown.setEnabled(true);
                jbstop.setEnabled(false);
                System.out.println("The curtains have stopped rolling!");
            }
        }
    }

    class CurtainScheduleActionListen implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        if (!jcbup.isSelected() && !jcbdwn.isSelected()){
            JOptionPane.showMessageDialog(null, "Pick the direction of the curtain"); }

        else if(jtf1.getText().isEmpty()|jtf2.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Enter when the timer should start and stop");
        }
        else if (jcbup.isSelected()){
            System.out.println("Curtain goes UP. From: " + jtf1.getText() + ", To: " + jtf2.getText());
        }
        else {
            System.out.println("Curtain goes DOWN. From: " + jtf1.getText() + ", To: " + jtf2.getText());
        }
    }}

    class TemperatureScheduleActionListen implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Higher than " + jsl1.getValue() + " to roll down\n" + "Lower than " + jsl2.getValue() + " to roll up");
        }
    }

    public static void main(String[]args)
    {
    	CurtainGui test=new CurtainGui();
    }
}