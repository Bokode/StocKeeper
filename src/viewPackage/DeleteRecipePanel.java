package viewPackage;

import controllerPackage.RecipeController;

import javax.swing.*;
import java.awt.*;

public class DeleteRecipePanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;
    private JTextField labelField;
    private JLabel titleLabel, labelLabel;
    private RecipeController recipeController;
    public DeleteRecipePanel(MainWindow mainWindow){
        setLayout(new BorderLayout());

        TitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Supprimer une recette : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        labelLabel = new JLabel("Nom de la recette à supprimer : ");
        labelLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        FormPanel.add(labelLabel, gbc);

        labelField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        FormPanel.add(labelField, gbc);

        add(FormPanel, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton cancelButton = new JButton("Retour");
        cancelButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(deleteButton);
        ButtonsPanel.add(cancelButton);
        ButtonsPanel.add(resetButton);
        add(ButtonsPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> {
            labelField.setText("");
            mainWindow.showHomePanel();
        });

        resetButton.addActionListener(e -> {
            labelField.setText("");
        });

        deleteButton.addActionListener(e -> {
            try {
                String labelToFind = labelField.getText().trim();
                if (labelToFind.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Veuillez rentrer quelque chose.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                recipeController = new RecipeController();
                recipeController.deleteRecipe(labelToFind);
                JOptionPane.showMessageDialog(this, "Recette supprimée avec succès !", "Recette supprimée", JOptionPane.INFORMATION_MESSAGE);
                labelField.setText("");
            }
            catch(Exception exception){
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
