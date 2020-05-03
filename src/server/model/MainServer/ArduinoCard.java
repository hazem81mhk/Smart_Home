package server.model.MainServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import server.model.ArduinoServer.ArduinoHandler.readMessage;

public class ArduinoCard {
	private Socket socket;
	BufferedReader inPut;
	 BufferedWriter outPut;
	public  ArduinoCard()
	{
		connect();
	}

	private void connect() {
		try {
				socket=new Socket(InetAddress.getLocalHost(),9000);
				inPut = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				outPut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				respond("Cnnected");
				new readMessage().start();
			 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   new readMessage().start();
    }

    public class readMessage extends Thread {
        public void run() {
            while (true) {
                try {
                    String inputMessage = inPut.readLine();
                    if(inputMessage.toLowerCase().contains("up")||inputMessage.toLowerCase().contains("down"))
                    {	sleep(4000);
                    	respond(inputMessage);
                    }
                    if(inputMessage.toLowerCase().contains("temp"))
                    {	
                    	String str=JOptionPane.showInputDialog(null,"HOW HOT IS IT?");
                    	respond("temp:"+str);
                    }
                    
                    if(inputMessage.toLowerCase().contains("stop"))
                    {	sleep(4000);
                    	respond("stoop");
                    }
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
    public void respond(String str) throws IOException
    {
     	outPut.write(str);
        outPut.newLine();
        outPut.flush();
        //System.out.println("JUST SENT SOME"+ str);
    }
    
    public static void main(String[]args)
    {
    	ArduinoCard card=new ArduinoCard();
    }
		
	}
	


