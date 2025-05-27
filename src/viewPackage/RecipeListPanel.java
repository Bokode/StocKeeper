package viewPackage;

import controllerPackage.RecipeController;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecipeListPanel extends JPanel {
    private JList<Recipe> recipeJList;
    private DefaultListModel<Recipe> listModel;
    private JPanel ButtonsPanel;
    private JButton detailsButton, backButton, deleteButton, updateButton;
    private JLabel titleLabel;
    private RecipeController recipeController;

    public RecipeListPanel(MainWindow mainWindow) {
        this(mainWindow, null);
    }

    public RecipeListPanel(MainWindow mainWindow, List<Recipe> customRecipeList) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Ttire
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Liste des recettes");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // List Panel
        listModel = new DefaultListModel<>();
        recipeJList = new JList<>(listModel);
        recipeJList.setFont(new Font("Poppins", Font.PLAIN, 15));
        recipeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        recipeJList.setVisibleRowCount(10);
        recipeJList.setFixedCellHeight(30);
        recipeJList.setFixedCellWidth(200);
        recipeJList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Affichage du label
        recipeJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Recipe) {
                    setText(((Recipe) value).getLabel());
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(recipeJList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Recettes disponibles"));
        add(scrollPane, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        detailsButton = new JButton("Détails");
        detailsButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        backButton = new JButton("Retour");
        backButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        updateButton = new JButton("Modifier");
        updateButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        ButtonsPanel.add(detailsButton);
        ButtonsPanel.add(updateButton);
        ButtonsPanel.add(deleteButton);
        ButtonsPanel.add(backButton);
        add(ButtonsPanel, BorderLayout.SOUTH);

        recipeController = new RecipeController();

        // Charge la liste de recettes (custom ou toutes)
        if (customRecipeList != null) {
            loadRecipes(customRecipeList);
        } else {
            loadAllRecipes();
        }

        detailsButton.addActionListener(e -> {
            if (recipeJList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une recette.", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Recipe selectedRecipe = recipeJList.getSelectedValue();
            if (selectedRecipe != null) {
                mainWindow.getMainContainer().removeAll();
                mainWindow.getMainContainer().add(new RecipeDetailsPanel(mainWindow, selectedRecipe, "recipeList"), BorderLayout.CENTER);
                mainWindow.getMainContainer().revalidate();
                mainWindow.getMainContainer().repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une recette.");
            }
        });

        updateButton.addActionListener(e -> {
            if (recipeJList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une recette.", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Recipe selectedRecipe = recipeJList.getSelectedValue();
            try {
                mainWindow.showUpdateRecipePanel(new UpdateRecipePanel(mainWindow, selectedRecipe, "recipeList"));
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            if (recipeJList.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une recette.", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Recipe selectedRecipe = recipeJList.getSelectedValue();
            try {
                recipeController.deleteRecipe(selectedRecipe.getLabel());
                JOptionPane.showMessageDialog(this, "Recette supprimée avec succès !", "Recette supprimée", JOptionPane.INFORMATION_MESSAGE);
                if (customRecipeList != null) {
                    loadRecipes(customRecipeList);
                } else {
                    loadAllRecipes();
                }
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            mainWindow.showHomePanel();
        });
    }

    public void loadAllRecipes() {
        try {
            List<Recipe> recipes = recipeController.getAllRecipes();
            loadRecipes(recipes);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement des recettes : " + e.getMessage());
        }
    }

    private void loadRecipes(List<Recipe> recipes) {
        listModel.clear();
        for (Recipe recipe : recipes) {
            listModel.addElement(recipe);
        }
    }
}
