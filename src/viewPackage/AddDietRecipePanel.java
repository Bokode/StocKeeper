package viewPackage;

import controllerPackage.DietController;
import controllerPackage.DietRecipeController;
import modelPackage.Diet;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddDietRecipePanel extends JPanel {
    private JLabel titleLabel, dietLabel;
    private JPanel formPanel, buttonPanel;
    private JList<Diet> dietList;
    private JButton addButton, backButton;
    private DietController dietController;
    private DietRecipeController dietRecipeController;

    public AddDietRecipePanel(MainWindow mainWindow, Recipe recipe, String previousPanelName) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        dietController = new DietController();
        dietRecipeController = new DietRecipeController();

        // Titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Associer des régimes à la recette");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Formulaire
        formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Liste des régimes
        gbc.gridx = 0;
        gbc.gridy = 0;
        dietLabel = new JLabel("Sélectionnez les régimes :");
        dietLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        formPanel.add(dietLabel, gbc);

        gbc.gridx = 1;
        List<Diet> diets = diet.getAllDiets();
        dietList = new JList<>(diets.toArray(new Diet[0]));
        dietList.setVisibleRowCount(6);
        dietList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dietList.setFont(new Font("Poppins", Font.PLAIN, 14));
        JScrollPane dietScrollPane = new JScrollPane(dietList);
        formPanel.add(dietScrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Boutons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        backButton = new JButton("Retour");
        backButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action "Ajouter"
        addButton.addActionListener(e -> {
            List<Diet> selectedDiets = dietList.getSelectedValuesList();
            if (selectedDiets.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner au moins un régime.", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                for (Diet diet : selectedDiets) {
                    dietRecipeController.addDietToRecipe(diet.getLabel(), recipe.getLabel());
                }
                JOptionPane.showMessageDialog(this, "Régimes ajoutés avec succès à la recette !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action "Retour"
        backButton.addActionListener(e -> {
            if (previousPanelName.equals("home")) {
                mainWindow.showHomePanel();
            } else if (previousPanelName.equals("updateRecipe")) {
                mainWindow.showUpdateRecipePanel(new UpdateRecipePanel(mainWindow, recipe, "recipeList"));
            } else if (previousPanelName.equals("addRecipe")) {
                mainWindow.showAddMaterialPanel(new AddMaterialPanel(mainWindow, recipe, "home"));
            }
        });
    }
}