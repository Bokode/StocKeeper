package controllerPackage;

import java.sql.SQLException;
import java.util.List;

import businessPackage.FoodInManager;
import exceptionPackage.AppException;
import modelPackage.*;

public class FoodInController {
    private FoodInManager foodInManager;

    public FoodInController() {
        setFoodInManager(new FoodInManager());
    }

    public void setFoodInManager(FoodInManager foodInManager) {
        this.foodInManager = foodInManager;
    }

    public List<FoodIn> getAllFoodIns() throws AppException {
        return foodInManager.getAllFoodIn();
    }

    public void addFoodIn(FoodIn foodIn) throws AppException, SQLException {
        foodInManager.addFoodIn(foodIn);
    }

    public FoodIn getFoodIn(String label) throws AppException {
        return foodInManager.getFoodIn(label);
    }

    public void updateFoodIn(FoodIn foodIn) throws AppException {
        foodInManager.updateFoodIn(foodIn);
    }

    public Integer deleteFoodInByFoodLabel(String foodLabel) throws AppException {
        return foodInManager.deleteFoodInByFoodLabel(foodLabel);
    }

    // Task 2
    public QuantityLeft showQuantityLeft(String typeOfFood) throws AppException {
        return foodInManager.showQuantityLeft(typeOfFood);
    }

    // Search 2
    public List<ExpiredFood> foodExpired(StorageType storageType, FoodType foodType) throws AppException {
        return foodInManager.expiredFood(storageType, foodType);
    }
}