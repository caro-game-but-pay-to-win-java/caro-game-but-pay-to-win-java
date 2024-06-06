package client_test;

import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class CaroBoard extends JFrame {
	private JPanel CaroBoard;
	private JButton[][] squares;
	boolean playerPlay = false;

	public CaroBoard() {
		setTitle("Caro Game");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		CaroBoard = new JPanel(new GridLayout(19, 19));
		CaroBoard.setBounds(0, 0, 800, 763);
		squares = new JButton[19][19];
		// Map<JButton, Boolean> map = new HashMap<>();
		// Create chess board with buttons representing squares
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				squares[i][j] = new JButton();
				squares[i][j].setPreferredSize(new Dimension(40, 40));
				squares[i][j].setMargin(new Insets(0, 0, 0, 0)); // Tắt margin
				squares[i][j].setContentAreaFilled(false); // Tắt padding
				squares[i][j].setFocusable(false);
				squares[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
				// map.put(squares[i][j], false);
				CaroBoard.add(squares[i][j]);

				// Set button colors based on chess board pattern

				squares[i][j].setBackground(Color.WHITE);

				// Add action listener to handle button clicks
				final int row = i;
				final int col = j;
				squares[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Color color;
						boolean temp = playerPlay;
						if (!playerPlay) {
							color = Color.red;
							playerPlay = true;
						} else {
							color = Color.black;
							playerPlay = false;
						}
						if (squares[row][col].getBackground() == Color.WHITE) {
							String text = playerPlay != false && playerPlay ? "X" : "O";
							Color colorText = text.equals("X") ? Color.red : Color.black;
							squares[row][col].setText(text);
							squares[row][col].setBackground(color);
							squares[row][col].setForeground(colorText);
							System.out.println("Người chơi đánh: " + squares[row][col].getText());
							// map.put(squares[row][col], true);
						} else {
							playerPlay = temp;
							return;
						}
					}
				});
			}
		}
		getContentPane().setLayout(null);

		getContentPane().add(CaroBoard);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CaroBoard();
			}
		});
	}
}