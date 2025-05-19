package interfacePackage;

import modelPackage.Recipe;

import java.sql.Date;
import java.util.List;

public interface RecipeDAOInterface
{
    List<Recipe> getAllRecipes();

    Integer addRecipe(String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, Integer type);

    Recipe getRecipeByLabel(String label);

    Integer updateRecipe(Integer id, String label, String description, Integer caloricIntake, boolean isCold, Date lastDateDone, Integer timeToMake, Integer type);

    Integer deleteRecipe(Integer id);


}
