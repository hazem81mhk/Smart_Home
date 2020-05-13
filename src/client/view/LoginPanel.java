package client.view;

import client.controller.Controller;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 18/04/2020
 *
 * @Agon Beqa
 */

public class LoginPanel extends JPanel{
    private Controller controller;
    private JTextField jtfusr = new JTextField("Hazem");
    private JTextField jtfip = new JTextField("192.168.1.9");
    private JTextField jtfport = new JTextField("8000");

    private JPasswordField jtfPassWord = new JPasswordField("123");
    private JToggleButton jbd = new JToggleButton("Display password");
    private JFrame jf;

    public LoginPanel(Controller controller) {
        this.controller=controller;
        jf = new JFrame();
        JPanel jpmain = new JPanel();
        jf.add(jpmain);
        jf.setPreferredSize(new Dimension(380, 300));
        JPanel jpn = new JPanel();
        jpn.setPreferredSize(new Dimension(300, 90));
        JPanel jpc = new JPanel();
        jpc.setPreferredSize(new Dimension(300, 60));
        JPanel jps = new JPanel();
        jps.setPreferredSize(new Dimension(300, 60));
        JPanel jpnc = new JPanel();
        jpnc.setPreferredSize(new Dimension(360, 25));

        jtfPassWord.setEchoChar('*');

        jtfusr.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));
        jtfPassWord.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));
        jpnc.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));

        jpmain.add(jpnc, BorderLayout.NORTH);
        jpmain.add(jpn, BorderLayout.NORTH);
        jpmain.add(jpc, BorderLayout.CENTER);
        jpmain.add(jps, BorderLayout.SOUTH);

        Dimension dim = new Dimension(140, 35);
        JButton jbsign = new JButton("Sign In");
        jbsign.setPreferredSize(dim);
        jbd.setPreferredSize(dim);
        jpn.setLayout(new GridLayout(0, 1));
        jpnc.setLayout(new GridLayout(0, 4));

        jtfusr.setPreferredSize(new Dimension(150,25));
        jtfPassWord.setPreferredSize(new Dimension(150,25));

        JLabel jlw = new JLabel("   Welcome to SmartHome");
        jpn.add(jlw);
        JLabel jls = new JLabel("             Sign In!");
        jpn.add(jls);
        JLabel jlip = new JLabel("  IP-address:");
        jpnc.add(jlip);
        jpnc.add(jtfip);
        JLabel jlport = new JLabel("  Port:");
        jpnc.add(jlport);
        jpnc.add(jtfport);
        JLabel jlusr = new JLabel("Username:        ");
        jpc.add(jlusr, BorderLayout.WEST);
        jpc.add(jtfusr, BorderLayout.EAST);
        JLabel jlpsw = new JLabel("Password:         ");
        jpc.add(jlpsw,BorderLayout.WEST);
        jpc.add(jtfPassWord,BorderLayout.EAST);

        jlw.setFont(new Font("Ink free", Font.BOLD, 25));
        jls.setFont(new Font("Ink free", Font.BOLD, 25));

        jps.add(jbsign);
        jps.add(jbd);
        jf.add(jpmain);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocation(430, 150);
        jf.setTitle("SmartHome Application");
        jf.pack();
        jf.setVisible(true);
        jbd.addActionListener(new DisplayActionListener());
        jbsign.addActionListener(new SignupActionListener());

        jtfusr.addKeyListener(new KeyAdapter() {

         public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         jtfPassWord.requestFocus(true);
         }
         }
         });
    }

    public String getIp(){
        return jtfip.getText();
    }

    public int getPort(){
        return Integer.parseInt(jtfport.getText());
    }

    public String getUserName(){
        return jtfusr.getText();
    }

    public void setFramevisiblity(boolean b) {
        jf.setVisible(b);
    }

    class DisplayActionListener implements ActionListener {
     public void actionPerformed(ActionEvent e) {
     String btnText = (jbd.getText());
     if (btnText.equals("Display password")) {
     jbd.setText("Hide password");
     jtfPassWord.setEchoChar((char)0);
     }else {
     jbd.setText("Display password");
     jtfPassWord.setEchoChar('*'); } }
     }

    class SignupActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String iptxt = (jtfip.getText());
            String porttxt = (jtfport.getText());
            String usrtxt = (jtfusr.getText());
            String pswtxt = (jtfPassWord.getText());
            if (iptxt.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter IP Address!");
            }
            else if (porttxt.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter Port!");
            }
            else if (usrtxt.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter Username!");
            }
              else if (pswtxt.isEmpty()){
                JOptionPane.showMessageDialog(null,"You forgot to enter your password!");
            }
            else{
                controller.buttonPressed(ButtonType.login);
            }}
    }
}