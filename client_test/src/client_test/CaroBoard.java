package client_test;

import javax.swing.*;

import Socket.MOVE;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CaroBoard extends JFrame {
	private JPanel CaroBoard;
	private JButton[][] squares;
	boolean playerPlay = false;

	Integer playerMoveMark = null;
	Integer opponentMoveMark = null;
	private JTextField chatTextField;
	
	Calendar calendar = Calendar.getInstance();
	Calendar matchCalendar = Calendar.getInstance();

	JTextPane chatTextPane = new JTextPane();
	JButton sendBtn = new JButton("SEND");
	JLabel lbl_pName = new JLabel("P_NAME");
	JLabel lbl_pElo = new JLabel("P_ELO_VALUES (RANK_NAME)");
	JLabel lbl_pMark = new JLabel("P_MARK_VALUE");
	JLabel lbl_oMark = new JLabel("O_MARK_VALUE");
	JLabel lbl_oElo = new JLabel("O_ELO_VALUES");
	JLabel lbl_oName = new JLabel("O_NAME");
	JLabel lbl_pRTime = new JLabel("P_REMAINING_TIME");
	JLabel lbl_oRTime = new JLabel("O_REMAINING_TIME");
	JLabel lbl_matchTimer = new JLabel("MATCH_TIMER");
	
	Timer oTimer;
	Timer pTimer;
	Timer mTimer;

	public CaroBoard() {
		setTitle("Caro Game");
		setSize(1100, 690);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		CaroBoard = new JPanel(new GridLayout(19, 19));
		CaroBoard.setBounds(0, 0, 650, 650);
		squares = new JButton[19][19];
		// Map<JButton, Boolean> map = new HashMap<>();
		// Create chess board with buttons representing squares
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				squares[i][j] = new JButton();
				squares[i][j].setPreferredSize(new Dimension(35, 35));
				squares[i][j].setMargin(new Insets(0, 0, 0, 0)); // Tắt margin
				squares[i][j].setContentAreaFilled(false); // Tắt padding
				squares[i][j].setFocusable(false);
				squares[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
				// map.put(squares[i][j], false);
				CaroBoard.add(squares[i][j]);
				// Set button colors based on chess board pattern
				squares[i][j].setBackground(Color.WHITE);
				// Add action listener to handle button clicks
				final int row = i;
				final int col = j;
				squares[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						runClient.socketHandler.sendGameEventMove(row, col, playerMoveMark);
					}
				});
			}
		}
		getContentPane().setLayout(null);

		getContentPane().add(CaroBoard);

		chatTextPane.setEditable(false);
		chatTextPane.setBounds(660, 394, 414, 204);
		getContentPane().add(chatTextPane);

		chatTextField = new JTextField();
		chatTextField.setBounds(660, 609, 344, 31);
		getContentPane().add(chatTextField);
		chatTextField.setColumns(10);

		sendBtn.setBounds(1006, 609, 68, 31);
		getContentPane().add(sendBtn);

		JLabel lbl_pNamex = new JLabel("Bạn:");
		lbl_pNamex.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_pNamex.setBounds(681, 29, 117, 23);
		getContentPane().add(lbl_pNamex);

		lbl_pName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_pName.setBounds(808, 29, 266, 23);
		getContentPane().add(lbl_pName);

		JLabel lbl_pElox = new JLabel("Elo:");
		lbl_pElox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_pElox.setBounds(681, 54, 117, 23);
		getContentPane().add(lbl_pElox);

		lbl_pElo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_pElo.setBounds(808, 54, 266, 23);
		getContentPane().add(lbl_pElo);

		JLabel lbl_pMarkx = new JLabel("Dấu quân:");
		lbl_pMarkx.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_pMarkx.setBounds(681, 80, 117, 23);
		getContentPane().add(lbl_pMarkx);

		lbl_pMark.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_pMark.setBounds(808, 80, 266, 23);
		getContentPane().add(lbl_pMark);

		JLabel lbl_oNamex = new JLabel("Đối thủ:");
		lbl_oNamex.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oNamex.setBounds(681, 271, 117, 23);
		getContentPane().add(lbl_oNamex);

		JLabel lbl_oElox = new JLabel("Elo:");
		lbl_oElox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oElox.setBounds(681, 296, 117, 23);
		getContentPane().add(lbl_oElox);

		JLabel lbl_oMarkx = new JLabel("Dấu quân:");
		lbl_oMarkx.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oMarkx.setBounds(681, 322, 117, 23);
		getContentPane().add(lbl_oMarkx);

		lbl_oMark.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oMark.setBounds(808, 322, 266, 23);
		getContentPane().add(lbl_oMark);

		lbl_oElo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oElo.setBounds(808, 296, 266, 23);
		getContentPane().add(lbl_oElo);

		
		lbl_oName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oName.setBounds(808, 271, 266, 23);
		getContentPane().add(lbl_oName);

		JLabel lblBn_1_1 = new JLabel("VS");
		lblBn_1_1.setForeground(new Color(128, 128, 255));
		lblBn_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblBn_1_1.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD | Font.ITALIC, 58));
		lblBn_1_1.setBounds(681, 171, 174, 50);
		getContentPane().add(lblBn_1_1);

		JLabel lbl_pRTimex = new JLabel("Thời gian:");
		lbl_pRTimex.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_pRTimex.setBounds(681, 106, 117, 23);
		getContentPane().add(lbl_pRTimex);

		lbl_pRTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_pRTime.setBounds(808, 106, 266, 23);
		getContentPane().add(lbl_pRTime);

		JLabel lbl_oRTimex = new JLabel("Thời gian:");
		lbl_oRTimex.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oRTimex.setBounds(681, 348, 117, 23);
		getContentPane().add(lbl_oRTimex);

		lbl_oRTime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oRTime.setBounds(808, 348, 266, 23);
		getContentPane().add(lbl_oRTime);

		lbl_matchTimer.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_matchTimer.setBounds(896, 183, 138, 23);
		getContentPane().add(lbl_matchTimer);
		setVisible(true);
		
		init();
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
		lbl_pRTime.setText("__:__");
		lbl_oRTime.setText("__:__");
	}
	
	private void resetCalendar() {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
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
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
				lbl_pRTime.setText(sdf.format(calendar.getTime()));
				calendar.add(Calendar.SECOND, 1);
			}
		});
		pTimer.start();
	}
	
	public void resetOTimer() {
		resetCalendar();
		oTimer = new Timer(1000, (ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
				lbl_oRTime.setText(sdf.format(calendar.getTime()));
				calendar.add(Calendar.SECOND, 1);
			}
		});
		oTimer.start();
	}
	
	public void blockPTimer() {
		try {
			pTimer.stop();
			lbl_pRTime.setText("__:__");
			
		} catch (Exception ex) {
			
		}
	}
	
	public void blockOTimer() {
		try {
			oTimer.stop();
			lbl_oRTime.setText("__:__");
		} catch (Exception ex) {
			
		}
	}

	public void setMetaData(String data) {
		String pName = data.split("/")[1];
		String pMark = data.split("/")[2];
		String pElo = data.split("/")[3];
		String oName = data.split("/")[4];
		String oMark = data.split("/")[5];
		String oElo = data.split("/")[6];
		
		lbl_pName.setText(pName);
		if (Integer.valueOf(pMark) == MOVE.X_MOVE) {			
			lbl_pMark.setText("X");
			lbl_pMark.setForeground(Color.blue);
			lbl_oMark.setText("O");
			lbl_oMark.setForeground(Color.red);
		} else {
			lbl_pMark.setText("O");
			lbl_pMark.setForeground(Color.red);
			lbl_oMark.setText("X");
			lbl_oMark.setForeground(Color.blue);
		}
		lbl_pElo.setText(pElo);
		lbl_oName.setText(oName);
		lbl_oElo.setText(oElo);
	}

	public void paint(int x, int y, Integer move) {
		if (move == MOVE.X_MOVE) {
			squares[x][y].setText("X");
			squares[x][y].setForeground(Color.blue);
		} else if (move == MOVE.O_MOVE) {
			squares[x][y].setText("O");
			squares[x][y].setForeground(Color.red);
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