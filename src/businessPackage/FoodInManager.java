package businessPackage;

import java.util.ArrayList;
import java.util.Objects;

import dataAccessPackageTemporaire.FoodInDBAccess;

import modelPackage.ExpiredFood;
import modelPackage.FoodIn;
import modelPackage.FoodInToSearch;

public class FoodInManager {
    private FoodInDBAccess dao;

    public FoodInManager() {
        setDao(new FoodInDBAccess());
    }

    private void setDao(FoodInDBAccess newDao) {
        dao = newDao;
    }

    public ArrayList<FoodIn> getAllFoodIn() {
        ArrayList<FoodIn> foodIns = dao.getAllFoodIn();
        // Traitements éventuels sur la liste de foodIn
        return foodIns;
    }

    public void addFoodIn(FoodIn foodIn) {
        dao.addFoodIn(foodIn);
    }

    public FoodIn getFoodIn(String label) {
        return dao.getFoodIn(label);
    }

    public void deleteFoodIn(String label) {
        dao.deleteFoodIn(label);
    }

    public void updateFoodIn(FoodIn foodIn) {
        dao.updateFoodIn(foodIn);
    }

    public Integer showQuantityLeft(String typeOfFood) {
        ArrayList<FoodInToSearch> foodInToSearch = dao.getFoodInToSearch();
        Integer quantityLeft = 0;

        for (FoodInToSearch f : foodInToSearch) {
            if (Objects.equals(f.getFoodType().getLabel(), typeOfFood)) {
                quantityLeft += f.getFoodIn().getQuantity();
            }
        }

        return quantityLeft;
    }

    public ArrayList<ExpiredFood> expiredFood(String storageType, String foodType) {
        return dao.expiredFood(storageType, foodType);
    }
}
