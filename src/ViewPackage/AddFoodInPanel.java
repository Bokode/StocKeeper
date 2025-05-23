package ViewPackage;

import controllerPackage.FoodInController;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class AddFoodInPanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;

    private JTextField quantityField, nutriScoreField;

    private JLabel titleLabel, expirationDateLabel, quantityLabel, nutriScoreLabel, purchaseLabel, foodLabel, storageTypeLabel;

    FoodInController foodInController = new FoodInController();

    public AddFoodInPanel(MainWindow mainWindow) {
        setLayout(new BorderLayout());

        TitlePanel = new JPanel(new FlowLayout());
        titleLabel = new JLabel("Ajouter un aliment : ");
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
        UtilDateModel expirationModel = new UtilDateModel();
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
        FormPanel.add(quantityField, gbc);

        row++;

        // Is Open
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JCheckBox isOpen = new JCheckBox("Paquet ouvert");
        isOpen.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(isOpen, gbc);
        gbc.gridwidth = 1;

        row++;

        // NutriScore
        gbc.gridx = 0; gbc.gridy = row;
        nutriScoreLabel = new JLabel("NutriScore : ");
        nutriScoreLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(nutriScoreLabel, gbc);
        gbc.gridx = 1;
        nutriScoreField = new JTextField(20);
        FormPanel.add(nutriScoreField, gbc);

        row++;

        // Purchase Date
        gbc.gridx = 0; gbc.gridy = row;
        purchaseLabel = new JLabel("Date d'achat :");
        purchaseLabel.setFont(new Font("Poppins", Font.PLAIN, 15));
        FormPanel.add(purchaseLabel, gbc);
        gbc.gridx = 1;
        UtilDateModel purchaseModel = new UtilDateModel();
        JDatePanelImpl purchasePanel = new JDatePanelImpl(purchaseModel, p);
        JDatePickerImpl purchaseDatePicker = new JDatePickerImpl(purchasePanel, new DateLabelFormatter());
        FormPanel.add(purchaseDatePicker, gbc);

        row++;

        // Food


        // StorageType

        add(FormPanel, BorderLayout.CENTER);
    }
}
