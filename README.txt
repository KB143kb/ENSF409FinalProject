Students: Nathaniel Lipura, 30037596
          Kruti Bhatt, 30067609
 
This project takes takes user inputs to calculate the cheapest combination of furniture items
and produces an order form text file after each request. The main project file is FurnitureStore.java.
The unit tests are in FurnitureStoreTest.java and the commands to run both FurnitureStore.java and 
FurnitureStoreTest.java are below. 

The commands are for the Windows CMD terminal and the program must be run in
the main project directory. The java files are stored in the edu/ucalgary/ensf409 directory and the necessary
.jar files to connect the sql database and run the junit tests are in the lib directory

FurnitureStore.java:
To compile: javac -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/FurnitureStore.java
To run: java -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/FurnitureStore 

FurnitureStoreTest.java:
To compile: javac -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/FurnitureStoreTest.java 
To run: java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar org.junit.runner.JUnitCore edu.ucalgary.ensf409.FurnitureStoreTest

The database user settings can be changed with user prompts in FurnitureStore.java. 

The current MySQL settings in FurnitureStoreTest.java are: Username: scm
                                                           Password: ensf409
                                                           dbURL: jdbc:mysql://localhost/inventory