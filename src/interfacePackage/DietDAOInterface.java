package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.Diet;

import java.util.List;

public interface DietDAOInterface {
    List<Diet> getAllDiets() throws AppException;

    void addDiet(String label) throws AppException;

    int getDietIdByLabel(String label) throws AppException;

    String getDietLabelById(int id) throws AppException;
}
