package models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import database.DAL.PlayerDAL;
import database.DTO.PlayerDTO;
import global.GVAR;
import global.MOVE;
import server.runServer;
import global.StreamDataType;

public class Player implements Runnable {

	Socket socket;
	DataInputStream inputStream;
	DataOutputStream outputStream;

	PlayerDTO playerDTO;

	boolean isMatching = false;
	String matchingStatus = "CANCEL";

	Match match = null;
	Player opponentPlayer = null;

	Integer move = null;

	Boolean isAbleToMove = false;

	public Player(Socket socket) throws IOException {
		this.socket = socket;
		this.inputStream = new DataInputStream(socket.getInputStream());
		this.outputStream = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		String receivedData;

		while (!runServer.isShutdown) {
			try {
				receivedData = inputStream.readUTF();
				Integer streamDataType = StreamDataType.getTypeFromData(receivedData);
				System.out.println(receivedData);
				if (streamDataType == StreamDataType.LOGIN) {
					onLogin(receivedData);
				} else if (streamDataType == StreamDataType.SIGNUP) {
					onSignUp(receivedData);
				} else if (streamDataType == StreamDataType.GAME_EVENT_MOVE) {
					onGameEventMove(receivedData);
				} else if (streamDataType == StreamDataType.SEND_MESSAGE) {
					onSendMessage(receivedData);
					System.out.println("SERVER: RECEIVED DATA:" + receivedData);
				} else if (streamDataType == StreamDataType.FIND_MATCH) {
					onMatching(receivedData);
				} else if (streamDataType == StreamDataType.START_MATCHING) {
					onStartMatching();
				} else if (streamDataType == StreamDataType.SEND_MESSAGE_IN_MATCH) {
					onSendMessageInMatch(receivedData);
				} else if (streamDataType == StreamDataType.GAME_EVENT_TIMEOUT) {
					onGameEventTimeOut(receivedData);
				}
			} catch (Exception ex) {
				break;
			}
		}

		try {
			this.inputStream.close();
			this.outputStream.close();
			this.socket.close();
			runServer.playerManager.remove(this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onLogin(String receivedData) {
		try {
			System.out.println("login: " + receivedData);
			String email = receivedData.split("/")[1];
			String password = receivedData.split("/")[2];
			PlayerDTO player = PlayerDAL.getInstance().getPlayerByLogin(email, password);
			if (player != null) {
				this.playerDTO = player;
				this.outputStream
						.writeUTF(StreamDataType.LOGIN + "/" + "SUCCESSFULLY" + "/" + this.playerDTO.getFull_name());
			} else {
				this.outputStream.writeUTF(StreamDataType.LOGIN + "/" + "FAILED");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onSignUp(String receivedData) {
		try {
			System.out.println("on sigup from player: " + receivedData);
			String id = "P0000000002";
			String fullName = receivedData.split("/")[1];
			String email = receivedData.split("/")[2];
			String password = receivedData.split("/")[3];
			String date = receivedData.split("/")[4];
			String gender = receivedData.split("/")[5];
			System.out.println(id + " " + fullName + " " + gender);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			LocalDate dob;
			try {
				dob = LocalDate.parse(date, formatter);
//			String user_uid, String full_name, String gender, String email, String password
				PlayerDTO player = new PlayerDTO("P002", fullName, gender, email, password, dob);
				PlayerDAL playerDAL = PlayerDAL.getInstance();
				boolean flag = playerDAL.createPlayer(player);
				if (flag) {
					this.playerDTO = player;
					this.outputStream
							.writeUTF(StreamDataType.SIGNUP + "/" + "SUCCESSFULLY" + "/" + this.playerDTO.getEmail());
				} else {
					this.outputStream.writeUTF(StreamDataType.SIGNUP + "/" + "FAILED");
				}
			} catch (Exception e) {
			}
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
	}

	public void onSendMessage(String receivedData) {
		String user = receivedData.split("/")[1];
		String data = receivedData.split("/")[2];
		LocalTime time = LocalTime.now();
		runServer.playerManager.broadcast(StreamDataType.SEND_MESSAGE + "/" + user + " - "
				+ time.truncatedTo(ChronoUnit.SECONDS).format(GVAR.DTFormatter) + ": " + data);
	}

	public void onStartMatching() {
		try {
			this.isMatching = true;
			System.out.println("SETTING UP!");
			this.outputStream.writeUTF(StreamDataType.START_MATCHING + "/");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public synchronized void onMatching(String receiveData) {
		synchronized (this) {
			for (Player player : runServer.playerManager.getPlayers()) {
				synchronized (player) {
					if (player != this && player.isMatching) {
						try {
							this.isMatching = false;
							this.move = MOVE.X_MOVE;
							this.outputStream.writeUTF(StreamDataType.FIND_MATCH + "/" + this.move + "/" + "/");
							System.out.println("Match created for player one");
							player.isMatching = false;
							player.move = MOVE.O_MOVE;
							player.outputStream.writeUTF(StreamDataType.FIND_MATCH + "/" + player.move + "/" + "/");
							System.out.println("Match created for player two");
							this.opponentPlayer = player;
							player.opponentPlayer = this;
							Match match = new Match(this, player);
							this.match = match;
							player.match = match;
							match.broadcast(StreamDataType.ACCEPT_MATCH + "/");
							// SEND PRE-MATCH META DATA
							this.outputStream.writeUTF(StreamDataType.PREMATCH_META_DATA + "/"
									+ this.playerDTO.getFull_name() + "/" + this.move + "/"
									+ this.playerDTO.getElo_rating_points() + "/"
									+ this.opponentPlayer.playerDTO.getFull_name() + "/" + this.opponentPlayer.move
									+ "/" + this.opponentPlayer.playerDTO.getElo_rating_points() + "/");
							this.opponentPlayer.outputStream.writeUTF(StreamDataType.PREMATCH_META_DATA + "/"
									+ this.opponentPlayer.playerDTO.getFull_name() + "/" + this.opponentPlayer.move
									+ "/" + this.opponentPlayer.playerDTO.getElo_rating_points() + "/"
									+ this.playerDTO.getFull_name() + "/" + this.move + "/"
									+ this.playerDTO.getElo_rating_points() + "/");

							LocalTime time = LocalTime.now();
							Random random = new Random();

							if (random.nextInt() % 2 == 0) {
								this.opponentPlayer.outputStream.writeUTF(StreamDataType.GAME_EVENT_ABLE_TO_MOVE + "/");
								this.outputStream.writeUTF(StreamDataType.GAME_EVENT_UNABLE_TO_MOVE + "/");
								this.opponentPlayer.isAbleToMove = true;
								this.isAbleToMove = false;
								this.match.broadcast(StreamDataType.SEND_MESSAGE_IN_MATCH + "/" + "Hệ thống" + "/"
										+ time.truncatedTo(ChronoUnit.SECONDS).format(GVAR.DTFormatter) + "/"
										+ "Trận đấu bắt đầu! Lượt đi đầu tiên thuộc về "
										+ this.opponentPlayer.playerDTO.getFull_name() + "!");

							} else {
								this.opponentPlayer.outputStream
										.writeUTF(StreamDataType.GAME_EVENT_UNABLE_TO_MOVE + "/");
								this.outputStream.writeUTF(StreamDataType.GAME_EVENT_ABLE_TO_MOVE + "/");
								this.opponentPlayer.isAbleToMove = false;
								this.isAbleToMove = true;
								this.match.broadcast(StreamDataType.SEND_MESSAGE_IN_MATCH + "/" + "Hệ thống" + "/"
										+ time.truncatedTo(ChronoUnit.SECONDS).format(GVAR.DTFormatter) + "/"
										+ "Trận đấu bắt đầu! Lượt đi đầu tiên thuộc về " + this.playerDTO.getFull_name()
										+ "!");
							}

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}

	public void onGameEventMove(String receivedData) {
		try {
			if (this.isAbleToMove) {
				int x = Integer.valueOf(receivedData.split("/")[1]);
				int y = Integer.valueOf(receivedData.split("/")[2]);
				Integer move = Integer.valueOf(receivedData.split("/")[3]);
				LocalTime time = LocalTime.now();
				if (this.match.move(x, y, move)) {
					this.opponentPlayer.isAbleToMove = true;
					this.isAbleToMove = false;
					this.opponentPlayer.outputStream.writeUTF(StreamDataType.GAME_EVENT_ABLE_TO_MOVE + "/");
					this.outputStream.writeUTF(StreamDataType.GAME_EVENT_UNABLE_TO_MOVE + "/");

					if (this.match.isMatchEnded(x, y, move)) {
						this.match.broadcast(StreamDataType.SEND_MESSAGE_IN_MATCH + "/" + "Hệ thống" + "/"
								+ time.truncatedTo(ChronoUnit.SECONDS).format(GVAR.DTFormatter) + "/"
								+ "Nước mắt anh rơi, trò chơi kết thúc. Xin chúc mừng " + this.playerDTO.getFull_name()
								+ " đã chiến thắng!");

						double pEloRatio = (double) this.opponentPlayer.playerDTO.getElo_rating_points()
								/ this.playerDTO.getElo_rating_points();
						double oEloRatio = (double) this.playerDTO.getElo_rating_points()
								/ this.opponentPlayer.playerDTO.getElo_rating_points();
						int OPp = (int) (1 * 22.5 * pEloRatio);
						int OPo = (int) (1 * 22.5 * oEloRatio);

						PlayerDAL.getInstance().updateElo(this.playerDTO, OPp);
						PlayerDAL.getInstance().updateElo(this.opponentPlayer.playerDTO, -OPo);

						this.outputStream.writeUTF(StreamDataType.GAME_EVENT_WIN + "/"
								+ this.playerDTO.getElo_rating_points() + "/" + OPp);
						this.opponentPlayer.outputStream.writeUTF(StreamDataType.GAME_EVENT_LOST + "/"
								+ this.opponentPlayer.playerDTO.getElo_rating_points() + "/" + OPo);

						this.playerDTO = PlayerDAL.getInstance()
								.updateCurrentPlayerDTOByUserUID(this.playerDTO.getUser_uid());
						this.opponentPlayer.playerDTO = PlayerDAL.getInstance()
								.updateCurrentPlayerDTOByUserUID(this.opponentPlayer.playerDTO.getUser_uid());

						this.match = null;
						this.move = null;

						this.opponentPlayer.match = null;
						this.opponentPlayer.move = null;
						this.opponentPlayer.opponentPlayer = null;
						this.opponentPlayer = null;

						return;
					}
					this.match.broadcast(StreamDataType.SEND_MESSAGE_IN_MATCH + "/" + "Hệ thống" + "/"
							+ time.truncatedTo(ChronoUnit.SECONDS).format(GVAR.DTFormatter) + "/"
							+ "Lượt đi tiếp theo của " + this.opponentPlayer.playerDTO.getFull_name() + ".");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onGameEventTimeOut(String receivedData) {
		try {
			LocalTime time = LocalTime.now();
			this.match.broadcast(StreamDataType.SEND_MESSAGE_IN_MATCH + "/" + "Hệ thống" + "/"
					+ time.truncatedTo(ChronoUnit.SECONDS).format(GVAR.DTFormatter) + "/"
					+ "Nước mắt anh rơi, trò chơi kết thúc. Người chơi " + this.playerDTO.getFull_name()
					+ " đã hết thời gian đánh và thua cuộc!" + " Xin chúc mừng "
					+ this.opponentPlayer.playerDTO.getFull_name() + " đã chiến thắng!");

			double pEloRatio = (double) this.opponentPlayer.playerDTO.getElo_rating_points()
					/ this.playerDTO.getElo_rating_points();
			double oEloRatio = (double) this.playerDTO.getElo_rating_points()
					/ this.opponentPlayer.playerDTO.getElo_rating_points();
			int OPp = (int) (1 * 22.5 * pEloRatio);
			int OPo = (int) (1 * 22.5 * oEloRatio);

			PlayerDAL.getInstance().updateElo(this.playerDTO, -OPp);
			PlayerDAL.getInstance().updateElo(this.opponentPlayer.playerDTO, OPo);

			this.opponentPlayer.outputStream.writeUTF(StreamDataType.GAME_EVENT_WIN + "/"
					+ this.opponentPlayer.playerDTO.getElo_rating_points() + "/" + OPp);
			this.outputStream
					.writeUTF(StreamDataType.GAME_EVENT_LOST + "/" + this.playerDTO.getElo_rating_points() + "/" + OPo);

			this.playerDTO = PlayerDAL.getInstance().updateCurrentPlayerDTOByUserUID(this.playerDTO.getUser_uid());
			this.opponentPlayer.playerDTO = PlayerDAL.getInstance()
					.updateCurrentPlayerDTOByUserUID(this.opponentPlayer.playerDTO.getUser_uid());

			this.match = null;
			this.move = null;

			this.opponentPlayer.match = null;
			this.opponentPlayer.move = null;
			this.opponentPlayer.opponentPlayer = null;
			this.opponentPlayer = null;

			return;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onSendMessageInMatch(String receivedData) {
		try {
			String message = receivedData.split("/")[1];
			LocalTime time = LocalTime.now();
			this.match.broadcast(StreamDataType.SEND_MESSAGE_IN_MATCH + "/" + this.playerDTO.getFull_name() + "/"
					+ time.truncatedTo(ChronoUnit.SECONDS).format(GVAR.DTFormatter) + "/" + message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
