package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.border.LineBorder;

import CustomComponents.ButtonBoard;
import CustomComponents.CustomPanel;
import CustomComponents.CustomPanelGradients;
import CustomComponents.CustomTextFiled;
import CustomComponents.RadiusButton;
import Socket.MOVE;
import Entry.Entry;

public class CaroBoard extends JFrame {
	private JPanel CaroBoard;
	private JPanel panel_TimeBorderPlayerP;
	private JPanel panel_TimeBorderPlayerO;
	private ButtonBoard[][] squares;
	private boolean playerPlay = false; // sử dụng để đánh dấu mốc đến lượt người chơi , false là x , true là o
	JLabel lblTimePlay; // Hiển thị thời gian trận đấu
	private CustomTextFiled txChat;
	JLabel lblPAvatar;
	JLabel lblOAvatar;
	JLabel lblResult;
	boolean flagTimeWarning = false; // sử dụng để cảnh báo thời gian
	boolean flagPaint = false;
	private int timeLess; // sử dụng để đếm thời gian đánh của người chơi
	Timer timerLess; // sử dụng để bắt đầu đếm thời gian
	private Image img_setting = new ImageIcon(LobbyFrame.class.getResource("/img/setting_img.png")).getImage()
			.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	private Image img_logout = new ImageIcon(LobbyFrame.class.getResource("/img/logout_img.png")).getImage()
			.getScaledInstance(59, 60, Image.SCALE_SMOOTH);
	private Image img_O = new ImageIcon(LobbyFrame.class.getResource("/img/man_img.png")).getImage()
			.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	private Image img_P = new ImageIcon(LobbyFrame.class.getResource("/img/girl_img.png")).getImage()
			.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	Integer playerMoveMark = null;
	Integer opponentMoveMark = null;
	RadiusButton btnSend;
	Calendar calendar = Calendar.getInstance();
	Calendar matchCalendar = Calendar.getInstance();

	JTextPane chatTextPane = new JTextPane();
	JButton sendBtn = new JButton("SEND");
	JLabel lbl_pName = new JLabel("P_NAME");
	JLabel lbl_pElo = new JLabel("P_ELO_VALUES (RANK_NAME)");

	JLabel lbl_oMark = new JLabel("O_MARK_VALUE");
	JLabel lbl_oElo = new JLabel("O_ELO_VALUES");

	JLabel lbl_oName = new JLabel("O_NAME");
	JLabel lbl_pRTime = new JLabel("P_REMAINING_TIME");
	JLabel lbl_oRTime = new JLabel("O_REMAINING_TIME");
	JLabel lbl_matchTimer = new JLabel("MATCH_TIMER");

	Timer oTimer;
	Timer pTimer;
	Timer mTimer;
	JLabel lbl_pMark_ = new JLabel("Y");

	public CaroBoard() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CaroBoard.class.getResource("/img/logo.png")));

		setTitle("Caro Game");
		setSize(1200, 850);
		JPanel panelContainer = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				initBG(g);
			}
		};
		setResizable(false);
		panelContainer.setBounds(0, 0, 1200, 850);
		getContentPane().add(panelContainer);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		CaroBoard = new JPanel(new GridLayout(19, 19)) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				initBG(g);
			}
		};
		CaroBoard.setBorder(new LineBorder(new Color(0, 0, 0, 40), 2));
		CaroBoard.setBounds(0, 0, 800, 800);

		squares = new ButtonBoard[19][19];

		initBoardCaro();
		panelContainer.setLayout(null);
		// textField.setColumns(10);

		btnSend = new RadiusButton();
		btnSend.setRadius(30);
		btnSend.setText("SEND");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txChat.getText().equals("")) {
					Entry.socketHandler.sendMessageInMatch(txChat.getText());
					txChat.setText("");
				}
			}
		});

		lbl_oMark = new JLabel("X");
		lbl_oMark.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_oMark.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oMark.setBounds(985, 475, 50, 20);
		panelContainer.add(lbl_oMark);
		lbl_pMark_.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lbl_pMark_.setBounds(985, 310, 50, 20);
		panelContainer.add(lbl_pMark_);
		btnSend.setBounds(1100, 740, 80, 34);
		panelContainer.add(btnSend);

		txChat = new CustomTextFiled();
		txChat.setForeground(Color.WHITE);
		txChat.setBounds(825, 740, 265, 34);
		txChat.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txChat.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!txChat.getText().equals("")) {
						Entry.socketHandler.sendMessageInMatch(txChat.getText());
						txChat.setText("");
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		panelContainer.add(txChat);

		lbl_pElo = new JLabel("1,000 point");
		lbl_pElo.setBounds(960, 290, 100, 20);
		lbl_pElo.setHorizontalAlignment(JLabel.CENTER);
		lbl_pElo.setVerticalAlignment(JLabel.CENTER);
		panelContainer.add(lbl_pElo);

		panel_TimeBorderPlayerP = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				borderClock(g, panel_TimeBorderPlayerP);
			}
		};
		panel_TimeBorderPlayerP.setOpaque(false);

		panel_TimeBorderPlayerP.setBounds(915, 241, 165, 100);
		panelContainer.add(panel_TimeBorderPlayerP);

		lbl_oName = new JLabel();
		lbl_oName.setBounds(935, 432, 150, 28);
		lbl_oName.setText("MASTER JAVA");
		lbl_oName.setForeground(Color.WHITE);

		lbl_oName.setFont(new Font("Tahoma", Font.BOLD, 10));
		lbl_oName.setHorizontalAlignment(JLabel.CENTER);
		lbl_oName.setVerticalAlignment(JLabel.CENTER);
		panelContainer.add(lbl_oName);

		lbl_oElo = new JLabel("1,000 point");
		lbl_oElo.setBounds(960, 450, 100, 20);
		lbl_oElo.setHorizontalAlignment(JLabel.CENTER);
		lbl_oElo.setVerticalAlignment(JLabel.CENTER);
		panelContainer.add(lbl_oElo);

		lbl_pName = new JLabel("MASTER ");
		lbl_pName.setBounds(935, 264, 150, 28);
		lbl_pName.setForeground(Color.WHITE);
		lbl_pName.setHorizontalAlignment(JLabel.CENTER);
		lbl_pName.setVerticalAlignment(JLabel.CENTER);
		lbl_pName.setFont(new Font("Tahoma", Font.BOLD, 10));
		panelContainer.add(lbl_pName);

		lblPAvatar = new JLabel();
		lblPAvatar.setBounds(925, 264, 55, 55);
		panelContainer.add(lblPAvatar);

		lblOAvatar = new JLabel();
		lblOAvatar.setBounds(925, 431, 55, 55);
		panelContainer.add(lblOAvatar);

		lblResult = new JLabel("VS");
		lblResult.setBounds(925, 320, 155, 104);
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblResult.setHorizontalAlignment(JLabel.CENTER);
		lblResult.setVerticalAlignment(JLabel.CENTER);
		panelContainer.add(lblResult);

		JLabel lblLogout = new JLabel();
		lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(new JFrame(), "Bạn có chắc chắn muốn thoát trận đấu chứ?",
						"Confirm Exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (result == JOptionPane.YES_OPTION) {
					Entry.socketHandler.sendSurrenderSignal();
					Entry.onSurrender();
					dispose();
				}
			}
		});
		lblLogout.setBounds(1133, 33, 60, 60);
		lblLogout.setIcon(new ImageIcon(img_logout));

		panelContainer.add(lblLogout);

		JLabel lblSetting = new JLabel("");
		lblSetting.setBounds(1050, 33, 60, 60);
		lblSetting.setIcon(new ImageIcon(img_setting));
		panelContainer.add(lblSetting);

		panelContainer.add(CaroBoard);

		CustomPanel panel_1 = new CustomPanel();
		panel_1.setBounds(1021, 19, 179, 89);
		panelContainer.add(panel_1);
		panel_1.setLayout(null);

		chatTextPane = new JTextPane() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int shadowSize = 4;
				int shadowOpacity = 20;
				Color shadowColor = new Color(0, 0, 0, shadowOpacity);
				int shadowOffset = 4;
				int cornerRadius = 20;
				g2d.setColor(shadowColor);
				g2d.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowOffset * 2,
						getHeight() - shadowOffset * 2, cornerRadius, cornerRadius);
				g2d.dispose();
			}
		};

		chatTextPane.setFont(new Font("Tahoma", Font.BOLD, 12));
		chatTextPane.setDisabledTextColor(Color.black);
		chatTextPane.setOpaque(false);
		// CustomPanel panel = new CustomPanel();
		chatTextPane.setEditable(false);
		chatTextPane.setBorder(null);
		chatTextPane.setBounds(806, 520, 369, 217);
		panelContainer.add(chatTextPane);
		chatTextPane.setLayout(null);

		CustomPanel panel_2 = new CustomPanel();
		panel_2.setBounds(825, 40, 162, 68);
		panelContainer.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblTilteTimePlay = new JLabel("THỜI GIAN TRẬN ĐẤU");
		lblTilteTimePlay.setBounds(14, 10, 138, 20);
		panel_2.add(lblTilteTimePlay);
		lblTilteTimePlay.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTilteTimePlay.setForeground(new Color(0xfffffff));
		lblTilteTimePlay.setHorizontalAlignment(JLabel.CENTER);
		lblTilteTimePlay.setVerticalAlignment(JLabel.CENTER);

		lbl_matchTimer = new JLabel();
		lbl_matchTimer.setBounds(35, 40, 89, 17);
		panel_2.add(lbl_matchTimer);
		lbl_matchTimer.setForeground(new Color(0xfffffff));
		lbl_matchTimer.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbl_matchTimer.setHorizontalAlignment(JLabel.CENTER);
		lbl_matchTimer.setVerticalAlignment(JLabel.CENTER);

		CustomPanelGradients panel_3 = new CustomPanelGradients();
		panel_3.setBounds(915, 241, 165, 99);
		panelContainer.add(panel_3);
		panel_3.setLayout(null);

		CustomPanelGradients panel_4 = new CustomPanelGradients();
		panel_4.setBounds(915, 408, 165, 99);
		panelContainer.add(panel_4);
		panel_4.setLayout(null);

		panel_TimeBorderPlayerO = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				borderClock(g, panel_TimeBorderPlayerO);
			}
		};
		panel_TimeBorderPlayerO.setBounds(0, 0, 165, 100);
		panel_4.add(panel_TimeBorderPlayerO);
		panel_TimeBorderPlayerO.setOpaque(false);
		panel_TimeBorderPlayerP.setVisible(false);
		panel_TimeBorderPlayerO.setVisible(false);
		setVisible(true);
		init();
	}

	private void squareClicked(int row, int col) {
		JOptionPane.showMessageDialog(this, "Square clicked: " + (char) ('A' + col) + (19 - row));
	}

	void initBG(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		GradientPaint gradient = new GradientPaint(0, 0, new Color(0x5170FF), // Màu bắt đầu (#ff8a00)#5170ff
				getWidth(), getHeight(), new Color(0xff66c4));
		// Sơn gradient trên JPanel
		g2d.setPaint(gradient);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
		int shadowOpacity = 90;
		Color shadowColor = new Color(0, 0, 0, shadowOpacity);
		g2d.setColor(shadowColor);
	}

	private void borderClock(Graphics g, JPanel j) {
		g.setColor(Color.green);
		if (timeLess < 7) {
			g.fillRect(83, 0, timeLess * 13, 5);
		} else if (timeLess < 14) {
			g.fillRect(83, 0, 7 * 13, 5);
			g.fillRect(160, 0, 5, (timeLess - 7) * 13);
		} else if (timeLess < 27) {
			g.fillRect(83, 0, 7 * 13, 5);
			g.fillRect(160, 0, 5, (timeLess - 7) * 13);
			int rectX1 = 165 - (timeLess - 14) * 13;
			g.fillRect(rectX1, 94, (timeLess - 14) * 13, 5);
		} else if (timeLess < 35) {
			g.fillRect(83, 0, 7 * 13, 5);
			g.fillRect(160, 0, 5, (timeLess - 7) * 13);
			int rectX1 = 165 - (timeLess - 14) * 13;
			g.fillRect(rectX1, 94, (timeLess - 14) * 13, 5);
			int rectY2 = 99 - (timeLess - 27) * 13;
			g.fillRect(0, rectY2, 5, (timeLess - 27) * 13);
		} else {
			if (flagTimeWarning == false) {
				j.setVisible(true);
				flagTimeWarning = true;
			} else {
				j.setVisible(false);
				flagTimeWarning = false;
			}
			g.setColor(Color.red);
			g.fillRect(83, 0, 7 * 13, 5);
			g.fillRect(160, 0, 5, (timeLess - 7) * 13);
			int rectX1 = 165 - (timeLess - 14) * 13;
			g.fillRect(rectX1, 94, (timeLess - 14) * 13, 5);
			int rectY2 = 99 - (timeLess - 27) * 13;
			g.fillRect(0, rectY2, 5, (timeLess - 27) * 13);
			g.fillRect(0, 0, (timeLess - 35) * 13, 5);

		}
	}

	void initBoardCaro() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				squares[i][j] = new ButtonBoard();
				squares[i][j].setContentAreaFilled(false);
				squares[i][j].setSize(30, 30);
				squares[i][j].setMargin(new Insets(5, 5, 5, 5));
				squares[i][j].setContentAreaFilled(false);
				squares[i][j].setFocusable(false);
				squares[i][j].setBorder(new LineBorder(new Color(0, 0, 0, 40), 2));
				squares[i][j].setText("");

				CaroBoard.add(squares[i][j]);

				final int row = i;
				final int col = j;
				// click
				squares[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Entry.socketHandler.sendGameEventMove(row, col, playerMoveMark);
					}
				});
			}
		}
	}

	private void init() {
		matchCalendar.set(Calendar.HOUR_OF_DAY, 0);
		matchCalendar.set(Calendar.MINUTE, 0);
		matchCalendar.set(Calendar.SECOND, 0);
		mTimer = new Timer(1000, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
				lbl_matchTimer.setText(sdf.format(matchCalendar.getTime()));
				matchCalendar.add(Calendar.SECOND, 1);
			}
		});
		mTimer.start();
		resetCalendar();
//		lbl_pRTime.setText("__:__");
//		lbl_oRTime.setText("__:__");
		lbl_matchTimer.setText("__:__");
	}

	private void resetCalendar() {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 40);
		timeLess = 0;
	}

	public void setMoveMark(Integer playerMoveMark) {
		this.playerMoveMark = playerMoveMark;
		if (playerMoveMark == MOVE.X_MOVE) {
			this.opponentMoveMark = MOVE.O_MOVE;
		} else {
			this.opponentMoveMark = MOVE.X_MOVE;
		}
	}

	public void setAbleToMove(boolean isAble) {
		this.CaroBoard.setEnabled(isAble);
	}

	public void resetPTimer() {
		resetCalendar();
		pTimer = new Timer(1000, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_TimeBorderPlayerP.setVisible(true);
				timeLess++;
				panel_TimeBorderPlayerP.repaint();
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
				if (calendar.get(Calendar.SECOND) <= 10) {
					lbl_pRTime.setForeground(Color.red);
				} else {
					lbl_pRTime.setForeground(Color.black);
				}

				lbl_pRTime.setText(sdf.format(calendar.getTime()));
				calendar.add(Calendar.SECOND, -1);
				System.out.println("PLAYER HAVE LEFT " + calendar.get(Calendar.SECOND));
				if (calendar.get(Calendar.SECOND) <= 0) {
					try {
						Thread.sleep(1000);
						Entry.socketHandler.sendTimeOutSignal();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		pTimer.start();
	}

	public void resetOTimer() {
		resetCalendar();
		oTimer = new Timer(1000, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel_TimeBorderPlayerO.setVisible(true);
				timeLess++;
				panel_TimeBorderPlayerO.repaint();
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
				if (calendar.get(Calendar.SECOND) <= 10) {
					lbl_oRTime.setForeground(Color.red);
				} else {
					lbl_oRTime.setForeground(Color.black);
				}

				lbl_oRTime.setText(sdf.format(calendar.getTime()));
				calendar.add(Calendar.SECOND, -1);
				System.out.println("OPPONENT HAVE LEFT " + calendar.get(Calendar.SECOND));
			}
		});
		oTimer.start();
	}

	public void blockPTimer() {
		try {
			pTimer.stop();
			panel_TimeBorderPlayerP.setVisible(false);
		} catch (Exception ex) {

		}
	}

	public void printMessage(String message) {
		this.chatTextPane.setText(this.chatTextPane.getText() + "\n" + "  " + message);
	}

	public void blockOTimer() {
		try {
			oTimer.stop();
			panel_TimeBorderPlayerO.setVisible(false);

		} catch (Exception ex) {

		}
	}

	public void blockMatchTimer() {
		try {
			mTimer.stop();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setMetaData(String data) {
		String pName = data.split("/")[1];
		String pMark = data.split("/")[2];
		String pElo = data.split("/")[3];
		String pImg = data.split("/")[4];
		String oName = data.split("/")[5];
		String oMark = data.split("/")[6];
		String oElo = data.split("/")[7];
		String oImg = data.split("/")[8];

		if (Integer.valueOf(pMark) == MOVE.X_MOVE) {
			lbl_pMark_.setText("X");
			lbl_pMark_.setForeground(Color.blue);
			lbl_oMark.setText("O");
			lbl_oMark.setForeground(Color.red);
		} else {
			lbl_pMark_.setText("O");
			lbl_pMark_.setForeground(Color.red);
			lbl_oMark.setText("X");
			lbl_oMark.setForeground(Color.blue);
		}
		lbl_pElo.setText(pElo);
		lbl_oName.setText(oName);
		lbl_oElo.setText(oElo);
		lbl_pName.setText(pName);
		java.net.URL OimgUrl = CaroBoard.class.getResource("/imgAvatar/" + oImg);
		if (OimgUrl != null) {
			img_O = new ImageIcon(OimgUrl).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		} else {
			OimgUrl = CaroBoard.class.getResource("/imgAvatar/beoj.jpg");
			if (OimgUrl != null) {
				img_O = new ImageIcon(OimgUrl).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			} else {
				System.err.println("Default image not found.");
			}
		}
		
		java.net.URL PimgUrl = CaroBoard.class.getResource("/imgAvatar/" +pImg);
		if (PimgUrl != null) {
			img_P = new ImageIcon(PimgUrl).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		} else {
			PimgUrl = CaroBoard.class.getResource("/imgAvatar/beoj.jpg");
			if (PimgUrl != null) {
				img_P = new ImageIcon(PimgUrl).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
			} else {
				System.err.println("Default image not found.");
			}
		}
		
		lblOAvatar.setIcon(new ImageIcon(img_O));
		lblPAvatar.setIcon(new ImageIcon(img_P));
	
	}

	public void paint(int x, int y, Integer move) {
		if (move == MOVE.X_MOVE) {
			squares[x][y].setText("X");
			squares[x][y].setForeground(Color.blue);
			squares[x][y].setFont(new Font("Tahoma", Font.BOLD, 25));

		} else if (move == MOVE.O_MOVE) {
			squares[x][y].setText("O");
			squares[x][y].setForeground(Color.red);
			squares[x][y].setFont(new Font("Tahoma", Font.BOLD, 25));

		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CaroBoard();
			}
		});
	}
}