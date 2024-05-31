package CustomComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class CustomTextFiled extends JTextField{

	   private int borderRadius;
	    private Color backgroundColor;
	    private Color borderColor;

	    public CustomTextFiled() {
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
	        int cornerRadius = 20; 
	        
	        g2d.setColor(shadowColor);
	        g2d.fillRoundRect(0, 0, getWidth(), getHeight() , cornerRadius, cornerRadius);
	        
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
