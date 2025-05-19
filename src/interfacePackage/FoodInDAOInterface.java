package interfacePackage;

import modelPackage.ExpiredFood;
import modelPackage.FoodIn;
import modelPackage.FoodInToSearch;

import java.util.List;

public interface FoodInDAOInterface
{
    int addFoodIn(int food, int storageType, int quantity, boolean isOpen, char nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate);

    int updateFoodIn(int id, int food, int storageType, int quantity, boolean isOpen, char nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate);

    int deleteFoodIn(int id);

    FoodIn getFoodInById(int id);

    List<FoodIn> getAllFoodIns();

    List<FoodInToSearch> getFoodInToSearch();

    List<ExpiredFood>  expiredFood(String storageType, String foodType);
}
