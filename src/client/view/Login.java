package client.view;

import javax.swing.*;
        import javax.swing.border.TitledBorder;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.KeyAdapter;
        import java.awt.event.KeyEvent;

public class Login extends JPanel{
    private int port;
    private String ip;
    private JPanel jpmain = new JPanel();
    private JPanel jpn = new JPanel();
    private JPanel jpnc = new JPanel();
    private JPanel jpc = new JPanel();
    private JPanel jps = new JPanel();
    private JButton jbsign = new JButton("Sign In");
    private JToggleButton jbd = new JToggleButton("Display password");
    private JTextField jtfusr = new JTextField();
    private JTextField jtfip = new JTextField();
    private JTextField jtfport = new JTextField();
    private JPasswordField jpsw = new JPasswordField();
    private JLabel jlusr = new JLabel("         Username: ");
    private JLabel jlpsw = new JLabel("         Password: ");
    private JLabel jls = new JLabel("                          Sign In!");
    private JLabel jlw = new JLabel("   Welcome to SmartHome");
    private JLabel jlip = new JLabel("IP-address:");
    private JLabel jlport = new JLabel("Port:");
    public Login() {
        jpsw.setEchoChar('*');
        jtfusr.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));
        jpsw.setBorder(BorderFactory.createTitledBorder(null, "",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER));
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
        jbd.setPreferredSize(dim);
        jpn.setLayout(new GridLayout(0, 1));
        jpnc.setLayout(new GridLayout(0, 4));
        jpc.setLayout(new GridLayout(2, 2));
        jpn.add(jlw);
        jpn.add(jls);

        jpnc.add(jlip);
        jpnc.add(jtfip);
        jpnc.add(jlport);
        jpnc.add(jtfport);

        jpc.add(jlusr);
        jpc.add(jtfusr);
        jpc.add(jlpsw);
        jpc.add(jpsw);

        Font f = new Font("Ink free", Font.BOLD, 25);
        Font f1 = new Font("Ink free", Font.BOLD, 15);
        jlw.setFont(f);
        jls.setFont(f1);

        jps.add(jbsign);
        jps.add(jbd);
        jf.add(jpmain);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setTitle("SmartHome Application");
        jf.pack();
        jf.setVisible(true);
        jbd.addActionListener(new DisplayActionListener());
        jbsign.addActionListener(new SignupActionListener());

        jtfusr.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    jpsw.requestFocus(true);
                }
            }
        });
    }
    public Login(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    class DisplayActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String btnText = (jbd.getText());
            if (btnText.equals("Display password")) {
                jbd.setText("Hide password");
                jpsw.setEchoChar((char)0);
            }else {
                jbd.setText("Display password");
                jpsw.setEchoChar('*');
            }
        }
    }
    class SignupActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String iptxt = (jtfip.getText());
            String porttxt = (jtfport.getText());
            String usrtxt = (jtfusr.getText());
            String pswtxt = (jpsw.getText());
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
                //Ska bytas till att GUI öppnas <<<
                System.exit(0);
            }}
    }

    public static void main(String[] args) {
        new Login();
    }
}
