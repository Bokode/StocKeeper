package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.RecipeDAOInterface;
import modelPackage.*;

import java.sql.*;
import java.util.Date;
import java.time.LocalDate;
import java.util.*;

public class RecipeDAO implements RecipeDAOInterface {

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

    public Recipe getRecipe(String label) throws AppException {
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

    public Integer updateRecipe(String labelToFind, String label, String description, Integer caloricIntake,
                                boolean isCold, Date lastDateDone, Integer timeToMake, RecipeType type) throws AppException {
        String query = "UPDATE recipe SET label = ?, description = ?, caloricIntake = ?, isCold = ?, lastDateDone = ?, timeToMake = ?, type = ? WHERE label = ?";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, label);
            stmt.setString(2, description);
            if (caloricIntake == null) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, caloricIntake);
            }
            stmt.setBoolean(4, isCold);
            if (lastDateDone == null) {
                stmt.setNull(5, Types.DATE);
            } else {
                stmt.setDate(5, new java.sql.Date(lastDateDone.getTime()));
            }

            if (timeToMake == null) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, timeToMake);
            }
            stmt.setInt(7, getOrInsertRecipeType(conn, type));
            stmt.setString(8, labelToFind);

            return stmt.executeUpdate();
        } catch (SQLException e) {
            exceptionHandler(e);
            return 0;
        }
    }

    public Integer deleteRecipe(String label) throws AppException {
        String query = "DELETE FROM recipe WHERE label = ?";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, label);
            if (stmt.executeUpdate() == 0) {
                throw new NotFoundException("Recette non trouvée.");
            }
        } catch (SQLException e) {
            exceptionHandler(e);

        }
        return 0;
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

    public int getRecipeIdByLabel(String label) throws AppException {
        String query = "SELECT id FROM recipe WHERE label = ?";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, label);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return -1;
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

    // Transforme un ResultSet en objet Recipe
    private Recipe mapResultSetToRecipe(ResultSet rs) throws AppException {
        try
        {
            int typeId = rs.getInt("type");
            RecipeType recipeType = null;

            try (PreparedStatement stmt = rs.getStatement().getConnection().prepareStatement(
                    "SELECT label FROM Recipe_Type WHERE id = ?")) {
                stmt.setInt(1, typeId);
                try (ResultSet typeRs = stmt.executeQuery()) {
                    if (typeRs.next()) {
                        String typeLabel = typeRs.getString("label");
                        recipeType = new RecipeType(typeLabel);
                    }
                }
            }

            return new Recipe(
                    rs.getString("label"),
                    rs.getString("description"),
                    rs.getObject("caloricIntake") != null ? rs.getInt("caloricIntake") : null,
                    rs.getDate("lastDateDone"),
                    rs.getObject("timeToMake") != null ? rs.getInt("timeToMake") : null,
                    rs.getBoolean("isCold"),
                    recipeType
            );
        }
        catch (SQLException e)
        {
            exceptionHandler(e);
            return null;
        }
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

    public List<RecipeWithExpiredFood> recipeWithExpireFood() throws AppException {
        List<RecipeWithExpiredFood> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate threshold = today.plusDays(5);
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        String sql = """
        SELECT r.id AS recipe_id,
               r.label AS recipe_label,
               r.description,
               r.caloricIntake,
               r.lastDateDone,
               r.timeToMake,
               r.isCold,
               f.id AS food_id,
               f.label AS food_label,
               ft.label AS food_type_label,
               fi.expirationDate
        FROM Recipe r
        JOIN IngredientAmount ia ON ia.recipe = r.id
        JOIN Food f ON f.id = ia.food
        JOIN FoodType ft ON f.foodType = ft.id
        JOIN FoodIn fi ON fi.food_id = f.id
    """;

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            Map<Integer, RecipeWithExpiredFood> map = new HashMap<>();
            Map<Integer, Set<Integer>> recipeToRequiredFoods = new HashMap<>();
            Map<Integer, Set<Integer>> recipeToAvailableFoods = new HashMap<>();
            Map<Integer, Boolean> recipeHasExpiringSoon = new HashMap<>();

            while (rs.next()) {
                int recipeId = rs.getInt("recipe_id");
                int foodId = rs.getInt("food_id");
                LocalDate expiration = rs.getDate("expirationDate") != null
                        ? rs.getDate("expirationDate").toLocalDate()
                        : null;

                // Construction ou récupération de l'objet
                RecipeWithExpiredFood rwef = map.computeIfAbsent(recipeId, id -> {
                    try {
                        return new RecipeWithExpiredFood(new Recipe(
                                rs.getString("recipe_label"),
                                rs.getString("description"),
                                rs.getInt("caloricIntake"),
                                rs.getDate("lastDateDone"),
                                rs.getInt("timeToMake"),
                                rs.getBoolean("isCold"),
                                new RecipeType(rs.getString("food_type_label"))
                        ));
                    } catch (SQLException e) {
                        exceptionHandler(e);
                        return null;
                    }
                });

                // Suivi des aliments nécessaires
                recipeToRequiredFoods.computeIfAbsent(recipeId, k -> new HashSet<>()).add(foodId);

                // Vérifie si l'aliment est en stock et périme bientôt
                if (expiration != null &&
                        ( !expiration.isBefore(today) && !expiration.isAfter(threshold))) {

                    recipeToAvailableFoods.computeIfAbsent(recipeId, k -> new HashSet<>()).add(foodId);
                    recipeHasExpiringSoon.put(recipeId, true);

                    FoodType ft = new FoodType(rs.getString("food_type_label"));
                    Food food = new Food(rs.getString("food_label"), ft);
                    rwef.addFood(food);
                }
            }

            // Filtrage final
            for (var entry : map.entrySet()) {
                int recipeId = entry.getKey();
                boolean hasExpiring = recipeHasExpiringSoon.getOrDefault(recipeId, false);

                if (hasExpiring) {
                    result.add(entry.getValue());
                }
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return result;
    }


    public List<RecipeWithExpiredFood> recipesWithSomeIngredientsInStock() throws AppException {
        List<RecipeWithExpiredFood> result = new ArrayList<>();
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        String sql = """
        SELECT r.id AS recipe_id,
               r.label AS recipe_label,
               r.description,
               r.caloricIntake,
               r.lastDateDone,
               r.timeToMake,
               r.isCold,
               f.id AS food_id,
               f.label AS food_label,
               ft.label AS food_type_label
        FROM Recipe r
        JOIN IngredientAmount ia ON ia.recipe = r.id
        JOIN Food f ON f.id = ia.food
        JOIN FoodType ft ON f.foodType = ft.id
        JOIN FoodIn fi ON fi.food_id = f.id
    """;

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            Map<Integer, RecipeWithExpiredFood> map = new HashMap<>();
            Set<Integer> seenFoodPerRecipe = new HashSet<>();

            while (rs.next()) {
                int recipeId = rs.getInt("recipe_id");
                int foodId = rs.getInt("food_id");

                int compositeKey = Objects.hash(recipeId, foodId);
                if (seenFoodPerRecipe.contains(compositeKey)) continue;
                seenFoodPerRecipe.add(compositeKey);

                // Crée ou récupère l'objet recette enrichie
                RecipeWithExpiredFood rwef = map.computeIfAbsent(recipeId, id -> {
                    try {
                        return new RecipeWithExpiredFood(new Recipe(
                                rs.getString("recipe_label"),
                                rs.getString("description"),
                                rs.getInt("caloricIntake"),
                                rs.getDate("lastDateDone"),
                                rs.getInt("timeToMake"),
                                rs.getBoolean("isCold"),
                                new RecipeType(rs.getString("food_type_label")) // ou un type si tu veux l'afficher
                        ));
                    } catch (SQLException e) {
                        exceptionHandler(e);
                        return null;
                    }
                });

                // Ajouter l'aliment en stock à la recette
                FoodType ft = new FoodType(rs.getString("food_type_label"));
                Food food = new Food(rs.getString("food_label"), ft);
                rwef.addFood(food);
            }

            result.addAll(map.values());

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return result;
    }
}
