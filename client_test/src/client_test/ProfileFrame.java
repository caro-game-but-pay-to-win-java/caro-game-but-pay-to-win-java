package client_test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProfileFrame extends JFrame {

	 private static final long serialVersionUID = 1L;
	    private JPanel contentPane;
	    private JTextField txtName;
	    private JTextField txGender;
	    private JTextField txDob;
	    private JTextField txBio;
	    private JTextField txImg;
	    private JLabel lblWinMatch;
	    private JLabel lblLostMatch;
	    private JLabel lblElo;
	    private JLabel lblJoinDate;
	    private JLabel lblRankid;
	    private JLabel lblTotalMatch;
	    private JLabel lblWinStreak;
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
		setBounds(100, 100, 844, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Full name");
		lblNewLabel.setBounds(42, 33, 46, 14);
		contentPane.add(lblNewLabel);

		txtName = new JTextField();
		txtName.setBounds(116, 30, 86, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Gender");
		lblNewLabel_1.setBounds(42, 58, 46, 14);
		contentPane.add(lblNewLabel_1);

		txGender = new JTextField();
		txGender.setBounds(116, 61, 86, 20);
		contentPane.add(txGender);
		txGender.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Dob");
		lblNewLabel_2.setBounds(42, 97, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Bio");
		lblNewLabel_3.setBounds(42, 122, 46, 14);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("img_path");
		lblNewLabel_4.setBounds(42, 147, 46, 14);
		contentPane.add(lblNewLabel_4);

		txDob = new JTextField();
		txDob.setBounds(116, 92, 86, 20);
		contentPane.add(txDob);
		txDob.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("win matches");
		lblNewLabel_5.setBounds(258, 33, 46, 14);
		contentPane.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("lost matches");
		lblNewLabel_6.setBounds(258, 64, 46, 14);
		contentPane.add(lblNewLabel_6);

		txBio = new JTextField();
		txBio.setBounds(116, 119, 86, 20);
		contentPane.add(txBio);
		txBio.setColumns(10);

		txImg = new JTextField();
		txImg.setBounds(116, 144, 86, 20);
		contentPane.add(txImg);
		txImg.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("elo");
		lblNewLabel_7.setBounds(258, 97, 46, 14);
		contentPane.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("join date");
		lblNewLabel_8.setBounds(258, 122, 46, 14);
		contentPane.add(lblNewLabel_8);

		JLabel lblNewLabel_8_1 = new JLabel("rank id");
		lblNewLabel_8_1.setBounds(258, 150, 46, 14);
		contentPane.add(lblNewLabel_8_1);

		JLabel lblNewLabel_8_2 = new JLabel("totals matches");
		lblNewLabel_8_2.setBounds(246, 193, 75, 14);
		contentPane.add(lblNewLabel_8_2);

		JLabel lblNewLabel_8_4 = new JLabel("win streaks count");
		lblNewLabel_8_4.setBounds(42, 193, 46, 14);
		contentPane.add(lblNewLabel_8_4);

		JButton btnCapNhat = new JButton("cap nhap");
		btnCapNhat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				runClient.socketHandler.sendEditProfile(tx);
			}
		});
		btnCapNhat.setBounds(38, 284, 150, 34);
		contentPane.add(btnCapNhat);

		 lblWinMatch = new JLabel("New label");
		lblWinMatch.setBounds(348, 33, 46, 14);
		contentPane.add(lblWinMatch);

		 lblLostMatch = new JLabel("New label");
		lblLostMatch.setBounds(348, 64, 46, 14);
		contentPane.add(lblLostMatch);

		 lblElo = new JLabel("New label");
		lblElo.setBounds(348, 97, 46, 14);
		contentPane.add(lblElo);

		 lblJoinDate = new JLabel("New label");
		lblJoinDate.setBounds(348, 122, 46, 14);
		contentPane.add(lblJoinDate);

		 lblRankid = new JLabel("New label");
		lblRankid.setBounds(348, 147, 46, 14);
		contentPane.add(lblRankid);

		 lblTotalMatch = new JLabel("New label");
		lblTotalMatch.setBounds(348, 193, 46, 14);
		contentPane.add(lblTotalMatch);

		 lblWinStreak = new JLabel("New label");
		lblWinStreak.setBounds(131, 193, 46, 14);
		contentPane.add(lblWinStreak);
	}

	   public void setProfile(String data) {
	        String[] dataParts = data.split("/");
	        String fullName = dataParts[2];
	        String gender = dataParts[3];
	        String dob = dataParts[6];
	        String biography = dataParts[13];
	        String playerImgPath = dataParts[12];
	        String winMatches = dataParts[9];
	        String lostMatches = dataParts[10];
	        String eloRatingPoints = dataParts[11];
	        String joinedDate = dataParts[14];
	        String rankId = dataParts[15];
	        String totalMatches = dataParts[7];
	        String winStreakCounts = dataParts[8];

	        txtName.setText(fullName);
	        txGender.setText(gender);
	        txDob.setText(dob);
	        txBio.setText(biography);
	        txImg.setText(playerImgPath);
	        lblWinMatch.setText(winMatches);
	        lblLostMatch.setText(lostMatches);
	        lblElo.setText(eloRatingPoints);
	        lblJoinDate.setText(joinedDate);
	        lblRankid.setText(rankId);
	        lblTotalMatch.setText(totalMatches);
	        lblWinStreak.setText(winStreakCounts);
	    }
}
