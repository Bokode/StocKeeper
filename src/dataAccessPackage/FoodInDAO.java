package dataAccessPackage;

import interfacePackage.FoodInDAOInterface;
import modelPackage.FoodIn;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodInDAO implements FoodInDAOInterface
{

    @Override
    public List<FoodIn> getAllFoodIns() {
        List<FoodIn> FoodsIn = new ArrayList<>();

        String query = "SELECT * FROM recipe";

        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();

        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FoodsIn.add(mapResultSetToFoodIn(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FoodsIn;
    }


    @Override
    public int addFoodIn(int food, int storageType, int quantity, boolean isOpen, char nutriScore, int dayExp, int monthExp, int yearExp) {
        String query = "INSERT INTO foodIn (food, storageType, quantity, isOpen, nutriScore, purchaseDate, expirationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";
        return executeFoodInUpdate(query, food, storageType, quantity, isOpen, nutriScore, dayExp, monthExp, yearExp, 0);
    }

    @Override
    public int updateFoodIn(int id, int food, int storageType, int quantity, boolean isOpen, char nutriScore, Date purchaseDate, Date expirationDate) {
        return 0;
    }

    @Override
    public int deleteFoodIn(int id) {
        return 0;
    }

    @Override
    public FoodIn getFoodInById(int id) {
        return null;
    }


    private int executeFoodInUpdate(String query, int food, int storageType, int quantity, boolean isOpen, char nutriScore, int day, int month, int  year, int id) {
        int rowsAffected = 0;
        FridgeDBAccess dbAccess = FridgeDBAccess.getInstance();
        try (Connection conn = dbAccess.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, food);
            stmt.setInt(2, storageType);
            stmt.setInt(3, quantity);
            stmt.setBoolean(4, isOpen);
            stmt.setString(5, String.valueOf(nutriScore));
            stmt.setDate(6, new java.sql.Date(System.currentTimeMillis()));
            stmt.setDate(7, new java.sql.Date(year, month, day));
            stmt.setInt(8, id);

            rowsAffected = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
}
