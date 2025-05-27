package viewPackage;

import threadPackage.BreathingThread;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private JLabel line1Label, line2Label;
    private BreathingThread breathingThread;

    public HomePanel() {
        line1Label = new JLabel("StocKeeper");
        line2Label = new JLabel("Bienvenue sur StocKeeper, vous pouvez choisir une des options dans le menu ci-dessus.");
        line1Label.setFont(new Font("Poppins", Font.BOLD, 45));
        line2Label.setFont(new Font("Poppins", Font.PLAIN , 24));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

// Centre les éléments verticalement
        this.add(Box.createVerticalGlue());
        line1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        line2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(line1Label);
        this.add(Box.createRigidArea(new Dimension(0, 15))); // petit espace entre les deux lignes
        this.add(line2Label);
        this.add(Box.createVerticalGlue());

        breathingThread = new BreathingThread(line1Label);
        breathingThread.start();
    }

}
