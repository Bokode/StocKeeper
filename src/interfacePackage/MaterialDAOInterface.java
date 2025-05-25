package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.Material;

public interface MaterialDAOInterface {
    void addMaterial(String label, String typeLabel) throws AppException;

    void deleteMaterial(String label) throws AppException;

    int getMaterialIdByLabel(String label) throws AppException;

    String getMaterialLabelById(int id) throws AppException;

    void addMaterial(Material mat) throws AppException;
}
