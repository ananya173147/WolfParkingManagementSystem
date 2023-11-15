package helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SelectHelper {
    public List<List<Object>> select(String tableName, List<String> columnNames, String condition, String groupBy, String having, String orderBy, Connection connection){
        try{
            StringBuilder sqlBuilder = new StringBuilder("SELECT ");
            for (String columnName : columnNames) {
                sqlBuilder.append(columnName).append(",");
            }
            sqlBuilder.setCharAt(sqlBuilder.length() - 1, ' ');
            sqlBuilder.append("FROM ").append(tableName);
            if (condition != null && !condition.isEmpty()) {
                sqlBuilder.append(" WHERE ").append(condition);
            }
            if (groupBy != null && !groupBy.isEmpty()) {
                sqlBuilder.append(" GROUP BY ").append(groupBy);
            }
            if (having != null && !having.isEmpty()) {
                sqlBuilder.append(" HAVING ").append(having);
            }
            if (orderBy != null && !orderBy.isEmpty()) {
                sqlBuilder.append(" ORDER BY ").append(orderBy);
            }
            String sql = sqlBuilder.toString();
            PreparedStatement statement = connection.prepareStatement(sql);

            // Execute the SELECT query
            ResultSet resultSet = statement.executeQuery();

            // Get the results
            List<List<Object>> results = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    row.add(value);
                }
                results.add(row);
            }

            // Close the resources
            resultSet.close();
            statement.close();
           // connection.close();

            return results;
        }catch (SQLException e) {
            System.out.println("Exception at Select");
            e.printStackTrace();
            List<List<Object>> dummy = new ArrayList<>();
            return dummy;
        }
    }
}