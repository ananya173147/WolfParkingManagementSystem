package operations;

import java.sql.Connection;
import java.util.Scanner;
import helpers.*;


import java.sql.Date;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MaintainingPermitVehicles {
	
    Scanner scanner = new Scanner(System.in);
    public void run(final Connection conn) throws Exception {
    	
    	int choice = 0;

        while (true) {
            System.out.println("Choose an operation you want to perform:");
            System.out.println("1. Insert Vehicle Info");
            System.out.println("2. Update Vehicle Info");
            System.out.println("3. Delete Vehicle Info");
            System.out.println("4. Enter Permit Info");
            System.out.println("5. Update Permit Info");
            System.out.println("6. Delete Permit Info");
            System.out.println("7. Exit Maintaining Permits/Vehicles");
            System.out.println("\nEnter your choice: \t");
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch(choice) {
            	case 1: addVehicle(conn);
            			break;
            			
            	case 2: updateVehicle(conn);
            			break;
            			
            	case 3: deleteVehicle(conn);
            			break;
            			
            	case 4: addPermit(conn);
            			break;
            	
            	case 5: updatePermit(conn);
            			break;
            	
            	case 6: deletePermit(conn);
            			break;
            	
            	case 7: break;
            	
            	default: System.out.println("Invalid input!\n");
            }
            if (choice == 7){
	            break;
	        }
        } 
    }
    
    public void addVehicle(Connection conn) {
        SelectHelper selectHelper = new SelectHelper();
        InsertHelper insertHelper = new InsertHelper();

        Map<String, Object> columnValues1 = new HashMap<>();
        Map<String, Object> columnValues2 = new HashMap<>();

        System.out.println("Enter Plate Number: ");
        String plate = scanner.nextLine();
        columnValues1.put("Plate", plate);

        System.out.println("Enter Driver ID:");
        String id = scanner.nextLine();

        // Find out the type of driver this ID belongs to
        try {
        List<String> columnsForDriverStatus = List.of("Status");
        List<List<Object>> objForDriverStatus = selectHelper.select("Drivers", columnsForDriverStatus, "ID = \'" + id + '\'', null, null, null, conn);
        
        // If the result is empty, the driver was not found
        if (objForDriverStatus.isEmpty()) {
            System.out.println("Error: Driver not found in the table. Please add the driver first.");
            return;
        }

        String statusOfDriver = objForDriverStatus.get(0).get(0).toString();
        System.out.println("The driver's status is " + statusOfDriver);

        System.out.println("Enter Permit ID:");
        String permitId = scanner.nextLine();
        
        List<String> columnsForExistingPermits = List.of("PermitID");
        List<List<Object>> objForExistingPermits = selectHelper.select("Permits", columnsForExistingPermits, "ID = \'" + id + '\'' + "AND PermitID = \'" + permitId + '\'', null, null, null, conn);
        
        // If the result is empty, the permit was not found
        if (objForExistingPermits.isEmpty()) {
            System.out.println("Error: Permit does not exist. Please add the permit first.");
            return;
        }
        
        // Check if the driver already has the maximum allowed number of vehicles on the permit
        List<String> columnsForPermitVehicles = List.of("Plate");
        List<List<Object>> objForPermitVehicles = selectHelper.select("Vehicles", columnsForPermitVehicles, "PermitID = \'" + permitId + '\'' + " AND ID = \'" + id + '\'', null, null, null, conn);
        
        // Maximum allowed vehicles based on driver status
        int maxVehiclesAllowed = "E".equalsIgnoreCase(statusOfDriver) ? 2 : 1;

        // If the driver has reached the maximum allowed vehicles, inform the user
        if (!"F".equalsIgnoreCase(statusOfDriver) && objForPermitVehicles.size() >= maxVehiclesAllowed) {
            System.out.println("Error: The driver already has the maximum allowed vehicles on the permit.");
            return;
        }
        
        columnValues1.put("ID", id);
        columnValues1.put("PermitID", permitId);

        System.out.println("Enter Year:");
        Integer year = Integer.parseInt(scanner.nextLine());
        columnValues1.put("Year", year);
        scanner.nextLine();

        System.out.println("Enter Vehicle Color:");
        String color = scanner.nextLine();
        columnValues1.put("Color", color);

        System.out.println("Enter Model:");
        String model = scanner.nextLine();

        // Check if the model is non-null
        if (model != null && !model.isEmpty()) {
            // Check if the model already exists in the ModelInfo Table
            List<String> columnsForModel = List.of("Model");
            List<List<Object>> objForModel = selectHelper.select("ModelInfo", columnsForModel, "Model = \'" + model + '\'', null, null, null, conn);

            // If the Model is not present in the ModelInfo table, then only add it
            if (objForModel.isEmpty()) {
                columnValues2.put("Model", model);
                System.out.println("Enter Manufacturer:");
                String manf = scanner.nextLine();
                columnValues2.put("Manufacturer", manf);
                insertHelper.insertQuery(columnValues2, "ModelInfo", conn);
            }
        }
        
        insertHelper.insertQuery(columnValues1, "Vehicles", conn);
        
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred.");
            e.printStackTrace();
        }
    }

       
    public void updateVehicle(Connection conn) {
    	
    	Map<String, Object> columnValues = new HashMap<>();
    	
    	System.out.println("Enter the plate of the vehicle you want to update: ");
    	String plate = scanner.nextLine();
    	
    	System.out.println("Choose the column to update:");
        System.out.println("1. Plate Number");
        System.out.println("2. Driver ID");
        System.out.println("3. Permit ID");
        System.out.println("4. Year");
        System.out.println("5. Color");
        System.out.println("6. Model");
        System.out.println("7. Manufacturer");
        
        System.out.println("Press 8 to go back ");
        System.out.println("Enter your choice: \t");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch(choice) {
        	
        	case 1:	System.out.println("Enter the new Plate Number: \t");
        			String newPlate = scanner.nextLine();
        			columnValues.put("Plate", newPlate);
        			break;
        	
        	case 2:	System.out.println("Enter the new Driver ID: \t");
					String newID = scanner.nextLine();
					columnValues.put("ID", newID);
					break;
        	
        	case 3:	System.out.println("Enter the new Permit ID: \t");
					String newPermit = scanner.nextLine();
					columnValues.put("PermitID", newPermit);
					break;
					
        	case 4:	System.out.println("Enter the updated Year: \t");
        			Integer newYear = Integer.parseInt(scanner.nextLine());
					columnValues.put("Year", newYear);
					break;
					
        	case 5:	System.out.println("Enter the updated Color: \t");
					String newColor= scanner.nextLine();
					columnValues.put("Color", newColor);
					break;
					
        	case 6:	System.out.println("Enter the new Model: \t");
					String newModel = scanner.nextLine();
					columnValues.put("Model", newModel);
					
					//Check if the model already exists in the ModelInfo Table
		            SelectHelper selectHelper = new SelectHelper();
		            List<String> stringList = List.of("Model");
		            List<List<Object>> obj = selectHelper.select("ModelInfo", stringList, "Model = \'" + newModel + "\'",null , null,null, conn);
		            
		            if(obj.isEmpty()) {
		            	InsertHelper insertHelper = new InsertHelper();
		            	System.out.println("Enter the Manufacturer: \t");
		            	String manf = scanner.nextLine();
		            	
		            	Map<String, Object> columnValues1 = new HashMap<>();
		            	columnValues1.put("Model", newModel);
		            	columnValues1.put("Manufacturer", manf);
		            	
		            	insertHelper.insertQuery(columnValues1, "ModelInfo", conn);
		            	
		            }
					
					break;
			
        	case 7:	System.out.println("Enter the updated Manufacturer: \t");
					String newManf = scanner.nextLine();
					columnValues.put("Manufacturer", newManf);
					break;
					
        	case 8: return;
			
        	default: System.out.println("Invalid Input!");
        			
        }
        
        if(choice < 7 && choice > 0) {
        	
        	String condition = "Plate = \'" + plate + "\' ";
            UpdateHelper updateHelper = new UpdateHelper();
            updateHelper.update("Vehicles", condition, columnValues, conn);
        
        } else if(choice == 7) {
        	
        	//Fetching the model of the car with the input plate number
        	SelectHelper selectHelper = new SelectHelper();
            List<String> stringList = List.of("Model");
            List<List<Object>> obj = selectHelper.select("Vehicles", stringList, "Plate = \'" + plate + "\'" ,null , null,null, conn);
            String model = obj.get(0).get(0).toString();
        	
            //Update the Manufacturer in the ModelInfo Table
        	String condition = "Model = \'" + model + "\' ";
            UpdateHelper updateHelper = new UpdateHelper();
            updateHelper.update("ModelInfo", condition, columnValues, conn);
        	
        }    	
    }
    
    public void deleteVehicle(Connection conn) {
    	
    	System.out.println("Enter the plate of the vehicle you want to delete: ");
    	String plate = scanner.nextLine();
    	
    	String condition = "Plate = \'" + plate + "\'";
    	DeleteHelper deleteHelper = new DeleteHelper();
        deleteHelper.delete("Vehicles", condition, conn);
    	
    }
    
    public void addPermit(Connection conn) throws ParseException {
    	
    	SelectHelper selectHelper = new SelectHelper();
    	Map<String, Object> columnValues = new HashMap<>();
    	
    	System.out.println("Enter Permit ID:");
        String permitId = scanner.nextLine();
        columnValues.put("PermitID", permitId);
        
        System.out.println("Enter Driver ID:");
        String id = scanner.nextLine();
        
        try {
            List<String> columnsForDriverStatus = List.of("Status");
            List<List<Object>> objForDriverStatus = selectHelper.select("Drivers", columnsForDriverStatus, "ID = \'" + id + '\'', null, null, null, conn);
            
            // If the result is empty, the driver was not found
            if (objForDriverStatus.isEmpty()) {
                System.out.println("Error: Driver not found in the table. Please add the driver first.");
                return;
            }

            String statusOfDriver = objForDriverStatus.get(0).get(0).toString();
            System.out.println("The driver's status is " + statusOfDriver);
            
            // Check if the driver already has a permit based on the status
            List<String> columnsForExistingPermits = List.of("PermitID");
            List<List<Object>> objForExistingPermits = selectHelper.select("Permits", columnsForExistingPermits, "ID = \'" + id + '\'', null, null, null, conn);
            
            System.out.println("Enter Permit Type ('residential', 'commuter', 'peak hours', 'special event', 'park & ride'):");
	        String permitType = scanner.nextLine();
	        
	        // Limit the number of permits based on the driver's status and permit type
	        int maxPermitsAllowed;

	        if ("F".equalsIgnoreCase(statusOfDriver)) {
	            // Driver with status F can have any number of permits
	            maxPermitsAllowed = Integer.MAX_VALUE;
	        } else {
	            maxPermitsAllowed = 1; // Default to 1 permit

	            if ("special event".equals(permitType) || "park & ride".equals(permitType)) {
	                if ("E".equalsIgnoreCase(statusOfDriver)) {
	                    maxPermitsAllowed = 3;
	                } else if ("S".equalsIgnoreCase(statusOfDriver)) {
	                    maxPermitsAllowed = 2;
	                }
	            } else {
	                if ("E".equalsIgnoreCase(statusOfDriver)) {
	                    maxPermitsAllowed = 2;
	                }
	            }
	        }

	        // If the driver has reached the maximum allowed permits, inform the user
	        if (objForExistingPermits.size() >= maxPermitsAllowed) {
	            System.out.println("Error: The driver already has the maximum allowed permits for the given status.");
	            return;
	        }
           
	        columnValues.put("ID", id);
	        columnValues.put("PermitType", permitType);
	        
	        System.out.println("Enter Zone ID:");
	        String zone = scanner.nextLine();
	        columnValues.put("ZoneID", zone);
	        
	        System.out.println("Enter Lot Name:");
	        String lot = scanner.nextLine();
	        columnValues.put("LotName", lot);
	        
	        System.out.println("Enter Space Type:");
	        String space = scanner.nextLine();
	        columnValues.put("SpaceType", space);
	        
	        System.out.print("Enter the Start Date (YYYY-MM-DD): ");
	        String startDate = scanner.nextLine();
	        Date stDate = Date.valueOf(startDate);
	        columnValues.put("StartDate", stDate);
	        
	        System.out.print("Enter the Expiration Date (YYYY-MM-DD): ");
	        String expirationDate = scanner.nextLine();
	        Date expDate = Date.valueOf(expirationDate);
	        columnValues.put("ExpDate", expDate);
	        
	        System.out.print("Enter the Expiration Time (HH:mm:ss): ");
	        String userInput = scanner.nextLine();
	        columnValues.put("ExpTime", java.sql.Time.valueOf(userInput));			
	        
	        InsertHelper insertHelper = new InsertHelper();
	        insertHelper.insertQuery(columnValues, "Permits", conn);
	        
        } catch (Exception e) {
            System.out.println("Error: An unexpected error occurred.");
            e.printStackTrace();
        }
    }
    
    public void updatePermit(Connection conn) throws ParseException {
    	
    	Map<String, Object> columnValues = new HashMap<>();
    	
    	System.out.println("Enter the Permit ID you want to update: ");
    	String permitID = scanner.nextLine();
    	
    	System.out.println("Choose the column to update:");
        System.out.println("1. Permit ID");
        System.out.println("2. Driver ID");
        System.out.println("3. Zone ID");
        System.out.println("4. Lot Name");
        System.out.println("5. Space Type");
        System.out.println("6. Permit Type");
        System.out.println("7. Start Date");
        System.out.println("8. Expiration Date");
        System.out.println("9. Expiration Time");
        
        System.out.println("Press 0 to go back ");
        System.out.println("Enter your choice: \t");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch(choice) {
        	
        	case 1: System.out.println("Enter the new Permit ID: \t");
        			String newPermit = scanner.nextLine();
        			columnValues.put("PermitID", newPermit);
        			break;
        			
        	case 2: System.out.println("Enter the updated Driver ID: \t");
					String newDriver = scanner.nextLine();
					columnValues.put("ID", newDriver);
					break;
					
        	case 3: System.out.println("Enter the updated Zone ID: \t");
					String newZone = scanner.nextLine();
					columnValues.put("ZoneID", newZone);
					break;
			
        	case 4: System.out.println("Enter the updated Lot Name: \t");
					String newLot = scanner.nextLine();
					columnValues.put("LotName", newLot);
					break;
					
        	case 5: System.out.println("Enter the updated Space Type (\'electric\', \'handicap\', \'compact car\', \'regular\'): \t");
					String newSpace = scanner.nextLine();
					columnValues.put("SpaceType", newSpace);
					break;
					
        	case 6: System.out.println("Enter the updated Permit Type (\'residential\', \'commuter\', \'peak hours\', \'special event\', \' park & ride\'): \t");
					String newPermitType = scanner.nextLine();
					columnValues.put("PermitType", newPermitType);
					break;
					
        	case 7: System.out.print("Enter the updated Start Date (YYYY-MM-DD): ");
            		String startDate = scanner.nextLine();
            		Date stDate = Date.valueOf(startDate);
            		columnValues.put("StartDate", stDate);
            		break;
					
        	case 8: System.out.print("Enter the updated Expiration Date (YYYY-MM-DD): ");
    				String expDate = scanner.nextLine();
    				Date expirationDate = Date.valueOf(expDate);
    				columnValues.put("ExpDate", expirationDate);		
    				break;
    				
        	case 9: System.out.print("Enter updated expiration time (HH:mm:ss): ");
            		String userInput = scanner.nextLine();
		            columnValues.put("ExpTime", java.sql.Time.valueOf(userInput));		
    				break;
            		
        	case 0: return;
        	
        	default: System.out.println("Invalid Input");
        }
        
        String condition = "PermitID = \'" + permitID + "\' ";
        UpdateHelper updateHelper = new UpdateHelper();
        updateHelper.update("Permits", condition, columnValues, conn);
    	
    }
    
    public void deletePermit(Connection conn) {
    	
    	System.out.println("Enter the Permit ID you want to delete: ");
    	String permit = scanner.nextLine();
    	
    	String condition = "Permit = \'" + permit + "\'";
    	DeleteHelper deleteHelper = new DeleteHelper();
        deleteHelper.delete("Permits", condition, conn);
    	
    }
}
