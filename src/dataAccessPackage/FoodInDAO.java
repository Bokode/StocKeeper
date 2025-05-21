package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.FoodInDAOInterface;
import modelPackage.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodInDAO implements FoodInDAOInterface {

    @Override
    public List<FoodIn> getAllFoodIns() throws AppException {
        /*List<FoodIn> foodsIn = new ArrayList<>();
        String query = "SELECT * FROM food_in";

        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                foodsIn.add(mapResultSetToFoodIn(rs));
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return foodsIn;*/
        return null;
    }

    /*@Override
    public Integer addFoodIn(Food food, StorageType storageType, Integer quantity, boolean isOpen, char nutriScore, Date purchaseDate, Date expirationDate) throws AppException
    {
        String query = "INSERT INTO food_in (food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return executeFoodInUpdate(query, food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate, null);
    }*/


    public void addFoodIn(FoodIn foodIn) throws SQLException {
        System.out.println(foodIn);
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();
        Connection conn = dbAccess.getConnection();
        System.out.println("Connecting to database...");
        try {
            conn.setAutoCommit(false);

            // 1. Vérifie et insère FoodType
            int foodTypeId = getOrInsertFoodType(conn, foodIn.getFood().getFoodType());

            // 2. Vérifie et insère Food
            int foodId = getOrInsertFood(conn, foodIn.getFood(), foodTypeId);

            // 3. Vérifie et insère StorageType
            int storageTypeId = getOrInsertStorageType(conn, foodIn.getStorageType());

            // 4. Insère FoodIn
            String sql = """
            INSERT INTO food_in (expirationDate, quantity, isOpen, nutriScore, purchaseDate, food, storageType)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, new java.sql.Date(foodIn.getExpirationDate().getTime()));
                stmt.setInt(2, foodIn.getQuantity());
                stmt.setBoolean(3, foodIn.getOpen() != null && foodIn.getOpen());
                if (foodIn.getNutriScore() != null) {
                    stmt.setString(4, foodIn.getNutriScore().toString());
                } else {
                    stmt.setNull(4, Types.VARCHAR);
                }
                if (foodIn.getPurchaseDate() != null) {
                    stmt.setDate(5, new java.sql.Date(foodIn.getPurchaseDate().getTime()));
                } else {
                    stmt.setNull(5, Types.DATE);
                }
                stmt.setInt(6, foodId);
                stmt.setInt(7, storageTypeId);
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    private int getOrInsertFoodType(Connection conn, FoodType foodType) throws SQLException {
        String select = "SELECT Id FROM food_type WHERE label = ?";
        try (PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, foodType.getLabel());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("Id");
        }

        String insert = "INSERT INTO food_type (label) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, foodType.getLabel());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        throw new SQLException("Unable to insert or get food_type");
    }

    private int getOrInsertFood(Connection conn, Food food, int foodTypeId) throws SQLException {
        String select = "SELECT Id FROM food WHERE label = ? AND food_type = ?";
        try (PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, food.getLabel());
            stmt.setInt(2, foodTypeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("Id");
        }

        String insert = "INSERT INTO food (label, food_type) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, food.getLabel());
            stmt.setInt(2, foodTypeId);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        throw new SQLException("Unable to insert or get food");
    }

    private int getOrInsertStorageType(Connection conn, StorageType storageType) throws SQLException {
        String select = "SELECT Id FROM storage_type WHERE label = ?";
        try (PreparedStatement stmt = conn.prepareStatement(select)) {
            stmt.setString(1, storageType.getLabel());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt("Id");
        }

        String insert = "INSERT INTO storage_type (label) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, storageType.getLabel());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        throw new SQLException("Unable to insert or get storage_type");
    }



    @Override
    public Integer updateFoodIn(Food food, StorageType storageType, Integer quantity, boolean isOpen, char nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate) throws AppException
    {
        /*String query = "UPDATE food_in SET food = ?, storageType = ?, quantity = ?, isOpen = ?, nutriScore = ?, purchaseDate = ?, expirationDate = ? WHERE id = ?";
        return executeFoodInUpdate(query, food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate, id);*/
        return null;
    }

    @Override
    public Integer deleteFoodIn(String label) throws AppException
    {
        /*String query = "DELETE FROM food_in WHERE id = ?";
        Integer rowsDeleted = 0;

        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            //stmt.setInt(1, id);
            rowsDeleted = stmt.executeUpdate();

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return rowsDeleted;*/
        return null;
    }

    @Override
    public FoodIn getFoodInByLabel(String label) throws AppException
    {
        /*String query = "SELECT * FROM food_in WHERE id = ?";
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();
        FoodIn foodIn = null;

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    foodIn = mapResultSetToFoodIn(rs);
                }
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return foodIn;*/
        return null;
    }

    private Integer executeFoodInUpdate(String query, Integer food, Integer storageType, Integer quantity, boolean isOpen,
                                    char nutriScore, Date purchaseDate, Date expirationDate, Integer id) throws AppException
    {
        /*Integer rowsAffected = 0;
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query))
        {

            stmt.setInt(1, food);
            stmt.setInt(2, storageType);
            stmt.setInt(3, quantity);
            stmt.setBoolean(4, isOpen);
            stmt.setString(5, String.valueOf(nutriScore));
            stmt.setDate(6, new java.sql.Date(purchaseDate.getTime()));
            stmt.setDate(7, new java.sql.Date(expirationDate.getTime()));
            if (id != 0)
            {
                stmt.setInt(8, id);
            }
            rowsAffected = stmt.executeUpdate();

        } catch (SQLException e)
        {
            exceptionHandler(e);
        }

        return rowsAffected;*/
        return null;
    }



    private FoodIn mapResultSetToFoodIn(ResultSet rs) throws SQLException
    {
        /*return new FoodIn(
                rs.getDate("expirationDate"),
                rs.getInt("quantity"),
                rs.getBoolean("isOpen"),
                rs.getString("nutriScore").charAt(0),
                rs.getDate("purchaseDate"),
                rs.getInt("food"),
                rs.getInt("storageType")
        );*/
        return null;
    }

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

    // Task 2
    public List<FoodInToSearch> getFoodInToSearch() throws AppException {
        List<FoodInToSearch> result = new ArrayList<>();

        String query = "SELECT fi.Id AS foodIn_id, fi.expirationDate, fi.quantity, fi.nutriScore, fi.purchaseDate, fi.isOpen, " +
                "f.Id AS food_id, f.label AS food_label, " +
                "ft.Id AS foodType_id, ft.label AS foodType_label " +
                "FROM food_in fi " +
                "JOIN food f ON fi.food = f.Id " +
                "JOIN food_type ft ON f.food_type = ft.Id";


        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FoodType foodType = new FoodType(
                        rs.getString("foodType_label")
                );

                Food food = new Food(
                        rs.getString("food_label"),
                        foodType
                );

                Character nutriScore = null;
                String nutriScoreStr = rs.getString("nutriScore");
                if (nutriScoreStr != null && !nutriScoreStr.isEmpty()) {
                    nutriScore = nutriScoreStr.charAt(0);
                }

                FoodIn foodIn = new FoodIn(
                        rs.getDate("expirationDate"),
                        rs.getInt("quantity"),
                        rs.getBoolean("isOpen"),
                        nutriScore,
                        rs.getDate("purchaseDate"),
                        food,
                        null // Handle StorageType if needed
                );

                result.add(new FoodInToSearch(foodIn, food, foodType));
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return result;
    }

    // Search 2
    public List<ExpiredFood> expiredFood(String storageType, String foodType) throws AppException {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}