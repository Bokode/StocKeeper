package businessPackage;

import dataAccessPackage.FoodDAO;
import exceptionPackage.AppException;
import modelPackage.FoodType;

public class FoodManager {
    private FoodDAO dao;

    public FoodManager() {
        setDao(new FoodDAO());
    }

    private void setDao(FoodDAO newDao) {
        dao = newDao;
    }

    public int getFoodIdByLabel(String label) throws AppException {
        return dao.getFoodIdByLabel(label);
    }

    public String getFoodLabelById(int id) throws AppException {
        return dao.getFoodLabelById(id);
    }

    public void addFood(String label, FoodType type) throws AppException {
        dao.addFood(label, type);
    }
}
