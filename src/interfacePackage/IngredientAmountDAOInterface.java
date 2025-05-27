package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.IngredientAmount;

import java.util.ArrayList;

public interface IngredientAmountDAOInterface {
    void addIngredientAmount(String recipe, String food, Integer quantity) throws AppException;

    void deleteIngredientAmount(String recipe, String food) throws AppException;

    ArrayList<IngredientAmount> getIngredientAmountsByRecipe(String recipe) throws AppException;
}
