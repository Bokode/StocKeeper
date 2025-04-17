package Business;

import Model.Recipe;

import java.util.ArrayList;

public class RecipeManager {
    private RecipeDBAccess dao;

    public RecipeManager() {
        setDao(new RecipeDBAcces);
    }

    private void setDao(RecipeDBAccess newDao) {
        // test à faire
        dao = newDao;
    }

    public ArrayList<Recipe> getAllRecipes() {
        // test à faire
        dao.getAllRecipes();
    }

    public void addRecipe(Recipe recipe) {
        // test à faire
        dao.addRecipe(recipe);
    }

    public void getRecipe(int id) {
        // test à faire
        dao.getRecipe(id);
    }

    public void deleteRecipe(Recipe recipe) {
        // test à faire
        dao.deleteRecipe(recipe);
    }

    public void updateRecipe(Recipe recipe) {
        // test à faire
        dao.updateRecipe(recipe);
    }
}
