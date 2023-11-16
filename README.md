# WolfParkingManagementSystem

The Wolf Parking Management System is a database management system developed using JDBC for efficient parking space management.

## Installation

Follow these steps to install and set up the Wolf Parking Management System.

### Prerequisites

Make sure you have the following installed on your system:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (version 8 or higher)
- [MariaDB](https://mariadb.com/downloads/) 
- [PuTTY](https://putty.org/)
- 
### Build and Run

1. Clone the repository:

    ```bash
    git clone https://github.com/your-username/WolfParkingManagementSystem.git
    ```
2. Navigate to the project directory:

    ```bash
    cd WolfParkingManagementSystem
    ```
3. Install dependencies
  Dependencies mentioned in the Prerequisites should be installed.

### Database Configuration
There are two ways to access the MariaDB database on the remote server. The course project can be done with JDBC.
Using the MariaDB terminal on the remote server
1.	You need an ssh client. We recommend Putty for windows. For Mac users use the Terminal app.
a.	May require a VPN connection for off campus.
https://oit.ncsu.edu/campus-it/campus-data-network/network-security/vpn/ 
b.	Remote Host:
Putty: remote.eos.ncsu.edu
Terminal app: ssh yourUnityID@remote.eos.ncsu.edu
2.	Launch your ssh client and open a secured connection to the remote host 
a)	enter your unity ID (if applicable)
b)	enter your unity ID password
3.	Access your database
/mnt/apps/public/CSC/Mysql-Shell/bin/mysql -u your_mysql_acct -p -h classdb2.csc.ncsu.edu 
(replace your_mysql_acct with your unity ID)
4.	Enter password: (this is your 9-digit student id)
MySQL Shell 8.0.25
Copyright (c) 2016, 2021, Oracle and/or its affiliates.
Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
Other names may be trademarks of their respective owners.
Type '\help' or '\?' for help; '\quit' to exit.
Creating a session to 'uid@classdb2.csc.ncsu.edu'
Fetching schema names for autocompletion... Press ^C to stop.
Your MySQL connection id is xxxxx
Server version: 5.5.68-MariaDB MariaDB Server
No default schema selected; type \use to set one.
MySQL classdb2.csc.ncsu.edu:3306 JS >

5.	At the MySQL classdb2.csc.ncsu.edu:3306 JS > prompt type \sql This will switch your command line to SQL instead of JS.
6.	To exit the command line, type \q and press Enter.
7.	Type use your_unity_ID; to activate your database
8.	Type show tables; and other sql commands

Setup Eclipse with a JDBC connector to connect to a MariaDB Server version 5.5.68
1.	Open Eclipse IDE (You can install Eclipse following https://www.eclipse.org/downloads/packages/installer)

2.	From the File menu select New  Java Project  


3.	Give your project a name, like “DBConnect”. Uncheck module-info.java file as it’s not required (Next Window). You can use the default values for all the other settings.  

4.	Press Finish to create the project. Eclipse might ask you whether you want to switch to the Java perspective. If so, say yes. 


5.	You should see an empty project which looks something like this.  

6.	Install Maria DB connector JAR file from https://downloads.mariadb.com/Connectors/java/latest/mariadb-java-client-2.3.0.jar


7.	Right-click on project  build path  add external archives  select the JAR file you just downloaded
 

8.	Add a new Java class in the src folder, name it as DBConnect and click Finish.    

9.	Paste the following code in DBConnect.java file
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnect{
public static void main(String args[]) {
Connection conn = null;
String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/";
String user = "YourUnityId";
String pswd = "YourStudentId";
try {
conn = DriverManager.getConnection(url, user, pswd);
} catch (SQLException e){
e.printStackTrace();
} finally {
close(conn);}
System.out.println("Successfully connected to DB");
}

static void close(Connection connection) {
if(connection != null) {
try {
connection.close();
} catch(Throwable whatever) {}
}
}
}
Note: string user is your unity id, and string pswd is your student id number. 

10.	Run the program and you should get no errors. 

### Usage

The system has a main menu that gives the user the following options:
1. Create Schema and Load Demo Data
2. Information Processing
3. Maintaining Permits/Vehicles
4. Maintaining Citations
5. Report Generation
6. Exit App
When prompted, the user enters a number 0-6 corresponding with what kind of action they would like to take. For each menu option in numbers 2-5 there is a submenu displayed that has specific operations listed. Our system has a public App class (App.java) that facilitates switching between the main menu options and separate classes for each related group of operations. This was done to break the application into more manageable and maintainable pieces of code. 

Our program also contains helper classes:
1. InitDB: to create and initialize database schema, and load demo data into the database.
2. ConnHelper: for establishing and closing a connection to a MariaDB database.
3. InsertHelper, UpdateHelper, SelectHelper, and DeleteHelper: to handle the preparation and execution of the queries, displaying a success message if rows are inserted, and printing any SQL exceptions that occur during the process. 


## Contributing

If you'd like to contribute to the project, please follow the [contribution guidelines](CONTRIBUTING.md).

## License

This project is licensed under the [MIT License](LICENSE).
