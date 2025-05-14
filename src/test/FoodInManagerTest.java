package test;

import businessPackage.FoodInManager;
import modelPackage.FoodIn;

public class FoodInManagerTest {
    private FoodInManager foodInManager;
    private FoodIn foodIn;

    void setUp() {
        foodInManager = new FoodInManager();
    }

    void testShowQuantityLeft() {
        //foodIn = FoodIn();
        foodInManager.addFoodIn(foodIn);
        Integer quantityTest = foodInManager.showQuantityLeft("");
    }
}
