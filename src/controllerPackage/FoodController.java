package controllerPackage;

import businessPackage.FoodManager;
import exceptionPackage.AppException;
import modelPackage.Food;
import modelPackage.FoodType;

import java.util.List;

public class FoodController {
    private FoodManager foodManager;

    public FoodController() {
        setFoodManager(new FoodManager());
    }

    public void setFoodManager(FoodManager foodManager) {
        this.foodManager = foodManager;
    }

    public List<Food> getAllFoods() throws AppException {
        return foodManager.getAllFoods();
    }

    public int getFoodIdByLabel(String label) throws AppException {
        return foodManager.getFoodIdByLabel(label);
    }

    public String getFoodLabelById(int id) throws AppException {
        return foodManager.getFoodLabelById(id);
    }

    public void addFood(String label, FoodType type) throws AppException {
        foodManager.addFood(label, type);
    }

    public Food getFoodByLabel(String label) throws AppException {
        return foodManager.getFoodByLabel(label);
    }
}
