package dataAccessPackage;

import exceptionPackage.AppException;
import exceptionPackage.AuthenticationFailureException;
import exceptionPackage.DataBaseUnavailableException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class FridgeDBAccess {

    private static final String URL = "jdbc:mysql://localhost:3306/food_recipes?serverTimezone=UTC"; // Remplacer test par le nom de la DB
    private static final String USER = "root";
    private static final String PASSWORD = "ppcc"; // Remplacer par ton vrai mot de passe
    private static FridgeDBAccess instance;


    private FridgeDBAccess() throws AppException
    {
        try {
            this.getConnection();
        } catch (SQLException exception) {
            switch (exception.getSQLState()) {
                case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", exception);
                case "28000" -> throw new AuthenticationFailureException("Authentification refusée.", exception);
                default -> throw new AppException("Erreur de connexion à la base de données.", exception);
            }
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
