package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.RecipeMaterialDAOInterface;
import modelPackage.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeMaterialDAO implements RecipeMaterialDAOInterface {

    /* ───────────────── Constantes de colonnes ───────────────── */
    private static final String COL_RECIPE   = "recipe";
    private static final String COL_MATERIAL = "material";

    /* ───────────────── Dépendances injectées ────────────────── */
    private final RecipeDAO       recipeDAO;
    private final MaterialDAO     materialDAO;
    private final TypeMaterialDAO typeDAO;

    public RecipeMaterialDAO() {
        this(new RecipeDAO(), new MaterialDAO(), new TypeMaterialDAO());
    }

    public RecipeMaterialDAO(RecipeDAO recipeDAO, MaterialDAO materialDAO, TypeMaterialDAO typeDAO) {
        this.recipeDAO   = recipeDAO;
        this.materialDAO = materialDAO;
        this.typeDAO     = typeDAO;
    }

    /* ─────────────────────── CRUD ───────────────────────────── */


    public void addMaterialToRecipe(String recipeLabel, String materialLabel) throws AppException {
        final String sql = "INSERT INTO recipematerial (" + COL_RECIPE + ", " + COL_MATERIAL + ") VALUES (?, ?)";
        executeUpdate(sql, recipeLabel, materialLabel);
    }


    public void deleteMaterialFromRecipe(String recipeLabel, String materialLabel) throws AppException {
        final String sql = "DELETE FROM recipematerial WHERE " + COL_RECIPE + " = ? AND " + COL_MATERIAL + " = ?";
        executeUpdate(sql, recipeLabel, materialLabel);
    }


    public List<Material> getMaterialsByRecipe(String recipeLabel) throws AppException {
        List<Material> list = new ArrayList<>();
        final String sql = "SELECT " + COL_MATERIAL + " FROM recipematerial WHERE " + COL_RECIPE + " = ?";

        try (Connection c = StocKeeperDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, recipeDAO.getRecipeIdByLabel(recipeLabel));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int matId      = rs.getInt(COL_MATERIAL);
                    String matLbl  = materialDAO.getMaterialLabelById(matId);
                    int typeId     = getTypeIdByMaterialId(matId);
                    String typeLbl = typeDAO.getLabelById(typeId);
                    list.add(new Material(matLbl));
                }
            }
        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return list;
    }

    /* ─────────────────── Helpers internes ───────────────────── */

    private void executeUpdate(String sql, String recipeLabel, String materialLabel) throws AppException {
        try (Connection c = StocKeeperDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, recipeDAO.getRecipeIdByLabel(recipeLabel));
            ps.setInt(2, materialDAO.getMaterialIdByLabel(materialLabel));
            ps.executeUpdate();

        } catch (SQLException e) {
            exceptionHandler(e);
        }
    }


    private int getTypeIdByMaterialId(int matId) throws AppException {
        final String sql = "SELECT type_id FROM material WHERE id = ?";
        try (Connection c = StocKeeperDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, matId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("type_id");
            }
        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return -1;
    }

    /* ─────────────── Gestion des erreurs SQL ────────────────── */
    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("Authentification refusée.", e);
            case "22001" -> throw new DataSizeException("Chaîne trop longue pour un champ.", e);
            case "23000" -> throw new AlreadyExistException("Lien recette‑matériel déjà présent.", e);
            default       -> throw new RecipeOperationException("Erreur SQL " + e.getSQLState(), e);
        }
    }
}
