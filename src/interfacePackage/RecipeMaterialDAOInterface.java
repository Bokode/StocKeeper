package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.Material;

import java.util.List;

public interface RecipeMaterialDAOInterface {
    void addMaterialToRecipe(String recipeLabel, String materialLabel) throws AppException;

    void deleteMaterialFromRecipe(String recipeLabel, String materialLabel) throws AppException;

    List<Material> getMaterialsByRecipe(String recipeLabel) throws AppException;
}
