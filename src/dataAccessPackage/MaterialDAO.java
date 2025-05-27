package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.MaterialDAOInterface;
import java.sql.*;

public class MaterialDAO implements MaterialDAOInterface {

    /* ────────── Constantes de colonnes ────────── */
    private static final String COL_ID    = "id";
    private static final String COL_LABEL = "label";
    private static final String COL_TYPE  = "type_id";

    /* ────────── Dépendances injectées ─────────── */
    private final TypeMaterialDAO typeDAO;

    public MaterialDAO() {
        this(new TypeMaterialDAO());
    }

    /* Pour l’injection (tests unitaires ou DI) */
    public MaterialDAO(TypeMaterialDAO typeDAO) {
        this.typeDAO = typeDAO;
    }

    /* ───────────── CRUD principal ─────────────── */

    public void addMaterial(String label, String typeLabel) throws AppException {
        final String sql = "INSERT INTO material (" + COL_LABEL + ", " + COL_TYPE + ") VALUES (?, ?)";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, label);
            ps.setInt(2, typeDAO.getIdByLabel(typeLabel));
            ps.executeUpdate();

        } catch (SQLException e) { exceptionHandler(e); }
    }

    public int getMaterialIdByLabel(String label) throws AppException {
        final String sql = "SELECT " + COL_ID + " FROM material WHERE " + COL_LABEL + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, label);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(COL_ID);
            }
        } catch (SQLException e) { exceptionHandler(e); }
        return -1;
    }

    public String getMaterialLabelById(int id) throws AppException {
        final String sql = "SELECT " + COL_LABEL + " FROM material WHERE " + COL_ID + " = ?";
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(COL_LABEL);
            }
        } catch (SQLException e) { exceptionHandler(e); }
        return null;
    }

    /* ─────────── Gestion centralisée des erreurs ─────────── */

    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("Identifiant ou mot de passe incorrect.", e);
            case "22001" -> throw new DataSizeException("Chaîne trop longue pour le champ correspondant.", e);
            case "23000" -> throw new AlreadyExistException("Matériel déjà existant.", e);
            default      -> throw new RecipeOperationException("Erreur SQL " + e.getSQLState(), e);
        }
    }
}
