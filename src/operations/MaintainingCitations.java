package operations;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import helpers.InsertHelper;
import helpers.SelectHelper;
import helpers.UpdateHelper;

public class MaintainingCitations {
	
    Scanner scanner = new Scanner(System.in);

    public void run(final Connection conn) throws Exception {

        while (true) {
            System.out.println("Choose an operation you want to perform:");
            System.out.println("1. Check for Violation");
            System.out.println("2. Enter Citation Info");
            System.out.println("3. Update Citation Info");
            System.out.println("4. Appeal Citation");
            System.out.println("5. Pay Citation");
            System.out.println("6. Exit Maintaining Citations");
            System.out.println("\nEnter your choice: \t");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice) {
                case 1:
                    CheckForViolation(conn);
                    break;
                
                case 2:
                    EnterCitationInfo(conn);
                    break;

                case 3:
                    UpdateCitation(conn);
                    break;

                case 4:
                    AppealCitation(conn);
                    break;

                case 5:
                    PayCitation(conn);
                    break;
                    
                case 6:
                	break;
                	
                default:
                    System.out.println("Invalid Input, Please try again.");
            }
            if (choice == 6){
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
    
    public void CheckForViolation(Connection connection) {
        // Taking required input from the user
        System.out.println("Enter the Plate");
        String plate = scanner.nextLine();

        System.out.println("Enter the Timestamp");
        String timestamp = scanner.nextLine();

        SelectHelper selectHelper = new SelectHelper();
        List<String> columnNames = new ArrayList<>();

        // Add columns for violation and violation type
        columnNames.add("MAX(Violation) AS isViolation");
        columnNames.add("MAX(ViolationType) AS ViolationType");

        // Check for violations in four cases:
        // 1. wrong zone parking/invalid permit
        // 2. no permit
        // 3. expired permit
        // 4. space type non-compliance
        // Return 1 if violation, else 0
        String tableName = "(SELECT PA.Plate, PA.Timestamp, "
                + "(CASE "
                + "WHEN V.PermitID IS NULL THEN 1 "
                + "WHEN DATE(PA.Timestamp) > P.ExpDate OR (DATE(PA.Timestamp) = P.ExpDate AND TIME(PA.Timestamp) > P.ExpTime) THEN 1 "
                + "WHEN PA.ZoneID NOT IN ("
                + "SELECT P.ZoneID FROM Permits P "
                + "WHERE P.LotName = PA.LotName AND P.PermitID = V.PermitID"
                + ") THEN 1 "
                + "WHEN P.SpaceType != S.SpaceType THEN 1 ELSE 0 "
                + "END) AS Violation, "
                + "(CASE "
                + "WHEN V.PermitID IS NULL THEN 'No Permit' "
                + "WHEN DATE(PA.Timestamp) > P.ExpDate OR (DATE(PA.Timestamp) = P.ExpDate AND TIME(PA.Timestamp) > P.ExpTime) THEN 'Expired Permit' "
                + "WHEN PA.ZoneID NOT IN ("
                + "SELECT P.ZoneID FROM Permits P "
                + "WHERE P.LotName = PA.LotName AND P.PermitID = V.PermitID"
                + ") THEN 'Wrong Zone Parking/Invalid Permit' "
                + "WHEN P.SpaceType != S.SpaceType THEN 'Space Type Non-Compliance' ELSE 'No Violation' "
                + "END) AS ViolationType "
                + "FROM ParkingActivity PA "
                + "LEFT JOIN Vehicles V ON PA.Plate = V.Plate "
                + "LEFT JOIN Permits P ON V.PermitID = P.PermitID "
                + "LEFT JOIN Spaces S ON PA.Number = S.Number "
                + "AND PA.ZoneID = S.ZoneID "
                + "AND PA.LotName = S.LotName) AS Subquery";

        String condition = "Subquery.Plate = '" + plate + "' AND Subquery.Timestamp = '" + timestamp + "'";

        List<List<Object>> results = selectHelper.select(tableName, columnNames, condition, null, null, null, connection);

        List<String> columnNames1 = new ArrayList<>();

        // Add columns for violation and violation type
        columnNames1.add("isViolation");
        columnNames1.add("ViolationType");

        // Printing the result
        if (!results.isEmpty()) {
            tablePrint(columnNames1, results);
        } else {
            System.out.println("Empty result.");
        }
    }


    public void EnterCitationInfo(Connection connection){
        //insert operation on citation table
        Map<String, Object> columnValues = new HashMap<>();
        System.out.println("Citation ID: ");
        String cId = scanner.nextLine();
        columnValues.put("CitationID", cId);
        System.out.println("Enter Car Plate Number:");
        String plate = scanner.nextLine();
        
        SelectHelper selectHelper1 = new SelectHelper();
        List<String> columnsForVehicle = List.of("Plate");
        List<List<Object>> objForVehicle = selectHelper1.select("Vehicles", columnsForVehicle, "Plate = \'" + plate + '\'', null, null, null, connection);
        
        if (objForVehicle.isEmpty()) {
            System.out.println("Error: Vehicle not found in the table. Please add the vehicle first.");
            return;
        }
        
        columnValues.put("Plate", plate);
        System.out.println("Enter Space Number:");
        Integer s_no = Integer.parseInt(scanner.nextLine());
        columnValues.put("Number", s_no );
        System.out.println("Enter Zone ID:");
        String zID = scanner.nextLine();
        columnValues.put("ZoneID", zID);
        System.out.println("Enter LotName:");
        String lName = scanner.nextLine();
        columnValues.put("LotName", lName);

        System.out.println("Enter Payment Status:");
        String pStatus = scanner.nextLine();
        columnValues.put("PayStatus", pStatus);
	    
	System.out.println("Enter Category:");
        String pCategory = scanner.nextLine();
	columnValues.put("Category", pCategory);
	    
        System.out.println("Enter Fee:");
        Float fee = Float.parseFloat(scanner.nextLine());
        List<String> columnsForDriverDisability = List.of("isDisabled");
        SelectHelper selectHelper = new SelectHelper();
        String tableName = "Vehicles JOIN Drivers ON Vehicles.ID = Drivers.ID";
		List<List<Object>> objForDriverDisability = selectHelper.select(tableName, columnsForDriverDisability, "Plate = \'" + plate + '\'', null, null, null, connection);
        
		String statusOfDriver = objForDriverDisability.get(0).get(0).toString();
		System.out.println("The driver is disabled: "+statusOfDriver);
		if (statusOfDriver.equals("true")) {
			System.out.println("The driver is disabled, discounting by 50%, " + (fee/2));
			columnValues.put("Fee", fee/2);
		}
		else {
			columnValues.put("Fee", fee);
		}
        System.out.println("Enter CitationDate:");
        String citationDate = scanner.nextLine();
        columnValues.put("CitationDate", citationDate);
        System.out.println("Enter CitationTime:");
        String citationTime = scanner.nextLine();
        columnValues.put("CitationTime", citationTime);
      
        InsertHelper insertHelper = new InsertHelper();
        insertHelper.insertQuery(columnValues, "Citations", connection);
    }

     public void UpdateCitation(Connection conn) {
        //update operation on basic info for driver
        Map<String, Object> columnValues = new HashMap<>();

        System.out.println("Choose the column to update:");
        System.out.println("1. Citation ID");
        System.out.println("2. Plate");
        System.out.println("3. Space Number");
        System.out.println("4. Zone ID");
        System.out.println("5. LotName");
        System.out.println("6. PayStatus");
        System.out.println("7. Fee");
        System.out.println("8. CitationDate");
        System.out.println("9. CitationTime");
        System.out.println("Enter your choice: \t");
        int column = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the Citation ID for which you want to update field:");
        String cID = scanner.nextLine();
        System.out.println("Enter the new value:");

        if (column == 1) {
            Integer value1 = Integer.parseInt(scanner.nextLine());
            columnValues.put("CitationID", value1);
        } else if (column == 2) {
            String value1 = scanner.nextLine();
            scanner.nextLine();
            columnValues.put("Plate", value1);
        } else if (column == 3) {
        	Integer value1 = Integer.parseInt(scanner.nextLine());
            columnValues.put("Number", value1);
        } else if (column == 4) {
            String value1 = scanner.nextLine();
            columnValues.put("ZoneID", value1);
        }
        else if (column == 5) {
            String value1 = scanner.nextLine();
            columnValues.put("LotName", value1);
        }
        else if (column == 6) {
            String value1 = scanner.nextLine();
            columnValues.put("PayStatus", value1);
        }
        else if (column == 7) {
        	Integer value1 = Integer.parseInt(scanner.nextLine());
            columnValues.put("Fee", value1);
        }
        else if (column == 8) {
            String value1 = scanner.nextLine();
            columnValues.put("CitationDate", value1);
        }
        else if (column == 9) {
            String value1 = scanner.nextLine();
            columnValues.put("CitationTime", value1);
        }

        String condition = "CitationID=" + ("\"") + cID + ("\"");
        UpdateHelper updateHelper = new UpdateHelper();
        updateHelper.update("Citations", condition, columnValues, conn);
    }

    public void AppealCitation(Connection connection){
        Map<String, Object> columnValues = new HashMap<>();

        //Taking the required input and assigning the values
        System.out.println("Enter the details for appealing the citation -");
        System.out.println("Enter the Citation ID:");

        String cID = scanner.nextLine();
        columnValues.put("PayStatus", "appealed");

        String condition = "CitationID=" + ("\"") + cID + ("\"");
        UpdateHelper updateHelper = new UpdateHelper();
        updateHelper.update("Citations", condition, columnValues, connection);
    }

    public void PayCitation(Connection connection){
        Map<String, Object> columnValues = new HashMap<>();

        //Taking the required input and assigning the values
        System.out.println("Enter the details for paying the citation -");
        System.out.println("Enter the Citation ID:");

        String cID = scanner.nextLine();

        columnValues.put("PayStatus", "paid");

        String condition = "CitationID=" + ("\"") + cID + ("\"");
        UpdateHelper updateHelper = new UpdateHelper();
        updateHelper.update("Citations", condition, columnValues, connection);
    }

}
