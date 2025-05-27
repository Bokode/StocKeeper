package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.DietRecipeDAOInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DietRecipeDAO implements DietRecipeDAOInterface {

    /* ─────────── Constantes de colonnes ─────────── */
    private static final String COL_DIET   = "diet";
    private static final String COL_RECIPE = "recipe";

    /* ─────────── Dépendances injectées ─────────── */
    private final DietDAO dietDAO;
    private final RecipeDAO recipeDAO;

    public DietRecipeDAO() {
        this(new DietDAO(), new RecipeDAO());
    }

    public DietRecipeDAO(DietDAO dietDAO, RecipeDAO recipeDAO) {
        this.dietDAO = dietDAO;
        this.recipeDAO = recipeDAO;
    }

    /* ────────────── CRUD Principal ─────────────── */

    public void addDietToRecipe(String dietLabel, String recipeLabel) throws AppException {
        final String sql = "INSERT INTO dietrecipe (" + COL_DIET + ", " + COL_RECIPE + ") VALUES (?, ?)";
        executeUpdate(sql, dietLabel, recipeLabel);
    }

    public void deleteDietFromRecipe(String dietLabel, String recipeLabel) throws AppException {
        final String sql = "DELETE FROM dietrecipe WHERE " + COL_DIET + " = ? AND " + COL_RECIPE + " = ?";
        executeUpdate(sql, dietLabel, recipeLabel);
    }

    public List<String> getDietsByRecipe(String recipeLabel) throws AppException {
        List<String> diets = new ArrayList<>();
        final String sql = "SELECT " + COL_DIET + " FROM dietrecipe WHERE " + COL_RECIPE + " = ?";

        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, recipeDAO.getRecipeIdByLabel(recipeLabel));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int dietId = rs.getInt(COL_DIET);
                    String dietLabel = dietDAO.getDietLabelById(dietId);
                    if (dietLabel != null) {
                        diets.add(dietLabel);
                    }
                }
            }
        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return diets;
    }

    /* ────────────── Méthode utilitaire ───────────── */

    private void executeUpdate(String sql, String dietLabel, String recipeLabel) throws AppException {
        try (Connection c = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, dietDAO.getDietIdByLabel(dietLabel));
            ps.setInt(2, recipeDAO.getRecipeIdByLabel(recipeLabel));
            ps.executeUpdate();

        } catch (SQLException e) {
            exceptionHandler(e);
        }
    }

    /* ────────────── Gestion des erreurs ───────────── */

    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("Authentification refusée.", e);
            case "22001" -> throw new DataSizeException("Valeurs non trouvée", e);
            case "23000" -> throw new AlreadyExistException("Association régime-recette déjà existante.", e);
            default       -> throw new RecipeOperationException("Erreur SQL " + e.getSQLState(), e);
        }
    }
}
