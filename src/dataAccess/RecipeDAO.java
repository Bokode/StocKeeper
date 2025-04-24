package dataAccess;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO {

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipe";

        try (Connection conn = FridgeDBAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Recipe recipe = new Recipe(
                        rs.getInt("id"),
                        rs.getString("label"),
                        rs.getString("description"),
                        rs.getInt("caloricIntake"),
                        rs.getBoolean("isCold"),
                        rs.getDate("lastDateDone"),
                        rs.getInt("timeToMake"),
                        rs.getInt("type")
                );
                recipes.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    public Recipe getRecipeById(int id) {
        String query = "SELECT * FROM recipe WHERE id = ?";
        Recipe recipe = null;

        try (Connection conn = FridgeDBAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                recipe = new Recipe(
                        rs.getInt("id"),
                        rs.getString("label"),
                        rs.getString("description"),
                        rs.getInt("caloricIntake"),
                        rs.getBoolean("isCold"),
                        rs.getDate("lastDateDone"),
                        rs.getInt("timeToMake"),
                        rs.getInt("type")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipe;
    }

}
