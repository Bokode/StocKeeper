package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.*;

import java.util.Date;
import java.util.List;

public interface FoodInDAOInterface
{
    void addFoodIn(FoodIn foodIn) throws AppException;

    List<FoodIn> getAllFoodIns() throws AppException;

    void updateFoodIn(Food food, StorageType storageType, Integer quantity, boolean isOpen, Character nutriScore, Date purchaseDate, Date expirationDate) throws AppException;

    Integer deleteFoodInByFoodLabel(String foodLabel) throws AppException;

    FoodIn getFoodInByFoodLabel(String foodLabel) throws AppException;

    List<FoodInToSearch> getFoodInToSearch() throws AppException;

    List<ExpiredFood>  expiredFood(StorageType storageType, FoodType foodType) throws AppException;
}
