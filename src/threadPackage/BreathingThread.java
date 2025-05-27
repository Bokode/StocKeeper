package threadPackage;


import javax.swing.*;
import java.awt.*;

public class BreathingThread extends Thread {
    private final JComponent component;
    private boolean running = true;

    public BreathingThread(JComponent component) {
        this.component = component;
        this.setDaemon(true); // Pour ne pas bloquer l'arrêt de l'application
    }

    @Override
    public void run() {
        float hue = 0f;
        float delta = 0.005f;

        while (running) {
            hue += delta;
            if (hue > 1f) hue = 0f;

            final Color color = Color.getHSBColor(hue, 0.6f, 1.0f);

            SwingUtilities.invokeLater(() -> {
                component.setForeground(color);
            });

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }
}
