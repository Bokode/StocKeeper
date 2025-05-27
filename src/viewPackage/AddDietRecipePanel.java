package viewPackage;

import controllerPackage.DietController;
import controllerPackage.DietRecipeController;
import modelPackage.Diet;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

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

        // Liste des régimes disponibles (exclure déjà liés)
        List<Diet> allDiets = dietController.getAllDiets();
        List<String> existingDietLabels = dietRecipeController.getDietsByRecipe(recipe.getLabel());
        List<Diet> availableDiets = allDiets.stream()
                .filter(diet -> !existingDietLabels.contains(diet.getLabel()))
                .collect(Collectors.toList());

        gbc.gridx = 0;
        gbc.gridy = 0;
        dietLabel = new JLabel("Sélectionnez les régimes :");
        dietLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        formPanel.add(dietLabel, gbc);

        gbc.gridx = 1;
        DefaultListModel<Diet> dietModel = new DefaultListModel<>();
        for (Diet d : availableDiets) {
            dietModel.addElement(d);
        }

        dietList = new JList<>(dietModel);
        dietList.setVisibleRowCount(6);
        dietList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dietList.setFont(new Font("Poppins", Font.PLAIN, 14));

        // Affichage label au lieu de toString()
        dietList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Diet) {
                    setText(((Diet) value).getLabel());
                }
                return this;
            }
        });

        JScrollPane dietScrollPane = new JScrollPane(dietList);
        dietScrollPane.setPreferredSize(new Dimension(300, 150)); // Taille fixe de la liste
        formPanel.add(dietScrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Boutons
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        backButton = new JButton("Fin d'ajout");
        backButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action "Ajouter"
        addButton.addActionListener(e -> {
            List<Diet> selectedDiets = dietList.getSelectedValuesList();
            if (selectedDiets.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucun régime sélectionné. Rien à ajouter.", "Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                for (Diet diet : selectedDiets) {
                    dietRecipeController.addDietToRecipe(diet.getLabel(), recipe.getLabel());
                }

                // Mise à jour de la liste (retrait des régimes déjà sélectionnés)
                for (Diet diet : selectedDiets) {
                    dietModel.removeElement(diet);
                }

                JOptionPane.showMessageDialog(this, "Le régime a bien été ajouté");

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
            }
        });
    }
}