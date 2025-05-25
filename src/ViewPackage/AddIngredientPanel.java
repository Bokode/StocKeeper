package ViewPackage;

import controllerPackage.IngredientAmountController;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;

public class AddIngredientPanel extends JPanel {

    private JPanel FormPanel, ButtonsPanel;
    private JLabel titleLabel, labelLabel, ingredientAmountLabel;
    private JTextField labelField, ingredientAmountField;
    private IngredientAmountController ingredientAmountController;

    public AddIngredientPanel(MainWindow mainWindow, Recipe recipe, String cancelPanelName) {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Ajouter un ingrédient : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_END;

        int row = 0;

        // Nom
        gbc.gridx = 0;
        gbc.gridy = row;
        labelLabel = new JLabel("Nom* :");
        labelLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(labelLabel, gbc);

        gbc.gridx = 1;
        labelField = new JTextField(20);
        FormPanel.add(labelField, gbc);

        // Quantité
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        ingredientAmountLabel = new JLabel("Quantité* (qtt/g/cl) :");
        ingredientAmountLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(ingredientAmountLabel, gbc);

        gbc.gridx = 1;
        ingredientAmountField = new JTextField(20);
        FormPanel.add(ingredientAmountField, gbc);

        add(FormPanel, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton stopButton = new JButton("Fin d'ajout");
        stopButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(addButton);
        ButtonsPanel.add(stopButton);
        ButtonsPanel.add(resetButton);
        add(ButtonsPanel, BorderLayout.SOUTH);

        resetButton.addActionListener(e -> {
            for (Component component : FormPanel.getComponents()){
                if (component instanceof JTextField){
                    ((JTextField) component).setText("");
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
            }
        });

        stopButton.addActionListener(e -> {
            for (Component component : FormPanel.getComponents()){
                if (component instanceof JTextField){
                    ((JTextField) component).setText("");
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
            }
            if (cancelPanelName.equals("addMaterial")){
                JOptionPane.showMessageDialog(this, "Vous allez pouvoir maintenant renseigner le matériel nécessaire pour la recette");
                mainWindow.showAddMaterialPanel(new AddMaterialPanel(mainWindow, recipe, "home"));
            } else if (cancelPanelName.equals("updateRecipe")) {
                UpdateRecipePanel updateRecipePanel = new UpdateRecipePanel(mainWindow, recipe, "recipeList");
                mainWindow.showUpdateRecipePanel(updateRecipePanel);
            }
        });

        addButton.addActionListener(e -> {
            String name = labelField.getText().trim();
            String amountStr = ingredientAmountField.getText().trim();

            if (name.isEmpty() || amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Champs manquants", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Integer amount;
            try {
                amount = Integer.valueOf(amountStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erreur : L'apport calorique doit être un nombre entier.");
                return;
            }
            if (amount < 0){
                JOptionPane.showMessageDialog(this, "La quantité doit être un nombre positif", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Ajout et création de l'ingrédient
                ingredientAmountController = new IngredientAmountController();
                ingredientAmountController.addIngredientAmount(recipe.getLabel(), name, amount);

                JOptionPane.showMessageDialog(this, "Ingrédient ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

                // Réinitialisation des champs pour un ajout en série
                labelField.setText("");
                ingredientAmountField.setText("");
                labelField.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}