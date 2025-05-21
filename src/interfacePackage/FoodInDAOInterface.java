package interfacePackage;

import modelPackage.*;

import java.sql.SQLException;
import java.util.List;

public interface FoodInDAOInterface
{
    void addFoodIn(FoodIn foodIn) throws SQLException;

    List<FoodIn> getAllFoodIns();

    Integer updateFoodIn(Food food, StorageType storageType, Integer quantity, boolean isOpen, char nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate);

    Integer deleteFoodIn(String label);

    FoodIn getFoodInByLabel(String label);

    List<FoodInToSearch> getFoodInToSearch();

    List<ExpiredFood>  expiredFood(String storageType, String foodType);
}
