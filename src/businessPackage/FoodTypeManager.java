package businessPackage;

import dataAccessPackage.FoodTypeDAO;
import exceptionPackage.AppException;
import interfacePackage.FoodTypeDAOInterface;

public class FoodTypeManager {
    private FoodTypeDAOInterface dao;

    public FoodTypeManager() {
        setDao(new FoodTypeDAO());
    }

    private void setDao(FoodTypeDAO newDao) {
        dao = newDao;
    }

    public int getIdByLabel(String label) throws AppException {
        return dao.getIdByLabel(label);
    }

    public String getLabelById(int id) throws AppException {
        return dao.getLabelById(id);
    }
}
