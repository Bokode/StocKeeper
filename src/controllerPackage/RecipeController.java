package controllerPackage;

import java.time.LocalDate;
import java.util.ArrayList;

import businessPackage.RecipeManager;

import modelPackage.*;

public class RecipeController {
    private RecipeManager recipeManager;

    public RecipeController() {
        setRecipeManager(new RecipeManager());
    }

    public void setRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }

    public ArrayList<Recipe> getAllRecipes() {
        return recipeManager.getAllRecipes();
    }

    public void addRecipe(Recipe recipe) {
        recipeManager.addRecipe(recipe);
    }

    public Recipe getRecipe(String label) {
        return recipeManager.getRecipe(label);
    }

    public void deleteRecipe(String label) {
        recipeManager.deleteRecipe(label);
    }

    public void updateRecipe(Recipe recipe) {
        recipeManager.updateRecipe(recipe);
    }

    // Task 1
    public ArrayList<Recipe> showRecipesBasedOnTime(Integer cookingTime) {
        return recipeManager.showRecipesBasedOnTime(cookingTime);
    }

    // Search 1
    public ArrayList<RecipeWithExpiredFood> recipeWithExpiredFood() {
        return recipeManager.recipeWithExpiredFood();
    }

    // Search 3
    public ArrayList<SeasonalRecipe> RecipesOfSeason(LocalDate date) {
        return recipeManager.recipesOfSeason(date);
    }
}