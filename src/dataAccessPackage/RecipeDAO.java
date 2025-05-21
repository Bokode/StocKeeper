package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.RecipeDAOInterface;
import modelPackage.Recipe;
import modelPackage.RecipeWithExpiredFood;
import modelPackage.SeasonalRecipe;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO implements RecipeDAOInterface
{

    @Override
    public List<Recipe> getAllRecipes() throws AppException
    {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipe";
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery())
        {

            while (rs.next())
            {
                recipes.add(mapResultSetToRecipe(rs));
            }

        } catch (SQLException e)
        {
            exceptionHandler(e);
        }

        return recipes;
    }

    @Override
    public Recipe getRecipeByLabel(String label) throws AppException
    {
        String query = "SELECT * FROM recipe WHERE label = ?";
        Recipe recipe = null;
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {

            stmt.setString(1, label);
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    recipe = mapResultSetToRecipe(rs);
                }
            }

        } catch (SQLException e)
        {
            exceptionHandler(e);
        }

        return recipe;
    }

    @Override
    public Integer updateRecipe(Integer id, String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, Integer type) throws AppException
    {
        String query = "UPDATE recipe SET label = ?, description = ?, caloricIntake = ?, isCold = ?, lastDateDone = ?, timeToMake = ?, type = ? WHERE id = ?";
        return executeRecipeUpdate(query, label, description, caloricIntake, isCold, lastDateDone, timeToMake, type, id);
    }

    @Override
    public Integer deleteRecipe(Integer id) throws AppException
    {
        String query = "DELETE FROM recipe WHERE id = ?";
        Integer rowsDeleted = 0;
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setInt(1, id);
            rowsDeleted = stmt.executeUpdate();

        } catch (SQLException e)
        {
            exceptionHandler(e);
        }

        return rowsDeleted;
    }

    @Override
    public Integer addRecipe(String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, Integer type) throws AppException
    {
        String query = "INSERT INTO recipe (label, description, caloricIntake, isCold, lastDateDone, timeToMake, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return executeRecipeUpdate(query, label, description, caloricIntake, isCold, lastDateDone, timeToMake, type, null);
    }

    // Méthode utilitaire pour factoriser l'insertion ou la mise à jour d'une recette

    private Integer executeRecipeUpdate(String query, String label, String description, Integer caloricIntake,
                                    boolean isCold, Date lastDateDone, Integer timeToMake, Integer type, Integer id) throws AppException
            {
        Integer rows = 0;
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, label);
            stmt.setString(2, description);
            stmt.setInt(3, caloricIntake);
            stmt.setBoolean(4, isCold);
            stmt.setDate(5, lastDateDone);
            stmt.setInt(6, timeToMake);
            stmt.setInt(7, type);

            if (id != null) {
                stmt.setInt(8, id); // pour update
            }

            rows = stmt.executeUpdate();

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return rows;
    }

    // transforme un ResultSet en objet Recipe

    private Recipe mapResultSetToRecipe(ResultSet rs) throws SQLException {
        return new Recipe(
                rs.getInt("id"),
                rs.getString("label"),
                rs.getString("description"),
                rs.getInt("caloricIntake"),
                rs.getDate("lastDateDone"),
                rs.getInt("timeToMake"),
                rs.getBoolean("isCold"),
                rs.getInt("type")
        );
    }

    // Méthode pour gérer les exceptions SQL

    private void exceptionHandler(SQLException e) throws AppException
    {
        String state = e.getSQLState();
        switch (state) {
            case "08S01":
                throw new DataBaseUnavailableException("La base de données est indisponible.", e);
            case "28000":
                throw new AuthenticationFailureException("L'utilisateur ou le mot de passe est incorrect.", e);
            case "22001":
                throw new DataSizeException("Chaine trop longue pour le champ correspondant.", e);
            default:
                throw new RecipeOperationException("Erreur lors de l'opération sur la recette.", e);
        }
    }

    // A faire !!! Search 1
    public List<RecipeWithExpiredFood> recipeWithExpireFood() throws AppException {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    // A faire !!! Search 3
    public List<SeasonalRecipe> recipesOfSeason(LocalDate date) throws AppException {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
