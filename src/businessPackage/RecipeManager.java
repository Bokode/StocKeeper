package businessPackage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dataAccessPackage.RecipeDAO;
import modelPackage.Recipe;
import modelPackage.RecipeWithExpiredFood;
import modelPackage.SeasonalRecipe;

public class RecipeManager {
    private RecipeDAO dao;

    public RecipeManager() {
        setDao(new RecipeDAO());
    }

    private void setDao(RecipeDAO newDao) {
        dao = newDao;
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = dao.getAllRecipes();
        // Traitements éventuels sur la liste de recipe
        return recipes;
    }

    public void addRecipe(Recipe recipe) {
        dao.addRecipe(recipe.getLabel(), recipe.getDescription(), recipe.getCaloricIntake(), recipe.getCold(), recipe.getLastDayDone(), recipe.getTimeToMake(), recipe.getType());
    }

    public Recipe getRecipe(String label) {
        return dao.getRecipeByLabel(label);
    }

    public void deleteRecipe(Integer id) {
        dao.deleteRecipe(id);
    }

    public void updateRecipe(Recipe recipe) {
        dao.updateRecipe(recipe.getId(), recipe.getLabel(), recipe.getDescription(), recipe.getCaloricIntake(), recipe.getCold(), recipe.getLastDayDone(), recipe.getTimeToMake(), recipe.getType());
    }

    public List<Recipe> showRecipesBasedOnTime(Integer cookingTime) {
        List<Recipe> recipesToSearch = getAllRecipes();
        ArrayList<Recipe> recipes = new ArrayList<>();

        recipesToSearch.forEach((r) -> {
            if (r.getTimeToMake() != null && r.getTimeToMake() <= cookingTime) {
                recipes.add(r);
            }
        });

        return (recipes);
    }

    public List<RecipeWithExpiredFood> recipeWithExpiredFood() {
        return dao.recipeWithExpireFood();
    }

    public List<SeasonalRecipe> recipesOfSeason(LocalDate date) {
        return dao.recipesOfSeason(date);
    }
}
