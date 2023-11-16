# WolfParkingManagementSystem

The Wolf Parking Management System is a database management system developed using JDBC for efficient parking space management.

## Installation

Follow these steps to install and set up the Wolf Parking Management System.

### Prerequisites

Make sure you have the following installed on your system:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) (version 8 or higher)
- [MySQL](https://www.mysql.com/) or [PostgreSQL](https://www.postgresql.org/) (Choose one based on your project)

### Database Configuration

1. Create a new database for the Wolf Parking Management System.

2. Update the database connection details in the `application.properties` file:

    ```properties
    jdbc:mysql://localhost:3306/your_database_name
    datasource.username=your_username
    datasource.password=your_password
    ```

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

4. Create a SQL Database

5. Open your web browser and go to [http://localhost:8080](http://localhost:8080) to access the Wolf Parking Management System.

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
