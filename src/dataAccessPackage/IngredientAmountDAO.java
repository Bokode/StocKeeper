package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.IngredientAmountDAOInterface;
import modelPackage.IngredientAmount;

import java.sql.*;
import java.util.ArrayList;

public class IngredientAmountDAO implements IngredientAmountDAOInterface
{

    public void addIngredientAmount(String recipe, String food, Integer quantity) throws AppException
    {
        String query = "INSERT INTO ingredientamount (recipe, food, quantity) VALUES (?, ?, ?)";
        try (Connection conn = StocKeeperDBAccess.getInstance().getConnection())
        {
            RecipeDAO recipeDAO = new RecipeDAO();
            FoodDAO foodDAO = new FoodDAO();
            int recipeId = recipeDAO.getRecipeIdByLabel(recipe);
            int foodId = foodDAO.getFoodIdByLabel(food);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, foodId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            exceptionHandler(e);
        }
    }

    public void deleteIngredientAmount(String recipe, String food) throws AppException
    {
        String query = "DELETE FROM ingredientamount WHERE recipe = ? AND food = ?";
        try (Connection conn = StocKeeperDBAccess.getInstance().getConnection())
        {
            RecipeDAO recipeDAO = new RecipeDAO();
            FoodDAO foodDAO = new FoodDAO();
            int recipeId = recipeDAO.getRecipeIdByLabel(recipe);
            int foodId = foodDAO.getFoodIdByLabel(food);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, foodId);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            exceptionHandler(e);
        }
    }

    public ArrayList<IngredientAmount> getIngredientAmountsByRecipe(String recipe) throws AppException
    {
        ArrayList<IngredientAmount> ingredientAmounts = new ArrayList<>();
        String query = "SELECT * FROM ingredientamount WHERE recipe = ?";
        try (Connection conn = StocKeeperDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            int recipeId = new RecipeDAO().getRecipeIdByLabel(recipe);
            stmt.setInt(1, recipeId);
            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                    int foodId = rs.getInt("food");
                    String foodLabel = new FoodDAO().getFoodLabelById(foodId);

                    Integer quantity = rs.getInt("quantity");
                    IngredientAmount ingredientAmount = new IngredientAmount(recipe, foodLabel, quantity);
                    ingredientAmounts.add(ingredientAmount);
                }
            }
        }
        catch (SQLException e)
        {
            exceptionHandler(e);
        }
        return ingredientAmounts;
    }

    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("La base de données est indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("L'utilisateur ou le mot de passe est incorrect.", e);
            case "22001" -> throw new DataSizeException("Donnée incorrect", e);
            case "23000" -> throw new AlreadyExistException("Ingredient déjà existant.", e);
            default -> throw new RecipeOperationException("Erreur lors de l'opération sur l'ingredient" + e.getSQLState(), e);
        }
    }

}
