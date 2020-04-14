package client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler1 {
	private Socket socket;

	public ClientHandler1(Socket socket) throws ClassNotFoundException, IOException
	{
		this.socket=socket;
		run();
	}
	
	public void run() throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
		
			Object o=ois.readObject();
			System.out.println(o.getClass());
		
		


		
	}
}
