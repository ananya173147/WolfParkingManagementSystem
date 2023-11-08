package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.sql.SQLException;

public class UpdateHelper {
    public void update(String tableName, String condition, Map<String, Object> columnValues, Connection connection){
        try{
            // Prepare the UPDATE query
            StringBuilder sqlBuilder = new StringBuilder("UPDATE ");
            sqlBuilder.append(tableName).append(" SET ");
            for (String columnName : columnValues.keySet()) {
                sqlBuilder.append(columnName).append("=?,");
            }
            sqlBuilder.setCharAt(sqlBuilder.length() - 1, ' ');
            if (condition != null && !condition.isEmpty()) {
                sqlBuilder.append("WHERE ").append(condition);
            }
            String sql = sqlBuilder.toString();
            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 1;
            for (Object columnValue : columnValues.values()) {
                statement.setObject(index++, columnValue);
            }

            // Execute the UPDATE query
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(rowsUpdated + " row(s) have been updated in " + tableName + ".");
            } else {
                System.out.println("No rows have been updated in " + tableName + ".");
            }

            // Close the resources
            statement.close();
            // connection.close();
        } catch (SQLException e) {
            System.out.println("Exception at Update");
            e.printStackTrace();
        }
    }
}