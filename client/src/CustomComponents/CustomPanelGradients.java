package CustomComponents;

import javax.swing.*;
import java.awt.*;

public class CustomPanelGradients extends JPanel {

    private Color shadowColor = new Color(0, 0, 0, 64);
    private int shadowSize = 4;
    private int borderRadius = 30;

    public CustomPanelGradients() {
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(shadowColor);
        g2d.fillRect(shadowSize, shadowSize, getWidth() - shadowSize * 2, getHeight() - shadowSize * 2);

        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        super.paintBorder(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE); 
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);

        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(230, 67); 
    }
}
