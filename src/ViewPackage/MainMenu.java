package ViewPackage;

import javax.swing.*;

public class MainMenu extends JMenuBar
{
    private JMenu recipeMenu, foodInMenu, searchMenu, homeMenu, taskMenu;
    private JMenuItem addRecipe, deleteRecipe, updateRecipe, listingRecipe, addFoodIn, deleteFoodIn, updateFoodIn, listingFoodIn, searchRecipeFoodInOwnedAndNotExpired, searchFoodTypeExpiredInStorage, searchRecipeWithFoodExpired5DAndWithActualSeason, homeScreen, exitApplication, quantityTypeFood, timeToMakeRecipe;
    private MainWindow mainWindow;
    public MainMenu(MainWindow mainWindow)
    {
        this.mainWindow = mainWindow;

        homeMenu = new JMenu("Accueil");
        homeMenu.setMnemonic('A');
        recipeMenu = new JMenu("Recette");
        recipeMenu.setMnemonic('Z');
        foodInMenu = new JMenu("Nourriture possédée");
        foodInMenu.setMnemonic('E');
        searchMenu = new JMenu("Recherches");
        searchMenu.setMnemonic('R');
        taskMenu = new JMenu("Tâches");
        taskMenu.setMnemonic('T');

        this.add(homeMenu);
        this.add(recipeMenu);
        this.add(foodInMenu);
        this.add(searchMenu);
        this.add(taskMenu);

        homeScreen = new JMenuItem("Retour à l'écran d'acceuil");
        exitApplication = new JMenuItem("Quitter l'application");
        homeMenu.add(homeScreen);
        homeMenu.add(exitApplication);

        addRecipe = new JMenuItem("Ajouter");
        deleteRecipe = new JMenuItem("Supprimer");
        updateRecipe = new JMenuItem("Modifier");
        listingRecipe = new JMenuItem("Recettes");
        recipeMenu.add(addRecipe);
        recipeMenu.add(deleteRecipe);
        recipeMenu.add(updateRecipe);
        recipeMenu.add(listingRecipe);

        addFoodIn = new JMenuItem("Ajouter");
        deleteFoodIn = new JMenuItem("Supprimer");
        updateFoodIn = new JMenuItem("Modifier");
        listingFoodIn = new JMenuItem("Aliments");
        foodInMenu.add(addFoodIn);
        foodInMenu.add(deleteFoodIn);
        foodInMenu.add(updateFoodIn);
        foodInMenu.add(listingFoodIn);

        searchRecipeFoodInOwnedAndNotExpired = new JMenuItem("Recette avec nourriture possédée et non périmée");
        searchFoodTypeExpiredInStorage = new JMenuItem("Un type de nourriture dans un certain type de stockage qui est périmé");
        searchRecipeWithFoodExpired5DAndWithActualSeason = new JMenuItem("Recette avec nourriture possédée périmé dans 5j");
        searchMenu.add(searchRecipeFoodInOwnedAndNotExpired);
        searchMenu.add(searchFoodTypeExpiredInStorage);
        searchMenu.add(searchRecipeWithFoodExpired5DAndWithActualSeason);

        quantityTypeFood = new JMenuItem("Quantité restante d'un type de nourriture");
        timeToMakeRecipe = new JMenuItem("Recettes faites dans un intervalle de temps choisi");
        taskMenu.add(quantityTypeFood);
        taskMenu.add(timeToMakeRecipe);

        ExitListener exitListener = new ExitListener();
        exitApplication.addActionListener(exitListener);
        homeScreen.addActionListener(e -> mainWindow.showHomePanel());
        addRecipe.addActionListener(e -> mainWindow.showAddRecipePanel());
        deleteRecipe.addActionListener(e -> mainWindow.showDeleteRecipePanel());
        updateRecipe.addActionListener(e -> mainWindow.showSearchRecipePanel());
        listingRecipe.addActionListener(e -> mainWindow.showRecipeListPanel());
        addFoodIn.addActionListener(e -> mainWindow.showAddFoodInPanel());
        listingFoodIn.addActionListener(e -> mainWindow.showFoodInListPanel());
        deleteFoodIn.addActionListener(e -> mainWindow.showDeleteFoodInPanel());
        updateFoodIn.addActionListener(e -> mainWindow.showSearchFoodInPanel());
        searchRecipeWithFoodExpired5DAndWithActualSeason.addActionListener(e -> mainWindow.showRecipeWithExpiredFoodListPanel());
        searchFoodTypeExpiredInStorage.addActionListener(e -> mainWindow.showSearchExpiredFoodInInStorage());
        searchRecipeFoodInOwnedAndNotExpired.addActionListener(e -> mainWindow.showPossibleRecipePanel());
        quantityTypeFood.addActionListener(e -> mainWindow.showQuantityTypeOfFood());
        timeToMakeRecipe.addActionListener(e -> mainWindow.showSearchRecipeBaseOnTime());
    }
}

