package server;

import java.io.Serializable;

public class Command implements Serializable{
	private String command;
	public Command(String command)
	{
		this.command=command;
	}
	public String getCommand()
	{
		return command;
	}

}
