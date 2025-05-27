package viewPackage;

import controllerPackage.IngredientAmountController;
import modelPackage.IngredientAmount;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;

public class DeleteIngredientPanel extends JPanel {

    private DefaultListModel<String> listModel;
    private JList<String> ingredientList;
    private IngredientAmountController ingredientAmountController;

    public DeleteIngredientPanel(MainWindow mainWindow, Recipe recipe) {
        setLayout(new BorderLayout());

        // Contrôleur
        ingredientAmountController = new IngredientAmountController();

        // Titre
        JLabel titleLabel = new JLabel("Ingrédients de : " + recipe.getLabel(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        add(titleLabel, BorderLayout.NORTH);

        ingredientAmountController.getIngredientAmountsByRecipe(recipe.getLabel());
        // Liste des ingrédients
        listModel = new DefaultListModel<>();
        for (IngredientAmount ingredient : ingredientAmountController.getIngredientAmountsByRecipe(recipe.getLabel())) {
            listModel.addElement(ingredient.getFood()/* + " " + ingredient.getQuantity() + " (qqt/g/cl)"*/);
        }

        ingredientList = new JList<>(listModel);
        ingredientList.setFont(new Font("Poppins", Font.PLAIN, 15));
        ingredientList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(ingredientList);
        add(scrollPane, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton backButton = new JButton("Retour");
        backButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            mainWindow.showUpdateRecipePanel(new UpdateRecipePanel(mainWindow, recipe, "recipeList"));
        });

        deleteButton.addActionListener(e -> {
            int selectedIndex = ingredientList.getSelectedIndex();
            if (selectedIndex != -1) {
                try {
                    ingredientAmountController.deleteIngredientAmount(recipe.getLabel(), listModel.getElementAt(selectedIndex));
                    listModel.remove(selectedIndex);
                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}