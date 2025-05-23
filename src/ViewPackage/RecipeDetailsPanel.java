package ViewPackage;

import businessPackage.IngredientAmountManager;
import controllerPackage.IngredientAmountController;
import modelPackage.IngredientAmount;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;

public class RecipeDetailsPanel extends JPanel {
    public RecipeDetailsPanel(MainWindow mainWindow, Recipe recipe) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JLabel titleLabel = new JLabel("Détails de la recette");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Infos de base
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 8, 8));
        Font contentFont = new Font("Poppins", Font.PLAIN, 15);

        infoPanel.add(createLabel("Nom : " + recipe.getLabel(), contentFont));
        infoPanel.add(createLabel("Description : " + recipe.getDescription(), contentFont));
        infoPanel.add(createLabel("Apport calorique : " +
                (recipe.getCaloricIntake() != null ? recipe.getCaloricIntake() + " kcal" : "Non renseigné"), contentFont));
        infoPanel.add(createLabel("Temps de préparation : " +
                (recipe.getTimeToMake() != null ? recipe.getTimeToMake() + " minutes" : "Non renseigné"), contentFont));
        infoPanel.add(createLabel("Type : " + recipe.getType().getLabel(), contentFont));
        infoPanel.add(createLabel("Recette froide : " + (Boolean.TRUE.equals(recipe.getCold()) ? "Oui" : "Non"), contentFont));
        infoPanel.add(createLabel("Dernière réalisation : " +
                (recipe.getLastDayDone() != null ? recipe.getLastDayDone().toString() : "Jamais"), contentFont));

        contentPanel.add(infoPanel, BorderLayout.NORTH);

        // Zone d'affichage des ingrédients (non cliquable)
        JTextArea ingredientsArea = new JTextArea();
        ingredientsArea.setFont(new Font("Poppins", Font.PLAIN, 14));
        ingredientsArea.setEditable(false);
        ingredientsArea.setOpaque(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);

        IngredientAmountManager ingredientAmountManager = new IngredientAmountManager();
        if (ingredientAmountManager.getIngredientAmountsByRecipe(recipe.getLabel()) != null && !ingredientAmountManager.getIngredientAmountsByRecipe(recipe.getLabel()).isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (IngredientAmount ing : ingredientAmountManager.getIngredientAmountsByRecipe(recipe.getLabel())) {
                sb.append("- ").append(ing.getFood()).append(" : ").append(ing.getQuantity()).append(" (qqt/g/cl)\n");
            }
            ingredientsArea.setText(sb.toString());
        } else {
            ingredientsArea.setText("Aucun ingrédient renseigné.");
        }

        JScrollPane scrollPane = new JScrollPane(ingredientsArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Ingrédients"));
        scrollPane.setPreferredSize(new Dimension(300, 150));

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        // Bouton retour
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Retour");
        backButton.setFont(contentFont);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> mainWindow.showRecipeListPanel());
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}