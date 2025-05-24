package interfacePackage;

import modelPackage.Recipe;
import modelPackage.RecipeType;
import modelPackage.RecipeWithExpiredFood;
import modelPackage.SeasonalRecipe;

import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface RecipeDAOInterface
{
    List<Recipe> getAllRecipes();

    void addRecipe(Recipe recipe) throws SQLException;

    Recipe getRecipe(String label);

    Integer updateRecipe(String labelToFind, String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, RecipeType type);

    Integer deleteRecipe(String label);

    List<RecipeWithExpiredFood> recipeWithExpireFood();
}
