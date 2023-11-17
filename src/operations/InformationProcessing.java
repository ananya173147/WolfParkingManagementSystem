package operations;

import helpers.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.lang.Exception;
import java.util.Scanner;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.sql.Statement;

public class InformationProcessing {

    Scanner scanner = new Scanner(System.in);

    public void run(final Connection conn) throws Exception {

        while (true) {
            System.out.println("Choose an operation you want to perform:");
            System.out.println("1: Insert Driver Info");
            System.out.println("2. Update Driver Info");
            System.out.println("3. Delete Driver Info");
            System.out.println("4. Insert Lot Info");
            System.out.println("5. Update Lot Info");
            System.out.println("6. Delete Lot Info");
            System.out.println("7. Insert Zones Info");
            System.out.println("8. Update Zones Info");
            System.out.println("9. Delete Zones Info");
            System.out.println("10. Insert Spaces Info");
            System.out.println("11. Update Spaces Info");
            System.out.println("12. Delete Spaces Info");
            System.out.println("13. Update Parking Activity");
            System.out.println("14. Update Exit Parking Activity");
            System.out.println("15. Exit Information Processing");
            System.out.println("\nEnter your choice: \t");
            int choice = scanner.nextInt();
            scanner.nextLine();
        
            switch(choice){
                case 1:
                    insertDriverInfo(conn);
                    break;
                case 2:
                    UpdateDriverInfo(conn);
                    break;
                case 3:
                    DeleteDriverInfo(conn);
                    break;
                case 4:
                    insertLotInfo(conn);
                    break;
                case 5:
                    UpdateLotInfo(conn);
                    break;
                case 6:
                    DeleteLotInfo(conn);
                    break;
                case 7:
                    insertZoneInfo(conn);
                    break;
                case 8:
                    UpdateZoneInfo(conn);
                    break;
                case 9:
                    DeleteZoneInfo(conn);
                    break;
                case 10:
                    insertSpaceInfo(conn);
                    break;
                case 11:
                    UpdateSpaceInfo(conn);
                    break;
                case 12:
                    DeleteSpaceInfo(conn);
                    break;
                case 13:
                    ParkVehicle(conn);
                    break;
                case 14:
                    ExitParking(conn);
                    break;
                case 15:
                	break;
                default:
	                System.out.println("Invalid Input, Please try again.");
	                break;
            }      
            if (choice == 15){
	            break;
	        }
        }
    }
    
    public void insertDriverInfo(Connection conn) throws SQLException, IllegalStateException {
    	
    	// Disable auto-commit to manage transactions manually
    	conn.setAutoCommit(false);
    	
    	try {
    		// Create a statement object for executing SQL queries
    		Statement statement = conn.createStatement();;
  
    		// Prompt user to input Driver ID
    		System.out.println("Driver ID: ");
    		String dId = scanner.nextLine();
    		
    		// Prompt user to input Driver name
    		System.out.println("Enter Driver name:");
    		String dname = scanner.nextLine();
    		
    		// Prompt user to input Status
    		System.out.println("Enter Status:");
    		String status = scanner.nextLine();
    		
    		// Prompt user to input isDisabled status (true/false)
    		System.out.println("isDisabled? (Type true/false)");
    		boolean isDisabled = false;
    		
    		try {
    			// Try to read a boolean value from user input
    			isDisabled = scanner.nextBoolean();
    		} catch (InputMismatchException e) {
    			// Handle exception if the input is not a valid boolean
    			scanner.nextLine();
    			System.out.println("Error: InputMismatchException occurred. Please enter valid input for 'isDisabled' field");
    			e.printStackTrace();
    			// Set auto-commit to true and return from the method
    			conn.setAutoCommit(true);
    			return;
    		}
    		
    		// Create SQL INSERT statement with user inputs
    		String insertStatement = "INSERT INTO Drivers VALUES ('" + dId + "','" + dname + "','" + status + "'," + isDisabled + ");";
    		
    		// Execute the SQL INSERT statement
    		statement.executeUpdate(insertStatement);
    		
    		// Commit the transaction
    		conn.commit();
    		
    	} catch(Exception e){
    		// Handle any exceptions that occur during the execution of the code within the try block
    	    System.out.println("Exception occurred: " + e.getMessage());

    	    // Roll back the transaction in case of an exception
    	    conn.rollback();
    	    
    	 // Set auto-commit to true and return from the method
			conn.setAutoCommit(true);
    	} finally {
    		// Set auto-commit back to true in the finally block to ensure it happens regardless of whether an exception occurred or not
    	    conn.setAutoCommit(true);
    	}
    }

    public void UpdateDriverInfo(Connection conn) {
    	// Update operation on basic driver information
    	
    	// Map to store column-value pairs for the update
        Map<String, Object> columnValues = new HashMap<>();

        System.out.println("Choose the column to update:");
        System.out.println("1. Driver ID");
        System.out.println("2. Driver Name");
        System.out.println("3. Status");
        System.out.println("4. isDisabled");
        System.out.println("Enter your choice: \t");
        
        Integer column = Integer.parseInt(scanner.nextLine());
        
        System.out.println("Enter the Driver ID to confirm the update operation:");
        String dId = scanner.nextLine();
        
        List<String> columnsForDriverStatus = List.of("ID");
        SelectHelper selectHelper = new SelectHelper();
		List<List<Object>> objForDriver = selectHelper.select("Drivers", columnsForDriverStatus, "ID = \'" + dId + '\'', null, null, null, conn);
        
        // If the result is empty, the driver was not found
        if (objForDriver.isEmpty()) {
            System.out.println("Error: Driver not found in the table. Please add the driver first.");
            return;
        }
        
        System.out.println("Enter the new value:");

        if (column == 1) {
            String value1 = scanner.nextLine();
            columnValues.put("ID", value1);
        } else if (column == 2) {
            String value1 = scanner.nextLine();
            scanner.nextLine();
            columnValues.put("Name", value1);
        } else if (column == 3) {
            String value1 = scanner.nextLine();
            columnValues.put("Status", value1);
        } else if (column == 4) {
            String value1 = scanner.nextLine();
            columnValues.put("isDisabled", value1);
        }
        else {
        	System.out.println("Invalid option selected, Please try again! \n");
        	return;
        }
        
        String condition = "ID=" + ("\"") + dId + ("\"");
        
        UpdateHelper updateHelper = new UpdateHelper();
        updateHelper.update("Drivers", condition, columnValues, conn);
    }

    public void DeleteDriverInfo(Connection conn){
        //Delete operation on basic info for Driver
        System.out.println("Provide the Driver ID which you want to delete:");
    	String value = scanner.nextLine();
        String condition = "ID=" + ("\"") + value + ("\""); 
        DeleteHelper deleteHelper = new DeleteHelper();
        deleteHelper.delete("Drivers", condition, conn);
    }

    public void insertLotInfo(Connection conn) throws SQLException {
        //Insert operation on inserting driver info to the DB
        Map<String, Object> columnValues = new HashMap<>();
        System.out.println("Enter Parking Lot Name ");
        String lotname = scanner.nextLine();
        columnValues.put("LotName", lotname);
        System.out.println("Enter the Parking Lot Address:");
        String addr = scanner.nextLine();
        scanner.nextLine();
        columnValues.put("Address", addr);
        InsertHelper insertHelper = new InsertHelper();
        insertHelper.insertQuery(columnValues, "ParkingLots", conn);
    }

    public void UpdateLotInfo(Connection conn) {
        //Update operation on basic info for driver
        Map<String, Object> columnValues = new HashMap<>();

        System.out.println("Choose the column you wish to update:");
        System.out.println("Press 1 for LotName");
        System.out.println("Press 2 for Address");

        System.out.println("Enter your choice: \t");
        Integer column = Integer.parseInt(scanner.nextLine());
        
        System.out.println("Enter the LotName for the update operation:");
        String LotName = scanner.nextLine();
        
        System.out.println("Enter the new value:");

        if (column == 1) {
            String value1 = scanner.nextLine();
            columnValues.put("LotName", value1);
        } else if (column == 2) {
            String value1 = scanner.nextLine();
            columnValues.put("Address", value1);
        } 
        else {
        	System.out.println("Invalid option selected, Please try again! \n");
        	return;
        }
        String condition = "LotName=" + ("\"") + LotName + ("\"");
        UpdateHelper updateHelper = new UpdateHelper();
        updateHelper.update("ParkingLots", condition, columnValues, conn);
    }

    public void DeleteLotInfo(Connection conn){
        //Delete operation on basic info for Driver
        System.out.println("Provide the LotName which you want to delete:");
    	String value = scanner.nextLine();
        String condition = "LotName=" + ("\"") + value + ("\""); 
        DeleteHelper deleteHelper = new DeleteHelper();
        deleteHelper.delete("ParkingLots", condition, conn);
    }

    public void insertZoneInfo(Connection conn) throws SQLException {
        //Insert operation on inserting driver info to the DB
        Map<String, Object> columnValues = new HashMap<>();
        System.out.println("Enter Zone ID ");
        String ZoneID = scanner.nextLine();
        columnValues.put("ZoneID", ZoneID);

        System.out.println("Enter Lot Name ");
        String lotname = scanner.nextLine();
        columnValues.put("LotName", lotname);

        InsertHelper insertHelper = new InsertHelper();
        insertHelper.insertQuery(columnValues, "Zones", conn);
    }

    public void UpdateZoneInfo(Connection conn) {
        //Update operation on basic info for driver
        Map<String, Object> columnValues = new HashMap<>();

        System.out.println("Choose the column to wish update:");
        System.out.println("Press 1 for ZoneID");
        System.out.println("Press 2 for LotName");

        System.out.println("Enter your choice: \t");
        Integer column = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the ZoneID to confirm the update operation:");
        String ZoneID = scanner.nextLine();
        
        System.out.println("Enter the corresponding LotName to confirm the update operation:");
        String LotName = scanner.nextLine();
        System.out.println("Enter the new value:");

        if (column == 1) {
            String value1 = scanner.nextLine();
            columnValues.put("ZoneID", value1);
        } else if (column == 2) {
            String value1 = scanner.nextLine();
            scanner.nextLine();
            columnValues.put("LotName", value1);
        }
        else {
        	System.out.println("Invalid option selected, Please try again! \n");
        	return;
        }
        
        String condition = "ZoneID=" + ("\"") + ZoneID + ("\"") + " AND " + "LotName=" + ("\"") + LotName + ("\"");
        UpdateHelper updateHelper = new UpdateHelper();
        updateHelper.update("Zones", condition, columnValues, conn);
    }

    public void DeleteZoneInfo(Connection conn){
        //Delete operation on basic info for Driver
        System.out.println("Provide the ZoneID which you want to delete:");
    	String value = scanner.nextLine();
    	System.out.println("Provide the LotName for the zone which you want to delete:");
    	String Lotvalue = scanner.nextLine();
        String condition = "null";
        condition = "ZoneID=" + ("\"") + value + ("\"") + " AND " + "LotName=" + ("\"") + Lotvalue + ("\""); 
        DeleteHelper deleteHelper = new DeleteHelper();
        deleteHelper.delete("ParkingLots", condition, conn);
    }

    public void insertSpaceInfo(Connection conn) throws SQLException {
      	 
    	//Using transactions to insert new spaces
    	conn.setAutoCommit(false);
    	 
        try {
        	
        	Statement statement = conn.createStatement();
        	
        	System.out.println("Enter Space Number: ");
        	Integer snumber = Integer.parseInt(scanner.nextLine());
        	
        	System.out.println("Enter Space Type:");
        	String stype = scanner.nextLine();
        	
        	System.out.println("Enter ZoneID:");
        	String ZoneID = scanner.nextLine();
        	
        	System.out.println("Enter LotName");
        	String LotName = scanner.nextLine();
        	
        	String insertStatement = "INSERT INTO Spaces VALUES (" + snumber + ",'" + stype + "','" + ZoneID + "','" + LotName + "');";
    		statement.executeUpdate(insertStatement);
    		
    		conn.commit();
    		System.out.println("Space added successfully!");
        	
        } catch(Exception e){
        	System.out.println("Exception occured: " + e.getMessage());
        	conn.rollback();
        	conn.setAutoCommit(true);
        } finally {
        	conn.setAutoCommit(true);
        }
    }
    
    public void UpdateSpaceInfo(Connection conn) {
        //Update operation on basic info for driver
        Map<String, Object> columnValues = new HashMap<>();

        System.out.println("Choose the column you wish to update:");
        System.out.println("Press 1 for Space Number");
        System.out.println("Press 2 for Space Type");
        System.out.println("Press 3 for ZoneID");
        System.out.println("Press 4 for LotName");
        System.out.println("Enter your choice: \t");
        int column = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the Space Number to confirm the update operation:");
        Integer snumber = Integer.parseInt(scanner.nextLine());
        
        System.out.println("Enter the ZoneID to confirm the update operation:");
        String ZoneID = scanner.nextLine();
        
        System.out.println("Enter the LotName to confirm the update operation:");
        String LotName = scanner.nextLine();
        System.out.println("Enter the new value:");
        

        if (column == 1) {
        	Integer value1 = Integer.parseInt(scanner.nextLine());
            columnValues.put("Number", value1);
        } else if (column == 2) {
            String value1 = scanner.nextLine();
            scanner.nextLine();
            columnValues.put("SpaceType", value1);
        } else if (column == 3) {   
            String value1 = scanner.nextLine();
            columnValues.put("ZoneID", value1);
        } else if (column == 4) {
            String value1 = scanner.nextLine();
            columnValues.put("LotName", value1);
        }
        else {
        	System.out.println("Invalid option selected, Please try again! \n");
        	return;
        }

        String condition = "Number=" + ("\"") + snumber + ("\"") + " AND " + "ZoneID=" + ("\"") + ZoneID + ("\"") + " AND " + "LotName=" + ("\"") + LotName + ("\"");
        UpdateHelper updateHelper = new UpdateHelper();
        updateHelper.update("Spaces", condition, columnValues, conn);
    }

    public void DeleteSpaceInfo(Connection conn){
        //Delete operation on basic info for Driver
        System.out.println("Provide the Space Number which you want to delete:");
    	Integer value = Integer.parseInt(scanner.nextLine());
    	System.out.println("Provide the ZoneID which you want to delete:");
     	String ZoneID = scanner.nextLine();
     	System.out.println("Provide the LotName for the zone which you want to delete:");
     	String LotName = scanner.nextLine();
        String condition = "null";
        condition = "Number=" + ("\"") + value + ("\"") + " AND " + "ZoneID=" + ("\"") + ZoneID + ("\"") + " AND " + "LotName=" + ("\"") + LotName + ("\""); 
        DeleteHelper deleteHelper = new DeleteHelper();
        deleteHelper.delete("Spaces", condition, conn);
    }

    public void ParkVehicle(Connection conn) throws SQLException {
        //Insert operation on inserting driver info to the DB
        Map<String, Object> columnValues = new HashMap<>();
        System.out.println("Enter Plate:");
        String plate = scanner.nextLine();
        columnValues.put("Plate", plate);
        System.out.println("Enter Space Number: ");
        Integer snumber = Integer.parseInt(scanner.nextLine());
        columnValues.put("Number", snumber);
        System.out.println("Enter ZoneID:");
        String ZoneID = scanner.nextLine();
        columnValues.put("ZoneID", ZoneID);
        System.out.println("Enter Parking Lot Name");
        String LotName = scanner.nextLine();
        columnValues.put("LotName", LotName);
        System.out.println("Enter LastAction: (parking/exiting)");
        String lastaction = scanner.nextLine();
        columnValues.put("LastAction", lastaction);
        System.out.println("Enter TimeStamp (Format: yyyy-mm-dd hrs:mins:secs)");
        String TimeStamp = scanner.nextLine();
        columnValues.put("TimeStamp", TimeStamp);
        InsertHelper insertHelper = new InsertHelper();
        insertHelper.insertQuery(columnValues, "ParkingActivity", conn);
    }

    public void ExitParking(Connection conn) throws SQLException {
        //Insert operation on inserting driver info to the DB
        Map<String, Object> columnValues = new HashMap<>();
        System.out.println("Enter Plate:");
        String plate = scanner.nextLine();
        columnValues.put("Plate", plate);
        System.out.println("Enter Space Number: ");
        Integer snumber = Integer.parseInt(scanner.nextLine());
        columnValues.put("Number", snumber);
        System.out.println("Enter ZoneID:");
        String ZoneID = scanner.nextLine();
        columnValues.put("ZoneID", ZoneID);
        System.out.println("Enter Parking Lot Name");
        String LotName = scanner.nextLine();
        columnValues.put("LotName", LotName);
        System.out.println("Enter LastAction: (exiting)");
        String lastaction = scanner.nextLine();
        columnValues.put("LastAction", lastaction);
        System.out.println("Enter TimeStamp (Format: yyyy-mm-dd hrs:mins:secs)");
        String TimeStamp = scanner.nextLine();
        columnValues.put("TimeStamp", TimeStamp);
        InsertHelper insertHelper = new InsertHelper();
        insertHelper.insertQuery(columnValues, "ParkingActivity", conn);
    }
}
