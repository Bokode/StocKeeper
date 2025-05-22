package controllerPackage;

import businessPackage.FoodManager;
import exceptionPackage.AppException;
import modelPackage.FoodType;

public class FoodController {
    private FoodManager foodManager;

    public FoodController() {
        setFoodManager(new FoodManager());
    }

    public void setFoodManager(FoodManager foodManager) {
        this.foodManager = foodManager;
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
}
