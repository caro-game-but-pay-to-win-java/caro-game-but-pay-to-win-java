package client_test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Socket.SocketHandler;
import client_test.Dialog.MatchingDialog;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	public static JPanel contentPane;
	private JTextField textField;
	public static JTextPane txtPane;
	private JButton btn_Profile;
	private JButton btn_Matching;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 781, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtPane = new JTextPane();
		txtPane.setBounds(8, 8, 751, 240);
		contentPane.add(txtPane);
		
		textField = new JTextField();
		textField.setBounds(10, 258, 556, 34);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("SEND");
		
		btnNewButton.setBounds(577, 258, 182, 34);
		contentPane.add(btnNewButton);
		
		btn_Profile = new JButton("PROFILE");
		btn_Profile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				runClient.socketHandler.sendWatchProfile();
			}
		});
		btn_Profile.setBounds(577, 420, 182, 34);
		contentPane.add(btn_Profile);
		
		btn_Matching = new JButton("TÌM TRẬN");
		
		btn_Matching.setBounds(384, 420, 182, 34);
		contentPane.add(btn_Matching);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = textField.getText();
				if (!data.equals("")) {					
					runClient.socketHandler.sendMessage(data);
					textField.setText("");
				}
			}
		});
		btn_Matching.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runClient.socketHandler.sendMatchingSignal();
				runClient.onMatchingClicked();
			}
		});
		btn_Profile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	public static void Paint(String data) {
		Test.txtPane.setText(Test.txtPane.getText() + "\n" + data);
	}
	
	public static void Close() {
		Test.getWindows()[0].setVisible(false);
	}
	
	public static void Show() {
		Test.getWindows()[0].setVisible(true);
	}
}
