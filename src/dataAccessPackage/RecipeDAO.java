package dataAccessPackage;

import exeptionPackage.AppException;
import exeptionPackage.DataBaseUnavailableException;
import exeptionPackage.AuthenticationFailureException;
import exeptionPackage.RecipeOperationException;
import interfacePackage.RecipeDAOInterface;
import modelPackage.Recipe;

import java.sql.*;
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
    public int updateRecipe(int id, String label, String description, int caloricIntake, boolean isCold, Date lastDateDone, int timeToMake, int type) throws AppException
    {
        String query = "UPDATE recipe SET label = ?, description = ?, caloricIntake = ?, isCold = ?, lastDateDone = ?, timeToMake = ?, type = ? WHERE id = ?";
        return executeRecipeUpdate(query, label, description, caloricIntake, isCold, lastDateDone, timeToMake, type, id);
    }

    @Override
    public int deleteRecipe(int id) throws AppException
    {
        String query = "DELETE FROM recipe WHERE id = ?";
        int rowsDeleted = 0;
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
    public int addRecipe(String label, String description, int caloricIntake, boolean isCold, Date lastDateDone, int timeToMake, int type) throws AppException
    {
        String query = "INSERT INTO recipe (label, description, caloricIntake, isCold, lastDateDone, timeToMake, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return executeRecipeUpdate(query, label, description, caloricIntake, isCold, lastDateDone, timeToMake, type, null);
    }

    // Méthode utilitaire pour factoriser l'insertion ou la mise à jour d'une recette

    private int executeRecipeUpdate(String query, String label, String description, int caloricIntake,
                                    boolean isCold, Date lastDateDone, int timeToMake, int type, Integer id) throws AppException
            {
        int rows = 0;
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

    private void exceptionHandler(SQLException e) throws AppException
    {
        String state = e.getSQLState();
        if (state.equals("08S01"))
        {
            throw new DataBaseUnavailableException("La base de données est indisponible.", e);
        }
        else if (state.equals("28000"))
        {
            throw new AuthenticationFailureException("L'utilisateur ou le mot de passe est incorrect.", e);
        }
        else
        {
            throw new RecipeOperationException("Erreur lors de l'opération sur la recette.", e);
        }
    }

}
