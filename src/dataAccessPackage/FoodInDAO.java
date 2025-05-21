package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.FoodInDAOInterface;
import modelPackage.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodInDAO implements FoodInDAOInterface {

    @Override
    public List<FoodIn> getAllFoodIns() throws AppException {
        List<FoodIn> foodsIn = new ArrayList<>();
        String query = "SELECT * FROM foodIn";

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

        return foodsIn;
    }

    @Override
    public Integer addFoodIn(Integer food, Integer storageType, Integer quantity, boolean isOpen, char nutriScore, Date purchaseDate, Date expirationDate) throws AppException
    {
        String query = "INSERT INTO foodIn (food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return executeFoodInUpdate(query, food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate, null);
    }

    @Override
    public Integer updateFoodIn(Integer id, Integer food, Integer storageType, Integer quantity, boolean isOpen, char nutriScore, Date purchaseDate, Date expirationDate) throws AppException
    {
        String query = "UPDATE foodIn SET food = ?, storageType = ?, quantity = ?, isOpen = ?, nutriScore = ?, purchaseDate = ?, expirationDate = ? WHERE id = ?";
        return executeFoodInUpdate(query, food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate, id);
    }

    @Override
    public Integer deleteFoodIn(Integer id) throws AppException
    {
        String query = "DELETE FROM foodIn WHERE id = ?";
        Integer rowsDeleted = 0;

        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            rowsDeleted = stmt.executeUpdate();

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return rowsDeleted;
    }

    @Override
    public FoodIn getFoodInById(Integer id) throws AppException
    {
        String query = "SELECT * FROM foodIn WHERE id = ?";
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

        return foodIn;
    }

    private Integer executeFoodInUpdate(String query, Integer food, Integer storageType, Integer quantity, boolean isOpen,
                                    char nutriScore, Date purchaseDate, Date expirationDate, Integer id) throws AppException
    {
        Integer rowsAffected = 0;
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

        return rowsAffected;
    }



    private FoodIn mapResultSetToFoodIn(ResultSet rs) throws SQLException
    {
        return new FoodIn(
                rs.getInt("id"),
                rs.getDate("expirationDate"),
                rs.getInt("quantity"),
                rs.getBoolean("isOpen"),
                rs.getString("nutriScore").charAt(0),
                rs.getDate("purchaseDate"),
                rs.getInt("food"),
                rs.getInt("storageType")
        );
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
    @Override
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
                FoodIn foodIn = new FoodIn(
                        rs.getInt("foodIn_id"),
                        rs.getDate("expirationDate"),
                        rs.getInt("quantity"),
                        rs.getBoolean("isOpen"),
                        rs.getString("nutriScore").charAt(0),
                        rs.getDate("purchaseDate"),
                        rs.getInt("food_id"),
                        0 // storageType can be added too
                );

                Food food = new Food(
                        rs.getInt("food_id"),
                        rs.getString("food_label"),
                        rs.getInt("foodType_id")
                );

                FoodType foodType = new FoodType(
                        rs.getInt("foodType_id"),
                        rs.getString("foodType_label")
                );

                result.add(new FoodInToSearch(foodIn, food, foodType));
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return result;
    }




    // Search 2
    @Override
    public List<ExpiredFood> expiredFood(String storageType, String foodType) throws AppException {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
