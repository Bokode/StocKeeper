package ViewPackage;

import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;

public class AddIngredientPanel extends JPanel {

    private JPanel FormPanel, ButtonsPanel;
    private JLabel titleLabel, labelLabel, ingredientAmountLabel, foodTypeLabel;
    private JTextField labelField, ingredientAmountField;

    public AddIngredientPanel(MainWindow mainWindow, Recipe recipe, String cancelPanelName) {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Ajout d'un aliment : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_END;

        int row = 0;

        // Nom
        gbc.gridx = 0;
        gbc.gridy = row;
        labelLabel = new JLabel("Nom :");
        labelLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(labelLabel, gbc);

        gbc.gridx = 1;
        labelField = new JTextField(20);
        FormPanel.add(labelField, gbc);

        // Quantité
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        ingredientAmountLabel = new JLabel("Quantité :");
        ingredientAmountLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(ingredientAmountLabel, gbc);

        gbc.gridx = 1;
        ingredientAmountField = new JTextField(20);
        FormPanel.add(ingredientAmountField, gbc);

        // Type de mesure
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        foodTypeLabel = new JLabel("Unité :");
        foodTypeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(foodTypeLabel, gbc);

        gbc.gridx = 1;
        String[] foodTypes = {"quantité", "centilitres", "grammes"};
        JComboBox<String> foodTypeComboBox = new JComboBox<>(foodTypes);
        foodTypeComboBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(foodTypeComboBox, gbc);

        add(FormPanel, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton stopButton = new JButton("Fin d'ajout");
        stopButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(addButton);
        ButtonsPanel.add(stopButton);
        ButtonsPanel.add(resetButton);
        add(ButtonsPanel, BorderLayout.SOUTH);

        resetButton.addActionListener(e -> {
            for (Component component : FormPanel.getComponents()){
                if (component instanceof JTextField){
                    ((JTextField) component).setText("");
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
            }
        });

        stopButton.addActionListener(e -> {
            for (Component component : FormPanel.getComponents()){
                if (component instanceof JTextField){
                    ((JTextField) component).setText("");
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
            }
            if (cancelPanelName.equals("home")){
                mainWindow.showHomePanel();
            } else if (cancelPanelName.equals("updateRecipe")) {
                //
            }
        });

        addButton.addActionListener(e -> {

        });
    }
}