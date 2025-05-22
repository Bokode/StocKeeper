package dataAccessPackage;

import java.sql.*;

import exceptionPackage.*;


public class FoodTypeDAO
{
    public int getIdByLabel(String label) throws AppException
    {
        String query = "SELECT id FROM foodType WHERE label = ?";
        try (Connection conn = FridgeDBAccess.getInstance().getConnection())
        {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, label);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                return resultSet.getInt("id");
            }
            else
            {
                throw new AppException("Food type not found");
            }
        } catch (SQLException e)
        {
            exceptionHandler(e);
        }
        return -1;
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
