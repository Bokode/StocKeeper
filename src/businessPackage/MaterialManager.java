package businessPackage;

import dataAccessPackage.MaterialDAO;
import exceptionPackage.AppException;
import interfacePackage.MaterialDAOInterface;
import modelPackage.Material;

public class MaterialManager {
    private MaterialDAOInterface dao;

    public MaterialManager() {
        setDao(new MaterialDAO());
    }

    private void setDao(MaterialDAO newDao) {
        dao = newDao;
    }

    public void addMaterial(String label, String typeLabel) throws AppException {
        dao.addMaterial(label, typeLabel);
    }

    public void deleteMaterial(String label) throws AppException {
        dao.deleteMaterial(label);
    }

    public int getMaterialIdByLabel(String label) throws AppException {
        return dao.getMaterialIdByLabel(label);
    }

    public String getMaterialLabelById(int id) throws AppException {
        return dao.getMaterialLabelById(id);
    }

    public void addMaterial(Material mat) throws AppException {
        dao.addMaterial(mat);
    }
}
