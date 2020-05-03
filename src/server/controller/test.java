package server.controller;

import javax.swing.JOptionPane;

public class test {
	public static void main(String[]args)
	{
		int x=JOptionPane.showConfirmDialog(null, "chose", "ERROR ", JOptionPane.YES_NO_OPTION);
		if(x==JOptionPane.YES_OPTION)
		{
			System.out.println("YES");
		}
		else
		{
			System.out.println("NO");
		}
		
	}

}
