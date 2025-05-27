package businessPackage;

import dataAccessPackage.DietRecipeDAO;
import exceptionPackage.AppException;
import interfacePackage.DietRecipeDAOInterface;
import modelPackage.Diet;

import java.util.List;

public class DietRecipeManager {
    private DietRecipeDAOInterface dao;

    public DietRecipeManager() {
        setDao(new DietRecipeDAO());
    }

    private void setDao(DietRecipeDAO newDao) {
        dao = newDao;
    }

    public void addDietToRecipe(String recipeLabel, String dietLabel) throws AppException {
        dao.addDietToRecipe(recipeLabel, dietLabel);
    }

    public void deleteDietFromRecipe(String recipeLabel, String dietLabel) throws AppException {
        dao.deleteDietFromRecipe(recipeLabel, dietLabel);
    }

    public List<String> getDietsByRecipe(String recipeLabel) throws AppException {
        return dao.getDietsByRecipe(recipeLabel);
    }
}
