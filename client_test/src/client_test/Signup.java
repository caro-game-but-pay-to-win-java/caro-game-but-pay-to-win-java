package client_test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

import CustomComponent.DateTextField;

public class Signup extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JTextField txtFullName;
	private JTextField txtPassword;
	private JTextField txtRePassword;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	private JComboBox comboBox;

		
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
		//setIconImage(Toolkit.getDefaultToolkit().getImage(Signup.class.getResource("/img/logo.png")));
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
		

		
		
		
		JPanel regis_layout = new JPanel();
		regis_layout.setBounds(236, 0, 481, 346);
		panel.add(regis_layout);
		regis_layout.setForeground(Color.WHITE);
		regis_layout.setBackground(new Color(0, 0, 0,80));
		regis_layout.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, new Color(192, 192, 192), new Color(0, 0, 0)));
		SpringLayout sl_regis_layout = new SpringLayout();
		regis_layout.setLayout(sl_regis_layout);
		
		DateTextField txtDate = new DateTextField();
		sl_regis_layout.putConstraint(SpringLayout.EAST, txtDate, -24, SpringLayout.EAST, regis_layout);
		txtDate.setBounds(29, 20, 20, 20);
		regis_layout.add(txtDate);
		JLabel lblNewLabel = new JLabel("REGISTER");
		sl_regis_layout.putConstraint(SpringLayout.NORTH, lblNewLabel, 5, SpringLayout.NORTH, regis_layout);
		sl_regis_layout.putConstraint(SpringLayout.WEST, lblNewLabel, 56, SpringLayout.WEST, regis_layout);
		sl_regis_layout.putConstraint(SpringLayout.SOUTH, lblNewLabel, -276, SpringLayout.SOUTH, regis_layout);
		sl_regis_layout.putConstraint(SpringLayout.EAST, lblNewLabel, -36, SpringLayout.EAST, regis_layout);
		lblNewLabel.setForeground(Color.PINK);
		lblNewLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		regis_layout.add(lblNewLabel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password:");
		sl_regis_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1_1, 174, SpringLayout.NORTH, regis_layout);
		sl_regis_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_1, 22, SpringLayout.WEST, regis_layout);
		lblNewLabel_1_1.setForeground(Color.PINK);
		lblNewLabel_1_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		regis_layout.add(lblNewLabel_1_1);
		
		txtEmail = new JTextField();
		sl_regis_layout.putConstraint(SpringLayout.SOUTH, txtDate, -6, SpringLayout.NORTH, txtEmail);
		txtEmail.setBackground(Color.BLACK);
		txtEmail.setColumns(10);
		txtEmail.setForeground(Color.WHITE);
		regis_layout.add(txtEmail);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Gender:");
		sl_regis_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_2_1, 0, SpringLayout.WEST, lblNewLabel_1_1);
		lblNewLabel_1_2_1.setForeground(Color.PINK);
		lblNewLabel_1_2_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		regis_layout.add(lblNewLabel_1_2_1);
		
	  
		
		JButton btnRegister = new JButton("REGISTER");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtFullName.getText().trim();
				String password = txtPassword.getText().trim();
				String repassword = txtRePassword.getText().trim();
				String email = txtEmail.getText().trim();
				String date = txtDate.getText().trim();
				String gender =  comboBox.getSelectedItem().toString().trim();
				if(!name.isEmpty() && !password.isEmpty() && !repassword.isEmpty() && !email.isEmpty() && !date.isEmpty())
				{
					if(!validateEmail(email))
					{
						JOptionPane.showMessageDialog(getParent(), "Định dạng mail không hợp lệ");return;
					}
					if(!password.equals(repassword))
					{
						JOptionPane.showMessageDialog(getParent(), "Mật khẩu xác thực không trùng khớp");return;
					}
					else {
						runClient.socketHandler.sendSigupInformation(name,email,password,date,gender);					
					}
				
				}
				else {
					JOptionPane.showMessageDialog(getParent(), "KHÔNG ĐƯỢC BỎ TRỐNG CÁC TRƯỜNG");
				}
			}
		});
		sl_regis_layout.putConstraint(SpringLayout.EAST, txtEmail, 0, SpringLayout.EAST, btnRegister);
		sl_regis_layout.putConstraint(SpringLayout.WEST, btnRegister, 20, SpringLayout.WEST, regis_layout);
		sl_regis_layout.putConstraint(SpringLayout.EAST, btnRegister, -24, SpringLayout.EAST, regis_layout);
		btnRegister.setBorderPainted(false);
		btnRegister.setBackground(Color.PINK);
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setFont(new Font("Monospaced", Font.PLAIN, 20));
		regis_layout.add(btnRegister);
		
		JButton btnBackLogin = new JButton("Back to Login");
		sl_regis_layout.putConstraint(SpringLayout.SOUTH, btnRegister, -17, SpringLayout.NORTH, btnBackLogin);
		sl_regis_layout.putConstraint(SpringLayout.NORTH, btnBackLogin, 287, SpringLayout.NORTH, regis_layout);
		sl_regis_layout.putConstraint(SpringLayout.WEST, btnBackLogin, 20, SpringLayout.WEST, regis_layout);
		sl_regis_layout.putConstraint(SpringLayout.EAST, btnBackLogin, -24, SpringLayout.EAST, regis_layout);
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
		
		regis_layout.add(btnBackLogin);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Retype Password:");
		sl_regis_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1_1_1, 196, SpringLayout.NORTH, regis_layout);
		sl_regis_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_1_1, 22, SpringLayout.WEST, regis_layout);
		lblNewLabel_1_1_1.setForeground(Color.PINK);
		lblNewLabel_1_1_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		regis_layout.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("FullName: ");
		sl_regis_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1_2, 31, SpringLayout.SOUTH, lblNewLabel);
		sl_regis_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_2, 22, SpringLayout.WEST, regis_layout);
		lblNewLabel_1_2.setForeground(Color.PINK);
		lblNewLabel_1_2.setFont(new Font("Monospaced", Font.PLAIN, 14));
		regis_layout.add(lblNewLabel_1_2);
		
		txtFullName = new JTextField();
		sl_regis_layout.putConstraint(SpringLayout.NORTH, txtDate, 6, SpringLayout.SOUTH, txtFullName);
		sl_regis_layout.putConstraint(SpringLayout.NORTH, txtFullName, 2, SpringLayout.NORTH, lblNewLabel_1_2);
		sl_regis_layout.putConstraint(SpringLayout.WEST, txtFullName, 56, SpringLayout.EAST, lblNewLabel_1_2);
		sl_regis_layout.putConstraint(SpringLayout.EAST, txtFullName, 0, SpringLayout.EAST, txtEmail);
		txtFullName.setForeground(Color.WHITE);
		txtFullName.setColumns(10);
		txtFullName.setBackground(Color.BLACK);
		regis_layout.add(txtFullName);

		  comboBox = new JComboBox();
			sl_regis_layout.putConstraint(SpringLayout.NORTH, comboBox, 4, SpringLayout.SOUTH, txtFullName);
			sl_regis_layout.putConstraint(SpringLayout.WEST, comboBox, 85, SpringLayout.EAST, lblNewLabel_1_2_1);
			sl_regis_layout.putConstraint(SpringLayout.EAST, comboBox, -257, SpringLayout.EAST, regis_layout);
			comboBox.addItem("Nam");
			comboBox.addItem("Nữ");
			regis_layout.add(comboBox);
		txtPassword = new JPasswordField();
		sl_regis_layout.putConstraint(SpringLayout.SOUTH, txtEmail, -8, SpringLayout.NORTH, txtPassword);
		sl_regis_layout.putConstraint(SpringLayout.EAST, lblNewLabel_1_1, -48, SpringLayout.WEST, txtPassword);
		sl_regis_layout.putConstraint(SpringLayout.EAST, txtPassword, 0, SpringLayout.EAST, btnRegister);
		sl_regis_layout.putConstraint(SpringLayout.NORTH, txtPassword, 2, SpringLayout.NORTH, lblNewLabel_1_1);
		txtPassword.setColumns(10);
		txtPassword.setBackground(Color.BLACK);
		txtPassword.setForeground(Color.WHITE);
		regis_layout.add(txtPassword);
		

		
		JLabel lblNewLabel_1_2_2 = new JLabel("Email: ");
		sl_regis_layout.putConstraint(SpringLayout.WEST, txtEmail, 85, SpringLayout.EAST, lblNewLabel_1_2_2);
		sl_regis_layout.putConstraint(SpringLayout.SOUTH, lblNewLabel_1_2_1, -6, SpringLayout.NORTH, lblNewLabel_1_2_2);
		sl_regis_layout.putConstraint(SpringLayout.WEST, lblNewLabel_1_2_2, 0, SpringLayout.WEST, lblNewLabel_1_1);
		sl_regis_layout.putConstraint(SpringLayout.SOUTH, lblNewLabel_1_2_2, -6, SpringLayout.NORTH, lblNewLabel_1_1);
		lblNewLabel_1_2_2.setForeground(Color.PINK);
		lblNewLabel_1_2_2.setFont(new Font("Monospaced", Font.PLAIN, 14));
		regis_layout.add(lblNewLabel_1_2_2);
		
		txtRePassword = new JPasswordField();
		sl_regis_layout.putConstraint(SpringLayout.WEST, txtPassword, 0, SpringLayout.WEST, txtRePassword);
		sl_regis_layout.putConstraint(SpringLayout.NORTH, btnRegister, 10, SpringLayout.SOUTH, txtRePassword);
		sl_regis_layout.putConstraint(SpringLayout.EAST, txtRePassword, -26, SpringLayout.EAST, regis_layout);
		sl_regis_layout.putConstraint(SpringLayout.NORTH, txtRePassword, 2, SpringLayout.NORTH, lblNewLabel_1_1_1);
		sl_regis_layout.putConstraint(SpringLayout.WEST, txtRePassword, 6, SpringLayout.EAST, lblNewLabel_1_1_1);
		txtRePassword.setColumns(10);
		txtRePassword.setBackground(Color.BLACK);
		txtRePassword.setForeground(Color.WHITE);
		
		
		regis_layout.add(txtRePassword);
		
		
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("Date of Birth");
		sl_regis_layout.putConstraint(SpringLayout.NORTH, lblNewLabel_1_2_1_1, 5, SpringLayout.SOUTH, txtFullName);
		sl_regis_layout.putConstraint(SpringLayout.EAST, lblNewLabel_1_2_1_1, -10, SpringLayout.WEST, txtDate);
		lblNewLabel_1_2_1_1.setForeground(Color.PINK);
		lblNewLabel_1_2_1_1.setFont(new Font("Monospaced", Font.PLAIN, 14));
		regis_layout.add(lblNewLabel_1_2_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("CARO GAME");
		lblNewLabel_2.setBounds(0, 0, 238, 346);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(Color.PINK);
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));
	
	}
	
	public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
}
}
