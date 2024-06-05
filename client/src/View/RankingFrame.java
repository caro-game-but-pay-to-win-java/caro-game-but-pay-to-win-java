package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CustomComponents.CustomTable;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RankingFrame extends JFrame {
	private Image img_close = new ImageIcon(RankingFrame.class.getResource("/img/close_img.png")).getImage()
			.getScaledInstance(30, 37, Image.SCALE_SMOOTH);

	private CustomTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RankingFrame window = new RankingFrame();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RankingFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		setUndecorated(true);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				GradientPaint gradient = new GradientPaint(0, 0, new Color(0x5170FF), // Màu bắt đầu (#ff8a00)#5170ff
						getWidth(), getHeight(), new Color(0xff66c4) // Màu kết thúc (#e52e71)#ff66c4
				);

				// Sơn gradient trên JPanel
				g2d.setPaint(gradient);
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		panel.setBounds(0, 0, 1000, 800);

		getContentPane().add(panel);
		panel.setLayout(null);

		table = new CustomTable();
		String[] columnNames = { "ID", "NAME", "RANK", "ELO" };
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setColumnIdentifiers(columnNames);

		JTableHeader tableHeader = table.getTableHeader();
		tableHeader.setBackground(Color.decode("#D9D9D9"));
		tableHeader.setFont(new Font("Tahoma", Font.BOLD, 24));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setViewportBorder(UIManager.getBorder("CheckBoxMenuItem.border"));
		scrollPane.setLocation(10, 242);
		scrollPane.setSize(980, 500);
		scrollPane.setBackground(Color.decode("#D9D9D9"));
		panel.add(scrollPane, BorderLayout.CENTER);

		JLabel close = new JLabel("New label");
		close.setIcon(new ImageIcon(img_close));
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});
		close.setBounds(960, 10, 30, 37);
		panel.add(close);

		setLocationRelativeTo(null);

	}
}
