/*
 *	To compile: javac -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/FurnitureStore.java
 *	To run: java -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/FurnitureStore 
 *	
 * 	References:
 *		the combination methods were adapted from https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
 *		the sorting calculations were adapted from https://www.javatpoint.com/java-program-to-find-smallest-number-in-an-array
 */


package edu.ucalgary.ensf409;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
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
	*/
	private ArrayList<Manufacturer> manufacturerList = new ArrayList<Manufacturer>();
	
	
	//0 argument constructor
	public FurnitureStore(){}

	//constructory that accepts the inputs to create the database connection
	public FurnitureStore (String DBURL, String USERNAME, String PASSWORD){
		this.DBURL = DBURL;
		this.USERNAME = USERNAME;
		this.PASSWORD = PASSWORD;
	}
	
	//return DBURL
	public String getDBURL () {return this.DBURL;}
		
	//return USERNAME
	public String getUSERNAME () {return this.USERNAME;}
		
	//return PASSWORD
	public String getPASSWORD () {return this.PASSWORD;}
		
	//return inventoryConnection
	public Connection getInventoryConnection () {return this.inventoryConnection;}
	
	//initializes the connection the inventory database
	public void createConnection (){
		try{
			inventoryConnection = DriverManager.getConnection(this.DBURL, this.USERNAME, this.PASSWORD);
			
			//initialize manufacturing resultset
			Statement myStatement = inventoryConnection.createStatement();
			ResultSet manufacturerInventory = myStatement.executeQuery("SELECT * FROM manufacturer");

			while(manufacturerInventory.next()){
				manufacturerList.add(new Manufacturer(manufacturerInventory.getString("ManuID"), manufacturerInventory.getString("Name"), 
													  manufacturerInventory.getString("Phone"), manufacturerInventory.getString("Province")));
			}
			myStatement.close();
			manufacturerInventory.close();
		}
		
		catch (SQLException e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	//parses the chairs table from the inventory data base and computes the lowest price of the requested furniture
	public ArrayList<Chair> calculateChairPrice(String furnitureType, int quantity){
		ArrayList<Chair> orderList = new ArrayList<Chair>();
		ArrayList<Chair> chairList = new ArrayList<Chair>();
		ResultSet chairInventory;
		try{
			Statement myStatement = inventoryConnection.createStatement();
			chairInventory = myStatement.executeQuery("SELECT * FROM chair");

			//adds a new chair object to the chair combinations arraylist
			while(chairInventory.next()){
				if(chairInventory.getString("Type").compareToIgnoreCase(furnitureType) == 0){
					chairList.add(new Chair(chairInventory.getString("ID"), chairInventory.getString("Type"), chairInventory.getString("Legs"),
											chairInventory.getString("Arms"), chairInventory.getString("Seat"), chairInventory.getString("Cushion"),
											chairInventory.getInt("Price"), chairInventory.getString("ManuID")));
				}
			}
			if(chairList.size() == 0){
				System.out.println("The furniture type you want to order is not available");
				myStatement.close();
				chairInventory.close();
				orderList.clear();
				return orderList;
			}
			myStatement.close();
			chairInventory.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		ArrayList<ArrayList<Chair>> totalCombo = new ArrayList<ArrayList<Chair>>();
		int n = chairList.size();
		//find every combination of items in the list of lamp items
		//i <= 2 since we only need 4 components
		for(int i = 1; i <= 4; i++){
			totalCombo.addAll(chairCombinations(chairList, n, i));
		}

		ArrayList<ArrayList<Chair>> componentCombos = new ArrayList<ArrayList<Chair>>();
		//find all the combinations that satisfy all the components
		for(ArrayList<Chair> chairCombo : totalCombo){
			//counters for components are used for checking to see if the combination
			//can satisfy the requested quantity
			boolean legsFound = false;
			int legsCount = 0;
			boolean armsFound = false;
			int armsCount = 0;
			boolean seatFound = false;
			int seatCount = 0;
			boolean cushionFound = false;
			int cushionCount = 0;
			
			//search the current combination for legs
			for(Chair chr : chairCombo){
				if(chr.getLegs().compareToIgnoreCase("Y") == 0){
					legsFound = true;
					legsCount++;
				}
			}
			//search the current combination for arms
			for(Chair chr : chairCombo){
				if(chr.getArms().compareToIgnoreCase("Y") == 0){
					armsFound = true;
					armsCount++;
				}
			}
			//search the current combination for seat
			for(Chair chr : chairCombo){
				if(chr.getSeat().compareToIgnoreCase("Y") == 0){
					seatFound = true;
					seatCount++;
				}
			}
			//search the current combination for cushion
			for(Chair chr : chairCombo){
				if(chr.getCushion().compareToIgnoreCase("Y") == 0){
					cushionFound = true;
					cushionCount++;
				}
			}
			//if both components are found, add to componentCombos
			if(legsFound && armsFound && seatFound && cushionFound && 
			   legsCount >= quantity && armsCount >= quantity && seatCount >= quantity && cushionCount >= quantity){
				componentCombos.add(chairCombo);
			}
		}
		//if combinations with all components were found, sort them to get the
		//lowest price
		if(componentCombos.size() > 0){
			//search for the combinations with the lowest price
			//sort the all the combonations and add the first elements to a list of size quantity
			for(int i = 0; i < componentCombos.size(); i++){
				ArrayList<Chair> chairComboI = componentCombos.get(i);
				int priceI = 0;
				//calculate the price for combination i
				for(Chair chr : chairComboI){
					priceI += chr.getPrice();
				}
				//sort the array by the smallest total price
				for(int j = i + 1; j < componentCombos.size(); j++){
					ArrayList<Chair> chairComboJ = componentCombos.get(j);
					int priceJ = 0;
					//calculate the price for combination j
					for(Chair chr : chairComboJ){
						priceJ += chr.getPrice();
					}
					
					if(priceI > priceJ){
						//swap elements i and j
						Collections.swap(componentCombos, i, j);
					}
				}
			}
			//add the items from the first combination to the orderList
			for(Chair chr : componentCombos.get(0)){
				orderList.add(chr);
			}
		}
		else{
			System.out.println("Your order cannot be fulfilled based on our inventory");
			System.out.println("The suggested manufacturers are: ");

			for(Manufacturer manu : this.manufacturerList){
				System.out.println(manu.getName());
			}
			//return empty orderList
			orderList.clear();
		}
		
		return orderList;
	}
	
	public ArrayList<ArrayList<Chair>> chairCombinations(ArrayList<Chair> chairArray, int n, int r){
		ArrayList<Chair> tempData = new ArrayList<Chair>();
		ArrayList<ArrayList<Chair>> partialChairList = new ArrayList<ArrayList<Chair>>();

		combinationChairUtil(chairArray, tempData, partialChairList, 0, n - 1, 0, r);
		return partialChairList;
	}

	public void combinationChairUtil(ArrayList<Chair> arr, ArrayList<Chair> tempData, ArrayList<ArrayList<Chair>> partialList, int start, int end, int index, int r){
		//current combination is ready to be added
		if(index == r){
			ArrayList<Chair> tempChairArray = new ArrayList<Chair>();
			
			for(int i = 0; i < r; i++){
				tempChairArray.add(tempData.get(i));
			}
			partialList.add(tempChairArray);

			return;
		}
		// replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
		for (int i = start; i <= end && end - i + 1 >= r - index; i++){
            tempData.add(index, arr.get(i));
            //combinationTest.add(data);
            combinationChairUtil(arr, tempData, partialList, i + 1, end, index + 1, r);
        }
	}
	//parses the desk table from the inventory data base and computes the lowest price of the requested furniture
	public ArrayList<Desk> calculateDeskPrice(String furnitureType, int quantity){
		ArrayList<Desk> deskList = new ArrayList<Desk>();
		ArrayList<Desk> orderList = new ArrayList<Desk>();
		ResultSet deskInventory;
		try{
			Statement myStatement = inventoryConnection.createStatement();
			deskInventory = myStatement.executeQuery("SELECT * FROM desk");

			//adds a new chair object to the chair combinations arraylist
			while(deskInventory.next()){
				if(deskInventory.getString("Type").compareToIgnoreCase(furnitureType) == 0){
					deskList.add(new Desk(deskInventory.getString("ID"), deskInventory.getString("Type"), deskInventory.getString("Legs"),
										  deskInventory.getString("Top"), deskInventory.getString("Drawer"), deskInventory.getInt("Price"), deskInventory.getString("ManuID")));
				}
			}
			if(deskList.size() == 0){
				System.out.println("The furniture type you want to order is not available");
				myStatement.close();
				deskInventory.close();
				orderList.clear();
				
				return orderList;
			}
			myStatement.close();
			deskInventory.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		ArrayList<ArrayList<Desk>> totalCombo = new ArrayList<ArrayList<Desk>>();
		int n = deskList.size();
		//find every combination of items in the list of lamp items
		//i <= 2 since we only need 4 components
		for(int i = 1; i <= 4; i++){
			totalCombo.addAll(deskCombinations(deskList, n, i));
		}

		ArrayList<ArrayList<Desk>> componentCombos = new ArrayList<ArrayList<Desk>>();
		//find all the combinations that satisfy all the components
		for(ArrayList<Desk> deskCombo : totalCombo){
			//counters for components are used for checking to see if the combination
			//can satisfy the requested quantity
			boolean legsFound = false;
			int legsCount = 0;
			boolean topFound = false;
			int topCount = 0;
			boolean drawerFound = false;
			int drawerCount = 0;
			
			//search the current combination for legs
			for(Desk dsk : deskCombo){
				if(dsk.getLegs().compareToIgnoreCase("Y") == 0){
					legsFound = true;
					legsCount++;
				}
			}
			//search the current combination for arms
			for(Desk dsk : deskCombo){
				if(dsk.getTop().compareToIgnoreCase("Y") == 0){
					topFound = true;
					topCount++;
				}
			}
			//search the current combination for drawer
			for(Desk dsk : deskCombo){
				if(dsk.getDrawer().compareToIgnoreCase("Y") == 0){
					drawerFound = true;
					drawerCount++;
				}
			}
			//if both components are found, add to componentCombos
			if(legsFound && topFound && drawerFound && legsCount >= quantity && topCount >= quantity && drawerCount >= quantity){
				componentCombos.add(deskCombo);
			}
		}
		//if combinations with all components were found, sort them to get the
		//lowest price
		if(componentCombos.size() > 0){
			//search for the combinations with the lowest price
			//sort the all the combonations and add the first elements to a list of size quantity
			for(int i = 0; i < componentCombos.size(); i++){
				ArrayList<Desk> deskComboI = componentCombos.get(i);
				int priceI = 0;
				//calculate the price for combination i
				for(Desk dsk : deskComboI){
					priceI += dsk.getPrice();
				}
				//sort the array by the smallest total price
				for(int j = i + 1; j < componentCombos.size(); j++){
					ArrayList<Desk> deskComboJ = componentCombos.get(j);
					int priceJ = 0;
					//calculate the price for combination j
					for(Desk dsk : deskComboJ){
						priceJ += dsk.getPrice();
					}
					
					if(priceI > priceJ){
						//swap elements i and j
						Collections.swap(componentCombos, i, j);
					}
				}
			}
			//add the items from the first combination to the orderList
			for(Desk dsk : componentCombos.get(0)){
				orderList.add(dsk);
			}
		}
		else{
			System.out.println("Your order cannot be fulfilled based on our inventory");
			System.out.println("The suggested manufacturers are: ");

			for(Manufacturer manu : this.manufacturerList){
				System.out.println(manu.getName());
			}
			//return empty orderList
			orderList.clear();
		}
		return orderList;
	}

	public ArrayList<ArrayList<Desk>> deskCombinations(ArrayList<Desk> deskArray, int n, int r){
		ArrayList<Desk> tempData = new ArrayList<Desk>();
		ArrayList<ArrayList<Desk>> partialDeskList = new ArrayList<ArrayList<Desk>>();

		combinationDeskUtil(deskArray, tempData, partialDeskList, 0, n - 1, 0, r);
		return partialDeskList;
	}

	public void combinationDeskUtil(ArrayList<Desk> arr, ArrayList<Desk> tempData, ArrayList<ArrayList<Desk>> partialList, int start, int end, int index, int r){
		//current combination is ready to be added
		if(index == r){
			ArrayList<Desk> tempDeskArray = new ArrayList<Desk>();
			
			for(int i = 0; i < r; i++){
				tempDeskArray.add(tempData.get(i));
			}
			partialList.add(tempDeskArray);

			return;
		}
		// replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
		for (int i = start; i <= end && end - i + 1 >= r - index; i++){
            tempData.add(index, arr.get(i));
            //combinationTest.add(data);
            combinationDeskUtil(arr, tempData, partialList, i + 1, end, index + 1, r);
        }
	}
	//parses the filing table from the inventory data base and computes the lowest price of the requested furniture
	public ArrayList<Filing> calculateFilingPrice(String furnitureType, int quantity){
		ArrayList<Filing> filingList = new ArrayList<Filing>();
		ArrayList<Filing> orderList = new ArrayList<Filing>();
		ResultSet filingInventory;
		try{
			Statement myStatement = inventoryConnection.createStatement();
			filingInventory = myStatement.executeQuery("SELECT * FROM filing");

			//adds a new chair object to the chair combinations arraylist
			while(filingInventory.next()){
				if(filingInventory.getString("Type").compareToIgnoreCase(furnitureType) == 0){
					filingList.add(new Filing(filingInventory.getString("ID"), filingInventory.getString("Type"), filingInventory.getString("Rails"),
													  filingInventory.getString("Drawers"), filingInventory.getString("Cabinet"), filingInventory.getInt("Price"), 
													  filingInventory.getString("ManuID")));
				}
			}
			if(filingList.size() == 0){
				System.out.println("The furniture type you want to order is not available");
				myStatement.close();
				filingInventory.close();
				orderList.clear();

				return orderList;
			}
			myStatement.close();
			filingInventory.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		ArrayList<ArrayList<Filing>> totalCombo = new ArrayList<ArrayList<Filing>>();
		int n = filingList.size();
		//find every combination of items in the list of lamp items
		//i <= 2 since we only need 4 components
		for(int i = 1; i <= 4; i++){
			totalCombo.addAll(filingCombinations(filingList, n, i));
		}

		ArrayList<ArrayList<Filing>> componentCombos = new ArrayList<ArrayList<Filing>>();
		//find all the combinations that satisfy all the components
		for(ArrayList<Filing> filingCombo : totalCombo){
			//counters for components are used for checking to see if the combination
			//can satisfy the requested quantity
			boolean railsFound = false;
			int railsCount = 0;
			boolean drawersFound = false;
			int drawersCount = 0;
			boolean cabinetFound = false;
			int cabinetCount = 0;
			
			//search the current combination for rails
			for(Filing flg : filingCombo){
				if(flg.getRails().compareToIgnoreCase("Y") == 0){
					railsFound = true;
					railsCount++;
				}
			}
			//search the current combination for drawers
			for(Filing flg : filingCombo){
				if(flg.getDrawers().compareToIgnoreCase("Y") == 0){
					drawersFound = true;
					drawersCount++;
				}
			}
			//search the current combination for cabinet
			for(Filing flg : filingCombo){
				if(flg.getCabinet().compareToIgnoreCase("Y") == 0){
					cabinetFound = true;
					cabinetCount++;
				}
			}
			//if both components are found, add to componentCombos
			if(railsFound && drawersFound && cabinetFound && railsCount >= quantity && drawersCount >= quantity && cabinetCount >= quantity){
				componentCombos.add(filingCombo);
			}
		}
		//if combinations with all components were found, sort them to get the
		//lowest price
		if(componentCombos.size() > 0){
			//search for the combinations with the lowest price
			//sort the all the combonations and add the first elements to a list of size quantity
			for(int i = 0; i < componentCombos.size(); i++){
				ArrayList<Filing> filingComboI = componentCombos.get(i);
				int priceI = 0;
				//calculate the price for combination i
				for(Filing flg : filingComboI){
					priceI += flg.getPrice();
				}
				//sort the array by the smallest total price
				for(int j = i + 1; j < componentCombos.size(); j++){
					ArrayList<Filing> filingComboJ = componentCombos.get(j);
					int priceJ = 0;
					//calculate the price for combination j
					for(Filing flg : filingComboJ){
						priceJ += flg.getPrice();
					}
					
					if(priceI > priceJ){
						//swap elements i and j
						Collections.swap(componentCombos, i, j);
					}
				}
			}
			//add the items from the first combination to the orderList
			for(Filing dsk : componentCombos.get(0)){
				orderList.add(dsk);
			}
		}
		else{
			System.out.println("Your order cannot be fulfilled based on our inventory");
			System.out.println("The suggested manufacturers are: ");

			for(Manufacturer manu : this.manufacturerList){
				System.out.println(manu.getName());
			}
			//return empty orderList
			orderList.clear();
		}

		return orderList;
	}

	public ArrayList<ArrayList<Filing>> filingCombinations(ArrayList<Filing> lampArray, int n, int r){
		ArrayList<Filing> tempData = new ArrayList<Filing>();
		ArrayList<ArrayList<Filing>> partialFilingList = new ArrayList<ArrayList<Filing>>();

		combinationFilingUtil(lampArray, tempData, partialFilingList, 0, n - 1, 0, r);
		return partialFilingList;
	}

	public void combinationFilingUtil(ArrayList<Filing> arr, ArrayList<Filing> tempData, ArrayList<ArrayList<Filing>> partialList, int start, int end, int index, int r){
		//current combination is ready to be added
		if(index == r){
			ArrayList<Filing> tempFilingArray = new ArrayList<Filing>();
			
			for(int i = 0; i < r; i++){
				tempFilingArray.add(tempData.get(i));
			}
			partialList.add(tempFilingArray);

			return;
		}
		// replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
		for (int i = start; i <= end && end - i + 1 >= r - index; i++){
            tempData.add(index, arr.get(i));
            //combinationTest.add(data);
            combinationFilingUtil(arr, tempData, partialList, i + 1, end, index + 1, r);
        }
	}	
	//parses the lamp table from the inventory data base and computes the lowest price of the requested furniture
	public ArrayList<Lamp> calculateLampPrice(String furnitureType, int quantity){
		ArrayList<Lamp> orderList = new ArrayList<Lamp>();
		ArrayList<Lamp> lampsList = new ArrayList<Lamp>();
		ResultSet lampInventory;
		try{
			Statement myStatement = inventoryConnection.createStatement();
			lampInventory = myStatement.executeQuery("SELECT * FROM lamp");

			//adds a new chair object to the chair combinations arraylist
			while(lampInventory.next()){
				if(lampInventory.getString("Type").compareToIgnoreCase(furnitureType) == 0){
					lampsList.add(new Lamp(lampInventory.getString("ID"), lampInventory.getString("Type"), lampInventory.getString("Base"),
										   lampInventory.getString("Bulb"), lampInventory.getInt("Price"), lampInventory.getString("ManuID")));
				}
			}
			if(lampsList.size() == 0){
				System.out.println("The furniture type you want to order is not available");
				myStatement.close();
				lampInventory.close();
				orderList.clear();
				return orderList;
			}
			myStatement.close();
			lampInventory.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		ArrayList<ArrayList<Lamp>> totalCombo = new ArrayList<ArrayList<Lamp>>();
		int n = lampsList.size();
		//find every combination of items in the list of lamp items
		for(int i = 1; i <= n; i++){
			totalCombo.addAll(lampCombinations(lampsList, n, i));
		}

		ArrayList<ArrayList<Lamp>> componentCombos = new ArrayList<ArrayList<Lamp>>();
		//find all the combinations that satisfy all the components
		for(ArrayList<Lamp> lampCombo : totalCombo){
			//counters for components are used for checking to see if the combination
			//can satisfy the requested quantity
			boolean baseFound = false;
			int baseCount = 0;
			boolean bulbFound = false;
			int bulbCount = 0;
			//search the current combination for a base
			for(Lamp lmp : lampCombo){
				if(lmp.getBase().compareToIgnoreCase("Y") == 0){
					baseFound = true;
					baseCount++;
				}
			}
			//search the current combination for a bulb
			for(Lamp lmp : lampCombo){
				if(lmp.getBulb().compareToIgnoreCase("Y") == 0){
					bulbFound = true;
					bulbCount++;
				}
			}
			//if both components are found, add to componentCombos
			if(baseFound && bulbFound && baseCount >= quantity && bulbCount >= quantity){
				componentCombos.add(lampCombo);
			}
		}
		//if combinations with all components were found sort them to get the
		//lowest price
		if(componentCombos.size() > 0){
			//search for the combinations with the lowest price
			//sort the all the combonations and add the first elements to a list of size quantity
			for(int i = 0; i < componentCombos.size(); i++){
				ArrayList<Lamp> lampComboI = componentCombos.get(i);
				int priceI = 0;
				//calculate the price for combination i
				for(Lamp lmp : lampComboI){
					priceI += lmp.getPrice();
				}
				//sort the array by the smallest total price
				for(int j = i + 1; j < componentCombos.size(); j++){
					ArrayList<Lamp> lampComboJ = componentCombos.get(j);
					int priceJ = 0;
					//calculate the price for combination i
					for(Lamp lmp : lampComboJ){
						priceJ += lmp.getPrice();
					}
					
					if(priceI > priceJ){
						//swap elements i and j
						Collections.swap(componentCombos, i, j);
					}
				}
			}
			//add the items from the first combination to the orderList
			for(Lamp lmp : componentCombos.get(0)){
				orderList.add(lmp);
			}
		}
		else{
			System.out.println("Your order cannot be fulfilled based on our inventory");
			System.out.println("The suggested manufacturers are: ");

			for(Manufacturer manu : this.manufacturerList){
				System.out.println(manu.getName());
			}
			//return empty orderList
			orderList.clear();
		}
		return orderList;
	}

	public ArrayList<ArrayList<Lamp>> lampCombinations(ArrayList<Lamp> lampArray, int n, int r){
		ArrayList<Lamp> tempData = new ArrayList<Lamp>();
		ArrayList<ArrayList<Lamp>> partialLampList = new ArrayList<ArrayList<Lamp>>();

		combinationLampUtil(lampArray, tempData, partialLampList, 0, n - 1, 0, r);
		return partialLampList;
	}

	public void combinationLampUtil(ArrayList<Lamp> arr, ArrayList<Lamp> tempData, ArrayList<ArrayList<Lamp>> partialList, int start, int end, int index, int r){
		//current combination is ready to be added
		if(index == r){
			ArrayList<Lamp> tempLampArray = new ArrayList<>();
			
			for(int i = 0; i < r; i++){
				tempLampArray.add(tempData.get(i));
			}
			partialList.add(tempLampArray);

			return;
		}
		// replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
		for (int i = start; i <= end && end - i + 1 >= r - index; i++){
            tempData.add(index, arr.get(i));
            //combinationTest.add(data);
            combinationLampUtil(arr, tempData, partialList, i + 1, end, index + 1, r);
        }
	}	

	public void deleteItem(ArrayList<String> itemID, String furnitureCategory){
		try{
			String query = "DELETE FROM " + furnitureCategory.toLowerCase() + " WHERE ID = ?";
			PreparedStatement myStatement = inventoryConnection.prepareStatement(query);
			for(String id : itemID){
				myStatement.setString(1, id);
				myStatement.executeUpdate();
			}
			myStatement.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
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
					System.out.println("The order Form is ready in the project directory");
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
		String dbUsername = "nathan";
		String dbPassword = "ensf409";
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
					ArrayList<Chair> chairResult = schoolFurnitureStore.calculateChairPrice(userFurnitureRequest[0], userQuantity);
					itemID.clear();	//clears the id list from its last use
					
					//check to see if the calculatePrice found combinations
					if(chairResult.size() > 0){
						for(int i = 0; i < chairResult.size(); i++){
							itemID.add(chairResult.get(i).getID());
							totalPrice += chairResult.get(i).getPrice();
						}
						//call orderform method to create the order form
						schoolFurnitureStore.createOrderForm(furnitureOrdered, itemID, totalPrice);
						schoolFurnitureStore.deleteItem(itemID, userFurnitureRequest[1]);
					}
				}
				else if(userFurnitureRequest[1].compareToIgnoreCase("desk") == 0){
					ArrayList<Desk> deskResult = schoolFurnitureStore.calculateDeskPrice(userFurnitureRequest[0], userQuantity);
					itemID.clear();	//clears the id list from its last use
				
					//check to see if the calculatePrice found combinations
					if(deskResult.size() > 0){
						for(int i = 0; i < deskResult.size(); i++){
							itemID.add(deskResult.get(i).getID());
							totalPrice += deskResult.get(i).getPrice();
						}
						//call orderform method to create the order form
						schoolFurnitureStore.createOrderForm(furnitureOrdered, itemID, totalPrice);
						schoolFurnitureStore.deleteItem(itemID, userFurnitureRequest[1]);
					}
				}
				else if(userFurnitureRequest[1].compareToIgnoreCase("filing") == 0){
					ArrayList<Filing> filingResult = schoolFurnitureStore.calculateFilingPrice(userFurnitureRequest[0], userQuantity);
					itemID.clear();	//clears the id list from its last use

					//check to see if the calculatePrice found combinations
					if(filingResult.size() > 0){
						for(int i = 0; i < filingResult.size(); i++){
							itemID.add(filingResult.get(i).getID());
							totalPrice += filingResult.get(i).getPrice();
						}
						//call orderform method to create the order form
						schoolFurnitureStore.createOrderForm(furnitureOrdered, itemID, totalPrice);
						schoolFurnitureStore.deleteItem(itemID, userFurnitureRequest[1]);
					}
				}
				else if(userFurnitureRequest[1].compareToIgnoreCase("lamp") == 0){
					ArrayList<Lamp> lampResult = schoolFurnitureStore.calculateLampPrice(userFurnitureRequest[0], userQuantity);
					itemID.clear();	//clears the id list from its last use

					//check to see if the calculatePrice found combinations
					if(lampResult.size() > 0){
						for(int i = 0; i < lampResult.size(); i++){
							itemID.add(lampResult.get(i).getID());
							totalPrice += lampResult.get(i).getPrice();
						}
						//call orderform method to create the order form
						schoolFurnitureStore.createOrderForm(furnitureOrdered, itemID, totalPrice);
						schoolFurnitureStore.deleteItem(itemID, userFurnitureRequest[1]);
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
