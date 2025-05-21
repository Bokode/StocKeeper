package controllerPackage;

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

    public void addFoodIn(FoodIn foodIn) throws AppException {
        foodInManager.addFoodIn(foodIn);
    }

    public FoodIn getFoodIn(String label) throws AppException {
        return foodInManager.getFoodIn(label);
    }

    public void deleteFoodIn(String label) throws AppException {
        foodInManager.deleteFoodIn(label);
    }

    public void updateFoodIn(FoodIn foodIn) throws AppException {
        foodInManager.updateFoodIn(foodIn);
    }

    // Task 2
    public Integer showQuantityLeft(String typeOfFood) throws AppException {
        return foodInManager.showQuantityLeft(typeOfFood);
    }

    // Search 2
    public List<ExpiredFood> foodExpired(String storageType, String foodType) throws AppException {
        return foodInManager.expiredFood(storageType, foodType);
    }
}