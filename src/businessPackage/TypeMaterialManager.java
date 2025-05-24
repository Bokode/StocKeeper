package businessPackage;

import dataAccessPackage.TypeMaterialDAO;
import exceptionPackage.AppException;

public class TypeMaterialManager {
    private TypeMaterialDAO dao;

    public TypeMaterialManager() {
        setDao(new TypeMaterialDAO());
    }

    private void setDao(TypeMaterialDAO newDao) {
        dao = newDao;
    }

    public void addTypeMaterial(String label) throws AppException {
        dao.addTypeMaterial(label);
    }

    public void deleteTypeMaterial(String label) throws AppException {
        dao.deleteTypeMaterial(label);
    }

    public int getIdByLabel(String label) throws AppException {
        return dao.getIdByLabel(label);
    }

    public String getLabelById(int id) throws AppException {
        return dao.getLabelById(id);
    }
}
