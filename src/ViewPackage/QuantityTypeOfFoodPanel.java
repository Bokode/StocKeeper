package ViewPackage;

import controllerPackage.FoodInController;

import javax.swing.*;
import java.awt.*;

public class QuantityTypeOfFoodPanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;
    private JLabel titleLabel, foodTypeLabel;
    private FoodInController foodInController;

    public QuantityTypeOfFoodPanel(MainWindow mainWindow) {
        setLayout(new BorderLayout(10, 10));

        foodInController = new FoodInController();

        TitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Rechercher quantité restante d'un certain type: ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // FoodType
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        foodTypeLabel = new JLabel("Type d'aliment : ");
        foodTypeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(foodTypeLabel, gbc);
        gbc.gridx = 1;
        String[] typeFood = {"Solide", "Liquide"};
        JComboBox<String> typeFoodComboBox = new JComboBox<>(typeFood);
        typeFoodComboBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(typeFoodComboBox, gbc);

        add(FormPanel, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton searchButton = new JButton("Rechercher");
        searchButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton cancelButton = new JButton("Retour");
        cancelButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(searchButton);
        ButtonsPanel.add(cancelButton);
        add(ButtonsPanel, BorderLayout.SOUTH);

        cancelButton.addActionListener(e -> {
            mainWindow.showHomePanel();
        });

        searchButton.addActionListener(e -> {
            try {
                Integer quantityLeft =  foodInController.showQuantityLeft(typeFoodComboBox.getSelectedItem().toString());
                JOptionPane.showMessageDialog(this, "Quantité restante : " +  quantityLeft + "(qqt/g/cl)", "Quantité restante", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(Exception exception){
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
