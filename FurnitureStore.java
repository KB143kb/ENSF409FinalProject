package edu.ucalgary.ensf409;

import java.sql.*;

public class FurnitureStore 
{
	private String DBURL;
	private String USERNAME;
	private String PASSWORD;
	private Connection inventoryConnection;
	private ResultSet chairInventory;
	private ResultSet deskInventory;
	private ResultSet filingInventory;
	private ResultSet lampInventory;
	private ResultSet manufacturerList;
	
	public FurnitureStore () {}
	
	public FurnitureStore (String DBURL, String USERNAME, String PASSWORD)
	{
		this.DBURL = DBURL;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
	}
	
	public void createConnection ()
	{
		try
		{
			inventoryConnection = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "Kruti#123");
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	//type, category
	public String selectChairs()
	{
        StringBuffer priceAndManuID = new StringBuffer();
        
        try 
        {                    
            Statement myStmt = inventoryConnection.createStatement();
            chairInventory = myStmt.executeQuery("SELECT * FROM chair");
            
            while (chairInventory.next())
            {
                System.out.println("Print results: " + chairInventory.getString("Price") + ", " + chairInventory.getString("ManuID"));
                
                priceAndManuID.append(chairInventory.getString("Price") + ", " + chairInventory.getString("ManuID"));
                priceAndManuID.append('\n');
            }
            
            myStmt.close();
        } 
        
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        return priceAndManuID.toString();
    }   
	
	public void close() 
	{
		try 
		{
			chairInventory.close();
			inventoryConnection.close();
		}
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	//public ArrayList calculateChairPrice () {}
	
	//public int calculateLowestPrice (String [][] inventoryTable) {}
		
		public static void main (String [] arg)
		{
			String type = arg [0];
			String furnitureCategory = arg [1];
			String numberOfItems = arg [2];
			
			if (furnitureCategory.compareToIgnoreCase("chair,") == 0)
			{
				if (type.compareToIgnoreCase("Kneeling") == 0)
				{
					
				}
				
				else if (type.compareToIgnoreCase("Task") == 0)
				{
					System.out.println("Found it and amount is: " + numberOfItems);
				}
				
				else if (type.compareToIgnoreCase("Mesh") == 0)
				{
					
				}
				
				else if (type.compareToIgnoreCase("Executive") == 0)
				{
					
				}
				
				else if (type.compareToIgnoreCase("Ergonomic") == 0)
				{
					
				}
			}
			
			else if (furnitureCategory.compareToIgnoreCase("desk,") == 0)
			{
				if (type.compareToIgnoreCase("Standing") == 0)
				{
					
				}
				
				else if (type.compareToIgnoreCase("Adjustable") == 0)
				{
					
				}
				
				else if (type.compareToIgnoreCase("Traditional") == 0)
				{
					
				}
			}
			
			else if (furnitureCategory.compareToIgnoreCase("filing,") == 0)
			{
				if (type.compareToIgnoreCase("Small") == 0)
				{
					
				}
				
				else if (type.compareToIgnoreCase("Medium") == 0)
				{
					
				}
				
				else if (type.compareToIgnoreCase("Large") == 0)
				{
					
				}
			}
			
			else if (furnitureCategory.compareToIgnoreCase("lamp,") == 0)
			{
				if (type.compareToIgnoreCase("Desk") == 0)
				{
					
				}
				
				else if (type.compareToIgnoreCase("Study") == 0)
				{
					
				}
				
				else if (type.compareToIgnoreCase("Swing Arm") == 0)
				{
					
				}
			}
			
			/*FurnitureStore myFP = new FurnitureStore ();
			
			myFP.createConnection();
			
			String allChairs = myFP.selectChairs();
			System.out.println("Here is a list of prices and manuID: ");
	        System.out.println(allChairs);
			
			myFP.close();*/
		}
}
