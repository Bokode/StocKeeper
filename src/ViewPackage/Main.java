package ViewPackage;

import controllerPackage.FoodInController;
import modelPackage.Food;
import modelPackage.FoodIn;
import modelPackage.FoodType;
import modelPackage.StorageType;

import java.sql.SQLException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws SQLException {
        //MainWindow mainWindow = new MainWindow();

        StorageType storageType = new StorageType("Armoire");

        FoodType foodType = new FoodType("Légumes");

        Food testFood = new Food("Champignons", foodType);

        Date today = new Date();

        today.setHours(0);

        FoodIn test = new FoodIn(today, 5, true, 'C',today, testFood, storageType);

        FoodInController controllerTest = new FoodInController();

        controllerTest.addFoodIn(test);
    }
}