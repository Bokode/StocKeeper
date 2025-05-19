package ViewPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
    private Container mainContainer;
    private JPanel homePanel;
    private AddRecipePanel addRecipePanel;
    private MainMenu mainMenu;
    public MainWindow() {
        super("StocKeeper");
        setBounds(200, 155, 1400, 780);
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                System.exit(0);
            }
        } );
        mainMenu = new MainMenu(this);
        setJMenuBar(mainMenu);
        homePanel = new HomePanel();
        addRecipePanel = new AddRecipePanel(this);
        mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(homePanel,BorderLayout.CENTER);
        setVisible(true);
    }

    public void showHomePanel() {
        mainContainer.removeAll();
        mainContainer.add(homePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }

    public void showAddRecipePanel() {
        mainContainer.removeAll();
        mainContainer.add(addRecipePanel, BorderLayout.CENTER);
        mainContainer.revalidate();
        mainContainer.repaint();
    }
}
