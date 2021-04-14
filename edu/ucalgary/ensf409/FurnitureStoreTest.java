/*
 *	To compile: javac -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/FurnitureStoreTest.java 
 *	To run: java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar org.junit.runner.JUnitCore edu.ucalgary.ensf409.FurnitureStoreTest
 */

package edu.ucalgary.ensf409;

import org.junit.*;
import static org.junit.Assert.*;

import java.sql.*;
import java.util.ArrayList;

public class FurnitureStoreTest 
{
	public final static String DBURL = "jdbc:mysql://localhost/inventory";
	public final static String USERNAME = "nathan";
	public final static String PASSWORD = "ensf409";
	public static FurnitureStore schoolFurnitureStore;
	
	//Constructor with three arguments will be tested.
		//Constructor with three arguments, use getDBURL() to test DBURL was set.
		@Test
		public void testConstructor3_getDBURL ()
		{
			FurnitureStore testObj = new FurnitureStore (DBURL, USERNAME, PASSWORD);
			assertTrue ("Three argument constructor sets DBURL successfully.", testObj.getDBURL().equals(DBURL));
			System.out.println("testConstructor3_getDBURL successful.");
		}
		
		//Constructor with three arguments, use getUSERNAME() to test USERNAME was set.
		@Test
		public void testConstructor3_getUSERNAME ()
		{
			FurnitureStore testObj = new FurnitureStore (DBURL, USERNAME, PASSWORD);
			assertTrue ("Three argument constructor sets USERNAME successfully.", testObj.getUSERNAME().equals(USERNAME));
			System.out.println("testConstructor3_getUSERNAME successful.");
		}
		
		//Constructor with three arguments, use getPASSWORD() to test PASSWORD was set.
		@Test
		public void testConstructor3_getPASSWORD ()
		{
			FurnitureStore testObj = new FurnitureStore (DBURL, USERNAME, PASSWORD);
			assertTrue ("Three argument constructor sets PASSWORD successfully.", testObj.getPASSWORD().equals(PASSWORD));
			System.out.println("testConstructor3_getPASSWORD successful.");
		}
		
		
	//Testing whether MySQL was successfully connected. User might need to change USERNAME/PASSWORD 
	//as well as DBURL according to their device. 
		@Test
		public void testCreateConnection() throws SQLException {
			schoolFurnitureStore = new FurnitureStore(DBURL, USERNAME, PASSWORD);
			schoolFurnitureStore.createConnection();
			assertNotNull(schoolFurnitureStore.getInventoryConnection());
			schoolFurnitureStore.close();
			System.out.println("testCreateConnection successful.");
		}
		
		//It is important for MySQL code to be refreshed or recreated before Unit Testing the FurnitureStore code. 
		//Testing whether CalculateChairPrice works after connecting with MySQL.
		@Test
		public void testCalculateChairPrice ()
		{
			FurnitureStore schoolFurnitureStore = new FurnitureStore(DBURL, USERNAME, PASSWORD);
			schoolFurnitureStore.createConnection();
			ArrayList<Chair> chairResult = schoolFurnitureStore.calculateChairPrice ("Mesh", 1);		
			assertEquals ("C6748", chairResult.get(0).getID());
			schoolFurnitureStore.close();
			System.out.println("testCalculateChairPrice successful.");
		}
		
		////Testing whether CalculateDeskPrice works after connecting with MySQL.
		@Test
		public void testCalculateDeskPrice ()
		{
			FurnitureStore schoolFurnitureStore = new FurnitureStore(DBURL, USERNAME, PASSWORD);
			schoolFurnitureStore.createConnection();
			ArrayList<Desk> deskResult = schoolFurnitureStore.calculateDeskPrice ("Traditional", 1);		
			assertEquals ("D0890", deskResult.get(0).getID());
			schoolFurnitureStore.close();
			System.out.println("testCalculateDeskPrice successful.");
		}
		
		//Testing whether CalculateFilingPrice works after connecting with MySQL.
		@Test
		public void testCalculateFilingPrice ()
		{
			FurnitureStore schoolFurnitureStore = new FurnitureStore(DBURL, USERNAME, PASSWORD);
			schoolFurnitureStore.createConnection();
			ArrayList<Filing> filingResult = schoolFurnitureStore.calculateFilingPrice ("Small", 1);		
			assertEquals ("F006", filingResult.get(0).getID());
			schoolFurnitureStore.close();
			System.out.println("testCalculateFilingPrice successful.");
		}
		
		//Testing whether CalculateLampPrice works after connecting with MySQL.
		@Test
		public void testCalculateLampPrice ()
		{
			FurnitureStore schoolFurnitureStore = new FurnitureStore(DBURL, USERNAME, PASSWORD);
			schoolFurnitureStore.createConnection();
			ArrayList<Lamp> lampResult = schoolFurnitureStore.calculateLampPrice ("Desk", 1);		
			assertEquals ("L564", lampResult.get(0).getID());
			schoolFurnitureStore.close();
			System.out.println("testCalculateLampPrice successful.");
		}
		
}
