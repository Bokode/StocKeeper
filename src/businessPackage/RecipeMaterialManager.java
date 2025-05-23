package businessPackage;

import dataAccessPackage.RecipeMaterialDAO;
import exceptionPackage.AppException;
import modelPackage.Material;

import java.util.List;

public class RecipeMaterialManager {
    private RecipeMaterialDAO dao;

    public RecipeMaterialManager() {
        setDao(new RecipeMaterialDAO());
    }

    public void setDao(RecipeMaterialDAO dao) {
        this.dao = dao;
    }

    public void addMaterialToRecipe(String recipeLabel, String materialLabel) throws AppException {
        dao.addMaterialToRecipe(recipeLabel, materialLabel);
    }

    public void deleteMaterialFromRecipe(String recipeLabel, String materialLabel) throws AppException {
        dao.deleteMaterialFromRecipe(recipeLabel, materialLabel);
    }

    public List<Material> getMaterialsByRecipe(String recipeLabel) throws AppException {
        return dao.getMaterialsByRecipe(recipeLabel);
    }
}
