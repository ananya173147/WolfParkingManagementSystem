package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteHelper {
    public void delete(String tableName, String condition, Connection connection) {
        try {
            // Prepare the DELETE query
            StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ");
            sqlBuilder.append(tableName);
            if (condition != null && !condition.isEmpty()) {
                sqlBuilder.append(" WHERE ").append(condition);
            }
            String sql = sqlBuilder.toString();
            PreparedStatement statement = connection.prepareStatement(sql);

            // Execute the DELETE query
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(rowsDeleted + " row(s) have been deleted from " + tableName + ".");
            } else {
                System.out.println("No rows have been deleted from " + tableName + ".");
            }

            // Close the resources
            statement.close();
            // connection.close();
        } catch (SQLException e) {
            System.out.println("Exception at Delete");
            e.printStackTrace();
        }
    }
}