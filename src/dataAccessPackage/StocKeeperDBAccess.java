package dataAccessPackage;

import exceptionPackage.AppException;
import exceptionPackage.AuthenticationFailureException;
import exceptionPackage.DataBaseUnavailableException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class StocKeeperDBAccess
{

    private static final String URL = "jdbc:mysql://localhost:3306/food_recipes?serverTimezone=UTC"; // Remplacer test par le nom de la DB
    private static final String USER = "root";
    private static final String PASSWORD = "20JGRL9q9"; // Remplacer par ton vrai mot de passe
    private static StocKeeperDBAccess instance;


    private StocKeeperDBAccess() throws AppException
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

    public static synchronized StocKeeperDBAccess getInstance()
    {
        if (instance == null) {
            instance = new StocKeeperDBAccess();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
