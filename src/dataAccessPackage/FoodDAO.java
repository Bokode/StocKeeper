package dataAccessPackage;

import exceptionPackage.*;
import interfacePackage.FoodDAOInterface;
import modelPackage.Food;
import modelPackage.FoodType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO implements FoodDAOInterface {
    private static final String TBL        = "food";
    private static final String COL_ID     = "id";
    private static final String COL_LABEL  = "label";
    private static final String COL_TYPEID = "foodType";

    private final FoodTypeDAO typeDAO;

    public FoodDAO()            { this(new FoodTypeDAO()); }
    public FoodDAO(FoodTypeDAO t) { this.typeDAO = t; }

    /* ---------- READ ---------- */
    public List<Food> getAllFoods() throws AppException {
        List<Food> foodList = new ArrayList<>();
        final String sql = "SELECT " + COL_LABEL + ", " + COL_TYPEID + " FROM " + TBL;

        try (Connection c = StocKeeperDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String label = rs.getString(COL_LABEL);
                int typeId = rs.getInt(COL_TYPEID);
                FoodType foodType = typeDAO.getFoodTypeById(typeId);
                foodList.add(new Food(label, foodType));
            }

        } catch (SQLException e) {
            exceptionHandler(e);
        }

        return foodList;
    }


    public int getFoodIdByLabel(String label) throws AppException {
        final String sql = "SELECT " + COL_ID + " FROM " + TBL + " WHERE " + COL_LABEL + " = ?";
        try (Connection c = StocKeeperDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, label);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(COL_ID);
            }
        } catch (SQLException e) { exceptionHandler(e); }
        return -1;
    }

    public String getFoodLabelById(int id) throws AppException {
        final String sql = "SELECT " + COL_LABEL + " FROM " + TBL + " WHERE " + COL_ID + " = ?";
        try (Connection c = StocKeeperDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString(COL_LABEL);
            }
        } catch (SQLException e) { exceptionHandler(e); }
        return null;
    }

    public Food getFoodByLabel(String label) throws AppException {
        final String sql = "SELECT " + COL_LABEL + ", " + COL_TYPEID + " FROM " + TBL + " WHERE " + COL_LABEL + " = ?";
        try (Connection c = StocKeeperDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, label);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String foodLabel = rs.getString(COL_LABEL);
                    int typeId = rs.getInt(COL_TYPEID);
                    FoodType foodType = typeDAO.getFoodTypeById(typeId); // méthode à prévoir dans FoodTypeDAO
                    return new Food(foodLabel, foodType);
                }
            }
        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return null;
    }

    /* ---------- ERRORS ---------- */
    private void exceptionHandler(SQLException e) throws AppException {
        switch (e.getSQLState()) {
            case "08S01" -> throw new DataBaseUnavailableException("Base de données indisponible.", e);
            case "28000" -> throw new AuthenticationFailureException("Authentification refusée.", e);
            case "23000" -> throw new AlreadyExistException("Aliment déjà existant.", e);
            case "22001" -> throw new DataSizeException("Chaîne trop longue.", e);
            default       -> throw new RecipeOperationException("Erreur SQL " + e.getSQLState(), e);
        }
    }
}