package businessPackage;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import dataAccessPackage.FoodInDAO;
import exceptionPackage.AppException;
import interfacePackage.FoodInDAOInterface;
import modelPackage.*;

public class FoodInManager {
    private FoodInDAOInterface dao;

    public FoodInManager() {
        setDao(new FoodInDAO());
    }

    private void setDao(FoodInDAO newDao) {
        dao = newDao;
    }

    public List<FoodIn> getAllFoodIn() throws AppException {
        List<FoodIn> foodIns = dao.getAllFoodIns();
        return foodIns;
    }

    public void addFoodIn(FoodIn foodIn) throws AppException {
        dao.addFoodIn(foodIn);
    }

    public FoodIn getFoodIn(String label) throws AppException {
        return dao.getFoodInByFoodLabel(label);
    }

    public void deleteFoodIn(String label) throws AppException {
        dao.deleteFoodInByFoodLabel(label);
    }

    public void updateFoodIn(FoodIn foodIn) throws AppException {
        dao.updateFoodIn(foodIn.getFood(), foodIn.getStorageType(), foodIn.getQuantity(), foodIn.getOpen(), foodIn.getNutriScore(), foodIn.getPurchaseDate(), foodIn.getExpirationDate());
    }

    public Integer deleteFoodInByFoodLabel(String foodLabel) throws AppException {
        return dao.deleteFoodInByFoodLabel(foodLabel);
    }

    public QuantityLeft showQuantityLeft(String typeOfFood) throws AppException {
        List<FoodInToSearch> foodInToSearch = dao.getFoodInToSearch();
        Integer quantityLeft = 0;
        Integer numberDifferentType = 0;

        for (FoodInToSearch f : foodInToSearch) {
            if (Objects.equals(f.getFoodType().getLabel(), typeOfFood)) {
                numberDifferentType++;
                quantityLeft += f.getFoodIn().getQuantity();
            }
        }


        return (new QuantityLeft(quantityLeft, numberDifferentType));
    }

    public List<ExpiredFood> expiredFood(StorageType storageType, FoodType foodType) throws AppException {
        return dao.expiredFood(storageType, foodType);
    }
}