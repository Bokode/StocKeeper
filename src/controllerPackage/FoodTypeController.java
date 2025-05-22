package controllerPackage;

import businessPackage.FoodTypeManager;
import exceptionPackage.AppException;

public class FoodTypeController {
    private FoodTypeManager foodTypeManager;

    public FoodTypeController() {
        setFoodTypeManager(new FoodTypeManager());
    }

    public void setFoodTypeManager(FoodTypeManager foodTypeManager) {
        this.foodTypeManager = foodTypeManager;
    }

    public int getIdByLabel(String label) throws AppException {
        return foodTypeManager.getIdByLabel(label);
    }

    public String getLabelById(int id) throws AppException {
        return foodTypeManager.getLabelById(id);
    }
}
