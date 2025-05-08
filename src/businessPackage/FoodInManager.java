package businessPackage;

import java.util.ArrayList;

import dataAccessPackageTemporaire.FoodInDBAccess;

import modelPackage.FoodIn;

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

    public FoodIn getFoodIn(int id) {
        return dao.getFoodIn(id);
    }

    public void deleteFoodIn(FoodIn foodIn) {
        dao.deleteFoodIn(foodIn);
    }

    public void updateFoodIn(FoodIn foodIn) {
        dao.updateFoodIn(foodIn);
    }

    public Integer showQuantityLeft(String typeOfFood) {
        ArrayList<FoodIn> foodInToSearch = getAllFoodIn();
        Integer quantityLeft = 0;

        foodInToSearch.forEach((f) -> {
            // Besoin d'une requete SQL contenant une jointure entre les trois tables
            if (f.getFood() == typeOfFood) {
                quantityLeft += f.getQuantity();
            }
        });

        return (quantityLeft);
    }
}
