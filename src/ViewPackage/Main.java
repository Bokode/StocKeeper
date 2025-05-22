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
        MainWindow mainWindow = new MainWindow();

        // Variable
        LocalDate localDate = LocalDate.now();
        // Convert to java.sql.Date
        Date today = Date.valueOf(localDate);

        // Controller
        FoodInController foodController = new FoodInController();
        RecipeController recipeController = new RecipeController();

        // Test recherche 1 (Non fonctionnel)
        //List<RecipeWithExpiredFood> recipeWithExpiredFood = recipeController.recipeWithExpiredFood();
        //System.out.println(recipeWithExpiredFood.size());
    }
}