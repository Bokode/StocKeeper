package businessPackage;

import java.time.LocalDate;
import java.util.ArrayList;

import dataAccessPackageTemporaire.RecipeDBAccess;

import modelPackage.Recipe;
import modelPackage.RecipeWithExpiredFood;
import modelPackage.SeasonalRecipe;

public class RecipeManager {
    private RecipeDBAccess dao;

    public RecipeManager() {
        setDao(new RecipeDBAccess());
    }

    private void setDao(RecipeDBAccess newDao) {
        dao = newDao;
    }

    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = dao.getAllRecipes();
        // Traitements éventuels sur la liste de recipe

        return recipes;
    }

    public void addRecipe(Recipe recipe) {
        dao.addRecipe(recipe);
    }

    public Recipe getRecipe(String label) {
        return dao.getRecipe(label);
    }

    public void deleteRecipe(String label) {
        dao.deleteRecipe(label);
    }

    public void updateRecipe(Recipe recipe) {
        dao.updateRecipe(recipe);
    }

    public ArrayList<Recipe> showRecipesBasedOnTime(Integer cookingTime) {
        ArrayList<Recipe> recipesToSearch = getAllRecipes();
        ArrayList<Recipe> recipes = new ArrayList<>();

        recipesToSearch.forEach((r) -> {
            if (r.getTimeToMake() != null && r.getTimeToMake() <= cookingTime) {
                recipes.add(r);
            }
        });

        return (recipes);
    }

    public ArrayList<RecipeWithExpiredFood> recipeWithExpiredFood() {
        return dao.recipeWithExpireFood();
    }

    public ArrayList<SeasonalRecipe> recipesOfSeason(LocalDate date) {
        return dao.recipesOfSeason(date);
    }
}
