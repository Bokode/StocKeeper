package dataAccessPackage;

import exceptionPackage.*;

import java.sql.*;

public class FoodDAO
{
    public int getFoodIdByLabel(String label) throws AppException
    {
        String query = "SELECT id FROM food WHERE label = ?";
        int foodId = -1;

        try (Connection conn = FridgeDBAccess.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, label);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    foodId = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            exceptionHandler(e);
        }
        return foodId;
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
}
