package arduinoServer.model;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import server.Command;

import javax.swing.*;

/**
 * 05/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class ArduinoHandler extends Thread {
    private Socket socket;
    private Socket socket2;
    private BufferedReader inPut;
    private BufferedWriter outPut;

    public ArduinoHandler(Socket socket) throws IOException {
        this.socket = socket;
        inPut = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outPut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String str= String.format("Client is connected. Client IP address is: %s. Client port is: %s."
                ,socket.getInetAddress() ,socket.getPort());
        System.out.println(str);
        new writeMessage().start();
        new sendMessage().start();


        //Mohammed
                String command = inPut.readLine();
                Command c = new Command(command);
                System.out.println("Server1.. is sending" + c.getCommand());
                socket2 = new Socket(InetAddress.getLocalHost(), 8000);
                ObjectOutputStream ous = new ObjectOutputStream(socket2.getOutputStream());
                ous.writeObject(c);

    }

    public class writeMessage extends Thread {
        public void run() {
            while (true) {
                try {
                    String inputMessage = inPut.readLine();
                    System.out.println("Received message: " + inputMessage);
                } catch (Exception e) {
                    try {
                        socket.close();
                    } catch (Exception e2) {
                    }
                }
            }
        }
    }

    public class sendMessage extends Thread {
        public String []outPutMessage={"lamp1_on","lamp1_off","lamp2_on","lamp2_off"};
        public void run() {
            try {
                while (true) {
                    //String outPutMessage = null;
                    //outPutMessage = JOptionPane.showInputDialog(null, "Enter your text");
                    //if (outPutMessage != null) {
                    for (int i = 0; i <outPutMessage.length ; i++) {
                        outPut.write(outPutMessage[i]);
                        outPut.newLine();
                        outPut.flush();
                        System.out.println("Send message: " + outPutMessage[i]);
                        sleep(9000);
                        //}
                    }
                }
            } catch (IOException | InterruptedException e) {
                try {
                    socket.close();
                } catch (Exception e2) {
                }
            }
            System.out.println("Klient nerkopplad");
        }
    }
}
