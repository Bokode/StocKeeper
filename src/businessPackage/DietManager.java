package businessPackage;

import dataAccessPackage.DietDAO;
import exceptionPackage.AppException;
import interfacePackage.DietDAOInterface;

public class DietManager {
    private DietDAOInterface dao;

    public DietManager() {
        setDao(new DietDAO());
    }

    private void setDao(DietDAO newDao) {
        dao = newDao;
    }

    public void addDiet(String label) throws AppException {
        dao.addDiet(label);
    }

    public int getDietIdByLabel(String label) throws AppException {
        return dao.getDietIdByLabel(label);
    }

    public String getDietLabelById(int id) throws AppException {
        return dao.getDietLabelById(id);
    }
}
