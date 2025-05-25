package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.TypeMaterialDAOInterface;

import java.sql.*;


public class TypeMaterialDAO implements TypeMaterialDAOInterface {

    private static final String COL_ID    = "id";
    private static final String COL_LABEL = "label";

    /* ---------- CRUD principal ---------- */

    public void addTypeMaterial(String label) throws AppException
    {
        final String sql = "INSERT INTO typematerial (" + COL_LABEL + ") VALUES (?)";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, label);
            ps.executeUpdate();
        } catch (SQLException e) { exceptionHandler(e); }
    }

    public void deleteTypeMaterial(String label) throws AppException
    {
        final String sql = "DELETE FROM typematerial WHERE " + COL_LABEL + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, label);
            ps.executeUpdate();
        } catch (SQLException e) { exceptionHandler(e); }
    }

    public int getIdByLabel(String label) throws AppException {
        final String sql = "SELECT " + COL_ID + " FROM typematerial WHERE " + COL_LABEL + " = ?";
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
        final String sql = "SELECT " + COL_LABEL + " FROM typematerial WHERE " + COL_ID + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(COL_LABEL);
            }
        } catch (SQLException e) { exceptionHandler(e); }
        return null;
    }

    /* ---------- Gestion centralisée des erreurs ---------- */
    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("Authentification refusée.", e);
            case "22001" -> throw new DataSizeException("Chaîne trop longue pour un champ.", e);
            case "23000" -> throw new AlreadyExistException("Type de matériel déjà existant.", e);
            default       -> throw new RecipeOperationException("Erreur SQL " + e.getSQLState(), e);
        }
    }
}