import helpers.*;
import operations.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App{
    public static void main(String args[]) {
        ConnHelper connHelper = new ConnHelper();
        Connection conn = null;
        try{
            try (Scanner scanner = new Scanner(System.in)){
                conn = connHelper.getConnection();

                System.out.println("Welcome to WolfParking Management System!\n\n");

                while (true) {
                    System.out.println("Choose an operation you want to perform:");
                    System.out.println("1. Create Schema and Load Demo Data");
                    System.out.println("2. Information Processing");
                    System.out.println("3. Maintaining Permits/Vehicles");
                    System.out.println("4. Maintaining Citations");
                    System.out.println("5. Report Generation");
                    System.out.println("8. Exit App");
                    System.out.println("Enter your choice: \t");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            InitDB initDB = new InitDB();
                            initDB.createSchema(conn);
//                            initDB.loadDemoData(conn);
                            break;

                        case 2:
                            InformationProcessing informationProcessing = new InformationProcessing();
                            informationProcessing.run(conn);
                            break;

                        case 3:
                            MaintainingPermitVehicles assignments = new MaintainingPermitVehicles();
                            assignments.run(conn);
                            break;
                        
                        case 4:
                            MaintainingCitations maintainingPayments = new MaintainingCitations();
                            maintainingPayments.run(conn);
                            break;

                        case 5:
                            ReportsGeneration reportsGeneration = new ReportsGeneration();
                            reportsGeneration.run(conn);
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

            }catch (SQLException e) {
                System.out.println("Exception Occurred: " + e.getMessage());
            } finally {
                System.out.println("Exiting...");
                connHelper.close(conn);
            }
        } catch (Exception e) {
            System.out.println("Exception Occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}