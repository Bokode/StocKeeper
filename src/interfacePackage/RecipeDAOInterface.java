package interfacePackage;

import modelPackage.Recipe;
import modelPackage.RecipeType;
import modelPackage.RecipeWithExpiredFood;
import modelPackage.SeasonalRecipe;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface RecipeDAOInterface
{
    List<Recipe> getAllRecipes();

    Integer addRecipe(String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, RecipeType type);

    Recipe getRecipeByLabel(String label);

    Integer updateRecipe(String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, RecipeType type);

    Integer deleteRecipe(String label);

    List<RecipeWithExpiredFood> recipeWithExpireFood();

    List<SeasonalRecipe> recipesOfSeason(LocalDate date);
}
