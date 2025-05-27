package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.DietDAOInterface;
import modelPackage.Diet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DietDAO implements DietDAOInterface {

    private static final String COL_ID    = "id";
    private static final String COL_LABEL = "label";
    private static final String TABLE     = "diet";

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


    public void addDiet(String label) throws AppException {
        final String sql = "INSERT INTO " + TABLE + " (" + COL_LABEL + ") VALUES (?)";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, label);
            ps.executeUpdate();

        } catch (SQLException e) {
            exceptionHandler(e);
        }
    }

    public int getDietIdByLabel(String label) throws AppException {
        final String sql = "SELECT " + COL_ID + " FROM " + TABLE + " WHERE " + COL_LABEL + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, label);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(COL_ID);
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return -1;
    }

    public String getDietLabelById(int id) throws AppException {
        final String sql = "SELECT " + COL_LABEL + " FROM " + TABLE + " WHERE " + COL_ID + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(COL_LABEL);
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return null;
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
