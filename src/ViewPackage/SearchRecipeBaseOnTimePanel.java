package ViewPackage;

import controllerPackage.RecipeController;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchRecipeBaseOnTimePanel extends JPanel {
    private JPanel TitlePanel, FormPanel, BouttonsPanel;
    private JLabel titleLabel, cookingTimeDebutLabel, cookingTimeEndLabel;
    private JTextField cookingTimeDebutField, cookingTimeEndField;
    private RecipeController recipeController;
    public SearchRecipeBaseOnTimePanel(MainWindow mainWindow){
        setLayout(new BorderLayout());

        TitlePanel = new JPanel(new FlowLayout());
        titleLabel = new JLabel("Choissez un intervalle : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_END;

        int row = 0;

        // Intervalle de début
        gbc.gridx = 0;
        gbc.gridy = row;
        cookingTimeDebutLabel = new JLabel("Temps minimum :");
        cookingTimeDebutLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(cookingTimeDebutLabel, gbc);

        gbc.gridx = 1;
        cookingTimeDebutField = new JTextField(20);
        FormPanel.add(cookingTimeDebutField, gbc);

        // Intervalle de fin
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        cookingTimeEndLabel = new JLabel("Temps maximum :");
        cookingTimeEndLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(cookingTimeEndLabel, gbc);

        gbc.gridx = 1;
        cookingTimeEndField = new JTextField(20);
        FormPanel.add(cookingTimeEndField, gbc);

        add(FormPanel, BorderLayout.CENTER);

        BouttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton searchButton = new JButton("Rechercher");
        searchButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton cancelButton = new JButton("Retour");
        cancelButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        BouttonsPanel.add(searchButton);
        BouttonsPanel.add(cancelButton);

        add(BouttonsPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> {
            cookingTimeDebutField.setText("");
            cookingTimeEndField.setText("");
            mainWindow.showHomePanel();
        });

        searchButton.addActionListener(e -> {
            try {
                String cookingTimeDebutStr = cookingTimeDebutField.getText().trim();
                String cookingTimeEndStr = cookingTimeEndField.getText().trim();

                if(cookingTimeDebutStr.isEmpty() || cookingTimeEndStr.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Tous champs doivent être remplis", "Champs manquants", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Integer cookingTimeDebut;
                Integer cookingTimeEnd;
                try {
                    cookingTimeDebut = Integer.valueOf(cookingTimeDebutStr);
                    cookingTimeEnd = Integer.valueOf(cookingTimeEndStr);
                }
                catch (Exception exception) {
                    JOptionPane.showMessageDialog(this, "Les intervalles doivent être des nombres entier", "Champs manquants", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                RecipeController recipeController = new RecipeController();
                List<Recipe> filteredRecipes = recipeController.showRecipesBasedOnTime(cookingTimeDebut, cookingTimeEnd);

                if (filteredRecipes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Aucune recette trouvée dans cet intervalle.", "Aucun résultat", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    mainWindow.getMainContainer().removeAll();
                    mainWindow.getMainContainer().add(new RecipeListPanel(mainWindow, filteredRecipes), BorderLayout.CENTER);
                    mainWindow.getMainContainer().revalidate();
                    mainWindow.getMainContainer().repaint();
                }
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
