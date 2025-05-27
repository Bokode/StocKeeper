package businessPackage;

import dataAccessPackage.FoodDAO;
import exceptionPackage.AppException;
import interfacePackage.FoodDAOInterface;
import modelPackage.Food;

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

    public Food getFoodByLabel(String label) throws AppException {
        return dao.getFoodByLabel(label);
    }
}
