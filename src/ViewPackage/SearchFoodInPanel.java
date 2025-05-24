package ViewPackage;

import controllerPackage.FoodInController;
import controllerPackage.RecipeController;
import modelPackage.FoodIn;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;

public class SearchFoodInPanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;
    private JTextField labelField;
    private JLabel titleLabel, labelLabel;
    private FoodInController foodInController;
    public SearchFoodInPanel(MainWindow mainWindow) {
        setLayout(new BorderLayout());

        TitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Modifier un aliment : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        labelLabel = new JLabel("Nom de l'aliment à modifier : ");
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
        JButton updateButton = new JButton("Modifier");
        updateButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton cancelButton = new JButton("Retour");
        cancelButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(updateButton);
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

        updateButton.addActionListener(e -> {
            try {
                String labelToFind = labelField.getText().trim();
                if (labelToFind.isEmpty()){
                    JOptionPane.showMessageDialog(this, "Veuillez rentrer quelque chose.", "Erreur", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                foodInController = new FoodInController();
                FoodIn foodIn = foodInController.getFoodIn(labelToFind);
                labelField.setText("");
                mainWindow.showUpdateFoodInPanel(new UpdateFoodInPanel(mainWindow, foodIn));
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
