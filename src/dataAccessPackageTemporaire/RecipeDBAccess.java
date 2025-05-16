package dataAccessPackageTemporaire;

import java.time.LocalDate;
import java.util.ArrayList;

import modelPackage.Recipe;
import modelPackage.RecipeWithExpiredFood;
import modelPackage.SeasonalRecipe;

public class RecipeDBAccess {
    public RecipeDBAccess() {
    }

    public void addRecipe(Recipe r) {
    }

    public Recipe getRecipe(String label) {
        return null;
    }

    public ArrayList<Recipe> getAllRecipes() {
        return null;
    }

    public void deleteRecipe(String r) {
    }

    public void updateRecipe(Recipe r) {
    }

    public ArrayList<RecipeWithExpiredFood> recipeWithExpireFood() {
        return null;
    }

    public ArrayList<SeasonalRecipe> recipesOfSeason(LocalDate date) {
        return null;
    }
}
