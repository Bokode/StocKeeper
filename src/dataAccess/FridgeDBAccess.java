package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FridgeDBAccess {

    private static final String URL = "jdbc:mysql://localhost:3306/stocKeeper?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "20JGRL9q9"; // Remplace par ton vrai mot de passe

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}