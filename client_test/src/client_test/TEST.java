package client_test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Socket.SocketHandler;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	public static JPanel contentPane;
	private JTextField textField;
	public static JTextPane txtPane;
	public static SocketHandler socketHandler;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		Test.socketHandler = new SocketHandler();
		Test.socketHandler.connect();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 781, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPane = new JTextPane();
		txtPane.setBounds(8, 8, 751, 405);
		contentPane.add(txtPane);
		
		textField = new JTextField();
		textField.setBounds(8, 421, 556, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("SEND");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = textField.getText();
				Test.socketHandler.sendMessage(data);
			}
		});
		btnNewButton.setBounds(577, 421, 182, 34);
		contentPane.add(btnNewButton);
	}
	
	public static void Paint(String data) {
		Test.txtPane.setText(Test.txtPane.getText() + "\n" + data);
	}
}
