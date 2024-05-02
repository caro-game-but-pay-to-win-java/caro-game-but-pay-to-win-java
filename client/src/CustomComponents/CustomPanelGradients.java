package CustomComponents;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

public class CustomPanelGradients extends JPanel {
	private int borderRadius;
    private Color backgroundColor;
    private Color borderColor;

    public CustomPanelGradients() {
        setOpaque(false); 
        backgroundColor = Color.decode("#D9D9D9"); 
      
        setBorder(null); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int shadowSize = 4;
        int shadowOpacity = 90;
        Color shadowColor = new Color(0, 0, 0, shadowOpacity);
        int shadowOffset = 4;
        int cornerRadius = 20; 
        g2d.setColor(shadowColor);
        g2d.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset * 2, getHeight() - shadowOffset * 2, cornerRadius, cornerRadius);
        
        g2d.dispose();
    }


    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        repaint();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        repaint(); 
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        setBorder(new LineBorder(borderColor, 1));
    }
}
