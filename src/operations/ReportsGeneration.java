package operations;
import helpers.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportsGeneration {
    Scanner scanner = new Scanner(System.in);

    public void run(final Connection conn) throws Exception {

        while (true) {
            System.out.println("Choose an operation you want to perform:");
            System.out.println("1. Display Citations");
            System.out.println("2. Number of Citations in a Parking Lot");
            System.out.println("3. List of Zones and Parking Lots" );
            System.out.println("4. Number of cars currently in violation");
            System.out.println("5. Number of employees with permits in the Zone");
            System.out.println("6. Display permit information");
            System.out.println("7. Display available space numbers in a parking lot by space type");
            System.out.println("8. Exit Reports Generation");
            System.out.println("\nEnter your choice: \t");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
	            case 1:
	                displayCitations(conn);
	                break;
	            
	            case 2:
	                numberOfCitations(conn);
	                break;
	            
	            case 3:
	                displayZones(conn);
	                break;
	            
	            case 4:
	                displayCarsInViolation(conn);
	                break;
	            
	            case 5:
	                displayEmployeePermits(conn);
	                break;
	            
	            case 6:
	                displayPermitInformation(conn);
	                break;
	
	            case 7:
	                displayAvailableSpaces(conn);
	                break;
	 
	            case 8:
	                break;
	            
	            default:
	                System.out.println("Invalid Input, Please try again.");
	        }
	        if (choice == 8){
	            break;
	        }
        }
    }

    public void tablePrint(List<String> columnNames, List<List<Object>> results) {
        // Calculate maximum column widths
        int[] columnWidths = new int[columnNames.size()];
        
        for (int i = 0; i < columnNames.size(); i++) {
            columnWidths[i] = columnNames.get(i).length();
            
            for (List<Object> row : results) {
                Object value = row.get(i);
                int valueLength = (value != null) ? value.toString().length() : 4;
                columnWidths[i] = Math.max(columnWidths[i], valueLength);
            }
        }
        // Print column names
        printRow(columnNames, columnWidths);
        // Print separator line
        printSeparator(columnWidths);
        // Print rows
        for (List<Object> row : results) {
            printRow(row, columnWidths);
        }
    }

    public void printRow(List<?> values, int[] columnWidths) {
        System.out.print("| ");
        
        for (int i = 0; i < values.size(); i++) {
            String formattedValue = String.format("%-" + columnWidths[i] + "s", values.get(i));
            System.out.print(formattedValue + " | ");
        }
        
        System.out.println();
    }

    public void printSeparator(int[] columnWidths) {
        System.out.print("+");
        
        for (int width : columnWidths) {
            System.out.print("-".repeat(width + 2) + "+");
        }
        
        System.out.println();
    }
    
    public void displayCitations(Connection connection){
        
      //Fetching the results from the table using select query
        SelectHelper selectHelper = new SelectHelper();
        List<String> columnNames = new ArrayList<>();
        
        columnNames.add("CitationID");
        columnNames.add("Plate");
        columnNames.add("Number");
        columnNames.add("ZoneID");
        columnNames.add("LotName");
        columnNames.add("PayStatus"); 
        columnNames.add("Fee");
        columnNames.add("CitationDate");
        columnNames.add("CitationTime");
        
        List<List<Object>> results = selectHelper.select("Citations", columnNames, null, null, null, null, connection);
        
      //Printing the result
        if (!results.isEmpty()) {
            tablePrint(columnNames, results);
        } else{
            System.out.println("Empty result.");
        }
    }
    
    public void numberOfCitations(Connection connection) {
    	//Taking required input from the user
    	System.out.println("Enter the Lot Name");
        String lot_name = scanner.nextLine();
    	
        System.out.println("Enter the Start Date (Format: yyyy-mm-dd)");
        String start_date = scanner.nextLine();

        System.out.println("Enter the End Date (Format: yyyy-mm-dd)");
        String end_date = scanner.nextLine();
        
      //Fetching the results from the table using select query
        SelectHelper selectHelper = new SelectHelper();
        List<String> columnNames = new ArrayList<>();
        
        columnNames.add("COUNT(*)");
		
        String condition = "LotName = " + "'" + lot_name + "'" + "AND CitationDate BETWEEN " + "'" + start_date + "'" + " AND " + "'" + end_date + "'";
        
        List<List<Object>> results = selectHelper.select("Citations", columnNames, condition, null, null, null, connection);

        List<String> columnNames1 = new ArrayList<>();
        columnNames1.add("NumberOfCitations");
        //Printing the result
        if (!results.isEmpty()) {
            tablePrint(columnNames1, results);
        } else{
            System.out.println("Empty result.");
        }
    }
    
    public void displayZones(Connection connection) {
    	//Fetching the results from the table using select query
        SelectHelper selectHelper = new SelectHelper();
        List<String> columnNames = new ArrayList<>();
        
        columnNames.add("LotName");
        columnNames.add("ZoneID");
        
        List<List<Object>> results = selectHelper.select("Zones", columnNames, null, null, null, "LotName", connection);

        //Printing the result
        if (!results.isEmpty()) {
            tablePrint(columnNames, results);
        } else{
            System.out.println("Empty result.");
        }
	}

    public void displayCarsInViolation(Connection connection) {
    	//Fetching the results from the table using select query
        SelectHelper selectHelper = new SelectHelper();
        List<String> columnNames = new ArrayList<>();
        
        columnNames.add("COUNT(DISTINCT Plate)");
        
        String condition = "PayStatus = 'unpaid'";
        
        List<List<Object>> results = selectHelper.select("Citations", columnNames, condition, null, null, null, connection);
        
        List<String> columnNames1 = new ArrayList<>();
        columnNames1.add("NumberOfCarsInViolation");
        
        //Printing the result
        if (!results.isEmpty()) {
            tablePrint(columnNames1, results);
        } else{
            System.out.println("Empty result.");
        }
	}

    public void displayEmployeePermits(Connection connection) {
    	//Taking required input from the user
    	System.out.println("Enter the Zone ID");
        String zone_id = scanner.nextLine();
    	
        System.out.println("Enter the Lot Name");
        String lot_name = scanner.nextLine();
        
      //Fetching the results from the table using select query
        SelectHelper selectHelper = new SelectHelper();
        List<String> columnNames = new ArrayList<>();
        
        columnNames.add("COUNT(*)");
        
        String tableName = "Permits JOIN Drivers ON Permits.ID = Drivers.ID";
        String condition = "Status = 'E' AND ZoneID = " + "'" + zone_id + "'" + " AND LotName = " + "'" + lot_name + "'" ;
		
        List<List<Object>> results = selectHelper.select(tableName, columnNames, condition, null, null, null, connection);
        
        List<String> columnNames1 = new ArrayList<>();
        columnNames1.add("NumberOfEmployeePermits");
        
        //Printing the result
        if (!results.isEmpty()) {
            tablePrint(columnNames1, results);
        } else{
            System.out.println("Empty result.");
        }
	}

    public void displayPermitInformation(Connection connection) {
    	//Taking required input from the user
    	System.out.println("Enter Driver ID");
        String id = scanner.next();
        
      //Fetching the results from the table using select query
        SelectHelper selectHelper = new SelectHelper();
        List<String> columnNames = new ArrayList<>();
        
        columnNames.add("PermitID");
        columnNames.add("ID");
        columnNames.add("ZoneID");
        columnNames.add("LotName");
        columnNames.add("SpaceType");
        columnNames.add("PermitType");
        columnNames.add("StartDate");
        columnNames.add("ExpDate");
        columnNames.add("ExpTime");
        
        String condition = "ID = " + "'" + id + "'";
        List<List<Object>> results = selectHelper.select("Permits", columnNames, condition, null, null, null, connection);
        
        //Printing the result
        if (!results.isEmpty()) {
            tablePrint(columnNames, results);
        } else{
            System.out.println("Empty result.");
        }
	}
    
    public void displayAvailableSpaces(Connection connection) {
    	//Taking required input from the user
    	System.out.println("Enter Space Type");
        String space_type = scanner.nextLine();
        
        System.out.println("Enter Parking Lot");
        String lot_name = scanner.nextLine();
        
      //Fetching the results from the table using select query
        SelectHelper selectHelper = new SelectHelper();
        List<String> columnNames = new ArrayList<>();
        
        columnNames.add("Number");
        columnNames.add("ZoneID");
        String condition = "Number NOT IN ( " +
                "SELECT P.Number " +
                "FROM ParkingActivity P " +
                "INNER JOIN ( " +
                "    SELECT Number, MAX(Timestamp) as Timestamp " +
                "    FROM ParkingActivity " +
                "    GROUP BY Number" +
                ") V ON V.number = P.number " +
                "AND P.Timestamp = V.Timestamp " +
                "AND P.LastAction = 'parking' " +
                ") " +
                "AND LotName = " + "'" + lot_name + "'" +
                "AND ZoneID = 'V' " +
                "AND SpaceType = '" + space_type + "'";
        
        List<List<Object>> results = selectHelper.select("Spaces", columnNames, condition, null, null, "ZoneID", connection);
        
        //Printing the result
        if (!results.isEmpty()) {
            tablePrint(columnNames, results);
        } else{
            System.out.println("Empty result.");
        }
	}
}
