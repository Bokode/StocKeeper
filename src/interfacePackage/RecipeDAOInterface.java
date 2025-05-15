package interfacePackage;

import dataAccess.Recipe;

import java.sql.Date;
import java.util.List;

public interface RecipeDAOInterface
{
    List<Recipe> getAllRecipes();

    int addRecipe(String label, String description, int caloricIntake, boolean isCold, Date lastDateDone, int timeToMake, int type);

    Recipe getRecipeByLabel(String label);

    int updateRecipe(int id, String label, String description, int caloricIntake, boolean isCold, Date lastDateDone, int timeToMake, int type);

    int deleteRecipe(int id);


}
