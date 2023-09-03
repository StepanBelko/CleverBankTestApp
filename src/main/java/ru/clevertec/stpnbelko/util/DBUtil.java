package ru.clevertec.stpnbelko.util;

import java.sql.*;

import static ru.clevertec.stpnbelko.util.AppConstants.*;

public class DBUtil {

    public static Connection getConnection() {

        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return connection;
    }


    public static void release(Connection connection, Statement statement, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
//                System.out.println("Connection is closed -> " + connection);
            }
            if (statement != null) {
                statement.close();
//                System.out.println("Statement is closed -> " + statement);
            }
            if (preparedStatement != null) {
                preparedStatement.close();
//                System.out.println("Prepared statement is closed -> " + preparedStatement);
            }
            if (resultSet != null) {
                resultSet.close();
//                System.out.println("ResultSet is closed -> " + resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
