package viewPackage;

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
    private FoodInListPanel foodInListPanel;
    private DeleteFoodInPanel deleteFoodInPanel;
    private SearchFoodInPanel searchFoodInPanel;
    private RecipeWithExpiredFoodListPanel recipeWithExpiredFoodListPanel;
    private SearchExpiredFoodInInStoragePanel searchExpiredFoodInInStoragePanel;
    private PossibleRecipePanel possibleRecipePanel;
    private QuantityTypeOfFoodPanel quantityTypeOfFoodPanel;
    private SearchRecipeBaseOnTimePanel searchRecipeBaseOnTimePanel;
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
        foodInListPanel = new FoodInListPanel(this);
        addFoodInPanel = new AddFoodInPanel(this);
        deleteFoodInPanel = new DeleteFoodInPanel(this);
        searchFoodInPanel = new SearchFoodInPanel(this);
        recipeWithExpiredFoodListPanel = new RecipeWithExpiredFoodListPanel(this);
        searchExpiredFoodInInStoragePanel = new SearchExpiredFoodInInStoragePanel(this);
        possibleRecipePanel = new PossibleRecipePanel(this);
        quantityTypeOfFoodPanel = new QuantityTypeOfFoodPanel(this);
        searchRecipeBaseOnTimePanel = new SearchRecipeBaseOnTimePanel(this);
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
        recipeListPanel.loadAllRecipes();
        mainContainer.add(recipeListPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }
    public void showFoodInListPanel() {
        mainContainer.removeAll();
        foodInListPanel.loadFoodIns();
        mainContainer.add(foodInListPanel, BorderLayout.CENTER);
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

    public void showDeleteFoodInPanel() {
        mainContainer.removeAll();
        mainContainer.add(deleteFoodInPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showSearchFoodInPanel() {
        mainContainer.removeAll();
        mainContainer.add(searchFoodInPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showUpdateFoodInPanel(UpdateFoodInPanel updateFoodInPanel) {
        mainContainer.removeAll();
        mainContainer.add(updateFoodInPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showAddMaterialPanel(AddMaterialPanel addMaterialPanel){
        mainContainer.removeAll();
        mainContainer.add(addMaterialPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }
  
    public void showDeleteMaterialPanel(DeleteMaterialPanel deleteMaterialPanel){
        mainContainer.removeAll();
        mainContainer.add(deleteMaterialPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showSearchExpiredFoodInInStorage() {
        mainContainer.removeAll();
        mainContainer.add(searchExpiredFoodInInStoragePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showExpiredFoodInInStorage(ExpiredFoodInInStoragePanel expiredFoodInInStoragePanel) {
        mainContainer.removeAll();
        mainContainer.add(expiredFoodInInStoragePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showRecipeWithExpiredFoodListPanel() {
        mainContainer.removeAll();
        recipeWithExpiredFoodListPanel.loadRecipes();
        mainContainer.add(recipeWithExpiredFoodListPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showPossibleRecipePanel() {
        mainContainer.removeAll();
        possibleRecipePanel.loadPossibleRecipes();
        mainContainer.add(possibleRecipePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showQuantityTypeOfFood() {
        mainContainer.removeAll();
        mainContainer.add(quantityTypeOfFoodPanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showSearchRecipeBaseOnTime() {
        mainContainer.removeAll();
        mainContainer.add(searchRecipeBaseOnTimePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showAddDietRecipePanel(AddDietRecipePanel addDietRecipePanel){
        mainContainer.removeAll();
        mainContainer.add(addDietRecipePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showDeleteDietRecipePanel(DeleteDietRecipePanel deleteDietRecipePanel){
        mainContainer.removeAll();
        mainContainer.add(deleteDietRecipePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }
}
