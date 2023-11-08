package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class InsertHelper {
    public void insertQuery(Map<String, Object> columnValues, String tableName, Connection connection) {
        try{
            // Prepare the INSERT query
            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
            sqlBuilder.append(tableName).append(" (");
            for (String columnName : columnValues.keySet()) {
                sqlBuilder.append(columnName).append(",");
            }
            sqlBuilder.setCharAt(sqlBuilder.length() - 1, ')');
            sqlBuilder.append(" VALUES (");
            for (int i = 0; i < columnValues.size(); i++) {
                sqlBuilder.append("?,");
            }
            sqlBuilder.setCharAt(sqlBuilder.length() - 1, ')');
            String sql = sqlBuilder.toString();
            PreparedStatement statement = connection.prepareStatement(sql);
            int index = 1;
            for (Object columnValue : columnValues.values()) {
                statement.setObject(index++, columnValue);
            }

            // Execute the INSERT query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new row has been inserted into " + tableName + ".");
            }

            // Close the resources
            statement.close();
        } catch (SQLException e) {
            System.err.println("Exception at Insert");
            e.printStackTrace();
        }
    }
}
