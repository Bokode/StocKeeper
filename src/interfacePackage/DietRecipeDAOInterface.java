package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.Diet;

import java.util.List;

public interface DietRecipeDAOInterface {
    void addDietToRecipe(String dietLabel, String recipeLabel) throws AppException;

    void deleteDietFromRecipe(String dietLabel, String recipeLabel) throws AppException;

    List<String> getDietsByRecipe(String recipeLabel) throws AppException;
}