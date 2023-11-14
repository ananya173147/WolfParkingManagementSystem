package operations;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import helpers.InsertHelper;
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
            System.out.println("Enter your choice: \t");
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
                default:
                    System.out.println("Invalid Input, Please try again.");

            }
        }
    }

    public void CheckForViolation(Connection connection){
        //Taking the required input
        System.out.println("Enter the details for Citation -");
        //Tp add code for this part    
    
    }

    public void EnterCitationInfo(Connection connection){
        //insert operation on citation table
        Map<String, Object> columnValues = new HashMap<>();
        System.out.println("Citation ID: ");
        Integer cId = scanner.nextInt();
        columnValues.put("CitationID", cId);
        System.out.println("Enter Car Plate Number:");
        String plate = scanner.nextLine();
        columnValues.put("Plate", plate);
        System.out.println("Enter Space Number:");
        Integer s_no = scanner.nextInt();
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
        System.out.println("Enter Fee:");
        String fee = scanner.nextLine();
        columnValues.put("Fee", fee);
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
            Integer value1 = scanner.nextInt();
            columnValues.put("CitationID", value1);
        } else if (column == 2) {
            String value1 = scanner.nextLine();
            scanner.nextLine();
            columnValues.put("Plate", value1);
        } else if (column == 3) {
            Integer value1 = scanner.nextInt();
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
            Integer value1 = scanner.nextInt();
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
