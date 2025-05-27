package interfacePackage;

import exceptionPackage.AppException;

public interface MaterialDAOInterface {
    void addMaterial(String label, String typeLabel) throws AppException;

    int getMaterialIdByLabel(String label) throws AppException;

    String getMaterialLabelById(int id) throws AppException;
}
