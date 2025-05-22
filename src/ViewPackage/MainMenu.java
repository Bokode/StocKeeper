package ViewPackage;

import javax.swing.*;

public class MainMenu extends JMenuBar {
    private JMenu recipeMenu, foodInMenu, searchMenu, homeMenu;
    private JMenuItem addRecipe, deleteRecipe, updateRecipe, listingRecipe, addFoodIn, deleteFoodIn, updateFoodIn, listingFoodIn, searchRecipeFoodInOwnedAndNotExpired, searchFoodTypeExpiredInStorage, searchRecipeWithFoodExpired5DAndWithActualSeason, homeScreen, exitApplication;
    private MainWindow mainWindow;
    public MainMenu(MainWindow mainWindow){
        this.mainWindow = mainWindow;

        homeMenu = new JMenu("Accueil");
        homeMenu.setMnemonic('A');
        recipeMenu = new JMenu("Recette");
        recipeMenu.setMnemonic('Z');
        foodInMenu = new JMenu("Nourriture possédée");
        foodInMenu.setMnemonic('E');
        searchMenu = new JMenu("Recherches");
        searchMenu.setMnemonic('R');

        this.add(homeMenu);
        this.add(recipeMenu);
        this.add(foodInMenu);
        this.add(searchMenu);

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
        searchRecipeWithFoodExpired5DAndWithActualSeason = new JMenuItem("Recette avec nourriture possédée périmé dans 5j avec un aliment de la saison actuelle");
        searchMenu.add(searchRecipeFoodInOwnedAndNotExpired);
        searchMenu.add(searchFoodTypeExpiredInStorage);
        searchMenu.add(searchRecipeWithFoodExpired5DAndWithActualSeason);

        ExitListener exitListener = new ExitListener();
        exitApplication.addActionListener(exitListener);
        homeScreen.addActionListener(e -> mainWindow.showHomePanel());
        addRecipe.addActionListener(e -> mainWindow.showAddRecipePanel());
        deleteRecipe.addActionListener(e -> mainWindow.showDeleteRecipePanel());
        updateRecipe.addActionListener(e -> mainWindow.showSearchRecipePanel());
        listingRecipe.addActionListener(e -> mainWindow.showRecipeListPanel());
    }
}
