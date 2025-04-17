package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnexion {
    private static SingletonConnexion uniqueInstance;
    private static Connection connection;

    private SingletonConnexion() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library",
                    "root",
                    "ppcc"
            );
            System.out.println("Connected to database successfully!");
        } catch (SQLException exception) {
            System.err.println("Connection failed: " + exception.getMessage());
        }
    }

    public static SingletonConnexion getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new SingletonConnexion();
        }
        return uniqueInstance;
    }

    public Connection getConnection() {
        return connection;
    }
}
