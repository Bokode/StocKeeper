package controllerPackage;

import businessPackage.DietManager;
import exceptionPackage.AppException;
import modelPackage.Diet;

import java.util.List;

public class DietController {
    private DietManager dietManager;

    public DietController() {
        setDietManager(new DietManager());
    }

    private void setDietManager(DietManager dietManager) {
        this.dietManager = dietManager;
    }

    public List<Diet> getAllDiets() throws AppException {
        return dietManager.getAllDiets();
    }

    public void addDiet(String label) throws AppException {
        dietManager.addDiet(label);
    }

    public int getDietIdByLabel(String label) throws AppException {
        return dietManager.getDietIdByLabel(label);
    }

    public String getDietLabelById(int id) throws AppException {
        return dietManager.getDietLabelById(id);
    }
}