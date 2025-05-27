package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.Recipe;
import modelPackage.RecipeType;
import modelPackage.RecipeWithExpiredFood;

import java.util.Date;
import java.util.List;

public interface RecipeDAOInterface
{
    List<Recipe> getAllRecipes() throws AppException;

    void addRecipe(Recipe recipe) throws AppException;

    Recipe getRecipe(String label) throws AppException;

    void updateRecipe(String labelToFind, String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, RecipeType type) throws AppException;

    void deleteRecipe(String label) throws AppException;

    List<RecipeWithExpiredFood> recipeWithExpireFood() throws AppException;

    List<RecipeWithExpiredFood> recipesWithSomeIngredientsInStock() throws AppException;
}
