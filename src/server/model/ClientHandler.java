package server.model;

import java.io.*;
import java.net.Socket;

/**
 * 05/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */


public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader inPut;
    private BufferedWriter outPut;
    private ObjectInputStream ois;
    private ObjectOutputStream ous;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        try {
        	ois = new ObjectInputStream((socket.getInputStream()));
            System.out.println("We got an object");
            //Object str = ois.readObject();
            int x=0;
            System.out.println(x);
           
            	Object o=ois.readObject();
                System.out.println((String)o);
            
            //System.out.println("this class is:" + str.getClass());
            //ous = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Erorr");
        }
        System.out.println(2);
        
        inPut = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outPut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
       
        	System.out.print(inPut.read());
        
        
        System.out.println(3);

        String str= String.format("Client is connected. Client IP address is: %s. Client port is: %s."
                ,socket.getInetAddress() ,socket.getPort());
        System.out.println(str);
        new writeMessage().start();
        new sendMessage().start();

    }

    public class writeMessage extends Thread {
        public void run() {
            while (true) {
                try {
                    // if(ois.readObject()==null) {
                    String inputMessage = inPut.readLine();
                    //System.out.println("Received message: " + inputMessage);
                    // }
                    //else
                    //{
                    //   Object object=ois.read();
                    //}
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
                        sleep(6000);
                        outPut.write(outPutMessage[i]);
                        outPut.newLine();
                        outPut.flush();
                        //System.out.println("Send message: " + outPutMessage[i]);
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