package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.List;

import javax.swing.border.LineBorder;

import CustomComponents.ButtonBoard;
import CustomComponents.CustomPanel;
import CustomComponents.CustomPanelGradients;
import CustomComponents.CustomTextFiled;
import CustomComponents.RadiusButton;

import Socket.MOVE;
import Entry.Entry;

public class CaroBoardClient extends JFrame {
	private JPanel CaroBoard;
	private JPanel panel_TimeBorderPlayerP;
	private JPanel panel_TimeBorderPlayerO;
	private ButtonBoard[][] squares;
	private String[][] squaresValues;
	private static final int BOARD_SIZE = 19;
	private static final int WIN_CONDITION = 5;
	private static final String EMPTY = " ";
	private static final String PLAYER = "X";
	private static final String COMPUTER = "O";
	private static final int MAX_TIME = 20000;
	private boolean playerPlay = false; // sử dụng để đánh dấu mốc đến lượt người chơi , false là x , true là o
	JLabel lblTimePlay; // Hiển thị thời gian trận đấu
	JLabel lblAvatarPlayer1;
	JLabel lblAvatarPlayer2;
	JLabel lblResult;
	boolean flagTimeWarning = false; // sử dụng để cảnh báo thời gian
	boolean flagPaint = false;
	Timer timerLess; // sử dụng để bắt đầu đếm thời gian
	private Image img_setting = new ImageIcon(LobbyFrame.class.getResource("/img/setting_img.png")).getImage()
			.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	private Image img_logout = new ImageIcon(LobbyFrame.class.getResource("/img/logout_img.png")).getImage()
			.getScaledInstance(59, 60, Image.SCALE_SMOOTH);
	private Image img_man = new ImageIcon(LobbyFrame.class.getResource("/img/man_img.png")).getImage()
			.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
	private Image img_girl = new ImageIcon(LobbyFrame.class.getResource("/img/girl_img.png")).getImage()
			.getScaledInstance(60, 60, Image.SCALE_SMOOTH);

	JLabel lbl_pName = new JLabel("P_NAME");
	JLabel lbl_pElo = new JLabel("P_ELO_VALUES (RANK_NAME)");

	JLabel lbl_oMark = new JLabel("O_MARK_VALUE");
	JLabel lbl_oElo = new JLabel("O_ELO_VALUES");

	JLabel lbl_oName = new JLabel("O_NAME");
	JLabel lbl_pRTime = new JLabel("P_REMAINING_TIME");
	JLabel lbl_oRTime = new JLabel("O_REMAINING_TIME");
	JLabel lbl_matchTimer = new JLabel("MATCH_TIMER");

	JLabel lbl_pMark_ = new JLabel("Y");

	private String level;

	public CaroBoardClient(String level) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CaroBoard.class.getResource("/img/logo.png")));
		this.level = level;
		
		setTitle("Caro Game");
		setSize(1200, 850);
		JPanel panelContainer = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				initBG(g);
			}
		};
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

		lbl_oMark = new JLabel("X");
		lbl_oMark.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_oMark.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbl_oMark.setBounds(985, 475, 50, 20);
		panelContainer.add(lbl_oMark);
		lbl_pMark_.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lbl_pMark_.setBounds(985, 310, 50, 20);
		panelContainer.add(lbl_pMark_);

		lbl_pElo = new JLabel("1,000 point");
		lbl_pElo.setBounds(960, 457, 100, 20);
		lbl_pElo.setHorizontalAlignment(JLabel.CENTER);
		lbl_pElo.setVerticalAlignment(JLabel.CENTER);
		panelContainer.add(lbl_pElo);

		panel_TimeBorderPlayerP = new JPanel();
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
		lbl_oElo.setBounds(960, 279, 100, 51);
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

		lblAvatarPlayer1 = new JLabel("New label");
		lblAvatarPlayer1.setBounds(925, 264, 55, 55);
		lblAvatarPlayer1.setIcon(new ImageIcon(img_girl));
		panelContainer.add(lblAvatarPlayer1);

		lblAvatarPlayer2 = new JLabel("New label");
		lblAvatarPlayer2.setBounds(925, 431, 55, 55);
		lblAvatarPlayer2.setIcon(new ImageIcon(img_man));
		panelContainer.add(lblAvatarPlayer2);

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

					Entry.lobbyFrame = new LobbyFrame();
					Entry.lobbyFrame.setVisible(true);
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

		panel_TimeBorderPlayerO = new JPanel();
		panel_TimeBorderPlayerO.setBounds(0, 0, 165, 100);
		panel_4.add(panel_TimeBorderPlayerO);
		panel_TimeBorderPlayerO.setOpaque(false);
		
		
		setVisible(true);
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

	void initBoardCaro() {
		squaresValues = new String[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				squaresValues[i][j] = EMPTY;
				squares[i][j] = new ButtonBoard();
				squares[i][j].setContentAreaFilled(false);
				squares[i][j].setSize(30, 30);
				squares[i][j].setMargin(new Insets(5, 5, 5, 5));
				squares[i][j].setContentAreaFilled(false);
				squares[i][j].setFocusable(false);
				squares[i][j].setBorder(new LineBorder(new Color(0, 0, 0, 40), 2));
				squares[i][j].setText(EMPTY);
				CaroBoard.add(squares[i][j]);
				final int row = i;
				final int col = j;
				squares[i][j].addActionListener(e -> squareClicked(row, col));

			}
		}
	}

	private void squareClicked(int row, int col) {
		if(!playerPlay)
		{
			if (squares[row][col].getText().equals(EMPTY)) {
				squares[row][col].setText(PLAYER);
				squaresValues[row][col] = PLAYER;
				squares[row][col].setForeground(Color.red);
				squares[row][col].setFont(new Font("Tahoma", Font.BOLD, 25));
				// Check for a winner after each move
				String winner = checkWinner(squaresValues);
				playerPlay = true;
				if (!winner.equals(EMPTY)) {
					JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
//					Entry.lobbyFrame 
					Entry.lobbyFrame.setVisible(true);
					dispose();
				} else {
					computerMove();
				}
			} else {
				JOptionPane.showMessageDialog(this, "?!");
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Đừng vội!");

		}
	}

	private static String checkWinner(String[][] board) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (!board[i][j].equals(EMPTY)
						&& (checkDirection(board, i, j, 1, 0) || checkDirection(board, i, j, 0, 1)
								|| checkDirection(board, i, j, 1, 1) || checkDirection(board, i, j, 1, -1))) {
					return board[i][j];
				}
			}
		}
		return EMPTY;
	}

	private void computerMove() {
		Thread aiThread = new Thread(() -> {
	        long startTime = System.currentTimeMillis();
	        
	        int[] move = bestMove(squaresValues, level.toLowerCase(), startTime);
	        if (move != null) {
	            SwingUtilities.invokeLater(() -> {
	                int row = move[0];
	                int col = move[1];
	                squaresValues[row][col]=COMPUTER;
	                squares[row][col].setText(COMPUTER);
	                squares[row][col].setForeground(Color.WHITE);
	    			squares[row][col].setFont(new Font("Tahoma", Font.BOLD, 25));
	                String winner = checkWinner(squaresValues);
	                if (!winner.equals(EMPTY)) {
	                    JOptionPane.showMessageDialog(null, "Computer " + winner + " wins!");
	                	Entry.lobbyFrame = new LobbyFrame();
						Entry.lobbyFrame.setVisible(true);
						dispose();
	                }
	                playerPlay = false;
	            });
	        }
	    });
	    aiThread.start();

	}

	private static boolean checkDirection(String[][]board, int x, int y, int dx, int dy) {
		String current = board[x][y];
		for (int i = 0; i < WIN_CONDITION; i++) {
			int nx = x + i * dx, ny = y + i * dy;
			if (nx < 0 || ny < 0 || nx >= BOARD_SIZE || ny >= BOARD_SIZE || !board[nx][ny].equals(current)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isFull(String[][]  board) {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j].equals(EMPTY)) {
					return false;
				}
			}
		}
		return true;
	}

	private static int evaluateBoard(String[][] board) {
		int score = 0;
		score += evaluateLines(board, COMPUTER) - evaluateLines(board, PLAYER);
		return score;
	}

	private static int evaluateLines(String[][] board, String player) {
		int score = 0;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j].equals(player)) {
					score += evaluateDirection(board, i, j, 1, 0, player);
					score += evaluateDirection(board, i, j, 0, 1, player);
					score += evaluateDirection(board, i, j, 1, 1, player);
					score += evaluateDirection(board, i, j, 1, -1, player);
				}
			}
		}
		return score;
	}

	private static int evaluateDirection(String[][] board, int x, int y, int dx, int dy, String player) {
		int count = 0;
		for (int i = 0; i < WIN_CONDITION; i++) {
			int nx = x + i * dx, ny = y + i * dy;
			if (nx < 0 || ny < 0 || nx >= BOARD_SIZE || ny >= BOARD_SIZE || !board[nx][ny].equals(player)) {
				break;
			}
			count++;
		}
		if (count == 5)
			return 1000000;
		if (count == 4)
			return 1000;
		if (count == 3)
			return 100;
		if (count == 2)
			return 10;
		return 0;
	}

	private static int minimax(String[][] board, int depth, boolean isMaximizing, int alpha, int beta, int maxDepth,
			long startTime) {
		if (System.currentTimeMillis() - startTime > MAX_TIME) {
			return 0;
		}

		String winner = checkWinner(board);
		if (winner.equals(PLAYER))
			return -1000000 + depth;
		if (winner.equals(COMPUTER))
			return 1000000 - depth;
		if (isFull(board) || depth == maxDepth)
			return evaluateBoard(board);

		int bestEval = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j].equals(EMPTY) && hasNeighbor(board, i, j)) {

					String temp = isMaximizing ? COMPUTER : PLAYER;
					board[i][j]=(temp);
					int eval = minimax(board, depth + 1, !isMaximizing, alpha, beta, maxDepth, startTime);
					board[i][j]=(EMPTY);
					if (isMaximizing) {
						bestEval = Math.max(bestEval, eval);
						alpha = Math.max(alpha, eval);
					} else {
						bestEval = Math.min(bestEval, eval);
						beta = Math.min(beta, eval);
					}
					if (beta <= alpha)
						return bestEval;
				}
			}
		}
		return bestEval;
	}

	private static boolean hasNeighbor(String[][] board, int x, int y) {
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				int nx = x + i, ny = y + j;
				if (nx >= 0 && ny >= 0 && nx < BOARD_SIZE && ny < BOARD_SIZE
						&& !board[nx][ny].equals(EMPTY)) {
					return true;
				}
			}
		}
		return false;
	}

	private static int[] bestMove(String[][] board, String difficulty, long startTime) {
		Random random = new Random();
		if (difficulty.equals("easy") || (difficulty.equals("medium") && random.nextDouble() < 0.5)) {
			List<int[]> availableMoves = new ArrayList<>();
			for (int i = 0; i < BOARD_SIZE; i++) {
				for (int j = 0; j < BOARD_SIZE; j++) {
					if (board[i][j].equals(EMPTY) && hasNeighbor(board, i, j)) {
						availableMoves.add(new int[] { i, j });
					}
				}
			}
			return availableMoves.get(random.nextInt(availableMoves.size()));
		}

		int maxDepth = switch (difficulty) {
		case "easy" -> 1;
		case "medium" -> 2;
		default ->3;
		};

		int bestVal = Integer.MIN_VALUE;
		int[] bestMove = null;
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (board[i][j].equals(EMPTY) && hasNeighbor(board, i, j)) {
					board[i][j]=COMPUTER;

					int moveVal = 0;
					for (int depth = 1; depth <= maxDepth; depth++) {
						moveVal = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE, depth, startTime);
						if (System.currentTimeMillis() - startTime > MAX_TIME)
							break;
					}
					board[i][j]=EMPTY;
					if (moveVal > bestVal) {
						bestMove = new int[] { i, j };
						bestVal = moveVal;
					}
				}
			}
		}
		return bestMove;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CaroBoardClient("hard");
			}
		});
	}
}