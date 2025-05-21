package dataAccessPackage;

import exceptionPackage.*;
import modelPackage.IngredientAmount;

import java.sql.*;
import java.util.ArrayList;

public class IngredientAmountDAO
{

    public void addIngredientAmount(String recipe, String food, double quantity) throws AppException
    {
        String query = "INSERT INTO ingredient_amount (recipe_id, food_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection())
        {
            RecipeDAO recipeDAO = new RecipeDAO();
            FoodDAO foodDAO = new FoodDAO();
            int recipeId = recipeDAO.getRecipeIdByLabel(recipe);
            int foodId = foodDAO.getFoodIdByLabel(food);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, recipeId);
            preparedStatement.setInt(2, foodId);
            preparedStatement.setDouble(3, quantity);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            exceptionHandler(e);
        }
    }

    private void deleteIngredientAmount(String recipe, String food) throws AppException
    {
        String query = "DELETE FROM ingredient_amount WHERE recipe_id = ? AND food_id = ?";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection())
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

    public void updateIngredientAmount(String recipe, String food, double quantity) throws AppException
    {
        String query = "UPDATE ingredient_amount SET quantity = ? WHERE recipe_id = ? AND food_id = ?";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection())
        {
            RecipeDAO recipeDAO = new RecipeDAO();
            FoodDAO foodDAO = new FoodDAO();
            int recipeId = recipeDAO.getRecipeIdByLabel(recipe);
            int foodId = foodDAO.getFoodIdByLabel(food);
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDouble(1, quantity);
            preparedStatement.setInt(2, recipeId);
            preparedStatement.setInt(3, foodId);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            exceptionHandler(e);
        }
    }

    public ArrayList<IngredientAmount> getIngredientAmountsByRecipe(String recipe) throws AppException
    {
        ArrayList<IngredientAmount> ingredientAmounts = new ArrayList<>();
        String query = "SELECT * FROM ingredient_amount WHERE recipe_id = ?";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, recipe);
            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                    String food = rs.getString("food_id");

                    double quantity = rs.getDouble("quantity");
                    IngredientAmount ingredientAmount = new IngredientAmount(recipe, food, quantity);
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
            case "22001" -> throw new DataSizeException("Chaîne trop longue pour le champ correspondant.", e);
            case "23000" -> throw new AlreadyExistException("Recette déjà existante.", e);
            default -> throw new RecipeOperationException("Erreur lors de l'opération sur la recette.", e);
        }
    }

}
