package businessPackage;

import dataAccessPackageTemporaire.RecipeTypeDBAccess;
import modelPackage.RecipeType;

import java.util.ArrayList;

public class RecipeTypeManager {
    private RecipeTypeDBAccess dao;

    public RecipeTypeManager() {
        setDao(new RecipeTypeDBAccess());
    }

    private void setDao(RecipeTypeDBAccess newDao) {
        dao = newDao;
    }

    public ArrayList<RecipeType> getAllRecipeTypes() {
        ArrayList<RecipeType> recipesType = dao.getAllRecipeTypes();
        // Traitements éventuels sur la liste de recipeType

        return recipesType;
    }
}
