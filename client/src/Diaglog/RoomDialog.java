package Diaglog;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import Entry.Entry;

public class RoomDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private String roomPassword = "";
	JLabel passwordLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RoomDialog dialog = new RoomDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RoomDialog() {
		
		setUndecorated(true);
		setResizable(false);
		setBounds(100, 100, 338, 206);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton cancelButton = new JButton("THOÁT");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Entry.socketHandler.sendCancelRoomSignal();
					dispose();
				}
			});
			cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
			cancelButton.setBounds(90, 136, 163, 45);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}

		passwordLabel = new JLabel("...");
		passwordLabel.setFont(new Font("Teko SemiBold", Font.BOLD, 44));
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		passwordLabel.setBounds(68, 72, 208, 45);
		contentPanel.add(passwordLabel);

		JLabel lblNewLabel_1 = new JLabel("PHÒNG");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(86, 24, 167, 24);
		contentPanel.add(lblNewLabel_1);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
	
	public void setPassword(String password) {
		passwordLabel.setText(password);
	}
}
