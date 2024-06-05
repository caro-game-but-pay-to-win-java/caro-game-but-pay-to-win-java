package client_test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Socket.SocketHandler;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextField txtField_email;
	private JTextField txtField_password;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 588, 341);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtField_email = new JTextField();
		txtField_email.setToolTipText("");
		txtField_email.setBounds(76, 72, 446, 34);
		contentPane.add(txtField_email);
		txtField_email.setColumns(10);
		
		txtField_password = new JTextField();
		txtField_password.setColumns(10);
		txtField_password.setBounds(76, 136, 446, 34);
		contentPane.add(txtField_password);
		
		JButton btn_Login = new JButton("ĐĂNG NHẬP");
		btn_Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = txtField_email.getText();
				String password = txtField_password.getText();
				if (!email.equals("") && !password.equals("")) {
					runClient.socketHandler.sendLoginInformation(email, password);					
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Không để trống tên đăng nhập hoặc mật khẩu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btn_Login.setBounds(81, 221, 148, 45);
		contentPane.add(btn_Login);
		
		JButton btnSignup = new JButton("Đăng ký");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Signup signup = new Signup();
				dispose();
				signup.setVisible(true);
			}
		});
		btnSignup.setBounds(277, 219, 117, 47);
		contentPane.add(btnSignup);
	}
}
