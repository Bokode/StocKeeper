package dataAccessPackage;

import java.sql.*;

import exceptionPackage.*;
import interfacePackage.FoodTypeDAOInterface;
import modelPackage.FoodType;


public class FoodTypeDAO implements FoodTypeDAOInterface {
    public FoodType getFoodTypeById(int id) throws AppException {
        final String sql = "SELECT label FROM foodtype WHERE id = ?";
        try (Connection c = StocKeeperDBAccess.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new FoodType(rs.getString("label"));
                }
            }
        } catch (SQLException e) {
            throw new RecipeOperationException("Erreur lors de la récupération du type d'aliment", e);
        }
        return null;
    }
}