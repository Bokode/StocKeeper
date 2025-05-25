package dataAccessPackage;

import java.sql.*;

import exceptionPackage.*;
import interfacePackage.FoodTypeDAOInterface;
import modelPackage.FoodType;


public class FoodTypeDAO implements FoodTypeDAOInterface {

    private static final String COL_ID    = "id";
    private static final String COL_LABEL = "label";

    public int getIdByLabel(String label) throws AppException {
        final String sql = "SELECT " + COL_ID + " FROM food_type WHERE " + COL_LABEL + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, label);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(COL_ID);
            }
        } catch (SQLException e) { exceptionHandler(e); }
        return -1;
    }

    public String getLabelById(int id) throws AppException {
        final String sql = "SELECT " + COL_LABEL + " FROM food_type WHERE " + COL_ID + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(COL_LABEL);
            }
        } catch (SQLException e) { exceptionHandler(e); }
        return null;
    }

    public FoodType getFoodTypeById(int id) throws AppException {
        final String sql = "SELECT label FROM foodtype WHERE id = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FoodType(rs.getString("label"));
                }
            }
        } catch (SQLException e) {
            throw new RecipeOperationException("Erreur lors de la récupération du type d'aliment", e);
        }
        return null;
    }


    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("Authentification refusée.", e);
            case "23000" -> throw new AlreadyExistException("Type d'aliment déjà existant.", e);
            case "22001" -> throw new DataSizeException("Chaîne trop longue.", e);
            default       -> throw new RecipeOperationException("Erreur SQL " + e.getSQLState(), e);
        }
    }
}