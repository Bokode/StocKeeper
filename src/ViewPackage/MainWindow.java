package ViewPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
    private Container mainContainer;
    private JPanel homePanel;
    private AddRecipePanel addRecipePanel;
    private DeleteRecipePanel deleteRecipePanel;
    private SearchRecipePanel searchRecipePanel;
    private RecipeListPanel recipeListPanel;
    private AddFoodInPanel addFoodInPanel;
    private MainMenu mainMenu;
    public MainWindow() {
        super("StocKeeper");
        setBounds(200, 155, 1400, 780);
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                System.exit(0);
            }
        } );
        mainMenu = new MainMenu(this);
        setJMenuBar(mainMenu);
        homePanel = new HomePanel();
        addRecipePanel = new AddRecipePanel(this);
        deleteRecipePanel = new DeleteRecipePanel(this);
        searchRecipePanel = new SearchRecipePanel(this);
        recipeListPanel = new RecipeListPanel(this);
        addFoodInPanel = new AddFoodInPanel(this);
        mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(homePanel,BorderLayout.CENTER);
        setVisible(true);
    }

    public Container getMainContainer() {
        return mainContainer;
    }

    public void showHomePanel() {
        mainContainer.removeAll();
        mainContainer.add(homePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showAddRecipePanel() {
        mainContainer.removeAll();
        mainContainer.add(addRecipePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showDeleteRecipePanel() {
        mainContainer.removeAll();
        mainContainer.add(deleteRecipePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showSearchRecipePanel() {
        mainContainer.removeAll();
        mainContainer.add(searchRecipePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showRecipeListPanel() {
        mainContainer.removeAll();
        recipeListPanel.loadRecipes();
        mainContainer.add(recipeListPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showAddIngredientPanel(AddIngredientPanel addIngredientPanel){
        mainContainer.removeAll();
        mainContainer.add(addIngredientPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showDeleteIngredientPanel(DeleteIngredientPanel deleteIngredientPanel){
        mainContainer.removeAll();
        mainContainer.add(deleteIngredientPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showUpdateRecipePanel(UpdateRecipePanel updateRecipePanel){
        mainContainer.removeAll();
        mainContainer.add(updateRecipePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showAddFoodInPanel() {
        mainContainer.removeAll();
        mainContainer.add(addFoodInPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showAddMaterialPanel(AddMaterialPanel addMaterialPanel){
        mainContainer.removeAll();
        mainContainer.add(addMaterialPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

}
