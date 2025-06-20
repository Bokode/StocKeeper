package businessPackage;

import java.util.ArrayList;
import java.util.List;

import dataAccessPackage.RecipeDAO;
import exceptionPackage.AppException;
import interfacePackage.RecipeDAOInterface;
import modelPackage.Recipe;
import modelPackage.RecipeWithExpiredFood;

public class RecipeManager {
    private RecipeDAOInterface dao;

    public RecipeManager() {
        setDao(new RecipeDAO());
    }

    private void setDao(RecipeDAO newDao) {
        dao = newDao;
    }

    public List<Recipe> getAllRecipes() throws AppException {
        List<Recipe> recipes = dao.getAllRecipes();
        return recipes;
    }

    public void addRecipe(Recipe recipe) throws AppException {
        dao.addRecipe(recipe);
    }

    public Recipe getRecipe(String label) throws AppException {
        return dao.getRecipe(label);
    }

    public void deleteRecipe(String label) throws AppException {
        dao.deleteRecipe(label);
    }

    public void updateRecipe(Recipe recipe, String labelToFind) throws AppException {
        dao.updateRecipe(labelToFind, recipe.getLabel(), recipe.getDescription(), recipe.getCaloricIntake(), recipe.getCold(), recipe.getLastDayDone(), recipe.getTimeToMake(), recipe.getType());
    }

    public List<Recipe> showRecipesBasedOnTime(Integer cookingTimeDebut, Integer cookingTimeEnd) throws AppException {
        List<Recipe> recipesToSearch = getAllRecipes();
        List<Recipe> recipes = new ArrayList<>();

        recipesToSearch.forEach((r) -> {
            if (r != null && r.getTimeToMake() != null && r.getTimeToMake() >= cookingTimeDebut && r.getTimeToMake() <= cookingTimeEnd) {
                recipes.add(r);
            }
        });

        return (recipes);
    }

    public List<RecipeWithExpiredFood> recipeWithExpiredFood() throws AppException {
        return dao.recipeWithExpireFood();
    }

    public List<RecipeWithExpiredFood> recipeWithSomeIngredientsInStock() throws AppException {
        return dao.recipesWithSomeIngredientsInStock();
    }
}
