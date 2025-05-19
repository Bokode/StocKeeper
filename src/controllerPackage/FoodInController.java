package controllerPackage;

import java.util.ArrayList;
import java.util.List;

import businessPackage.FoodInManager;

import modelPackage.*;

public class FoodInController {
    private FoodInManager foodInManager;

    public FoodInController() {
        setFoodInManager(new FoodInManager());
    }

    public void setFoodInManager(FoodInManager foodInManager) {
        this.foodInManager = foodInManager;
    }

    public List<FoodIn> getAllFoodIns() {
        return foodInManager.getAllFoodIn();
    }

    public void addFoodIn(FoodIn foodIn) {
        foodInManager.addFoodIn(foodIn);
    }

    public FoodIn getFoodIn(Integer id) {
        return foodInManager.getFoodIn(id);
    }

    public void deleteFoodIn(Integer id) {
        foodInManager.deleteFoodIn(id);
    }

    public void updateFoodIn(FoodIn foodIn) {
        foodInManager.updateFoodIn(foodIn);
    }

    // Task 2
    public Integer showQuantityLeft(String typeOfFood) {
        return foodInManager.showQuantityLeft(typeOfFood);
    }

    // Search 2
    public List<ExpiredFood> foodExpired(String storageType, String foodType) {
        return foodInManager.expiredFood(storageType, foodType);
    }
}