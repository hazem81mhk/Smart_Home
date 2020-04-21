package client.view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JPanel{
    private String port1;
    private String ip1;
    private String name1;

    private JPanel jpmain = new JPanel();
    private JPanel jpn = new JPanel();
    private JPanel jpnc = new JPanel();
    private JPanel jpc = new JPanel();
    private JPanel jps = new JPanel();
    private JButton jbsign = new JButton("Sign In");
    //private JToggleButton jbd = new JToggleButton("Display password");
    private JTextField jtfusr = new JTextField();
    private JTextField jtfip = new JTextField();
    private JTextField jtfport = new JTextField();
    //private JPasswordField jpsw = new JPasswordField();
    private JLabel jlusr = new JLabel("Username:      ");
    //private JLabel jlpsw = new JLabel("         Password: ");
    private JLabel jls = new JLabel("                          Sign In!");
    private JLabel jlw = new JLabel("   Welcome to SmartHome");
    private JLabel jlip = new JLabel("IP-address:");
    private JLabel jlport = new JLabel("Port:");
    public Login() {
        //jpsw.setEchoChar('*');
        jtfusr.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));
        //jpsw.setBorder(BorderFactory.createTitledBorder(null, "",
        //TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));
        JFrame jf = new JFrame();
        jf.add(jpmain);
        jf.setPreferredSize(new Dimension(380, 300));
        jpn.setPreferredSize(new Dimension(300, 90));
        jpc.setPreferredSize(new Dimension(300, 60));
        jps.setPreferredSize(new Dimension(300, 60));
        jpnc.setPreferredSize(new Dimension(360, 25));

        jpnc.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));
        jpmain.add(jpnc, BorderLayout.NORTH);
        jpmain.add(jpn, BorderLayout.NORTH);
        jpmain.add(jpc, BorderLayout.CENTER);
        jpmain.add(jps, BorderLayout.SOUTH);

        Dimension dim = new Dimension(150, 35);
        Dimension dim1 = new Dimension(130, 35);
        jbsign.setPreferredSize(dim1);
        //jbd.setPreferredSize(dim);
        jpn.setLayout(new GridLayout(0, 1));
        jpnc.setLayout(new GridLayout(0, 4));

        jtfusr.setPreferredSize(new Dimension(150,25));

        jpn.add(jlw);
        jpn.add(jls);

        jpnc.add(jlip);
        jpnc.add(jtfip);
        jpnc.add(jlport);
        jpnc.add(jtfport);

        jpc.add(jlusr, BorderLayout.WEST);
        jpc.add(jtfusr, BorderLayout.EAST);
        //jpc.add(jlpsw);
        //jpc.add(jpsw);

        jlw.setFont(new Font("Ink free", Font.BOLD, 25));
        jls.setFont(new Font("Ink free", Font.BOLD, 15));

        jps.add(jbsign);
        //jps.add(jbd);
        jf.add(jpmain);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setTitle("SmartHome Application");
        jf.pack();
        jf.setVisible(true);
        //jbd.addActionListener(new DisplayActionListener());
        jbsign.addActionListener(new SignupActionListener());

        /**jtfusr.addKeyListener(new KeyAdapter() {

         public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         jpsw.requestFocus(true);
         }
         }
         });**/
    }

    public void setIp1(String ip1){
        this.ip1 = jtfip.getText();
    }
    public String getIp1(){
        return ip1;
    }
    public void setPort1(){
        this.port1 = jtfport.getText();
    }
    public String getPort1(){
        return port1;
    }
    public void setName1(){
        this.name1 = jtfusr.getText();
    }
    public String getName1(){
        return name1;
    }

    /**class DisplayActionListener implements ActionListener {
     public void actionPerformed(ActionEvent e) {
     String btnText = (jbd.getText());
     if (btnText.equals("Display password")) {
     jbd.setText("Hide password");
     jpsw.setEchoChar((char)0);
     }else {
     jbd.setText("Display password");
     jpsw.setEchoChar('*'); } }
     }**/
    class SignupActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String iptxt = (jtfip.getText());
            String porttxt = (jtfport.getText());
            String usrtxt = (jtfusr.getText());
            //    String pswtxt = (jpsw.getText());
            if (iptxt.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter IP Address!");
            }
            else if (porttxt.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter Port!");
            }
            else if (usrtxt.isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter Username!");
            }
            //  else if (pswtxt.isEmpty()){
            //    JOptionPane.showMessageDialog(null,"You forgot to enter your password!");
            //}
            else{
                //Ska bytas till att GUI öppnas <<<
                System.out.println();
            }}
    }
    public static void main(String[] args) {
        new Login();
    }
}