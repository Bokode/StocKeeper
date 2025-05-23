package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.*;

import java.sql.SQLException;
import java.util.List;

public interface FoodInDAOInterface
{
    void addFoodIn(FoodIn foodIn) throws SQLException;

    List<FoodIn> getAllFoodIns();

    Integer updateFoodIn(Food food, StorageType storageType, Integer quantity, boolean isOpen, char nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate);

    public Integer deleteFoodInByFoodLabel(String foodLabel);

    public FoodIn getFoodInByFoodLabel(String foodLabel);

    List<FoodInToSearch> getFoodInToSearch();

    List<ExpiredFood>  expiredFood(StorageType storageType, FoodType foodType);
}
