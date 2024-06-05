package View;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Entry.Entry;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.SpringLayout;
import java.awt.SystemColor;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Image BackgroundLogin = new ImageIcon(Login.class.getResource("/img/bg_login.jpg")).getImage().getScaledInstance(1000, 800,Image.SCALE_SMOOTH);
	private JTextField txtUsername;
	private JTextField txtPassword;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0,0,0,80));
		panel.setBounds(178, 241, 630, 268);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel login_layout = new JPanel();
		login_layout.setBounds(236, 0, 393, 268);
		panel.add(login_layout);
		login_layout.setForeground(Color.WHITE);
		login_layout.setBackground(new Color(0, 0, 0,80));
		login_layout.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, new Color(192, 192, 192), new Color(0, 0, 0)));
		SpringLayout sl_login_layout = new SpringLayout();
		login_layout.setLayout(sl_login_layout);
		
		JLabel lblNewLabel = new JLabel("LOGIN");
		sl_login_layout.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, login_layout);
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.EAST, lblNewLabel, 385, SpringLayout.WEST, login_layout);
		lblNewLabel.setForeground(Color.PINK);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		login_layout.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		sl_login_layout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -9, SpringLayout.NORTH, lblNewLabel_1);
		sl_login_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 72, SpringLayout.NORTH, login_layout);
		lblNewLabel_1.setForeground(Color.PINK);
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1, 12, SpringLayout.WEST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.EAST, lblNewLabel_1, -294, SpringLayout.EAST, login_layout);
		lblNewLabel_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1);
		
		txtUsername = new JTextField();
		sl_login_layout.putConstraint(SpringLayout.NORTH, txtUsername, 13, SpringLayout.SOUTH, lblNewLabel);
		txtUsername.setForeground(Color.WHITE);
		txtUsername.setBackground(Color.BLACK);
		sl_login_layout.putConstraint(SpringLayout.EAST, txtUsername, -24, SpringLayout.EAST, login_layout);
		login_layout.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password:");
		sl_login_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_1, 12, SpringLayout.WEST, login_layout);
		lblNewLabel_1_1.setForeground(Color.PINK);
		sl_login_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1_1, 22, SpringLayout.SOUTH, lblNewLabel_1);
		lblNewLabel_1_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_1);
		
		txtPassword = new JTextField();
		sl_login_layout.putConstraint(SpringLayout.NORTH, txtPassword, 23, SpringLayout.SOUTH, txtUsername);
		sl_login_layout.putConstraint(SpringLayout.WEST, txtPassword, 116, SpringLayout.WEST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.EAST, txtPassword, -24, SpringLayout.EAST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.WEST, txtUsername, 0, SpringLayout.WEST, txtPassword);
		txtPassword.setBackground(Color.BLACK);
		sl_login_layout.putConstraint(SpringLayout.EAST, lblNewLabel_1_1, -25, SpringLayout.WEST, txtPassword);
		txtPassword.setColumns(10);
		login_layout.add(txtPassword);
		
		JButton btnLogin = new JButton("LOGIN");
		sl_login_layout.putConstraint(SpringLayout.NORTH, btnLogin, 161, SpringLayout.NORTH, login_layout);
		sl_login_layout.putConstraint(SpringLayout.WEST, btnLogin, 20, SpringLayout.WEST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.EAST, btnLogin, -24, SpringLayout.EAST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.SOUTH, txtPassword, -22, SpringLayout.NORTH, btnLogin);
		btnLogin.setBorderPainted(false);
		btnLogin.setBackground(Color.PINK);
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setFont(new Font("Monospaced", Font.PLAIN, 20));
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String email = txtUsername.getText();
				String password = txtPassword.getText();
				if (!email.equals("") && !password.equals("")) {
					Entry.socketHandler.sendLoginInformation(email, password);					
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Không để trống tên đăng nhập hoặc mật khẩu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		login_layout.add(btnLogin);
		
		JButton btnRegister = new JButton("REGISTER");
		sl_login_layout.putConstraint(SpringLayout.WEST, btnRegister, 20, SpringLayout.WEST, login_layout);
		sl_login_layout.putConstraint(SpringLayout.EAST, btnRegister, -24, SpringLayout.EAST, login_layout);
		btnRegister.setBackground(Color.PINK);
		btnRegister.setBorderPainted(false);
		btnRegister.setForeground(Color.WHITE);
		sl_login_layout.putConstraint(SpringLayout.NORTH, btnRegister, 207, SpringLayout.NORTH, login_layout);
		sl_login_layout.putConstraint(SpringLayout.SOUTH, btnLogin, -15, SpringLayout.NORTH, btnRegister);
		btnRegister.setFont(new Font("Monospaced", Font.PLAIN, 20));
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Signup rs = new Signup();
				dispose();
				rs.setVisible(true);
			}
		});
		login_layout.add(btnRegister);
		
		JLabel lblNewLabel_2 = new JLabel("CARO GAME");
		lblNewLabel_2.setBounds(0, 0, 238, 268);
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
