package dataAccessPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class FridgeDBAccess {

    private static final String URL = "jdbc:mysql://localhost:3306/food_recipes?serverTimezone=UTC"; // Remplacer test par le nom de la DB
    private static final String USER = "root";
    private static final String PASSWORD = "20JGRL9q9"; // Remplacer par ton vrai mot de passe
    private static FridgeDBAccess instance;


    private FridgeDBAccess()
    {
        try {
            this.getConnection();
            System.out.println("Connected to database successfully!");
        } catch (SQLException exception) {
            System.err.println("Connection failed: " + exception.getMessage());
        }
    }

    public static synchronized FridgeDBAccess getInstance()
    {
        if (instance == null) {
            instance = new FridgeDBAccess();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}