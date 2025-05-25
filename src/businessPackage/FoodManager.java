package businessPackage;

import dataAccessPackage.FoodDAO;
import exceptionPackage.AppException;
import interfacePackage.FoodDAOInterface;
import modelPackage.Food;
import modelPackage.FoodType;

import java.util.List;

public class FoodManager {
    private FoodDAOInterface dao;

    public FoodManager() {
        setDao(new FoodDAO());
    }

    private void setDao(FoodDAO newDao) {
        dao = newDao;
    }

    public List<Food> getAllFoods() throws AppException {
        return dao.getAllFoods();
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

    public Food getFoodByLabel(String label) throws AppException {
        return dao.getFoodByLabel(label);
    }
}
