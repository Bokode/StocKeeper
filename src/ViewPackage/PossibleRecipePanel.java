package ViewPackage;

import controllerPackage.RecipeController;
import modelPackage.Food;
import modelPackage.Recipe;
import modelPackage.RecipeWithExpiredFood;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PossibleRecipePanel extends JPanel {

    private JList<RecipeWithExpiredFood> recipeList;
    private DefaultListModel<RecipeWithExpiredFood> listModel;
    private JButton backButton, showRecipeButton;
    private JLabel titleLabel;

    public PossibleRecipePanel(MainWindow mainWindow) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Recettes possibles avec mes ingrédients");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Liste
        listModel = new DefaultListModel<>();
        recipeList = new JList<>(listModel);
        recipeList.setFont(new Font("Poppins", Font.PLAIN, 14));
        recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipeList.setCellRenderer(new PossibleRecipeRenderer());

        JScrollPane scrollPane = new JScrollPane(recipeList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Recettes disponibles"));
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

        // Action Afficher la recette
        showRecipeButton.addActionListener(e -> {
            RecipeWithExpiredFood selected = recipeList.getSelectedValue();
            Recipe selectedRecipe = selected.getRecipe();
            if (selectedRecipe != null) {
                mainWindow.getMainContainer().removeAll();
                mainWindow.getMainContainer().add(new RecipeDetailsPanel(mainWindow, selectedRecipe, "possibleRecipe"), BorderLayout.CENTER);
                mainWindow.getMainContainer().revalidate();
                mainWindow.getMainContainer().repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une recette.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> mainWindow.showHomePanel());
    }

    public void loadPossibleRecipes() {
        try {
            List<RecipeWithExpiredFood> recipes = new RecipeController().recipeWithSomeIngredientsInStock();
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
    private static class PossibleRecipeRenderer extends DefaultListCellRenderer {
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

                setText("<html><b>" + label + "</b><br><i>Ingrédients que vous possédez :</i> " + foodList + "</html>");
            }
            return this;
        }
    }
}
