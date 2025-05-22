package ViewPackage;

import controllerPackage.RecipeController;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecipeListPanel extends JPanel {
    private JList<Recipe> recipeJList;
    private DefaultListModel<Recipe> listModel;
    private JPanel ButtonsPanel;
    private JButton detailsButton, backButton;
    private JLabel titleLabel;

    public RecipeListPanel(MainWindow mainWindow) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
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

        // Custom renderer to only show the label
        recipeJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Recipe) {
                    setText(((Recipe) value).getLabel());
                }
                setFont(new Font("Poppins", Font.PLAIN, 15));
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(recipeJList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Recettes disponibles"));
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        detailsButton = new JButton("Détails");
        detailsButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        backButton = new JButton("Retour");
        backButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        ButtonsPanel.add(detailsButton);
        ButtonsPanel.add(backButton);
        add(ButtonsPanel, BorderLayout.SOUTH);

        // Load data
        loadRecipes();

        // Listener
        detailsButton.addActionListener(e -> {
            Recipe selectedRecipe = recipeJList.getSelectedValue();
            if (selectedRecipe != null) {
                mainWindow.getMainContainer().removeAll();
                mainWindow.getMainContainer().add(new RecipeDetailsPanel(mainWindow, selectedRecipe), BorderLayout.CENTER);
                mainWindow.getMainContainer().revalidate();
                mainWindow.getMainContainer().repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une recette.");
            }
        });

        backButton.addActionListener(e -> {
            mainWindow.showHomePanel();
        });

    }

    private void loadRecipes() {
        try {
            List<Recipe> recipes = new RecipeController().getAllRecipes();
            listModel.clear();
            for (Recipe recipe : recipes) {
                listModel.addElement(recipe);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur de chargement des recettes : " + e.getMessage());
        }
    }
}
