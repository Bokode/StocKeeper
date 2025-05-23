package ViewPackage;

import modelPackage.FoodIn;

import javax.swing.*;
import java.awt.*;

public class UpdateFoodInPanel extends JPanel {
    private JPanel FormPanel, ButtonsPanel, TitlePanel;
    private JTextField labelField, caloricIntakeField, timeToMakeField;
    private JTextArea descriptionArea;
    private JLabel titleLabel, labelLabel, descriptionLabel, caloricInTakeLabel, timeToMakeLabel, recipeTypeLabel;

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
    }

}
