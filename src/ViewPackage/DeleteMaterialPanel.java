package ViewPackage;

import controllerPackage.RecipeMaterialController;
import modelPackage.Material;
import modelPackage.Recipe;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DeleteMaterialPanel extends JPanel {

    private DefaultListModel<String> listModel;
    private JList<String> materialList;
    private RecipeMaterialController recipeMaterialController;

    public DeleteMaterialPanel(MainWindow mainWindow, Recipe recipe) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Contrôleur
        recipeMaterialController = new RecipeMaterialController();

        // Titre
        JLabel titleLabel = new JLabel("Matériels de : " + recipe.getLabel(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.PLAIN, 30));
        add(titleLabel, BorderLayout.NORTH);

        // Liste des matériels
        listModel = new DefaultListModel<>();

        try {
            List<Material> materials = recipeMaterialController.getMaterialsByRecipe(recipe.getLabel());
            for (Material material : materials) {
                listModel.addElement(material.getLabel());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des matériels : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        materialList = new JList<>(listModel);
        materialList.setFont(new Font("Poppins", Font.PLAIN, 15));
        materialList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(materialList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Matériels utilisés"));
        add(scrollPane, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        JButton backButton = new JButton("Retour");
        backButton.setFont(new Font("Poppins", Font.PLAIN, 15));

        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action retour
        backButton.addActionListener(e -> mainWindow.showUpdateRecipePanel(new UpdateRecipePanel(mainWindow, recipe)));

        // Action suppression
        deleteButton.addActionListener(e -> {
            int selectedIndex = materialList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedMaterial = listModel.getElementAt(selectedIndex);
                try {
                    recipeMaterialController.deleteMaterialFromRecipe(recipe.getLabel(), selectedMaterial);
                    listModel.remove(selectedIndex);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
