package interfacePackage;

import modelPackage.ExpiredFood;
import modelPackage.FoodIn;
import modelPackage.FoodInToSearch;

import java.util.List;

public interface FoodInDAOInterface
{
    Integer addFoodIn(Integer food, Integer storageType, Integer quantity, boolean isOpen, char nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate);

    Integer updateFoodIn(Integer id, Integer food, Integer storageType, Integer quantity, boolean isOpen, char nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate);

    Integer deleteFoodIn(Integer id);

    FoodIn getFoodInById(Integer id);

    List<FoodIn> getAllFoodIns();

    List<FoodInToSearch> getFoodInToSearch();

    List<ExpiredFood>  expiredFood(String storageType, String foodType);
}
