package controllerPackage;

import java.time.LocalDate;
import java.util.List;

import businessPackage.RecipeManager;
import exceptionPackage.AppException;
import modelPackage.*;

public class RecipeController {
    private RecipeManager recipeManager;

    public RecipeController() {
        setRecipeManager(new RecipeManager());
    }

    public void setRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    public List<Recipe> getAllRecipes() throws AppException {
        return recipeManager.getAllRecipes();
    }

    public void addRecipe(Recipe recipe) throws AppException {
        recipeManager.addRecipe(recipe);
    }

    public Recipe getRecipe(String label) throws AppException {
        return recipeManager.getRecipe(label);
    }

    public void deleteRecipe(String label) throws AppException {
        recipeManager.deleteRecipe(label);
    }

    public void updateRecipe(Recipe recipe) throws AppException {
        recipeManager.updateRecipe(recipe);
    }

    // Task 1
    public List<Recipe> showRecipesBasedOnTime(Integer cookingTime) throws AppException {
        return recipeManager.showRecipesBasedOnTime(cookingTime);
    }

    // Search 1
    public List<RecipeWithExpiredFood> recipeWithExpiredFood() throws AppException {
        return recipeManager.recipeWithExpiredFood();
    }

    // Search 3
    public List<SeasonalRecipe> RecipesOfSeason(LocalDate date) throws AppException {
        return recipeManager.recipesOfSeason(date);
    }
}