package ViewPackage;

import controllerPackage.FoodInController;
import modelPackage.FoodIn;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FoodInListPanel extends JPanel
{
    private JList<FoodIn> foodInJList;
    private DefaultListModel<FoodIn> listModel;
    private JPanel ButtonsPanel;
    private JButton detailsButton, backButton;
    private JLabel titleLabel;

    public FoodInListPanel(MainWindow mainWindow)
    {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titleLabel = new JLabel("Liste des aliments");
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // List Panel
        listModel = new DefaultListModel<>();
        foodInJList = new JList<>(listModel);
        foodInJList.setFont(new Font("Poppins", Font.PLAIN, 15));
        foodInJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        foodInJList.setVisibleRowCount(10);
        foodInJList.setFixedCellHeight(30);
        foodInJList.setFixedCellWidth(200);
        foodInJList.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Custom renderer to only show the label
        foodInJList.setCellRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
            {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof FoodIn)
                {
                    FoodIn foodIn = (FoodIn) value;
                    String label = foodIn.getFood().getLabel();
                    int quantity = foodIn.getQuantity();
                    Date expirationDate = foodIn.getExpirationDate();
                    Date currentDate = new Date();

                    long diffMillis = expirationDate.getTime() - currentDate.getTime();
                    long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

                    String expirationInfo;
                    if (diffDays < 0) {
                        expirationInfo = "Périmé depuis " + Math.abs(diffDays) + " jour(s)";
                    } else if (diffDays == 0) {
                        expirationInfo = "Expire aujourd'hui";
                    } else {
                        expirationInfo = "Expire dans " + diffDays + " jour(s)";
                    }

                    String labelHtml = (diffDays < 0) ? "<font color='red'>" + label + "</font>" : label;

                    setText("<html>" + labelHtml + " " + quantity + " (qtt/g/cl) " + (foodIn.getOpen() ? " (Ouvert)" : " (Fermé)") +
                            "   NutriScore : " + foodIn.getNutriScore() + "   " + expirationInfo + " (" + expirationDate + ")</html>");
                }
                setFont(new Font("Poppins", Font.PLAIN, 15));
                return this;
            }

        });

        JScrollPane scrollPane = new JScrollPane(foodInJList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Aliments disponibles"));
        add(scrollPane, BorderLayout.CENTER);




    }
    public void loadFoodIns()
    {
        try
        {
            List<FoodIn> foodIns = new FoodInController().getAllFoodIns();
            listModel.clear();
            for (FoodIn foodIn : foodIns)
            {
                listModel.addElement(foodIn);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des aliments : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
