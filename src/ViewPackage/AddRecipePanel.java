package ViewPackage;

import modelPackage.FoodType;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;

public class AddRecipePanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel;
    private JTextField labelField, descriptionField, caloricIntakeField, timeToMakeField;
    public AddRecipePanel(MainWindow mainWindow) {
        setLayout(new BorderLayout());

        FormPanel = new JPanel(new GridLayout(1, 2, 5 , 5));

        FormPanel.add(new JLabel("Nom : ", SwingConstants.RIGHT));
        labelField = new JTextField(20);
        FormPanel.add(labelField);

        FormPanel.add(new JLabel("Description de la recette : ", SwingConstants.RIGHT));
        descriptionField = new JTextField(20);
        FormPanel.add(descriptionField);

        FormPanel.add(new JLabel("Apport calorique (facultatif : ", SwingConstants.RIGHT));
        caloricIntakeField = new JTextField(20);
        FormPanel.add(caloricIntakeField);


        FormPanel.add(new JLabel("Durée de la recette (facultatif) : ", SwingConstants.RIGHT));
        timeToMakeField = new JTextField(20);
        FormPanel.add(timeToMakeField);

        JCheckBox isColdCheckBox = new JCheckBox("Recette froide");
        FormPanel.add(isColdCheckBox);

        FormPanel.add(new JLabel("Type de recette : ", SwingConstants.RIGHT));
        String[] typeRecettes = {"soupe", "entrée", "plat principal", "dessert"};
        JComboBox<String> typeRecetteComboBox = new JComboBox<>(typeRecettes);
        typeRecetteComboBox.setEnabled(true);
        FormPanel.add(typeRecetteComboBox);

        add(FormPanel, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Ajouter");
        ButtonsPanel.add(addButton);
        JButton cancelButton = new JButton("Annuler");
        ButtonsPanel.add(cancelButton);
        JButton resetButton = new JButton("Réinitialiser");
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
            FoodType foodType = new FoodType(foodTypeString);
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
