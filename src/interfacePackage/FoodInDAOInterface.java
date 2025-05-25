package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.*;

import java.util.List;

public interface FoodInDAOInterface {
    void addFoodIn(FoodIn foodIn) throws AppException;

    List<FoodIn> getAllFoodIns() throws AppException;

    Integer updateFoodIn(Food food, StorageType storageType, Integer quantity, boolean isOpen, Character nutriScore, java.util.Date purchaseDate, java.util.Date expirationDate) throws AppException;

    Integer deleteFoodInByFoodLabel(String foodLabel) throws AppException;

    FoodIn getFoodInByFoodLabel(String foodLabel) throws AppException;

    List<FoodInToSearch> getFoodInToSearch() throws AppException;

    List<ExpiredFood>  expiredFood(StorageType storageType, FoodType foodType) throws AppException;
}
