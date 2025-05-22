package businessPackage;

import dataAccessPackage.RecipeDAO;
import modelPackage.RecipeType;

import java.util.ArrayList;

public class RecipeTypeManager {
    private RecipeDAO dao;

    public RecipeTypeManager() {
        setDao(new RecipeDAO());
    }

    private void setDao(RecipeDAO newDao) {
        dao = newDao;
    }

    public ArrayList<RecipeType> getAllRecipeTypes() {
        //ArrayList<RecipeType> recipesType = dao.getAllRecipeTypes();
        return null;
    }
}
