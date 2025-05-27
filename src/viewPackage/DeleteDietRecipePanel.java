package viewPackage;

import controllerPackage.DietRecipeController;

import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeleteDietRecipePanel extends JPanel {
    private JLabel titleLabel, dietLabel;
    private JPanel formPanel, buttonPanel;
    private JList<String> dietList;
    private JButton removeButton, backButton;
    private DietRecipeController dietRecipeController;

    public DeleteDietRecipePanel(MainWindow mainWindow, Recipe recipe) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        dietRecipeController = new DietRecipeController();

        // Titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Supprimer des régimes associés");
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
        dietLabel = new JLabel("Régimes associés :");
        dietLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        formPanel.add(dietLabel, gbc);

        gbc.gridx = 1;
        List<String> diets = dietRecipeController.getDietsByRecipe(recipe.getLabel());
        dietList = new JList<>(diets.toArray(new String[0]));
        dietList.setVisibleRowCount(6);
        dietList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dietList.setFont(new Font("Poppins", Font.PLAIN, 14));

        JScrollPane dietScrollPane = new JScrollPane(dietList);
        dietScrollPane.setPreferredSize(new Dimension(250, 120)); // taille fixe
        formPanel.add(dietScrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Boutons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        removeButton = new JButton("Supprimer");
        removeButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        backButton = new JButton("Retour");
        backButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        buttonPanel.add(removeButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action "Supprimer"
        removeButton.addActionListener(e -> {
            List<String> selectedDiets = dietList.getSelectedValuesList();
            if (selectedDiets.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun régime sélectionné. Rien à supprimer.", "Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                for (String dietLabel : selectedDiets) {
                    dietRecipeController.deleteDietFromRecipe(dietLabel, recipe.getLabel());
                }

                JOptionPane.showMessageDialog(this, "Régime(s) supprimé(s) avec succès !");

                // Mise à jour de la liste
                List<String> updatedDiets = dietRecipeController.getDietsByRecipe(recipe.getLabel());
                dietList.setListData(updatedDiets.toArray(new String[0]));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Action "Retour"
        backButton.addActionListener(e -> {
            mainWindow.showUpdateRecipePanel(new UpdateRecipePanel(mainWindow, recipe, "recipeList"));
        });
    }
}