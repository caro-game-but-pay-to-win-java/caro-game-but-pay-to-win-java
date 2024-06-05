package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Image BackgroundLogin = new ImageIcon(Login.class.getResource("/img/bg_login.jpg")).getImage().getScaledInstance(1000, 800,Image.SCALE_SMOOTH);
	private JTextField txtUsername;
	private JTextField txtPassword;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Signup frame = new Signup();
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
	public Signup() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Signup.class.getResource("/img/logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0,0,0,80));
		panel.setBounds(118, 241, 717, 343);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel login_layout = new JPanel();
		login_layout.setBounds(236, 0, 481, 346);
		panel.add(login_layout);
		login_layout.setForeground(Color.WHITE);
		login_layout.setBackground(new Color(0, 0, 0,80));
		login_layout.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, new Color(192, 192, 192), new Color(0, 0, 0)));
		SpringLayout sl_login_layout = new SpringLayout();
		login_layout.setLayout(sl_login_layout);
		
		JLabel lblNewLabel = new JLabel("REGISTER");
		sl_login_layout.putConstraint(SpringLayout.NORTH, lblNewLabel, 5, SpringLayout.NORTH, login_layout);
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel, 56, SpringLayout.WEST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -276, SpringLayout.SOUTH, login_layout);
		sl_login_layout.putConstraint(SpringLayout.EAST, lblNewLabel, -36, SpringLayout.EAST, login_layout);
		lblNewLabel.setForeground(Color.PINK);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		login_layout.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		sl_login_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 6, SpringLayout.SOUTH, lblNewLabel);
		sl_login_layout.putConstraint(SpringLayout.EAST, lblNewLabel_1, -344, SpringLayout.EAST, login_layout);
		lblNewLabel_1.setForeground(Color.PINK);
		lblNewLabel_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1);
		
		txtUsername = new JTextField();
		sl_login_layout.putConstraint(SpringLayout.NORTH, txtUsername, 6, SpringLayout.SOUTH, lblNewLabel);
		sl_login_layout.putConstraint(SpringLayout.WEST, txtUsername, 36, SpringLayout.EAST, lblNewLabel_1);
		txtUsername.setForeground(Color.WHITE);
		txtUsername.setBackground(Color.BLACK);
		login_layout.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password:");
		sl_login_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1_1, 174, SpringLayout.NORTH, login_layout);
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_1, 22, SpringLayout.WEST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 0, SpringLayout.WEST, lblNewLabel_1_1);
		lblNewLabel_1_1.setForeground(Color.PINK);
		lblNewLabel_1_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_1);
		
		txtPassword = new JTextField();
		sl_login_layout.putConstraint(SpringLayout.EAST, txtUsername, 0, SpringLayout.EAST, txtPassword);
		txtPassword.setBackground(Color.BLACK);
		txtPassword.setColumns(10);
		login_layout.add(txtPassword);
		
		JButton btnRegister = new JButton("REGISTER");
		sl_login_layout.putConstraint(SpringLayout.EAST, txtPassword, 0, SpringLayout.EAST, btnRegister);
		sl_login_layout.putConstraint(SpringLayout.WEST, btnRegister, 20, SpringLayout.WEST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.EAST, btnRegister, -24, SpringLayout.EAST, login_layout);
		btnRegister.setBorderPainted(false);
		btnRegister.setBackground(Color.PINK);
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setFont(new Font("Monospaced", Font.PLAIN, 20));
		login_layout.add(btnRegister);
		
		JButton btnBackLogin = new JButton("Back to Login");
		sl_login_layout.putConstraint(SpringLayout.SOUTH, btnRegister, -17, SpringLayout.NORTH, btnBackLogin);
		sl_login_layout.putConstraint(SpringLayout.NORTH, btnBackLogin, 287, SpringLayout.NORTH, login_layout);
		sl_login_layout.putConstraint(SpringLayout.WEST, btnBackLogin, 20, SpringLayout.WEST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.EAST, btnBackLogin, -24, SpringLayout.EAST, login_layout);
		btnBackLogin.setBackground(Color.PINK);
		btnBackLogin.setBorderPainted(false);
		btnBackLogin.setForeground(Color.WHITE);
		btnBackLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				Login login = new Login();
				login.setVisible(true);
			}
		});
		btnBackLogin.setFont(new Font("Monospaced", Font.PLAIN, 20));
		
		login_layout.add(btnBackLogin);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Retype Password:");
		sl_login_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1_1_1, 196, SpringLayout.NORTH, login_layout);
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_1_1, 22, SpringLayout.WEST, login_layout);
		lblNewLabel_1_1_1.setForeground(Color.PINK);
		lblNewLabel_1_1_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("FullName: ");
		sl_login_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1_2, 5, SpringLayout.SOUTH, lblNewLabel_1);
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_2, 22, SpringLayout.WEST, login_layout);
		lblNewLabel_1_2.setForeground(Color.PINK);
		lblNewLabel_1_2.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_2);
		
		textField = new JTextField();
		sl_login_layout.putConstraint(SpringLayout.NORTH, textField, 2, SpringLayout.NORTH, lblNewLabel_1_2);
		sl_login_layout.putConstraint(SpringLayout.WEST, textField, 56, SpringLayout.EAST, lblNewLabel_1_2);
		sl_login_layout.putConstraint(SpringLayout.EAST, textField, 0, SpringLayout.EAST, txtPassword);
		textField.setForeground(Color.WHITE);
		textField.setColumns(10);
		textField.setBackground(Color.BLACK);
		login_layout.add(textField);
		
		textField_1 = new JTextField();
		sl_login_layout.putConstraint(SpringLayout.SOUTH, txtPassword, -8, SpringLayout.NORTH, textField_1);
		sl_login_layout.putConstraint(SpringLayout.EAST, lblNewLabel_1_1, -48, SpringLayout.WEST, textField_1);
		sl_login_layout.putConstraint(SpringLayout.EAST, textField_1, 0, SpringLayout.EAST, btnRegister);
		sl_login_layout.putConstraint(SpringLayout.NORTH, textField_1, 2, SpringLayout.NORTH, lblNewLabel_1_1);
		textField_1.setColumns(10);
		textField_1.setBackground(Color.BLACK);
		login_layout.add(textField_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Gender:");
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_2_1, 0, SpringLayout.WEST, lblNewLabel_1_1);
		lblNewLabel_1_2_1.setForeground(Color.PINK);
		lblNewLabel_1_2_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("Email: ");
		sl_login_layout.putConstraint(SpringLayout.WEST, txtPassword, 85, SpringLayout.EAST, lblNewLabel_1_2_2);
		sl_login_layout.putConstraint(SpringLayout.SOUTH, lblNewLabel_1_2_1, -6, SpringLayout.NORTH, lblNewLabel_1_2_2);
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_2_2, 0, SpringLayout.WEST, lblNewLabel_1_1);
		sl_login_layout.putConstraint(SpringLayout.SOUTH, lblNewLabel_1_2_2, -6, SpringLayout.NORTH, lblNewLabel_1_1);
		lblNewLabel_1_2_2.setForeground(Color.PINK);
		lblNewLabel_1_2_2.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_2_2);
		
		textField_3 = new JTextField();
		sl_login_layout.putConstraint(SpringLayout.WEST, textField_1, 0, SpringLayout.WEST, textField_3);
		sl_login_layout.putConstraint(SpringLayout.NORTH, btnRegister, 10, SpringLayout.SOUTH, textField_3);
		sl_login_layout.putConstraint(SpringLayout.EAST, textField_3, -26, SpringLayout.EAST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.NORTH, textField_3, 2, SpringLayout.NORTH, lblNewLabel_1_1_1);
		sl_login_layout.putConstraint(SpringLayout.WEST, textField_3, 6, SpringLayout.EAST, lblNewLabel_1_1_1);
		textField_3.setColumns(10);
		textField_3.setBackground(Color.BLACK);
		login_layout.add(textField_3);
		
		JComboBox comboBox = new JComboBox();
		sl_login_layout.putConstraint(SpringLayout.NORTH, comboBox, 4, SpringLayout.SOUTH, textField);
		sl_login_layout.putConstraint(SpringLayout.WEST, comboBox, 85, SpringLayout.EAST, lblNewLabel_1_2_1);
		sl_login_layout.putConstraint(SpringLayout.EAST, comboBox, -257, SpringLayout.EAST, login_layout);
		login_layout.add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("CARO GAME");
		lblNewLabel_2.setBounds(0, 0, 238, 346);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(Color.PINK);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
		
		JLabel background = new JLabel("background");
		background.setHorizontalAlignment(SwingConstants.LEFT);
		background.setBounds(0, 0, 1000, 800);
		background.setIcon(new ImageIcon(BackgroundLogin));
		contentPane.add(background);
	
	}
}
