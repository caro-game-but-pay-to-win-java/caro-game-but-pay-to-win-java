package CustomComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTable;

public class CustomTable extends JTable {

	public CustomTable() {
		setOpaque(false);
		setBackground(Color.decode("#D9D9D9"));

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
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

		g2d.dispose();
	}

}
