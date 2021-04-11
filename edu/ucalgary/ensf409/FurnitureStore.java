/*
 *	To compile: javac -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/FurnitureStore.java
 *	To run: java -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/FurnitureStore 
 */


package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class FurnitureStore {
	public String DBURL;
	public String USERNAME;
	public String PASSWORD;
	private Connection inventoryConnection;
	/*
	private ResultSet chairInventory;
	private ResultSet deskInventory;
	private ResultSet filingInventory;
	private ResultSet lampInventory;
	private ResultSet manufacturerList;
	*/
	
	//0 argument constructor
	public FurnitureStore(){}

	//constructory that accepts the inputs to create the database connection
	public FurnitureStore (String DBURL, String USERNAME, String PASSWORD){
		this.DBURL = DBURL;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
	}
	
	//returns DBURL
	public String getDBURL () 
	{
		return this.DBURL;
	}
	
	//returns USERNAME
	public String getUSERNAME () 
	{
		return this.USERNAME;
	}
	
	//returns PASSWORD
	public String getPASSWORD () 
	{
		return this.PASSWORD;
	}
	
	//returns inventoryCooncetion 
	public Connection getInventoryConnection () 
	{
		return this.inventoryConnection;
	}
	
	//initializes the connection the inventory database
	public void createConnection (){
		try{
			inventoryConnection = DriverManager.getConnection(this.DBURL, this.USERNAME, this.PASSWORD);
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
	}

	//parses the chairs table from the inventory data base and computes the lowest price of the requested furniture
	public ArrayList<Chair> calculateChairPrice(String furnitureType, int quantity) /*throws FurnitureNotAvailableException*/{
		ArrayList<Chair> chairCombinations = new ArrayList<Chair>();
		ResultSet chairInventory;
		try{
			Statement myStatement = inventoryConnection.createStatement();
			chairInventory = myStatement.executeQuery("SELECT * FROM chair");

			//adds a new chair object to the chair combinations arraylist
			while(chairInventory.next()){
				if(chairInventory.getString("Type").compareToIgnoreCase(furnitureType) == 0){
					chairCombinations.add(new Chair(chairInventory.getString("ID"), chairInventory.getString("Type"), chairInventory.getString("Legs").charAt(0),
											chairInventory.getString("Arms").charAt(0), chairInventory.getString("Seat").charAt(0), chairInventory.getString("Cushion").charAt(0),
											chairInventory.getInt("Price"), chairInventory.getString("ManuID")));
				}
			}
			
			if(chairCombinations.size() == 0){
				System.out.println("The furniture type you want to order is not available");
				//throw new FurnitureNotAvailableException();
			}

			myStatement.close();
			chairInventory.close();
		}
		
		catch(SQLException e){
			e.printStackTrace();
		}

		return chairCombinations;
	}

	public ArrayList<Desk> calculateDeskPrice(String furnitureType, int quantity){
		ArrayList<Desk> deskCombinations = new ArrayList<Desk>();
		ResultSet deskInventory;
		try{
			Statement myStatement = inventoryConnection.createStatement();
			deskInventory = myStatement.executeQuery("SELECT * FROM desk");

			//adds a new chair object to the chair combinations arraylist
			while(deskInventory.next()){
				if(deskInventory.getString("Type").compareToIgnoreCase(furnitureType) == 0){
					deskCombinations.add(new Desk(deskInventory.getString("ID"), deskInventory.getString("Type"), deskInventory.getString("Legs").charAt(0),
												  deskInventory.getString("Top").charAt(0), deskInventory.getString("Drawer").charAt(0),
												  deskInventory.getInt("Price"), deskInventory.getString("ManuID")));
				}
			}
			if(deskCombinations.size() == 0){
				System.out.println("The furniture type you want to order is not available");
			}
			myStatement.close();
			deskInventory.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return deskCombinations;
	}

	public ArrayList<Filing> calculateFilingPrice(String furnitureType, int quantity){
		ArrayList<Filing> filingCombinations = new ArrayList<Filing>();
		ResultSet filingInventory;
		try{
			Statement myStatement = inventoryConnection.createStatement();
			filingInventory = myStatement.executeQuery("SELECT * FROM filing");

			//adds a new chair object to the chair combinations arraylist
			while(filingInventory.next()){
				if(filingInventory.getString("Type").compareToIgnoreCase(furnitureType) == 0){
					filingCombinations.add(new Filing(filingInventory.getString("ID"), filingInventory.getString("Type"), filingInventory.getString("Rails").charAt(0),
												  filingInventory.getString("Drawers").charAt(0), filingInventory.getString("Cabinet").charAt(0),
												  filingInventory.getInt("Price"), filingInventory.getString("ManuID")));
				}
			}
			if(filingCombinations.size() == 0){
				System.out.println("The furniture type you want to order is not available");
			}
			myStatement.close();
			filingInventory.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return filingCombinations;
	}

	public ArrayList<Lamp> calculateLampPrice(String furnitureType, int quantity){
		ArrayList<Lamp> lampCombinations = new ArrayList<Lamp>();
		ResultSet lampInventory;
		try{
			Statement myStatement = inventoryConnection.createStatement();
			lampInventory = myStatement.executeQuery("SELECT * FROM lamp");

			//adds a new chair object to the chair combinations arraylist
			while(lampInventory.next()){
				if(lampInventory.getString("Type").compareToIgnoreCase(furnitureType) == 0){
					lampCombinations.add(new Lamp(lampInventory.getString("ID"), lampInventory.getString("Type"), lampInventory.getString("Base").charAt(0),
												  lampInventory.getString("Bulb").charAt(0), lampInventory.getInt("Price"), lampInventory.getString("ManuID")));
				}
			}
			if(lampCombinations.size() == 0){
				System.out.println("The furniture type you want to order is not available");
			}
			myStatement.close();
			lampInventory.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return lampCombinations;
	}

	public void createOrderForm(String orderRequest, ArrayList<String> itemID, int totalPrice){
		FileWriter orderForm = null;
		String outputFileName = "orderform.txt";

		try{
			//open an output stream for the orderform
			orderForm = new FileWriter(outputFileName);
			//User information, left blank for now
			orderForm.write("Furniture Order Form\n\n");
			orderForm.write("Faculty Name: \n");
			orderForm.write("Contact: \n");
			orderForm.write("Date: \n\n");
			orderForm.write("Original Request: " + orderRequest + "\n\n");
			
			//write the item id numbers to the orderform
			orderForm.write("Items Ordered\n");
			for(String temp : itemID){
				orderForm.write("ID: " + temp + "\n");
			}

			//write total price to the order form
			orderForm.write("\nTotal Price: $" + totalPrice + "\n");
		}
		catch (IOException e) {
			System.out.println("I/O exception when trying to write to " + outputFileName);
			e.printStackTrace();
		}
		finally{
			if(orderForm != null){
				try{
					orderForm.close();
				}
				catch(IOException e){
					System.out.println("Couldn't close file " + outputFileName);
					e.printStackTrace();
				}
			}
		}

	}
	
	//used for closing the connection to the inventory database
	public void close(){
		try{
			inventoryConnection.close();
		}
		
		catch(SQLException e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean readingUser = true;
		String dbUsername = "root";
		String dbPassword = "Kruti#123";
		String dbURL = "jdbc:mysql://localhost/inventory";
		String userRequest = "";

		System.out.println("Welcome to the UCalgary Supply Chain Management System");

		//Prompt user for the inputs to the database
		System.out.println("The current database settings are: " );
		System.out.println("MySQL URL: " + dbURL);
		System.out.println("Username: " + dbUsername);
		System.out.println("Password: " + dbPassword);

		System.out.print("Would you like to change these settings? (Enter yes to input new MySQL settings or no to use the current one) ");
		userRequest = scanner.nextLine().trim();
		
		if(userRequest.compareToIgnoreCase("yes") == 0){
			System.out.print("Enter the URL for the inventory database: ");
			try{
				dbURL = scanner.nextLine().trim();
			}
			catch(InputMismatchException e){
				System.out.println(e.getMessage());
			}
			System.out.print("Enter the username for the inventory database: ");
			try{
				dbUsername = scanner.nextLine().trim();
			}
			catch(InputMismatchException e){
				System.out.println(e.getMessage());
			}
			System.out.print("Enter the password for the inventory database: ");
			try{
				dbPassword = scanner.nextLine().trim();
			}
			catch(InputMismatchException e){
				System.out.println(e.getMessage());
			}
		}
		
		FurnitureStore schoolFurnitureStore = new FurnitureStore(dbURL, dbUsername, dbPassword);
		schoolFurnitureStore.createConnection();
		
		while(readingUser){
			System.out.print("Would you like to order an item? (Enter yes to order an item or enter no to exit the program) ");
			try{
				userRequest = scanner.nextLine().trim();
			}
			catch(InputMismatchException e){
				System.out.println(e.getMessage());
			}
			
			if(userRequest.compareToIgnoreCase("no") == 0){
				readingUser = false;
				scanner.close();
				schoolFurnitureStore.close();
			}
			else if(userRequest.compareToIgnoreCase("yes") == 0){
				String[] userFurnitureRequest = new String[2];
				int userQuantity = 0;

				System.out.print("Please enter the furniture type: ");
				try{
					userFurnitureRequest[0] = scanner.nextLine().trim();
				}
				catch(InputMismatchException e){
					System.out.println(e.getMessage());
				}
				
				System.out.print("Please enter the furniture category: ");
				try{
					userFurnitureRequest[1] = scanner.nextLine().trim();
				}
				catch(InputMismatchException e){
					System.out.println(e.getMessage());
				}

				System.out.print("Please enter the quantity: ");
				try{
					userQuantity = Integer.parseInt(scanner.nextLine().trim());
				}
				catch(InputMismatchException e){
					System.out.println(e.getMessage());
				}

				//used for passing to the createOrderFormMethod
				String furnitureOrdered = userFurnitureRequest[0] + " " + userFurnitureRequest[1] + ", " + Integer.toString(userQuantity);
				ArrayList<String> itemID = new ArrayList<String>();
				int totalPrice = 0;

				//check if the furniture category exists with in the store
				if(userFurnitureRequest[1].compareToIgnoreCase("chair") == 0){
					ArrayList<Chair> testChair = schoolFurnitureStore.calculateChairPrice(userFurnitureRequest[0], userQuantity);
					
					for(int i = 0; i < testChair.size(); i++){
						System.out.println(testChair.get(i).getID());
						itemID.add(testChair.get(i).getID());
						totalPrice += testChair.get(i).getPrice();
					}
					
					//test orderform method
					schoolFurnitureStore.createOrderForm(furnitureOrdered, itemID, totalPrice);
				}
				else if(userFurnitureRequest[1].compareToIgnoreCase("desk") == 0){
					ArrayList<Desk> testDesk = schoolFurnitureStore.calculateDeskPrice(userFurnitureRequest[0], userQuantity);
					
					for(int i = 0; i < testDesk.size(); i++){
						System.out.println(testDesk.get(i).getID());
					}
				}
				else if(userFurnitureRequest[1].compareToIgnoreCase("filing") == 0){
					ArrayList<Filing> testFiling = schoolFurnitureStore.calculateFilingPrice(userFurnitureRequest[0], userQuantity);
					
					for(int i = 0; i < testFiling.size(); i++){
						System.out.println(testFiling.get(i).getID());
					}
				}
				else if(userFurnitureRequest[1].compareToIgnoreCase("lamp") == 0){
					ArrayList<Lamp> testLamp = schoolFurnitureStore.calculateLampPrice(userFurnitureRequest[0], userQuantity);
					
					for(int i = 0; i < testLamp.size(); i++){
						System.out.println(testLamp.get(i).getID());
					}
				}
				else{
					System.out.println("The item you've requested is not in our inventory. Please enter another item.");
				}
			}
			userRequest = "";
		}
	}
}
