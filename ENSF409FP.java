package edu.ucalgary.ensf409;

import java.sql.*;

public class ENSF409FP
{
	private Connection dbConnect;
	private ResultSet results;
	
	public ENSF409FP () {}
	
	public void createConnection ()
	{
		try
		{
			dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "ENSF409", "ensf409");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		ENSF409FP myFP = new ENSF409FP ();
		
		myFP.createConnection();
		
	}

}
