package ViewPackage;

import controllerPackage.FoodController;
import controllerPackage.FoodInController;
import modelPackage.Food;
import modelPackage.FoodIn;
import modelPackage.StorageType;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.lang.model.type.NullType;
import javax.swing.*;
import java.awt.*;
import java.io.Console;
import java.util.Properties;

public class UpdateFoodInPanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;
    private JTextField quantityField, FoodField;
    private JLabel titleLabel, expirationDateLabel, quantityLabel, nutriScoreLabel, purchaseLabel, foodLabel, storageTypeLabel;
    private UtilDateModel expirationModel, purchaseModel;

    FoodInController foodInController = new FoodInController();
    FoodController foodController = new FoodController();

    public UpdateFoodInPanel(MainWindow mainWindow, FoodIn foodIn) {
        setLayout(new BorderLayout());

        String labelToFind = foodIn.getFood().getLabel();

        // Titre de Fenêtre
        TitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Modification de l'aliment : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);

        FormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Configurer les propriétés de format de date
        Properties p = new Properties();
        p.put("text.today", "Aujourd'hui");
        p.put("text.month", "Mois");
        p.put("text.year", "Année");

        // Expiration Date
        gbc.gridx = 0; gbc.gridy = row;
        expirationDateLabel = new JLabel("Date d'expiration :");
        expirationDateLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(expirationDateLabel, gbc);
        gbc.gridx = 1;
        expirationModel = new UtilDateModel();
        if (foodIn.getExpirationDate() != null) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(foodIn.getExpirationDate());
            expirationModel.setDate(cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH), cal.get(java.util.Calendar.DAY_OF_MONTH));
            expirationModel.setSelected(true);
        }
        JDatePanelImpl expirationPanel = new JDatePanelImpl(expirationModel, p);
        JDatePickerImpl expirationDatePicker = new JDatePickerImpl(expirationPanel, new DateLabelFormatter());
        FormPanel.add(expirationDatePicker, gbc);

        row++;

        // Quantity
        gbc.gridx = 0; gbc.gridy = row;
        quantityLabel = new JLabel("Quantité : ");
        quantityLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(quantityLabel, gbc);
        gbc.gridx = 1;
        quantityField = new JTextField(20);
        quantityField.setText(foodIn.getQuantity().toString());
        FormPanel.add(quantityField, gbc);

        row++;

        // Is Open
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JCheckBox isOpenCheckBox = new JCheckBox("Paquet ouvert");
        isOpenCheckBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        isOpenCheckBox.setSelected(Boolean.TRUE.equals(foodIn.getOpen()));
        FormPanel.add(isOpenCheckBox, gbc);
        gbc.gridwidth = 1;

        row++;

        // NutriScore
        gbc.gridx = 0; gbc.gridy = row;
        nutriScoreLabel = new JLabel("NutriScore : ");
        nutriScoreLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(nutriScoreLabel, gbc);
        gbc.gridx = 1;
        String[] nutriScore = {"A", "B", "C", "D", "E", "Non défini"};
        JComboBox<String> nutriScoreComboBox = new JComboBox<>(nutriScore);
        nutriScoreComboBox.setFont(new Font("Poppins", Font.PLAIN, 15));
        nutriScoreComboBox.setSelectedItem(foodIn.getNutriScore() == null ? "Non défini" : foodIn.getNutriScore().toString());
        FormPanel.add(nutriScoreComboBox, gbc);

        row++;

        // Purchase Date
        gbc.gridx = 0; gbc.gridy = row;
        purchaseLabel = new JLabel("Date d'achat :");
        purchaseLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(purchaseLabel, gbc);
        gbc.gridx = 1;
        purchaseModel = new UtilDateModel();
        if (foodIn.getPurchaseDate() != null) {
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(foodIn.getPurchaseDate());
            purchaseModel.setDate(cal.get(java.util.Calendar.YEAR), cal.get(java.util.Calendar.MONTH), cal.get(java.util.Calendar.DAY_OF_MONTH));
            purchaseModel.setSelected(true);
        }
        JDatePanelImpl purchasePanel = new JDatePanelImpl(purchaseModel, p);
        JDatePickerImpl purchaseDatePicker = new JDatePickerImpl(purchasePanel, new DateLabelFormatter());
        FormPanel.add(purchaseDatePicker, gbc);

        row++;

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
        typeStorageComboBox.setSelectedItem(foodIn.getStorageType().getLabel());
        FormPanel.add(typeStorageComboBox, gbc);

        // Add all to the panel
        add(FormPanel, BorderLayout.CENTER);

        ButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton updateButton = new JButton("Modifier");
        updateButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        JButton cancelButton = new JButton("Retour");
        cancelButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        ButtonsPanel.add(updateButton);
        ButtonsPanel.add(cancelButton);
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
                else if (component instanceof JScrollPane) {
                    Component view = ((JScrollPane) component).getViewport().getView();
                    if (view instanceof JTextArea) {
                        ((JTextArea) view).setText("");
                    }
                }
            }
            expirationModel.setValue(null);
            purchaseModel.setValue(null);
            mainWindow.showSearchFoodInPanel();
        });

        updateButton.addActionListener(e -> {
            try {
                java.util.Date expirationDate = expirationModel.getValue();
                String quantityString = quantityField.getText().trim();
                Integer quantity = null;
                boolean isOpen = isOpenCheckBox.isSelected();
                java.util.Date purchaseDate = purchaseModel.getValue(); // Facultatif
                String nutriScoreString = nutriScoreComboBox.getSelectedItem().toString(); // Facultatif
                Character nutriScoreCharacter = nutriScoreString.charAt(0);
                String typeStorageString = typeStorageComboBox.getSelectedItem().toString();
                if (quantityString.isEmpty() || expirationDate == null) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.");
                    return;
                }

                if (nutriScoreCharacter == 'N') {
                    nutriScoreCharacter = null;
                }

                try {
                    quantity = Integer.valueOf(quantityString);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur : La quantité doit être un nombre entier.");
                    return;
                }

                Food food = foodIn.getFood();

                if (food == null) {
                    JOptionPane.showMessageDialog(this, "Aliment non trouvé");
                } else {
                    StorageType storageType = new StorageType(typeStorageString);
                    FoodIn newFoodIn = new FoodIn(expirationDate, quantity, isOpen, nutriScoreCharacter, purchaseDate, food, storageType);
                    foodInController.updateFoodIn(newFoodIn);
                    JOptionPane.showMessageDialog(this, "Aliment modifié", "Aliment modifié", JOptionPane.INFORMATION_MESSAGE);
                    mainWindow.showHomePanel();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Une erreur est survenue : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
