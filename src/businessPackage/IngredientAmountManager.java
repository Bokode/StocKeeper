package businessPackage;

import dataAccessPackage.IngredientAmountDAO;
import exceptionPackage.AppException;
import modelPackage.IngredientAmount;

import java.util.ArrayList;

public class IngredientAmountManager {
    private IngredientAmountDAO dao;

    public IngredientAmountManager() {
        setDao(new IngredientAmountDAO());
    }

    public void setDao(IngredientAmountDAO dao) {
        this.dao = dao;
    }

    public void addIngredientAmount(String recipe, String food, Integer quantity) throws AppException {
        dao.addIngredientAmount(recipe, food, quantity);
    }

    public void updateIngredientAmount(String recipe, String food, Integer quantity) throws AppException {
        dao.updateIngredientAmount(recipe, food, quantity);
    }

    public ArrayList<IngredientAmount> getIngredientAmountsByRecipe(String recipe) throws AppException {
        return dao.getIngredientAmountsByRecipe(recipe);
    }
}
