package Front_end;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private JLabel   line1Label, line2Label;
    public Panel() {
        line1Label = new JLabel("StocKeeper");
        line2Label = new JLabel("Bienvenue sur StocKeeper, vous pouvez choisir une de options ci-dessous.");
        line1Label.setFont(new Font("Poppins", Font.BOLD, 40));
        line2Label.setFont(new Font("Poppins", Font.PLAIN , 20));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        line1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        line2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(line1Label);
        this.add(line2Label);
    }
}
