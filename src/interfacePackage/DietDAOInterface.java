package interfacePackage;

import exceptionPackage.AppException;

public interface DietDAOInterface {
    void addDiet(String label) throws AppException;

    int getDietIdByLabel(String label) throws AppException;

    String getDietLabelById(int id) throws AppException;
}
