package client_test.Dialog;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MatchingDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MatchingDialog dialog = new MatchingDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MatchingDialog() {
		setUndecorated(true);
		setResizable(false);
		setBounds(100, 100, 338, 206);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton cancelButton = new JButton("HỦY TÌM TRẬN");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
			cancelButton.setBounds(90, 136, 163, 45);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
		}
		
		JLabel timeLabel = new JLabel("00:00");
		timeLabel.setFont(new Font("Teko SemiBold", Font.BOLD, 44));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setBounds(68, 72, 208, 45);
		contentPanel.add(timeLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Đang tìm trận");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(86, 24, 167, 24);
		contentPanel.add(lblNewLabel_1);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		timer = new Timer(1000, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
				timeLabel.setText(sdf.format(calendar.getTime()));
				calendar.add(Calendar.SECOND, 1);
			}
		});
		timer.start();
		
	}
}
