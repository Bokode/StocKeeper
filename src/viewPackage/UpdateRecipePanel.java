package viewPackage;

import controllerPackage.RecipeController;
import modelPackage.Recipe;
import modelPackage.RecipeType;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class UpdateRecipePanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;
    private JTextField labelField, caloricIntakeField, timeToMakeField;
    private JTextArea descriptionArea;
    private JLabel titleLabel, labelLabel, descriptionLabel, caloricInTakeLabel, timeToMakeLabel, recipeTypeLabel, lastTimeDoneLabel;
    private UtilDateModel lastTimeDoneModel;
    private RecipeController recipeController;

    public UpdateRecipePanel(MainWindow mainWindow, Recipe recipe, String cancelPanelName) {
        setLayout(new BorderLayout());

        String labelToFind = recipe.getLabel();

        // Titre de Fenêtre
        TitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Modification de la recette : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Nom de la recette
        gbc.gridx = 0; gbc.gridy = row;
        labelLabel = new JLabel("Nom* :");
        labelLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(labelLabel, gbc);
        gbc.gridx = 1;
        labelField = new JTextField(20);
        labelField.setText(recipe.getLabel());
        FormPanel.add(labelField, gbc);

        // Description de la recette
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        descriptionLabel = new JLabel("Description de la recette* : ");
        descriptionLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("Poppins", Font.PLAIN, 15));
        descriptionArea.setText(recipe.getDescription());
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        FormPanel.add(descriptionScrollPane, gbc);

        // Apport calorique de la recette
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        caloricInTakeLabel = new JLabel("Apport calorique (kcal) : ");
        caloricInTakeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(caloricInTakeLabel, gbc);
        gbc.gridx = 1;
        caloricIntakeField = new JTextField(20);
        if (recipe.getCaloricIntake() != null && recipe.getCaloricIntake() != 0) {
            caloricIntakeField.setText(recipe.getCaloricIntake().toString());
        }
        FormPanel.add(caloricIntakeField, gbc);

        // Durée de la recette
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        timeToMakeLabel = new JLabel("Durée de la recette (kcal) : ");
        timeToMakeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(timeToMakeLabel, gbc);
        gbc.gridx = 1;
        timeToMakeField = new JTextField(20);
        if (recipe.getTimeToMake() != null && recipe.getTimeToMake() != 0) {
            timeToMakeField.setText(recipe.getTimeToMake().toString());
        }
        FormPanel.add(timeToMakeField, gbc);

        // Configurer les propriétés de format de date
        Properties p = new Properties();
        p.put("text.today", "Aujourd'hui");
        p.put("text.month", "Mois");
        p.put("text.year", "Année");

        // Dernière date de réalisation de la recette
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        lastTimeDoneLabel = new JLabel("Date de la dernière réalisation de la recette :");
        lastTimeDoneLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(lastTimeDoneLabel, gbc);
        gbc.gridx = 1;
        lastTimeDoneModel = new UtilDateModel();
        if (recipe.getLastDayDone() != null) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(recipe.getLastDayDone());
            lastTimeDoneModel.setDate(cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH), cal.get(java.util.Calendar.DAY_OF_MONTH));
            lastTimeDoneModel.setSelected(true);
        }
        JDatePanelImpl lastTimeDonePanel = new JDatePanelImpl(lastTimeDoneModel, p);
        JDatePickerImpl lastTimeDoneDatePicker = new JDatePickerImpl(lastTimeDonePanel, new DateLabelFormatter());
        FormPanel.add(lastTimeDoneDatePicker, gbc);

        // Si la recette est froide ou non
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        JCheckBox isColdCheckBox = new JCheckBox("Recette froide");
        isColdCheckBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        isColdCheckBox.setSelected(Boolean.TRUE.equals(recipe.getCold()));
        FormPanel.add(isColdCheckBox, gbc);
        gbc.gridwidth = 1;

        // Type de la recette
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        recipeTypeLabel = new JLabel("Type de recette* : ");
        recipeTypeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(recipeTypeLabel, gbc);
        gbc.gridx = 1;
        String[] typeRecettes = {"soupe", "entrée", "plat principal", "dessert"};
        JComboBox<String> typeRecetteComboBox = new JComboBox<>(typeRecettes);
        typeRecetteComboBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        typeRecetteComboBox.setSelectedItem(recipe.getType().getLabel());
        FormPanel.add(typeRecetteComboBox, gbc);

        add(FormPanel, BorderLayout.CENTER);

        // Panel des bouttons
        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton updateButton = new JButton("Modifier");
        updateButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton cancelButton = new JButton("Retour");
        cancelButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton manageIngredientButton = new JButton("Gérer ingrédients");
        manageIngredientButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton manageMaterialButton = new JButton("Gérer matériel");
        manageMaterialButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton manageDietRecipeButton = new JButton("Gérer régimes");
        manageDietRecipeButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(updateButton);
        ButtonsPanel.add(manageIngredientButton);
        ButtonsPanel.add(manageMaterialButton);
        ButtonsPanel.add(manageDietRecipeButton);
        ButtonsPanel.add(cancelButton);
        add(ButtonsPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> {
            for (Component component : FormPanel.getComponents()) {
                if (component instanceof JTextField) {
                    ((JTextField) component).setText("");
                } else if (component instanceof JScrollPane) {
                    Component view = ((JScrollPane) component).getViewport().getView();
                    if (view instanceof JTextArea) {
                        ((JTextArea) view).setText("");
                    }
                } else if (component instanceof JCheckBox) {
                    ((JCheckBox) component).setSelected(false);
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
            }
            if(cancelPanelName.equals("searchRecipe")){
                mainWindow.showSearchRecipePanel();
            } else if (cancelPanelName.equals("recipeList")) {
                mainWindow.showRecipeListPanel();
            }
        });

        updateButton.addActionListener(e -> {
            try {
                String label = labelField.getText().trim();
                String description = descriptionArea.getText().trim();

                if (label.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.");
                    return;
                }

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

                java.util.Date lastTimeDone = null;
                if (lastTimeDoneModel.getValue() != null){
                    lastTimeDone = lastTimeDoneModel.getValue();
                }

                boolean isCold = isColdCheckBox.isSelected();
                String typeString = typeRecetteComboBox.getSelectedItem().toString();
                RecipeType recipeType = new RecipeType(typeString);

                Recipe newRecipe = new Recipe(label, description, caloricIntake, lastTimeDone, timeToMake, isCold, recipeType);
                recipeController = new RecipeController();
                recipeController.updateRecipe(newRecipe, labelToFind);

                JOptionPane.showMessageDialog(this, "Recette modifiée avec succès !", "Recette ajoutée", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        manageIngredientButton.addActionListener(eIngredient -> {
            String[] options = {"Ajouter", "Supprimer"};
            int choix = JOptionPane.showOptionDialog(
                    this,
                    "Souhaittez vous ajouter ou supprimer des aliments ?",
                    "Choix de l'action",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, // Pas d'icône personnalisée
                    options, // Texte des boutons
                    options[0] // Option par défaut sélectionnée
            );

            if (choix == 0) {
                mainWindow.showAddIngredientPanel(new AddIngredientPanel(mainWindow, recipe, "updateRecipe"));
            } else if (choix == 1) {
                mainWindow.showDeleteIngredientPanel(new DeleteIngredientPanel(mainWindow, recipe));
            }
        });

        manageMaterialButton.addActionListener(eMaterial -> {
            String[] opt = {"Ajouter", "Supprimer"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Souhaittez vous ajouter ou supprimer du matériel ?",
                    "Choix de l'action",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, // Pas d'icône personnalisée
                    opt, // Texte des boutons
                    opt[0] // Option par défaut sélectionnée
            );
            if (choice == 0) {
                mainWindow.showAddMaterialPanel(new AddMaterialPanel(mainWindow, recipe, "updateRecipe"));
            } else if (choice == 1) {
                mainWindow.showDeleteMaterialPanel(new DeleteMaterialPanel(mainWindow, recipe));
            }
        });

        manageDietRecipeButton.addActionListener(eDiet -> {
            String[] addOrDel = {"Ajouter", "Supprimer"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Souhaittez vous ajouter ou supprimer du matériel ?",
                    "Choix de l'action",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, // Pas d'icône personnalisée
                    addOrDel, // Texte des boutons
                    addOrDel[0] // Option par défaut sélectionnée
            );
            if (choice == 0) {
                mainWindow.showAddDietRecipePanel(new AddDietRecipePanel(mainWindow, recipe, "updateRecipe"));
            } else if (choice == 1) {
                mainWindow.showDeleteDietRecipePanel(new DeleteDietRecipePanel(mainWindow, recipe));
            }
        });
    }
}
