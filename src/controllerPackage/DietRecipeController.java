package controllerPackage;

import businessPackage.DietRecipeManager;
import exceptionPackage.AppException;

import java.util.List;

public class DietRecipeController {
    private DietRecipeManager dietRecipeManager;

    public DietRecipeController() {
        setDietRecipeManager(new DietRecipeManager());
    }

    public void setDietRecipeManager(DietRecipeManager dietRecipeManager) {
        this.dietRecipeManager = dietRecipeManager;
    }

    public void addDietToRecipe(String recipeLabel, String dietLabel) throws AppException {
        dietRecipeManager.addDietToRecipe(recipeLabel, dietLabel);
    }

    public void deleteDietFromRecipe(String recipeLabel, String dietLabel) throws AppException {
        dietRecipeManager.deleteDietFromRecipe(recipeLabel, dietLabel);
    }

    public List<String> getDietsByRecipe(String recipeLabel) throws AppException {
        return dietRecipeManager.getDietsByRecipe(recipeLabel);
    }
}
