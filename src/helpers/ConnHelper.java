package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnHelper {

    public Connection getConnection() throws Exception{
        Connection conn = null;

        String url = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/agarg27";
        String user = "agarg27";
        String pswd = "";
        
        try{
            conn = DriverManager.getConnection(url, user, pswd);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
            
        return conn;
    }

    public void close(Connection connection) {
            
        if(connection != null) {
            try {
            connection.close();
            } catch(Throwable whatever) {}
        }
    }
}