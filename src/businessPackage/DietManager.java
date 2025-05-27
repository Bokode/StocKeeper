package businessPackage;

import dataAccessPackage.DietDAO;
import exceptionPackage.AppException;
import interfacePackage.DietDAOInterface;
import modelPackage.Diet;

import java.util.List;

public class DietManager {
    private DietDAOInterface dao;

    public DietManager() {
        setDao(new DietDAO());
    }

    private void setDao(DietDAO newDao) {
        dao = newDao;
    }

    public List<Diet> getAllDiets() throws AppException {
        return dao.getAllDiets();
    }
}
