package client.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 11/04/2020
 *
 * @Mohammed Hazem Kudaimi
 */

public class Client {
    public static void main(String[] argv) {

        try {
            InetAddress host = InetAddress.getLocalHost(); //public ip of router
            Socket clientSocket = new Socket(host, 8000);  //Create and connect Socket to the host on port 2000

            ObjectOutputStream bw = new ObjectOutputStream(clientSocket.getOutputStream()); // for outputs
            //ObjectInputStream br = new ObjectInputStream(clientSocket.getInputStream()); // for inputs

            String answer;
            String request = "HelloWorld";
            Object o=request;
            OurObject object=new OurObject("Haszem");
            bw.writeObject(o); //Write to server
            
            System.out.println("Waiting");
            //answer = br.(); //Wait for answer

            //System.out.println("Host = " + host);
            //System.out.println("Echo = " + answer);

            //Shut eveything down
            bw.flush();
            clientSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

