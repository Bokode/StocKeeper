package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.FoodType;

public interface FoodTypeDAOInterface {
    FoodType getFoodTypeById(int id) throws AppException;
}
