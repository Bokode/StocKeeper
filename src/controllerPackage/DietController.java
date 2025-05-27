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
}