package ViewPackage;

import modelPackage.FoodType;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;

public class AddRecipePanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;
    private JTextField labelField, descriptionField, caloricIntakeField, timeToMakeField;
    private JLabel titleLabel, labelLabel, descriptionLabel, caloricInTakeLabel, timeToMakeLabel, recipeTypeLabel;
    public AddRecipePanel(MainWindow mainWindow) {
        setLayout(new BorderLayout());

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        TitlePanel = new JPanel(new FlowLayout());
        titleLabel = new JLabel("Ajouter une recette : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);


        int row = 0;

        gbc.gridx = 0; gbc.gridy = row;
        labelLabel = new JLabel("Nom :");
        labelLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(labelLabel, gbc);
        gbc.gridx = 1;
        labelField = new JTextField(20);
        FormPanel.add(labelField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        descriptionLabel = new JLabel("Description de la recette : ");
        descriptionLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        FormPanel.add(descriptionField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        caloricInTakeLabel = new JLabel("Apport calorique (facultatif) : ");
        caloricInTakeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(caloricInTakeLabel, gbc);
        gbc.gridx = 1;
        caloricIntakeField = new JTextField(20);
        FormPanel.add(caloricIntakeField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        timeToMakeLabel = new JLabel("Durée de la recette (facultatif) : ");
        timeToMakeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(timeToMakeLabel, gbc);
        gbc.gridx = 1;
        timeToMakeField = new JTextField(20);
        FormPanel.add(timeToMakeField, gbc);

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JCheckBox isColdCheckBox = new JCheckBox("Recette froide");
        isColdCheckBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(isColdCheckBox, gbc);
        gbc.gridwidth = 1;

        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        recipeTypeLabel = new JLabel("Type de recette : ");
        recipeTypeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(recipeTypeLabel, gbc);
        gbc.gridx = 1;
        String[] typeRecettes = {"soupe", "entrée", "plat principal", "dessert"};
        JComboBox<String> typeRecetteComboBox = new JComboBox<>(typeRecettes);
        typeRecetteComboBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(typeRecetteComboBox, gbc);

        add(FormPanel, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Ajouter");
        addButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(addButton);
        ButtonsPanel.add(cancelButton);
        ButtonsPanel.add(resetButton);
        add(ButtonsPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> {
            for (Component component : FormPanel.getComponents()){
                if (component instanceof JTextField){
                    ((JTextField) component).setText("");
                } else if (component instanceof JCheckBox) {
                    ((JCheckBox) component).setSelected(false);
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
            }
            mainWindow.showHomePanel();
        });

        resetButton.addActionListener(e -> {
            for (Component component : FormPanel.getComponents()){
                if (component instanceof JTextField){
                    ((JTextField) component).setText("");
                } else if (component instanceof JCheckBox) {
                    ((JCheckBox) component).setSelected(false);
                } else if (component instanceof JComboBox) {
                    ((JComboBox<?>) component).setSelectedIndex(0);
                }
            }
        });

        addButton.addActionListener(e -> {
            String label = labelField.getText().trim();
            String description = descriptionField.getText().trim();
            String caloricInTakeString = caloricIntakeField.getText().trim();
            if(!caloricInTakeString.isEmpty()){
                try {
                    Integer caloricInTake = Integer.valueOf(caloricInTakeString);
                    System.out.println("Integer : " + caloricInTake);
                }
                catch (NumberFormatException numberFormatException){
                    JOptionPane.showMessageDialog(this, "Erreur : L'apport calorique doit être un nombre");
                }
            }
            String timeToMakeString = timeToMakeField.getText().trim();
            if(!timeToMakeString.isEmpty()){
                try {
                    Integer timeToMake = Integer.valueOf(timeToMakeString);
                    System.out.println("Integer : " + timeToMake);
                }
                catch (NumberFormatException numberFormatException){
                    JOptionPane.showMessageDialog(this, "Erreur : La durée de la recette doit être un nombre");
                }
            }
            boolean isCold = isColdCheckBox.isSelected();
            String foodTypeString = typeRecetteComboBox.getSelectedItem().toString();
            // FoodType foodType = new FoodType(foodTypeString);
            /*try {
                Recipe recipe = new Recipe()
            }
            catch (Exception e2){
                JOptionPane.showMessageDialog(this, );
            }
             */
        });
    }
}
