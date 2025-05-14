package controllerPackage;

import java.util.ArrayList;
import java.util.Date;

import businessPackage.FoodInManager;
import businessPackage.RecipeManager;

import modelPackage.*;

public class ApplicationController {
    private RecipeManager recipeManager;
    private FoodInManager foodInManager;

    public ApplicationController() {
        setRecipeManager(new RecipeManager());
        setFoodInManager(new FoodInManager());
    }

    public void setRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }
    public void setFoodInManager(FoodInManager foodInManager) {
        this.foodInManager = foodInManager;
    }

    public ArrayList<Recipe> getAllRecipes() {
        return recipeManager.getAllRecipes();
    }
    public ArrayList<FoodIn> getAllFoodIns() {
        return foodInManager.getAllFoodIn();
    }

    public void addRecipe(Recipe recipe) {
        recipeManager.addRecipe(recipe);
    }
    public void addFoodIn(FoodIn foodIn) {
        foodInManager.addFoodIn(foodIn);
    }

    public Recipe getRecipe(int id) {
        return recipeManager.getRecipe(id);
    }
    public FoodIn getFoodIn(int id) {
        return foodInManager.getFoodIn(id);
    }

    public void deleteRecipe(Recipe recipe) {
        recipeManager.deleteRecipe(recipe);
    }
    public void deleteFoodIn(FoodIn foodIn) {
        foodInManager.deleteFoodIn(foodIn);
    }

    public void updateRecipe(Recipe recipe) {
        recipeManager.updateRecipe(recipe);
    }
    public void updateFoodIn(FoodIn foodIn) {
        foodInManager.updateFoodIn(foodIn);
    }

    // Task 1
    public ArrayList<Recipe> showRecipesBasedOnTime(Integer cookingTime) {
        return recipeManager.showRecipesBasedOnTime(cookingTime);
    }
    // Task 2
    public Integer showQuantityLeft(String typeOfFood) {
        return foodInManager.showQuantityLeft(typeOfFood);
    }

    // Search 1
    public ArrayList<RecipeWithExpiredFood> recipeWithExpiredFood() {
        return recipeManager.recipeWithExpiredFood();
    }

    // Search 2
    public ArrayList<ExpiredFood> foodExpired(String storageType, String foodType) {
      return foodInManager.expiredFood(storageType, foodType);
    }

    // Search 3
    public ArrayList<SeasonalRecipe> RecipesOfSeason(Date date) {
        return recipeManager.recipesOfSeason(date);
    }
}