package interfacePackage;

import modelPackage.*;

import java.util.List;

public interface FoodInDAOInterface
{
    Integer addFoodIn(Food food, StorageType storageType, Integer quantity, boolean isOpen, char nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate);

    Integer updateFoodIn(Food food, StorageType storageType, Integer quantity, boolean isOpen, char nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate);

    Integer deleteFoodIn(String label);

    FoodIn getFoodInByLabel(String label);

    List<FoodIn> getAllFoodIns();

    List<FoodInToSearch> getFoodInToSearch();

    List<ExpiredFood>  expiredFood(String storageType, String foodType);
}
