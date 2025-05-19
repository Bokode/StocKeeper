package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.FoodInDAOInterface;
import modelPackage.FoodIn;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodInDAO implements FoodInDAOInterface {

    @Override
    public List<FoodIn> getAllFoodIns() {
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
    public int addFoodIn(int food, int storageType, int quantity, boolean isOpen, char nutriScore, Date purchaseDate, Date expirationDate) throws AppException
    {
        String query = "INSERT INTO foodIn (food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return executeFoodInUpdate(query, food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate, null);
    }

    @Override
    public int updateFoodIn(int id, int food, int storageType, int quantity, boolean isOpen, char nutriScore, Date purchaseDate, Date expirationDate) throws AppException
    {
        String query = "UPDATE foodIn SET food = ?, storageType = ?, quantity = ?, isOpen = ?, nutriScore = ?, purchaseDate = ?, expirationDate = ? WHERE id = ?";
        return executeFoodInUpdate(query, food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate, id);
    }

    @Override
    public int deleteFoodIn(int id) throws AppException
    {
        String query = "DELETE FROM foodIn WHERE id = ?";
        int rowsDeleted = 0;

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
    public FoodIn getFoodInById(int id) throws AppException
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

    private int executeFoodInUpdate(String query, int food, int storageType, int quantity, boolean isOpen,
                                    char nutriScore, Date purchaseDate, Date expirationDate, Integer id) throws AppException
    {
        int rowsAffected = 0;
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
}
