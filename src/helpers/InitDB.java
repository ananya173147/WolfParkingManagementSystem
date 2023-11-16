package helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {
    
    public void createSchema(final Connection conn){
        String schemaPath = "src/lib/create_schema.sql";
        executeSQLFile(schemaPath, "schema", conn);
    }

    public void loadDemoData(final Connection conn){
        String noConstraintPath = "src/lib/demo_data.sql";
        executeSQLFile(noConstraintPath, "data", conn);
    }

    public void executeSQLFile(String filePath, String message, Connection conn){
        try{
            String sqlFileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] sqlStatements = sqlFileContent.split(";");
            
            for (String sql : sqlStatements) {
                if (!sql.trim().isEmpty()) {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(sql);
                    } catch (SQLException e) {
                        System.err.println("Error executing SQL statement: " + sql);
                        e.printStackTrace();
                    }
                }
            }
        }catch (IOException e) {
            System.err.println("Error reading the SQL file");
            e.printStackTrace();
        } finally {
        	if(message.equals("schema")) {
        		System.out.println("Schema created successfully");
        	}
        	else {
        		System.out.println("Loaded demo data successfully\n");
        	}
        }
    }
}