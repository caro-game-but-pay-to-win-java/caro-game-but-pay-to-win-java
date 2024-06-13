package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;

public class ProfileFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfileFrame frame = new ProfileFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProfileFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 562, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(29, 30, 96, 87);
		contentPane.add(panel);
		panel.setLayout(new CardLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel, "name_2588297906200");
		
		textField = new JTextField();
		textField.setBounds(237, 183, 106, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(249, 39, 86, 20);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(249, 70, 86, 20);
		contentPane.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(249, 101, 86, 20);
		contentPane.add(textField_3);
		
		JLabel lblNewLabel_1 = new JLabel("Full name");
		lblNewLabel_1.setBounds(175, 42, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Gender");
		lblNewLabel_1_1.setBounds(175, 73, 46, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("Dob");
		lblNewLabel_2.setBounds(175, 104, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Bio");
		lblNewLabel_3.setBounds(175, 136, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(249, 132, 86, 20);
		contentPane.add(textField_4);
		
		JLabel lblNewLabel_8_4 = new JLabel("win streaks count");
		lblNewLabel_8_4.setBounds(58, 375, 46, 14);
		contentPane.add(lblNewLabel_8_4);
		
		JLabel lblWinStreak = new JLabel("New label");
		lblWinStreak.setBounds(147, 375, 46, 14);
		contentPane.add(lblWinStreak);
		
		JLabel lblNewLabel_8_2 = new JLabel("totals matches");
		lblNewLabel_8_2.setBounds(262, 375, 75, 14);
		contentPane.add(lblNewLabel_8_2);
		
		JLabel lblTotalMatch = new JLabel("New label");
		lblTotalMatch.setBounds(364, 375, 46, 14);
		contentPane.add(lblTotalMatch);
		
		JLabel lblNewLabel_8_1 = new JLabel("rank id");
		lblNewLabel_8_1.setBounds(274, 332, 46, 14);
		contentPane.add(lblNewLabel_8_1);
		
		JLabel lblRankid = new JLabel("New label");
		lblRankid.setBounds(364, 329, 46, 14);
		contentPane.add(lblRankid);
		
		JLabel lblNewLabel_8 = new JLabel("join date");
		lblNewLabel_8.setBounds(274, 304, 46, 14);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblJoinDate = new JLabel("New label");
		lblJoinDate.setBounds(364, 304, 46, 14);
		contentPane.add(lblJoinDate);
		
		JLabel lblNewLabel_7 = new JLabel("elo");
		lblNewLabel_7.setBounds(274, 279, 46, 14);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblElo = new JLabel("New label");
		lblElo.setBounds(364, 279, 46, 14);
		contentPane.add(lblElo);
		
		JLabel lblNewLabel_6 = new JLabel("lost matches");
		lblNewLabel_6.setBounds(274, 246, 46, 14);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblLostMatch = new JLabel("New label");
		lblLostMatch.setBounds(364, 246, 46, 14);
		contentPane.add(lblLostMatch);
		
		JLabel lblNewLabel_5 = new JLabel("win matches");
		lblNewLabel_5.setBounds(274, 215, 46, 14);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblWinMatch = new JLabel("New label");
		lblWinMatch.setBounds(364, 215, 46, 14);
		contentPane.add(lblWinMatch);
	}
}
