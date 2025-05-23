package controllerPackage;

import businessPackage.RecipeMaterialManager;
import exceptionPackage.AppException;
import modelPackage.Material;

import java.util.List;

public class RecipeMaterialController {
    private RecipeMaterialManager recipeMaterialManager;

    public RecipeMaterialController() {
        setRecipeManager(new RecipeMaterialManager());
    }

    public void setRecipeManager(RecipeMaterialManager recipeManager) {
        this.recipeMaterialManager = recipeManager;
    }

    public void addMaterialToRecipe(String recipeLabel, String materialLabel) throws AppException {
        recipeMaterialManager.addMaterialToRecipe(recipeLabel, materialLabel);
    }

    public void deleteMaterialFromRecipe(String recipeLabel, String materialLabel) throws AppException {
        recipeMaterialManager.deleteMaterialFromRecipe(recipeLabel, materialLabel);
    }

    public List<Material> getMaterialsByRecipe(String recipeLabel) throws AppException {
        return recipeMaterialManager.getMaterialsByRecipe(recipeLabel);
    }
}
