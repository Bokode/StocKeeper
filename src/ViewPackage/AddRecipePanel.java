package ViewPackage;

import controllerPackage.RecipeController;
import modelPackage.FoodType;
import modelPackage.Recipe;
import modelPackage.RecipeType;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class AddRecipePanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;
    private JTextField labelField, descriptionField, caloricIntakeField, timeToMakeField;
    private JLabel titleLabel, labelLabel, descriptionLabel, caloricInTakeLabel, timeToMakeLabel, recipeTypeLabel;
    private RecipeController recipeController;
    public AddRecipePanel(MainWindow mainWindow) {
        setLayout(new BorderLayout());

        TitlePanel = new JPanel(new FlowLayout());
        titleLabel = new JLabel("Ajouter une recette : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        labelLabel = new JLabel("Nom :");
        labelLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(labelLabel, gbc);
        gbc.gridx = 1;
        labelField = new JTextField(20);
        FormPanel.add(labelField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        descriptionLabel = new JLabel("Description de la recette : ");
        descriptionLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        JTextArea descriptionArea = new JTextArea(5, 20); // 5 lignes visibles, 20 colonnes
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Poppins", Font.PLAIN, 15));
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        FormPanel.add(descriptionScrollPane, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        caloricInTakeLabel = new JLabel("Apport calorique (facultatif) : ");
        caloricInTakeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(caloricInTakeLabel, gbc);
        gbc.gridx = 1;
        caloricIntakeField = new JTextField(20);
        FormPanel.add(caloricIntakeField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        timeToMakeLabel = new JLabel("Durée de la recette (facultatif) : ");
        timeToMakeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(timeToMakeLabel, gbc);
        gbc.gridx = 1;
        timeToMakeField = new JTextField(20);
        FormPanel.add(timeToMakeField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JCheckBox isColdCheckBox = new JCheckBox("Recette froide");
        isColdCheckBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(isColdCheckBox, gbc);
        gbc.gridwidth = 1;

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        recipeTypeLabel = new JLabel("Type de recette : ");
        recipeTypeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(recipeTypeLabel, gbc);
        gbc.gridx = 1;
        String[] typeRecettes = {"soupe", "entrée", "plat principal", "dessert"};
        JComboBox<String> typeRecetteComboBox = new JComboBox<>(typeRecettes);
        typeRecetteComboBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(typeRecetteComboBox, gbc);

        add(FormPanel, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(addButton);
        ButtonsPanel.add(cancelButton);
        ButtonsPanel.add(resetButton);
        add(ButtonsPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> {
            for (Component component : FormPanel.getComponents()){
                if (component instanceof JTextField){
                    ((JTextField) component).setText("");
                } else if (component instanceof JCheckBox) {
                    ((JCheckBox) component).setSelected(false);
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
                else if (component instanceof JScrollPane) {
                    Component view = ((JScrollPane) component).getViewport().getView();
                    if (view instanceof JTextArea) {
                        ((JTextArea) view).setText("");
                    }
                }
            }
            mainWindow.showHomePanel();
        });

        resetButton.addActionListener(e -> {
            for (Component component : FormPanel.getComponents()){
                if (component instanceof JTextField){
                    ((JTextField) component).setText("");
                } else if (component instanceof JCheckBox) {
                    ((JCheckBox) component).setSelected(false);
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
                else if (component instanceof JScrollPane) {
                    Component view = ((JScrollPane) component).getViewport().getView();
                    if (view instanceof JTextArea) {
                        ((JTextArea) view).setText("");
                    }
                }
            }
        });

        addButton.addActionListener(e -> {
            try {
                String label = labelField.getText().trim();
                String description = descriptionArea.getText().trim();

                if (label.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.");
                    return;
                }

                // Champs facultatifs
                Integer caloricIntake = null;
                Integer timeToMake = null;

                String caloricStr = caloricIntakeField.getText().trim();
                if (!caloricStr.isEmpty()) {
                    try {
                        caloricIntake = Integer.valueOf(caloricStr);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Erreur : L'apport calorique doit être un nombre entier.");
                        return;
                    }
                }

                String timeStr = timeToMakeField.getText().trim();
                if (!timeStr.isEmpty()) {
                    try {
                        timeToMake = Integer.valueOf(timeStr);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Erreur : La durée doit être un nombre entier.");
                        return;
                    }
                }

                boolean isCold = isColdCheckBox.isSelected();
                String typeString = typeRecetteComboBox.getSelectedItem().toString();
                RecipeType recipeType = new RecipeType(typeString); // ou enum selon ton modèle

                Date lastDayDone = null;

                Recipe newRecipe = new Recipe(label, description, caloricIntake, lastDayDone, timeToMake, isCold, recipeType);

                recipeController = new RecipeController();
                recipeController.addRecipe(newRecipe);
                System.out.println("Recette créée : " + newRecipe);

                JOptionPane.showMessageDialog(this, "Recette crée, vous allez pouvoir maintenant ajouter les aliments", "Recette crée", JOptionPane.INFORMATION_MESSAGE);
                mainWindow.showAddIngredientPanel(new AddIngredientPanel(mainWindow, newRecipe, "addMaterial"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
