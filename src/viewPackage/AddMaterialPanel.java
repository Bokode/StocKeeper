package viewPackage;

import controllerPackage.MaterialController;
import controllerPackage.RecipeMaterialController;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;

public class AddMaterialPanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel;
    private JLabel titleLabel, labelLabel, materialTypeLabel;
    private JTextField labelField;
    private RecipeMaterialController recipeMaterialController;
    public AddMaterialPanel(MainWindow mainWindow, Recipe recipe, String cancelPanelName) {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Ajouter du matériel : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_END;

        int row = 0;

        // Nom du matériel
        gbc.gridx = 0;
        gbc.gridy = row;
        labelLabel = new JLabel("Nom du matériel à ajouter* :");
        labelLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(labelLabel, gbc);

        gbc.gridx = 1;
        labelField = new JTextField(20);
        FormPanel.add(labelField, gbc);

        // Type de matériel
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        materialTypeLabel = new JLabel("Type de matériel* :");
        materialTypeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(materialTypeLabel, gbc);

        gbc.gridx = 1;
        String[] materialsType = {"Ustensile", "Cuisson", "Récipient", "Machine"};
        JComboBox<String> materialTypeComboBox = new JComboBox<>(materialsType);
        materialTypeComboBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(materialTypeComboBox, gbc);

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
            if (cancelPanelName.equals("addDiet")){
                JOptionPane.showMessageDialog(this, "Vous allez pouvoir maintenant choisir les régimes de votre recette");
                mainWindow.showAddDietRecipePanel(new AddDietRecipePanel(mainWindow, recipe, "home"));
            } else if (cancelPanelName.equals("updateRecipe")) {
                UpdateRecipePanel updateRecipePanel = new UpdateRecipePanel(mainWindow, recipe, "recipeList");
                mainWindow.showUpdateRecipePanel(updateRecipePanel);
            }
        });

        addButton.addActionListener(e -> {
            String name = labelField.getText().trim();
            String typeString = materialTypeComboBox.getSelectedItem().toString();
            if (name.isEmpty() || typeString.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Champs manquants", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                MaterialController materialController = new MaterialController();
                if (materialController.getMaterialIdByLabel(name) == -1){
                    materialController.addMaterial(name, typeString);
                }
                // Ajout et création de l'ingrédient
                recipeMaterialController = new RecipeMaterialController();
                recipeMaterialController.addMaterialToRecipe(recipe.getLabel(), name);

                JOptionPane.showMessageDialog(this, "Matériel ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);

                // Réinitialisation des champs pour un ajout en série
                labelField.setText("");
                materialTypeComboBox.setSelectedIndex(0);
                labelField.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
