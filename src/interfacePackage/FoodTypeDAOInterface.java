package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.FoodType;

public interface FoodTypeDAOInterface {
    int getIdByLabel(String label) throws AppException;

    String getLabelById(int id) throws AppException;

    FoodType getFoodTypeById(int id) throws AppException;
}
