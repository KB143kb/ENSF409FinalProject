/*
 *	To compile: javac -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/FurnitureStore.java
 *	To run: java -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/FurnitureStore 
 */


package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
	public ArrayList<Chair> calculateChairPrice(String furnitureType, int quantity){
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
			myStatement.close();
			lampInventory.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		return lampCombinations;
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
		String dbUsername = "nathan";
		String dbPassword = "ensf409";
		String dbURL = "jdbc:mysql://localhost/inventory";
		String userRequest = "";

		//Prompt user for the inputs to the database
		/*
		System.out.print("Enter the URL for the inventory database: ");
		try{
			dbURL = scanner.next();
		}
		catch(InputMismatchException e){
			System.out.println(e.getMessage());
		}
		System.out.print("Enter the username for the inventory database: ");
		try{
			dbUsername = scanner.next();
		}
		catch(InputMismatchException e){
			System.out.println(e.getMessage());
		}
		System.out.print("Enter the password for the inventory database: ");
		try{
			dbPassword = scanner.next();
		}
		catch(InputMismatchException e){
			System.out.println(e.getMessage());
		}
		*/

		FurnitureStore schoolFurnitureStore = new FurnitureStore(dbURL, dbUsername, dbPassword);
		schoolFurnitureStore.createConnection();
		
		System.out.println("Welcome to the UCalgary Supply Chain Management System");

		while(readingUser){
			System.out.println("Would you like to order an item? Enter yes to order an item and the quantity or enter no to exit the program. ");
			try{
				userRequest = scanner.nextLine();
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
					userFurnitureRequest[0] = scanner.nextLine();
				}
				catch(InputMismatchException e){
					System.out.println(e.getMessage());
				}
				
				System.out.print("Please enter the furniture category: ");
				try{
					userFurnitureRequest[1] = scanner.nextLine();
				}
				catch(InputMismatchException e){
					System.out.println(e.getMessage());
				}

				System.out.print("Please enter the quantity: ");
				try{
					userQuantity = Integer.parseInt(scanner.nextLine());
				}
				catch(InputMismatchException e){
					System.out.println(e.getMessage());
				}

				
				
				//check if the furniture category exists with in the store
				if(userFurnitureRequest[1].compareToIgnoreCase("chair") == 0){
					ArrayList<Chair> testChair = schoolFurnitureStore.calculateChairPrice(userFurnitureRequest[0], userQuantity);
					
					for(int i = 0; i < testChair.size(); i++){
						System.out.println(testChair.get(i).getID());
					}
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