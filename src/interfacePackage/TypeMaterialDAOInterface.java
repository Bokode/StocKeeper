package interfacePackage;

import exceptionPackage.AppException;

public interface TypeMaterialDAOInterface {
    void addTypeMaterial(String label) throws AppException;

    void deleteTypeMaterial(String label) throws AppException;

    int getIdByLabel(String label) throws AppException;

    String getLabelById(int id) throws AppException;
}
