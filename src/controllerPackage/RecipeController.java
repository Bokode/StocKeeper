package controllerPackage;

import java.sql.SQLException;
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

    public void updateRecipe(Recipe recipe, String labelToFind) throws AppException {
        recipeManager.updateRecipe(recipe, labelToFind);
    }

    public List<Recipe> showRecipesBasedOnTime(Integer cookingTimeDebut, Integer cookingTimeEnd) throws AppException {
        return recipeManager.showRecipesBasedOnTime(cookingTimeDebut, cookingTimeEnd);
    }


    public List<RecipeWithExpiredFood> recipeWithExpiredFood() throws AppException {
        return recipeManager.recipeWithExpiredFood();
    }

    public List<RecipeWithExpiredFood> recipeWithSomeIngredientsInStock() throws AppException {
        return recipeManager.recipeWithSomeIngredientsInStock();
    }


}