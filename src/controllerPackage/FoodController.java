package controllerPackage;

import businessPackage.FoodManager;
import exceptionPackage.AppException;
import modelPackage.Food;

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

    public Food getFoodByLabel(String label) throws AppException {
        return foodManager.getFoodByLabel(label);
    }
}
