package Front_end;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {
    private Container mainContainer;
    private Panel panel;
    public MainWindow() {
        super("StocKeeper");
        setBounds(200, 155, 1400, 780);
        addWindowListener (new WindowAdapter() {
            public void windowClosing (WindowEvent e) {
                System.exit(0);
            }
        } );
        panel = new Panel();
        mainContainer = this.getContentPane();
        mainContainer.add(panel,BorderLayout.CENTER);
        setVisible(true);
    }
}
