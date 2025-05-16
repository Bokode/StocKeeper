package controllerPackage;

import java.util.ArrayList;

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

    public ArrayList<FoodIn> getAllFoodIns() {
        return foodInManager.getAllFoodIn();
    }

    public void addFoodIn(FoodIn foodIn) {
        foodInManager.addFoodIn(foodIn);
    }

    public FoodIn getFoodIn(String label) {
        return foodInManager.getFoodIn(label);
    }

    public void deleteFoodIn(String label) {
        foodInManager.deleteFoodIn(label);
    }

    public void updateFoodIn(FoodIn foodIn) {
        foodInManager.updateFoodIn(foodIn);
    }

    // Task 2
    public Integer showQuantityLeft(String typeOfFood) {
        return foodInManager.showQuantityLeft(typeOfFood);
    }

    // Search 2
    public ArrayList<ExpiredFood> foodExpired(String storageType, String foodType) {
        return foodInManager.expiredFood(storageType, foodType);
    }
}