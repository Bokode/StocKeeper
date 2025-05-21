package ViewPackage;

import controllerPackage.FoodInController;
import controllerPackage.RecipeController;
import modelPackage.*;

import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        //MainWindow mainWindow = new MainWindow();

        // Variable
        LocalDate localDate = LocalDate.now();
        // Convert to java.sql.Date
        Date today = Date.valueOf(localDate);

        // Controller
        FoodInController controllerTest = new FoodInController();
        RecipeController recipeController = new RecipeController();

        // Test add food
        StorageType storageType = new StorageType("Armoire");
        FoodType foodType = new FoodType("Légumes");
        Food testFood = new Food("Champignons", foodType);
        FoodIn test = new FoodIn(today, 5, true, 'C',today, testFood, storageType);
        controllerTest.addFoodIn(test);

        // Test add recipe
        //RecipeType recipeType = new RecipeType("Féculent");
        //Recipe recipe = new Recipe("Pizza", "Une bonne pizza", 500, today, 30, false, recipeType);
        //recipeController.addRecipe(recipe);

    }
}