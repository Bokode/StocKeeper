package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.FoodType;

import java.sql.*;

public class FoodDAO {
    private static final String TBL        = "food";
    private static final String COL_ID     = "id";
    private static final String COL_LABEL  = "label";
    private static final String COL_TYPEID = "type_id";

    private final FoodTypeDAO typeDAO;

    public FoodDAO()            { this(new FoodTypeDAO()); }
    public FoodDAO(FoodTypeDAO t) { this.typeDAO = t; }

    /* ---------- READ ---------- */
    public int getFoodIdByLabel(String label) throws AppException {
        final String sql = "SELECT " + COL_ID + " FROM " + TBL + " WHERE " + COL_LABEL + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, label);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(COL_ID);
            }
        } catch (SQLException e) { exceptionHandler(e); }
        return -1;
    }

    public String getFoodLabelById(int id) throws AppException {
        final String sql = "SELECT " + COL_LABEL + " FROM " + TBL + " WHERE " + COL_ID + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(COL_LABEL);
            }
        } catch (SQLException e) { exceptionHandler(e); }
        return null;
    }

    /* ---------- CREATE ---------- */
    public void addFood(String label, FoodType type) throws AppException {
        final String sql = "INSERT INTO " + TBL + " (" + COL_LABEL + ", " + COL_TYPEID + ") VALUES (?, ?)";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, label);
            ps.setInt(2, typeDAO.getIdByLabel(type.getLabel()));
            ps.executeUpdate();
        } catch (SQLException e) { exceptionHandler(e); }
    }

    /* ---------- ERRORS ---------- */
    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("Authentification refusée.", e);
            case "23000" -> throw new AlreadyExistException("Aliment déjà existant.", e);
            case "22001" -> throw new DataSizeException("Chaîne trop longue.", e);
            default       -> throw new RecipeOperationException("Erreur SQL " + e.getSQLState(), e);
        }
    }
}