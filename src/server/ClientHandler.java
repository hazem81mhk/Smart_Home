package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler {
	private Socket socket;

	public ClientHandler(Socket socket) throws ClassNotFoundException, IOException
	{	System.out.println("ClientHandler2 IS HERE");
		this.socket=socket;
		run();
	}
	
	public void run() throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
		
			Object o=ois.readObject();
			System.out.println("This is server2: we got a msg");
			
				Command c=(Command)o;
				System.out.println("WE ACTUALLY GOT A NEW COMMAND:"+c.getCommand());

	}
}
