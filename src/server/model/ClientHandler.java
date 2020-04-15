package server.model;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import client.model.Command;

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
    private Socket socket2;
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
       
		int x=0;
		System.out.println(x);
        System.out.println(2);
        try {
        	  inPut = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              //outPut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             
              	String command=inPut.readLine();
              	
              	Command c=new Command(command);
              	System.out.println("Server1.. is sending"+c.getCommand());
              	socket2=new Socket(InetAddress.getLocalHost(),8000);
              	ObjectOutputStream ous=new ObjectOutputStream(socket2.getOutputStream());
              	ous.writeObject(c);
              
        	
        }
        catch(IOException e)
        {
        	System.out.println(e);
        }
      
    }
}