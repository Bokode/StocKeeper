package ViewPackage;

import controllerPackage.IngredientAmountController;
import controllerPackage.RecipeMaterialController;
import modelPackage.IngredientAmount;
import modelPackage.Material;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecipeDetailsPanel extends JPanel {

    public RecipeDetailsPanel(MainWindow mainWindow, Recipe recipe, String cancelPanelName) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        Font contentFont = new Font("Poppins", Font.PLAIN, 15);

        // Titre
        JLabel titleLabel = new JLabel("Détails de la recette");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Panel principal horizontal
        JPanel mainContentPanel = new JPanel(new GridLayout(1, 3, 20, 0)); // 1 ligne, 3 colonnes (50%, 25%, 25%)

        // === Partie Gauche : Infos + Description ===
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));

        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.add(createLabel("Nom : " + recipe.getLabel(), contentFont));
        infoPanel.add(createLabel("Apport calorique : " +
                (recipe.getCaloricIntake() != null ? recipe.getCaloricIntake() + " kcal" : "Non renseigné"), contentFont));
        infoPanel.add(createLabel("Temps de préparation : " +
                (recipe.getTimeToMake() != null ? recipe.getTimeToMake() + " minutes" : "Non renseigné"), contentFont));
        infoPanel.add(createLabel("Type : " + recipe.getType().getLabel(), contentFont));
        infoPanel.add(createLabel("Recette froide : " + (Boolean.TRUE.equals(recipe.getCold()) ? "Oui" : "Non"), contentFont));
        infoPanel.add(createLabel("Dernière réalisation : " +
                (recipe.getLastDayDone() != null ? recipe.getLastDayDone().toString() : "Jamais"), contentFont));

        leftPanel.add(infoPanel, BorderLayout.NORTH);

        // Description (TextArea)
        JTextArea descriptionArea = new JTextArea(recipe.getDescription());
        descriptionArea.setFont(contentFont);
        descriptionArea.setEditable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setBorder(BorderFactory.createTitledBorder("Description"));
        descScrollPane.setPreferredSize(new Dimension(200, 150));
        leftPanel.add(descScrollPane, BorderLayout.CENTER);

        mainContentPanel.add(leftPanel);

        // === Partie Milieu : Ingrédients ===
        JTextArea ingredientsArea = new JTextArea();
        ingredientsArea.setFont(contentFont);
        ingredientsArea.setEditable(false);
        ingredientsArea.setOpaque(false);
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);

        IngredientAmountController ingredientAmountController = new IngredientAmountController();
        List<IngredientAmount> ingredients = ingredientAmountController.getIngredientAmountsByRecipe(recipe.getLabel());
        if (ingredients != null && !ingredients.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (IngredientAmount ing : ingredients) {
                sb.append("- ").append(ing.getFood()).append(" : ").append(ing.getQuantity()).append(" (qtt/g/cl)\n");
            }
            ingredientsArea.setText(sb.toString());
        } else {
            ingredientsArea.setText("Aucun ingrédient renseigné.");
        }

        JScrollPane ingredientsScrollPane = new JScrollPane(ingredientsArea);
        ingredientsScrollPane.setBorder(BorderFactory.createTitledBorder("Ingrédients"));
        ingredientsScrollPane.setPreferredSize(new Dimension(200, 150));
        mainContentPanel.add(ingredientsScrollPane);

        // === Partie Droite : Matériel ===
        JTextArea materialArea = new JTextArea();
        materialArea.setFont(contentFont);
        materialArea.setEditable(false);
        materialArea.setOpaque(false);
        materialArea.setLineWrap(true);
        materialArea.setWrapStyleWord(true);

        RecipeMaterialController recipeMaterialController = new RecipeMaterialController();
        List<Material> materials = recipeMaterialController.getMaterialsByRecipe(recipe.getLabel());
        if (materials != null && !materials.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Material m : materials) {
                sb.append("- ").append(m.getLabel()).append("\n");
            }
            materialArea.setText(sb.toString());
        } else {
            materialArea.setText("Aucun matériel renseigné.");
        }

        JScrollPane materialScrollPane = new JScrollPane(materialArea);
        materialScrollPane.setBorder(BorderFactory.createTitledBorder("Matériel"));
        materialScrollPane.setPreferredSize(new Dimension(200, 150));
        mainContentPanel.add(materialScrollPane);

        add(mainContentPanel, BorderLayout.CENTER);

        // Bouton retour
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Retour");
        backButton.setFont(contentFont);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            if(cancelPanelName.equals("recipeList")) mainWindow.showRecipeListPanel();
            else if (cancelPanelName.equals("possibleRecipe")) {
                mainWindow.showPossibleRecipePanel();
            } else if (cancelPanelName.equals("recipeWithExpiredFoodList")) {
                mainWindow.showRecipeWithExpiredFoodListPanel();
            }
        });
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}