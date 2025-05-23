package ViewPackage;

import ThreadPackage.BreathingThread;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private JLabel line1Label, line2Label;
    private BreathingThread breathingThread;

    public HomePanel() {
        line1Label = new JLabel("StocKeeper");
        line2Label = new JLabel("Bienvenue sur StocKeeper, vous pouvez choisir une des options ci-dessous.");
        line1Label.setFont(new Font("Poppins", Font.BOLD, 40));
        line2Label.setFont(new Font("Poppins", Font.PLAIN , 20));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        line1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        line2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(line1Label);
        this.add(line2Label);

        breathingThread = new BreathingThread(line1Label);
        breathingThread.start();
    }

    // Optionnel : à appeler si tu veux stopper l'effet (ex: lors d’un changement de panel)
    public void stopBreathingEffect() {
        if (breathingThread != null) {
            breathingThread.stopBreathing();
        }
    }
}
