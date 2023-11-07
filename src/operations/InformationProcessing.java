package operations;

import helpers.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.lang.Exception;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class InformationProcessing {

    Scanner scanner = new Scanner(System.in);

    public void run(final Connection conn) throws Exception {

        while (true) {
            System.out.println("Choose an operation you want to perform:");
            System.out.println("Enter your choice: \t");
            int choice = scanner.nextInt();
            scanner.nextLine();
        }
    }
}
