package ViewPackage;

import modelPackage.Allergen;
import modelPackage.ExpiredFood;
import modelPackage.Season;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ExpiredFoodInInStoragePanel extends JPanel {
    public ExpiredFoodInInStoragePanel(MainWindow mainWindow, List<ExpiredFood> expiredFoodList) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Résultats des aliments périmés");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Nom de l'aliment", "Saisons", "Allergènes"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (ExpiredFood food : expiredFoodList) {
            String name = food.getFoodIn().getFood().getLabel();
            String seasons = food.getSeasons().stream()
                    .map(Season::getLabel)
                    .collect(Collectors.joining(", "));
            String allergens = food.getAllergens().stream()
                    .map(Allergen::getLabel)
                    .collect(Collectors.joining(", "));

            tableModel.addRow(new Object[]{name, seasons, allergens});
        }

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Poppins", Font.PLAIN, 14));
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 15));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Retour");
        backButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        backButton.addActionListener(e -> mainWindow.showHomePanel());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}
