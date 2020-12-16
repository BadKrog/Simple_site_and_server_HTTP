package server.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс проверяет исправно ли подключение к БД
 * */
    public class Test {
        private final String driverName = "com.mysql.jdbc.Driver";
        private final String connectionString = "jdbc:mysql://localhost:3306/RCSP_pract9";
        private final String login = "root";
        private final String password = "";
        public void run() {
            try {
                Class.forName(driverName);
            } catch (ClassNotFoundException e) {
                System.out.println("Can't get class. No driver found");
                e.printStackTrace();
                return;
            }
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(connectionString, login, password);
            } catch (SQLException e) {
                System.out.println("Can't get connection. Incorrect URL");
                e.printStackTrace();
                return;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Can't close connection");
                e.printStackTrace();
                return;
            }
        }
        public static void main(String[] args) {
            Test app = new Test();
            app.run();
            System.out.println("Если над этой записью нет ошибок, значит все исправно! Приступай к работе.");
        }
    }
