package ViewPackage;

import controllerPackage.FoodInController;
import modelPackage.ExpiredFood;
import modelPackage.FoodType;
import modelPackage.StorageType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SearchExpiredFoodInInStoragePanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;
    private JLabel titleLabel, storageTypeLabel, foodTypeLabel;
    private FoodInController foodInController;

    public SearchExpiredFoodInInStoragePanel(MainWindow mainWindow) {
        setLayout(new BorderLayout());

        foodInController = new FoodInController();

        TitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Rechercher les aliments périmés: ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // StorageType
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        storageTypeLabel = new JLabel("Type de stockage : ");
        storageTypeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(storageTypeLabel, gbc);
        gbc.gridx = 1;
        String[] typeStorage = {"Frigo", "Congélateur", "Armoire"};
        JComboBox<String> typeStorageComboBox = new JComboBox<>(typeStorage);
        typeStorageComboBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(typeStorageComboBox, gbc);

        row++;

        // FoodType
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.LINE_END;
        foodTypeLabel = new JLabel("Type d'aliment : ");
        foodTypeLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(foodTypeLabel, gbc);
        gbc.gridx = 1;
        String[] typeFood = {"Solide", "Liquide", "Quantité"};
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
                StorageType storageType = new StorageType(typeStorageComboBox.getSelectedItem().toString());
                FoodType foodType = new FoodType(typeFoodComboBox.getSelectedItem().toString());
                List<ExpiredFood> expiredFoods = foodInController.foodExpired(storageType, foodType);
                mainWindow.showExpiredFoodInInStorage(new ExpiredFoodInInStoragePanel(mainWindow, expiredFoods));
            }
            catch(Exception exception){
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
