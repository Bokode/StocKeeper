package ViewPackage;

import controllerPackage.FoodInController;
import controllerPackage.RecipeController;
import modelPackage.*;

import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        //App
        //MainWindow mainWindow = new MainWindow();

        // Variable
        LocalDate localDate = LocalDate.now();
        // Convert to java.sql.Date
        Date today = Date.valueOf(localDate);

        // Controller
        FoodInController foodController = new FoodInController();
        RecipeController recipeController = new RecipeController();

        // Test add food
        /*StorageType storageType = new StorageType("Armoire");
        FoodType foodType = new FoodType("Légumes");
        Food testFood = new Food("Champignons", foodType);
        FoodIn test = new FoodIn(today, 5, true, 'C',today, testFood, storageType);
        foodController.addFoodIn(test);*/

        // Test add recipe
        /*RecipeType recipeType = new RecipeType("Féculent");
        Recipe recipe = new Recipe("Pizza", "Une bonne pizza", 500, today, 30, false, recipeType);
        recipeController.addRecipe(recipe);*/

        // Test recherche 1 (Non fonctionnel)
        //List<RecipeWithExpiredFood> recipeWithExpiredFood = recipeController.recipeWithExpiredFood();
        //System.out.println(recipeWithExpiredFood.size());

        // Test recherche 2
        /*StorageType fridge = new StorageType("Fridge");
        FoodType fruit = new FoodType("Fruit");
        List<ExpiredFood> result = foodController.foodExpired(fridge, fruit);
        System.out.println(result.size());*/

        // Test recherche 3 (A tester / Ajuster le nom des colonnes)
        /*List<SeasonalRecipe> recipes = recipeController.RecipesOfSeason(LocalDate.now());
        for (SeasonalRecipe recipe : recipes) {
            System.out.println(recipe);
        }*/
    }
}