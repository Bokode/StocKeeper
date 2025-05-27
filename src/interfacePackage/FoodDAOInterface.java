package interfacePackage;

import exceptionPackage.AppException;
import modelPackage.Food;

import java.util.List;

public interface FoodDAOInterface {
    List<Food> getAllFoods() throws AppException;

    int getFoodIdByLabel(String label) throws AppException;

    String getFoodLabelById(int id) throws AppException;

    Food getFoodByLabel(String label) throws AppException;
}
