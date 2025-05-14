package ViewPackage;

import javax.swing.*;

public class MainMenu extends JMenuBar {
    private JMenu recipeMenu;
    private JMenuItem addRecipe, deleteRecipe, updateRecipe;
    private MainWindow mainWindow;
    public MainMenu(MainWindow mainWindow){
        this.mainWindow = mainWindow;

        recipeMenu = new JMenu("Recipe");
        recipeMenu.setMnemonic('A');
        this.add(recipeMenu);

        addRecipe = new JMenuItem("Add");
        deleteRecipe = new JMenuItem("Delete");
        updateRecipe = new JMenuItem("Update");
        recipeMenu.add(addRecipe);
        recipeMenu.add(deleteRecipe);
        recipeMenu.add(updateRecipe);
    }
}
