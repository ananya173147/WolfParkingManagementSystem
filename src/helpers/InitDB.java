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
        String schemaPath = "lib/create_schema.sql"; //Move to config

        executeSQLFile(schemaPath, conn);
        
    }

    public void loadNoConstraintData(final Connection conn){
        String noConstraintPath = "lib/no_constraint.sql"; //Move to config

        executeSQLFile(noConstraintPath, conn);
        
    }

    public void loadConstraintData(final Connection conn){
        String constraintPath = "lib/constraint.sql"; //Move to config

        executeSQLFile(constraintPath, conn);
        
    }

    public void executeSQLFile(String filePath, Connection conn){
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
            System.out.println("SQL File executed Successfully");
        }
    }
}