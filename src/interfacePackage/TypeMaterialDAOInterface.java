package interfacePackage;

import exceptionPackage.AppException;

public interface TypeMaterialDAOInterface {
    int getIdByLabel(String label) throws AppException;

    String getLabelById(int id) throws AppException;
}
