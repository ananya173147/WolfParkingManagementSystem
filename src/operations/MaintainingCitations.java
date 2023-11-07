package operations;

import java.sql.Connection;
import java.util.Scanner;

public class MaintainingCitations {
	
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
