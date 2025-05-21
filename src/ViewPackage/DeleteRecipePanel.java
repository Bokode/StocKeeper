package ViewPackage;

import javax.swing.*;
import java.awt.*;

public class DeleteRecipePanel extends JPanel {
    private JPanel FormPanel, ButtonPannel, TitlePanel;
    private JLabel titleLabel;
    public DeleteRecipePanel(MainWindow mainWindow){
        TitlePanel = new JPanel(new FlowLayout());
        titleLabel = new JLabel("Supprimer une recette : ");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        TitlePanel.add(titleLabel);
        add(TitlePanel, BorderLayout.NORTH);
    }
}
