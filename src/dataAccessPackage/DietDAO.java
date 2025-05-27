package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.DietDAOInterface;
import modelPackage.Diet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DietDAO implements DietDAOInterface {
    public List<Diet> getAllDiets() throws AppException {
        List<Diet> dietList = new ArrayList<>();
        final String sql = "SELECT label FROM Diet";

        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String label = rs.getString("label");
                Diet diet = new Diet(label);
                dietList.add(diet);
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return dietList;
    }

    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("Identifiant ou mot de passe incorrect.", e);
            case "22001" -> throw new DataSizeException("Chaîne trop longue pour le champ correspondant.", e);
            case "23000" -> throw new AlreadyExistException("Régime déjà existant.", e);
            default      -> throw new RecipeOperationException("Erreur SQL " + e.getSQLState(), e);
        }
    }
}
