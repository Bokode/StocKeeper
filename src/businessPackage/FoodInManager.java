package businessPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dataAccessPackage.FoodInDAO;
import interfacePackage.FoodInDAOInterface;
import modelPackage.ExpiredFood;
import modelPackage.FoodIn;
import modelPackage.FoodInToSearch;

public class FoodInManager {
    private FoodInDAOInterface dao;

    public FoodInManager() {
        setDao(new FoodInDAO());
    }

    private void setDao(FoodInDAO newDao) {
        dao = newDao;
    }

    public List<FoodIn> getAllFoodIn() {
        List<FoodIn> foodIns = dao.getAllFoodIns();
        // Traitements éventuels sur la liste de foodIn
        return foodIns;
    }

    public void addFoodIn(FoodIn foodIn) {
        dao.addFoodIn(foodIn.getFood(), foodIn.getStorageType(), foodIn.getQuantity(), foodIn.getOpen(), foodIn.getNutriScore(), foodIn.getPurchaseDate(), foodIn.getExpirationDate());
    }

    public FoodIn getFoodIn(Integer id) {
        return dao.getFoodInById(id);
    }

    public void deleteFoodIn(Integer id) {
        dao.deleteFoodIn(id);
    }

    public void updateFoodIn(FoodIn foodIn) {
        dao.updateFoodIn(foodIn.getId(), foodIn.getFood(), foodIn.getStorageType(), foodIn.getQuantity(), foodIn.getOpen(), foodIn.getNutriScore(), foodIn.getPurchaseDate(), foodIn.getExpirationDate());
    }

    public Integer showQuantityLeft(String typeOfFood) {
        List<FoodInToSearch> foodInToSearch = dao.getFoodInToSearch();
        Integer quantityLeft = 0;

        for (FoodInToSearch f : foodInToSearch) {
            if (Objects.equals(f.getFoodType().getLabel(), typeOfFood)) {
                quantityLeft += f.getFoodIn().getQuantity();
            }
        }

        return quantityLeft;
    }

    public List<ExpiredFood> expiredFood(String storageType, String foodType) {
        return dao.expiredFood(storageType, foodType);
    }
}
