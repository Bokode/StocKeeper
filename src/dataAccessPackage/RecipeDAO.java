package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.RecipeDAOInterface;
import modelPackage.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class RecipeDAO implements RecipeDAOInterface {

    @Override
    public List<Recipe> getAllRecipes() throws AppException {
        List<Recipe> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipe";
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

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
    public Recipe getRecipe(String label) throws AppException {
        String query = "SELECT * FROM recipe WHERE label = ?";
        Recipe recipe = null;
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

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
    public Integer updateRecipe(String labelToFind, String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, RecipeType type) throws AppException {
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
            stmt.setDate(5, lastDateDone);
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

    @Override
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

    // transforme un ResultSet en objet Recipe

    private Recipe mapResultSetToRecipe(ResultSet rs) throws SQLException {
        return new Recipe(
                rs.getString("label"),
                rs.getString("description"),
                rs.getObject("caloricIntake") != null ? rs.getInt("caloricIntake") : null,
                rs.getDate("lastDateDone"),
                rs.getObject("timeToMake") != null ? rs.getInt("timeToMake") : null,
                rs.getBoolean("isCold"),
                new RecipeType(rs.getString("label")),
                new IngredientAmountDAO().getIngredientAmountsByRecipe(rs.getString("label"))
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
        List<RecipeWithExpiredFood> result = new ArrayList<>();
        LocalDate date = LocalDate.now();
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        String sql = """
        SELECT r.id AS recipe_id,
               r.label AS recipe_label,
               r.description,
               r.caloricIntake,
               r.lastDateDone,
               r.timeToMake,
               r.isCold,
               m.label AS material_label,
               tm.label AS material_type_label,
               f.id AS food_id,
               f.label AS food_label,
               ft.label AS food_type_label,
               fi.expirationDate
        FROM Recipe r
        JOIN Ingredient_Amount ia ON ia.recipe = r.id
        JOIN Food f ON f.id = ia.food
        JOIN Food_Type ft ON f.foodType = ft.id
        LEFT JOIN Food_In fi ON fi.food_id = f.id
        JOIN Recipe_Material rm ON rm.recipe = r.id
        JOIN Material m ON m.id = rm.material
        JOIN Type_Material tm ON tm.id = m.type_id
    """;

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {

                Map<Integer, RecipeWithExpiredFood> map = new HashMap<>();
                Map<Integer, Set<Integer>> recipeToFoodsInStock = new HashMap<>();
                Map<Integer, Set<Integer>> recipeToRequiredFoods = new HashMap<>();
                Map<Integer, Boolean> recipeHasExpiringToday = new HashMap<>();

                while (rs.next()) {
                    int recipeId = rs.getInt("recipe_id");
                    int foodId = rs.getInt("food_id");

                    LocalDate expiration = rs.getDate("expirationDate") != null ?
                            rs.getDate("expirationDate").toLocalDate() : null;

                    // Création ou récupération de la structure
                    RecipeWithExpiredFood rwef = map.computeIfAbsent(recipeId, id -> {
                        Recipe recipe = null;
                        try {
                            recipe = new Recipe(
                                    rs.getString("recipe_label"),
                                    rs.getString("description"),
                                    rs.getInt("caloricIntake"),
                                    rs.getDate("lastDateDone"),
                                    rs.getInt("timeToMake"),
                                    rs.getBoolean("isCold"),
                                    null
                            );
                        } catch (SQLException e) {
                            exceptionHandler(e);
                        }
                        return new RecipeWithExpiredFood(recipe);
                    });

                    // Ajout du matériel
                    Material material = new Material(rs.getString("material_label"), rs.getString("material_type_label"));
                    rwef.addMaterial(material);

                    // Suivi des aliments nécessaires
                    recipeToRequiredFoods.computeIfAbsent(recipeId, k -> new HashSet<>()).add(foodId);

                    // Vérifie si l'aliment est en stock, non périmé
                    if (expiration != null) {
                        if (!expiration.isBefore(date)) {
                            recipeToFoodsInStock.computeIfAbsent(recipeId, k -> new HashSet<>()).add(foodId);
                            // Vérifie s’il expire aujourd’hui
                            if (expiration.isEqual(date)) {
                                recipeHasExpiringToday.put(recipeId, true);
                            }
                            // Ajouter l’aliment à l’objet
                            FoodType ft = new FoodType(rs.getString("food_type_label"));
                            Food food = new Food(rs.getString("food_label"), ft);
                            rwef.addFood(food);
                        }
                    }
                }

                // Filtrage : on garde les recettes qui respectent les règles
                for (var entry : map.entrySet()) {
                    int recipeId = entry.getKey();
                    Set<Integer> inStock = recipeToFoodsInStock.getOrDefault(recipeId, Set.of());
                    Set<Integer> required = recipeToRequiredFoods.get(recipeId);
                    boolean hasExpiring = recipeHasExpiringToday.getOrDefault(recipeId, false);

                    // Autoriser une seule absence d’ingrédient
                    if ((inStock.size() == required.size() || inStock.size() == required.size() - 1) && hasExpiring) {
                        result.add(entry.getValue());
                    }
                }

            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return result;
    }

    @Override
    public List<SeasonalRecipe> recipesOfSeason(LocalDate date) throws AppException {
        List<SeasonalRecipe> results = new ArrayList<>();
        Map<Integer, SeasonalRecipe> recipeMap = new HashMap<>();
        String season = getSeasonFromDate(date);
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        String sql = """
        SELECT DISTINCT r.id AS recipe_id,
                        r.label AS recipe_label,
                        r.description,
                        r.caloricIntake,
                        r.lastDateDone,
                        r.timeToMake,
                        r.isCold,
                        d.label AS diet_label,
                        m.label AS material_label,
                        tm.label AS material_type_label
        FROM Recipe r
        JOIN Recipe_Material rm ON rm.recipe = r.id
        JOIN Material m ON m.id = rm.material
        JOIN Type_Material tm ON tm.id = m.type_id
        JOIN Diet_Recipe dr ON dr.recipe = r.id
        JOIN Diet d ON d.id = dr.diet
        JOIN Ingredient_Amount ia ON ia.recipe = r.id
        JOIN Food f ON f.id = ia.food
        JOIN Food_In fi ON fi.food_id = f.id
        JOIN Season_Food sf ON sf.food = f.id
        WHERE sf.season = ?
          AND fi.expirationDate < ?
    """;

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, season);
            ps.setDate(2, java.sql.Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int recipeId = rs.getInt("recipe_id");

                    SeasonalRecipe sr = recipeMap.get(recipeId);
                    if (sr == null) {
                        Recipe recipe = new Recipe(
                                rs.getString("recipe_label"),
                                rs.getString("description"),
                                rs.getInt("caloricIntake"),
                                rs.getDate("lastDateDone"),
                                rs.getInt("timeToMake"),
                                rs.getBoolean("isCold"),
                                null
                        );
                        sr = new SeasonalRecipe(recipe);
                        recipeMap.put(recipeId, sr);
                    }

                    sr.addDiet(new Diet(rs.getString("diet_label")));
                    sr.addMaterial(new Material(rs.getString("material_label"), rs.getString("material_type_label")));
                }
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        results.addAll(recipeMap.values());
        return results;
    }


    private String getSeasonFromDate(LocalDate date) {
        int month = date.getMonthValue();

        if (month == 12 || month <= 2) return "Winter";
        else if (month <= 5) return "Spring";
        else if (month <= 8) return "Summer";
        else return "Autumn";
    }
    */

}