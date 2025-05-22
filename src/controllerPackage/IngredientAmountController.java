package controllerPackage;

import businessPackage.IngredientAmountManager;
import exceptionPackage.AppException;
import modelPackage.IngredientAmount;

import java.util.ArrayList;

public class IngredientAmountController {
    private IngredientAmountManager ingredientAmountManager;

    public IngredientAmountController() {
        setIngredientAmountManager(new IngredientAmountManager());
    }

    public void setIngredientAmountManager(IngredientAmountManager ingredientAmountManager) {
        this.ingredientAmountManager = ingredientAmountManager;
    }

    public void addIngredientAmount(String recipe, String food, Integer quantity) throws AppException {
        ingredientAmountManager.addIngredientAmount(recipe, food, quantity);
    }

    public void deleteIngredientAmount(String recipe, String food) throws AppException {
        ingredientAmountManager.deleteIngredientAmount(recipe, food);
    }

    public void updateIngredientAmount(String recipe, String food, Integer quantity) throws AppException {
        ingredientAmountManager.updateIngredientAmount(recipe, food, quantity);
    }

    public ArrayList<IngredientAmount> getIngredientAmountsByRecipe(String recipe) throws AppException {
        return ingredientAmountManager.getIngredientAmountsByRecipe(recipe);
    }
}
