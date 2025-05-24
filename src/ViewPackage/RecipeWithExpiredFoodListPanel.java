package ViewPackage;

import controllerPackage.RecipeController;
import modelPackage.Food;
import modelPackage.Recipe;
import modelPackage.RecipeWithExpiredFood;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RecipeWithExpiredFoodListPanel extends JPanel {

    private JList<RecipeWithExpiredFood> recipeList;
    private DefaultListModel<RecipeWithExpiredFood> listModel;
    private JButton backButton, showRecipeButton;
    private JLabel titleLabel;

    public RecipeWithExpiredFoodListPanel(MainWindow mainWindow) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Recettes avec ingrédients bientôt périmés");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Liste
        listModel = new DefaultListModel<>();
        recipeList = new JList<>(listModel);
        recipeList.setFont(new Font("Poppins", Font.PLAIN, 14));
        recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipeList.setCellRenderer(new RecipeWithExpiredFoodRenderer());

        JScrollPane scrollPane = new JScrollPane(recipeList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Recettes concernées"));
        add(scrollPane, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("Retour");
        backButton.setFont(new Font("Poppins", Font.PLAIN, 14));
        showRecipeButton = new JButton("Afficher la recette");
        showRecipeButton.setFont(new Font("Poppins", Font.PLAIN, 14));

        buttonPanel.add(showRecipeButton);
        buttonPanel.add(backButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        showRecipeButton.addActionListener(e -> {
            RecipeWithExpiredFood selectedRecipeExpiredFood = recipeList.getSelectedValue();
            Recipe selectedRecipe = selectedRecipeExpiredFood.getRecipe();
            if (selectedRecipe != null) {
                mainWindow.getMainContainer().removeAll();
                mainWindow.getMainContainer().add(new RecipeDetailsPanel(mainWindow, selectedRecipe), BorderLayout.CENTER);
                mainWindow.getMainContainer().revalidate();
                mainWindow.getMainContainer().repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une recette.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        });
        backButton.addActionListener(e -> mainWindow.showHomePanel());

    }

    public void loadRecipes() {
        try {
            List<RecipeWithExpiredFood> recipes = new RecipeController().recipeWithExpiredFood();
            listModel.clear();
            for (RecipeWithExpiredFood recipe : recipes) {
                listModel.addElement(recipe);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des recettes : " + e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Renderer interne
    private static class RecipeWithExpiredFoodRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof RecipeWithExpiredFood) {
                RecipeWithExpiredFood recipeItem = (RecipeWithExpiredFood) value;
                String label = recipeItem.getRecipe().getLabel();
                List<Food> foods = recipeItem.getFoods();

                StringBuilder foodList = new StringBuilder();
                for (int i = 0; i < foods.size(); i++) {
                    foodList.append(foods.get(i).getLabel());
                    if (i < foods.size() - 1) foodList.append(", ");
                }

                setText("<html><b>" + label + "</b><br><i>Ingrédients bientôt périmé :</i> " + foodList + "</html>");
            }
            return this;
        }
    }
}
