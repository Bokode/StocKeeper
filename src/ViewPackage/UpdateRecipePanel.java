package ViewPackage;

import controllerPackage.RecipeController;
import modelPackage.Recipe;
import modelPackage.RecipeType;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class UpdateRecipePanel extends JPanel {
        private JPanel FormPanel, ButtonsPanel, TitlePanel;
        private JTextField labelField, descriptionField, caloricIntakeField, timeToMakeField;
        private JLabel titleLabel, labelLabel, descriptionLabel, caloricInTakeLabel, timeToMakeLabel, recipeTypeLabel;
        private RecipeController recipeController;
    public UpdateRecipePanel(MainWindow mainWindow, Recipe recipe) {
        setLayout(new BorderLayout());

        String labelToFind = recipe.getLabel();

        // Titre
        TitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Modification de la recette : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);

        // Formulaire
        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Champ Nom
        gbc.gridx = 0; gbc.gridy = row;
        labelLabel = new JLabel("Nom :");
        labelLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(labelLabel, gbc);
        gbc.gridx = 1;
        labelField = new JTextField(20);
        labelField.setText(recipe.getLabel());
        FormPanel.add(labelField, gbc);

        // Champ Description
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        descriptionLabel = new JLabel("Description de la recette : ");
        descriptionLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        descriptionField.setText(recipe.getDescription());
        FormPanel.add(descriptionField, gbc);

        // Champ Apport calorique
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        caloricInTakeLabel = new JLabel("Apport calorique (facultatif) : ");
        caloricInTakeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(caloricInTakeLabel, gbc);
        gbc.gridx = 1;
        caloricIntakeField = new JTextField(20);
        caloricIntakeField.setText("");
        if (recipe.getCaloricIntake() != null && recipe.getCaloricIntake() != 0) {
            caloricIntakeField.setText(recipe.getCaloricIntake().toString());
        }
        FormPanel.add(caloricIntakeField, gbc);

        // Champ Durée de préparation
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        timeToMakeLabel = new JLabel("Durée de la recette (facultatif) : ");
        timeToMakeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(timeToMakeLabel, gbc);
        gbc.gridx = 1;
        timeToMakeField = new JTextField(20);
        timeToMakeField.setText("");
        if (recipe.getTimeToMake() != null && recipe.getTimeToMake() != 0) {
            timeToMakeField.setText(recipe.getTimeToMake().toString());
        }
        FormPanel.add(timeToMakeField, gbc);

        // Checkbox : faite aujourd’hui
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JCheckBox hasBeenDoneToday = new JCheckBox("Recette réalisée aujourd’hui");
        hasBeenDoneToday.setFont(new Font("Poppins", Font.PLAIN, 15));
        if (recipe.getLastDayDone() != null) {
            Date today = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (sdf.format(recipe.getLastDayDone()).equals(sdf.format(today))) {
                hasBeenDoneToday.setSelected(true);
            }
        }
        FormPanel.add(hasBeenDoneToday, gbc);
        gbc.gridwidth = 1;

        // Checkbox : recette froide
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        JCheckBox isColdCheckBox = new JCheckBox("Recette froide");
        isColdCheckBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        isColdCheckBox.setSelected(Boolean.TRUE.equals(recipe.getCold()));
        FormPanel.add(isColdCheckBox, gbc);
        gbc.gridwidth = 1;

        // ComboBox : type de recette
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
        typeRecetteComboBox.setSelectedItem(recipe.getType().getLabel());
        FormPanel.add(typeRecetteComboBox, gbc);

        // Ajout du formulaire au panel
        add(FormPanel, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton updateButton = new JButton("Modifier");
        updateButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(updateButton);
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
            }
            mainWindow.showSearchRecipePanel();
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
            }
        });

        updateButton.addActionListener(e -> {
            try {
                String label = labelField.getText().trim();
                String description = descriptionField.getText().trim();

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

                Date lastDayDone = null;
                if (hasBeenDoneToday.isSelected()){
                    // Variable
                    LocalDate localDate = LocalDate.now();
                    // Convert to java.sql.Date
                    lastDayDone = Date.valueOf(localDate);
                }

                boolean isCold = isColdCheckBox.isSelected();
                String typeString = typeRecetteComboBox.getSelectedItem().toString();
                RecipeType recipeType = new RecipeType(typeString); // ou enum selon ton modèle

                Recipe newRecipe = new Recipe(label, description, caloricIntake, lastDayDone, timeToMake, isCold, recipeType);
                recipeController = new RecipeController();
                recipeController.updateRecipe(newRecipe, labelToFind);
                JOptionPane.showMessageDialog(this, "Recette modifiée avec succès !", "Recette ajoutée", JOptionPane.INFORMATION_MESSAGE);
                mainWindow.showHomePanel();
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}

