package View;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import CustomComponents.DateTextField;

import javax.swing.JComboBox;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Entry.Entry;

public class SignUp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Image BackgroundLogin = new ImageIcon(Login.class.getResource("/img/bg_login.jpg")).getImage()
			.getScaledInstance(1000, 800, Image.SCALE_SMOOTH);
	private JTextField txtEmail;
	private JTextField txtFullName;
	private JTextField txtPassword;
	private JTextField txtRePassword;
	private DateTextField txtDate;
	JComboBox comboBox;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignUp.class.getResource("/img/logo.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0, 80));
		panel.setBounds(118, 241, 717, 343);
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel login_layout = new JPanel();
		login_layout.setBounds(236, 0, 481, 346);
		panel.add(login_layout);
		login_layout.setForeground(Color.WHITE);
		login_layout.setBackground(new Color(0, 0, 0, 80));
		login_layout.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, new Color(192, 192, 192),
				new Color(0, 0, 0)));
		login_layout.setLayout(null);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(171, 116, 62, 24);
		comboBox.addItem("Nam");
		comboBox.addItem("Nữ");
		login_layout.add(comboBox);

		JLabel lblNewLabel = new JLabel("REGISTER");
		lblNewLabel.setBounds(58, 7, 385, 61);
		lblNewLabel.setForeground(Color.PINK);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		login_layout.add(lblNewLabel);

		JLabel lblNewLabel_1_1 = new JLabel("Password:");
		lblNewLabel_1_1.setBounds(24, 176, 99, 20);
		lblNewLabel_1_1.setForeground(Color.PINK);
		lblNewLabel_1_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_1);

		txtEmail = new JTextField();
		txtEmail.setBounds(171, 148, 284, 22);
		txtEmail.setBackground(Color.BLACK);
		txtEmail.setColumns(10);
		txtEmail.setForeground(Color.WHITE);
		login_layout.add(txtEmail);

		txtRePassword = new JPasswordField();
		txtRePassword.setBounds(171, 210, 282, 22);
		txtRePassword.setColumns(10);
		txtRePassword.setBackground(Color.BLACK);
		txtRePassword.setForeground(Color.WHITE);
		login_layout.add(txtRePassword);

		JButton btnRegister = new JButton("REGISTER");
		btnRegister.setBounds(24, 245, 433, 40);
		btnRegister.setBorderPainted(false);
		btnRegister.setBackground(Color.PINK);
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setFont(new Font("Monospaced", Font.PLAIN, 20));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = txtFullName.getText().trim();
					String password = txtPassword.getText().trim();
					String repassword = txtRePassword.getText().trim();
					String email = txtEmail.getText().trim();
					String date = txtDate.getText().trim();
					String gender = comboBox.getSelectedItem().toString().trim();
					if (!name.isEmpty() && !password.isEmpty() && !repassword.isEmpty() && !email.isEmpty()
							&& !date.isEmpty()) {
						if (!validateEmail(email)) {
							JOptionPane.showMessageDialog(getParent(), "Định dạng mail không hợp lệ");
							return;
						}
						if (!password.equals(repassword)) {
							JOptionPane.showMessageDialog(getParent(), "Mật khẩu xác thực không trùng khớp");
							return;
						} else {
							Entry.socketHandler.sendSigupInformation(name, email, password, date, gender);
						}

					} else {
						JOptionPane.showMessageDialog(getParent(), "KHÔNG ĐƯỢC BỎ TRỐNG CÁC TRƯỜNG");
					}
				} catch (Exception ex) {
					// TODO: handle exception
				}

			}
		});
		login_layout.add(btnRegister);

		JButton btnBackLogin = new JButton("Back to Login");
		btnBackLogin.setBounds(24, 298, 433, 35);
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
		lblNewLabel_1_1_1.setBounds(24, 209, 141, 20);
		lblNewLabel_1_1_1.setForeground(Color.PINK);
		lblNewLabel_1_1_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("FullName: ");
		lblNewLabel_1_2.setBounds(24, 82, 88, 20);
		lblNewLabel_1_2.setForeground(Color.PINK);
		lblNewLabel_1_2.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_2);

		txtFullName = new JTextField();
		txtFullName.setBounds(168, 81, 287, 22);
		txtFullName.setForeground(Color.WHITE);
		txtFullName.setColumns(10);
		txtFullName.setBackground(Color.BLACK);
		login_layout.add(txtFullName);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(171, 175, 284, 22);
		txtPassword.setColumns(10);
		txtPassword.setBackground(Color.BLACK);
		txtPassword.setForeground(Color.WHITE);
		login_layout.add(txtPassword);

		JLabel lblNewLabel_1_2_1 = new JLabel("Gender:");
		lblNewLabel_1_2_1.setBounds(24, 115, 62, 20);
		lblNewLabel_1_2_1.setForeground(Color.PINK);
		lblNewLabel_1_2_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_2_1);

		JLabel lblNewLabel_1_2_2 = new JLabel("Email: ");
		lblNewLabel_1_2_2.setBounds(24, 150, 62, 20);
		lblNewLabel_1_2_2.setForeground(Color.PINK);
		lblNewLabel_1_2_2.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_2_2);

		JLabel lblNewLabel_1_2_1_1 = new JLabel("DOB");
		lblNewLabel_1_2_1_1.setBounds(330, 116, 37, 24);
		lblNewLabel_1_2_1_1.setForeground(Color.PINK);
		lblNewLabel_1_2_1_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		login_layout.add(lblNewLabel_1_2_1_1);

		txtDate = new DateTextField();
		txtDate.setBounds(367, 116, 88, 19);
		login_layout.add(txtDate);
		txtDate.setColumns(10);

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
		setResizable(false);

	}

	public static boolean validateEmail(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.matches();
	}
}
