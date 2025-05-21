package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.RecipeDAOInterface;
import modelPackage.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecipeDAO implements RecipeDAOInterface {

    @Override
    public List<Recipe> getAllRecipes() throws AppException {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipe";

        try (Connection conn = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                recipes.add(mapResultSetToRecipe(rs));
            }
        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return recipes;
    }

    @Override
    public Recipe getRecipe(String label) throws AppException
    {
        String query = "SELECT * FROM recipe WHERE label = ?";
        Recipe recipe = null;

        try (Connection conn = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, label);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    recipe = mapResultSetToRecipe(rs);
                }
            }
        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return recipe;
    }

    @Override
    public Integer updateRecipe(String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, RecipeType type) throws AppException {
        String query = "UPDATE recipe SET description = ?, caloricIntake = ?, isCold = ?, lastDateDone = ?, timeToMake = ?, type = ? WHERE label = ?";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, description);
            stmt.setInt(2, caloricIntake);
            stmt.setBoolean(3, isCold);
            stmt.setDate(4, lastDateDone);
            stmt.setInt(5, timeToMake);
            stmt.setInt(6, getOrInsertRecipeType(conn, type));
            stmt.setString(7, label);

            return stmt.executeUpdate();
        } catch (SQLException e) {
            exceptionHandler(e);
            return 0;
        }
    }

    @Override
    public Integer deleteRecipe(String label) throws AppException {
        String query = "DELETE FROM recipe WHERE label = ?";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, label);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            exceptionHandler(e);
            return 0;
        }
    }

    public void addRecipe(Recipe recipe) throws AppException {
        try (Connection conn = FridgeDBAccess.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            int recipeTypeId = getOrInsertRecipeType(conn, recipe.getType());

            String sql = """
                INSERT INTO recipe (label, description, caloricIntake, lastDateDone, timeToMake, isCold, type)
                VALUES (?, ?, ?, ?, ?, ?, ?)""";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, recipe.getLabel());
                stmt.setString(2, recipe.getDescription());
                stmt.setObject(3, recipe.getCaloricIntake(), Types.INTEGER);
                stmt.setObject(4, recipe.getLastDayDone(), Types.DATE);
                stmt.setObject(5, recipe.getTimeToMake(), Types.INTEGER);
                stmt.setBoolean(6, Boolean.TRUE.equals(recipe.getCold()));
                stmt.setInt(7, recipeTypeId);
                stmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            exceptionHandler(e);
        }
    }

    private int getOrInsertRecipeType(Connection conn, RecipeType type) throws AppException {
        String select = "SELECT id FROM recipe_type WHERE label = ?";
        try (PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, type.getLabel());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            exceptionHandler(e);
        }

        String insert = "INSERT INTO recipe_type (label) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, type.getLabel());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return -1;
    }

    private Recipe mapResultSetToRecipe(ResultSet rs) throws SQLException {
        return new Recipe(
                rs.getString("label"),
                rs.getString("description"),
                rs.getInt("caloricIntake"),
                rs.getDate("lastDateDone"),
                rs.getInt("timeToMake"),
                rs.getBoolean("isCold"),
                new RecipeType(rs.getString("label"))
        );
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

    @Override
    public List<RecipeWithExpiredFood> recipeWithExpireFood() throws AppException {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Search 3
    @Override
    public List<SeasonalRecipe> recipesOfSeason(LocalDate date) throws AppException {
        List<SeasonalRecipe> results = new ArrayList<>();

        String season = getSeasonFromDate(date); // Printemps, Été, etc.

        String sql = """
        SELECT DISTINCT r.id AS recipe_id, r.label AS recipe_label, r.description, r.calorieIntake,
                        r.lastDateDone, r.timeToMake, r.isCold,
                        d.label AS diet_label,
                        m.label AS material_label, m.material_type_label
        FROM recipe r
        JOIN recipe_material rm ON rm.recipe_id = r.id
        JOIN material m ON m.id = rm.material_id
        JOIN diet d ON d.id = r.diet_id
        JOIN ingredient_amount ia ON ia.recipe_id = r.id
        JOIN food f ON f.id = ia.food_id
        JOIN food_in fi ON fi.food_id = f.id
        JOIN food_season fs ON fs.food_id = f.id
        WHERE fs.season = ?
          AND fi.expirationDate < ?
    """;

        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, season);
            ps.setDate(2, java.sql.Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Diet diet = new Diet(rs.getString("diet_label"));
                    Material material = new Material(
                            rs.getString("material_label"),
                            rs.getString("material_type_label")
                    );

                    Recipe recipe = new Recipe(
                            rs.getString("recipe_label"),
                            rs.getString("description"),
                            rs.getInt("calorieIntake"),
                            rs.getDate("lastDateDone"),
                            rs.getInt("timeToMake"),
                            rs.getBoolean("isCold"),
                            null // ou récupère le RecipeType si nécessaire
                    );

                    results.add(new SeasonalRecipe(recipe, diet, material));
                }
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return results;
    }
    private String getSeasonFromDate(LocalDate date) {
        int month = date.getMonthValue();

        if (month == 12 || month <= 2) return "Winter";
        else if (month <= 5) return "Spring";
        else if (month <= 8) return "Summer";
        else return "Autumn";
    }

}
