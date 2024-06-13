package View;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import Entry.Entry;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridBagConstraints;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfileFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txFullName;
	private JTextField txDob;
	private JTextField txBio;
	private String txPassword;

	private String pathAvatarPlayer;
	private String pathAvatarRank;
	JLabel lblLostMatch;
	JLabel lblWinStreak;
	JLabel lblTotalMatch;
	JLabel lblJoinDate;
	JLabel lblElo;
	JLabel lblWinMatch;
	JLabel lblAvatar;
	JButton btnLuu;
	JButton btnDong;
	JComboBox comboBox;
	private Image img_Avatar;

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
		setBounds(10, 34, 387, 310);
		setUndecorated(true);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(22, 11, 117, 107);
		contentPane.add(panel);
		panel.setLayout(new CardLayout(0, 0));

		lblAvatar = new JLabel();
		lblAvatar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectImage();
			}
		});
		lblAvatar.setVerticalAlignment(JLabel.CENTER);
		lblAvatar.setHorizontalAlignment(JLabel.CENTER);

		panel.add(lblAvatar);

		txFullName = new JTextField();
		txFullName.setColumns(10);
		txFullName.setBounds(205, 11, 144, 20);
		contentPane.add(txFullName);

		txDob = new JTextField();
		txDob.setColumns(10);
		txDob.setBounds(205, 73, 144, 20);
		contentPane.add(txDob);

		JLabel lblNewLabel_1 = new JLabel("Full name");
		lblNewLabel_1.setBounds(149, 14, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Gender");
		lblNewLabel_1_1.setBounds(149, 45, 46, 14);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_2 = new JLabel("Dob");
		lblNewLabel_2.setBounds(149, 76, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Bio");
		lblNewLabel_3.setBounds(149, 104, 46, 14);
		contentPane.add(lblNewLabel_3);

		txBio = new JTextField();
		txBio.setColumns(10);
		txBio.setBounds(205, 104, 144, 20);
		contentPane.add(txBio);

		JLabel lblNewLabel_8_4 = new JLabel("win streaks count");
		lblNewLabel_8_4.setBounds(197, 198, 96, 14);
		contentPane.add(lblNewLabel_8_4);

		lblWinStreak = new JLabel("New label");
		lblWinStreak.setBounds(303, 198, 46, 14);
		contentPane.add(lblWinStreak);

		JLabel lblNewLabel_8_2 = new JLabel("totals matches");
		lblNewLabel_8_2.setBounds(197, 173, 75, 14);
		contentPane.add(lblNewLabel_8_2);

		lblTotalMatch = new JLabel("New label");
		lblTotalMatch.setBounds(303, 173, 46, 14);
		contentPane.add(lblTotalMatch);

		JLabel lblNewLabel_8 = new JLabel("join date");
		lblNewLabel_8.setBounds(22, 154, 56, 14);
		contentPane.add(lblNewLabel_8);

		lblJoinDate = new JLabel("New label");
		lblJoinDate.setBounds(111, 154, 84, 14);
		contentPane.add(lblJoinDate);

		JLabel lblNewLabel_7 = new JLabel("elo");
		lblNewLabel_7.setBounds(32, 129, 46, 14);
		contentPane.add(lblNewLabel_7);

		lblElo = new JLabel("");
		lblElo.setBounds(63, 129, 46, 14);
		contentPane.add(lblElo);

		JLabel lblNewLabel_6 = new JLabel("lost matches");
		lblNewLabel_6.setBounds(22, 198, 75, 14);
		contentPane.add(lblNewLabel_6);

		lblLostMatch = new JLabel("New label");
		lblLostMatch.setBounds(111, 198, 61, 14);
		contentPane.add(lblLostMatch);

		JLabel lblNewLabel_5 = new JLabel("win matches");
		lblNewLabel_5.setBounds(22, 173, 63, 14);
		contentPane.add(lblNewLabel_5);

		lblWinMatch = new JLabel("New label");
		lblWinMatch.setBounds(111, 173, 61, 14);
		contentPane.add(lblWinMatch);

		btnLuu = new JButton("Lưu");
		btnLuu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String[] avatarParts = pathAvatarPlayer.split("\\\\");
			        
			        String avatarPath;
			        if (avatarParts.length > 7) {
			            avatarPath = avatarParts[7];
			        } else {
			            avatarPath = pathAvatarPlayer;
			        }

			        Entry.socketHandler.sendEditProfile(
			            txFullName.getText(), 
			            comboBox.getSelectedItem().toString(), 
			            txDob.getText(),
			            avatarPath,
			            txBio.getText(),
			            txPassword
			        );

			}
		});
		btnLuu.setBounds(28, 253, 91, 23);
		contentPane.add(btnLuu);

		btnDong = new JButton("Đóng");
		btnDong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnDong.setBounds(258, 253, 91, 23);

		contentPane.add(btnDong);

		comboBox = new JComboBox();
		comboBox.addItem("Nam");
		comboBox.addItem("Nữ");
		comboBox.setBounds(205, 40, 144, 22);
		contentPane.add(comboBox);
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
		String totalMatches = dataParts[7];
		String winStreakCounts = dataParts[8];
		txPassword = dataParts[5];
		txFullName.setText(fullName);
		comboBox.setSelectedItem(gender);
		txDob.setText(dob);
		txBio.setText(biography);

		pathAvatarPlayer = "/img/" + playerImgPath;
		java.net.URL imgUrl = ProfileFrame.class.getResource(pathAvatarPlayer);
		if (imgUrl != null) {
			img_Avatar = new ImageIcon(imgUrl).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		} else {
			// Fallback to default image if the specified image is not found
			imgUrl = ProfileFrame.class.getResource("/img/beoj.jpg");
			if (imgUrl != null) {
				img_Avatar = new ImageIcon(imgUrl).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			} else {
				System.err.println("Default image not found.");
			}
		}

		if (img_Avatar != null) {
			lblAvatar.setIcon(new ImageIcon(img_Avatar));
		} else {
			lblAvatar.setIcon(null);
		}

		lblWinMatch.setText(winMatches);
		lblLostMatch.setText(lostMatches);
		lblElo.setText(eloRatingPoints);
		lblJoinDate.setText(joinedDate);
		lblTotalMatch.setText(totalMatches);
		lblWinStreak.setText(winStreakCounts);
	}

	private void selectImage() {
		JFileChooser fileChooser = new JFileChooser(ProfileFrame.class.getResource("/img/").getFile());
		FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		fileChooser.setFileFilter(imageFilter);

		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			java.io.File selectedFile = fileChooser.getSelectedFile();
			String imagePath = selectedFile.getPath();
			ImageIcon icon = new ImageIcon(
					new ImageIcon(imagePath).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
			lblAvatar.setIcon(icon);
			// Update the pathAvatarPlayer if needed
			pathAvatarPlayer = imagePath;
		}
	}
}
