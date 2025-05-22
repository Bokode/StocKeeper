package ViewPackage;

import modelPackage.Recipe;
import modelPackage.RecipeType;

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
        JPanel content = new JPanel(new GridLayout(0, 1, 8, 8));
        content.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        Font contentFont = new Font("Poppins", Font.PLAIN, 15);

        JLabel nameLabel = new JLabel("Nom : " + recipe.getLabel());
        nameLabel.setFont(contentFont);
        content.add(nameLabel);

        JLabel descLabel = new JLabel("Description : " + recipe.getDescription());
        descLabel.setFont(contentFont);
        content.add(descLabel);

        JLabel calLabel = new JLabel("Apport calorique : " +
                (recipe.getCaloricIntake() != null ? recipe.getCaloricIntake() + " kcal" : "Non renseigné"));
        calLabel.setFont(contentFont);
        content.add(calLabel);

        JLabel timeLabel = new JLabel("Temps de préparation : " +
                (recipe.getTimeToMake() != null ? recipe.getTimeToMake() + " minutes" : "Non renseigné"));
        timeLabel.setFont(contentFont);
        content.add(timeLabel);

        JLabel typeLabel = new JLabel("Type : " + recipe.getType().getLabel());
        typeLabel.setFont(contentFont);
        content.add(typeLabel);

        JLabel coldLabel = new JLabel("Recette froide : " + (recipe.getCold() != null && recipe.getCold() ? "Oui" : "Non"));
        coldLabel.setFont(contentFont);
        content.add(coldLabel);

        JLabel lastDoneLabel = new JLabel("Dernière réalisation : " +
                (recipe.getLastDayDone() != null ? recipe.getLastDayDone().toString() : "Jamais"));
        lastDoneLabel.setFont(contentFont);
        content.add(lastDoneLabel);

        add(content, BorderLayout.CENTER);

        // Back Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backButton = new JButton("Retour");
        backButton.setFont(contentFont);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action
        backButton.addActionListener(e -> mainWindow.showRecipeListPanel());
    }
}