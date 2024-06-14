package models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
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
				System.out.println("RECEIVED: " + receivedData);
				if (streamDataType == StreamDataType.LOGIN) {
					onLogin(receivedData);
				} else if (streamDataType == StreamDataType.SIGNUP) {
					onSignUp(receivedData);
				} else if (streamDataType == StreamDataType.GAME_EVENT_MOVE) {
					onGameEventMove(receivedData);
				} else if (streamDataType == StreamDataType.SEND_MESSAGE) {
					onSendMessage(receivedData);
				} else if (streamDataType == StreamDataType.FIND_MATCH) {
					onMatching(receivedData);
				} else if (streamDataType == StreamDataType.START_MATCHING) {
					onStartMatching();
				} else if (streamDataType == StreamDataType.SEND_MESSAGE_IN_MATCH) {
					onSendMessageInMatch(receivedData);
				} else if (streamDataType == StreamDataType.GAME_EVENT_TIMEOUT) {
					onGameEventTimeOut(receivedData);
				} else if (streamDataType == StreamDataType.WATCH_PROFILE) {
					onWatchProfile();
				} else if (streamDataType == StreamDataType.EDIT_PROFILE) {
					onEditProfile(receivedData);
				}
			} catch (Exception ex) {
				try {
					System.out.println(this.playerDTO.getUser_uid() + " IS DISCONNECTED!");
					onDisconnected();
					remove();
					System.out.println(runServer.playerManager.getPlayers().size());
					break;
				} catch (Exception iex) {
					break;
				}
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

	public void remove() {
		synchronized (this) {
			runServer.playerManager.remove(this);
		}
	}

	public void onDisconnected() {
		try {
			LocalTime time = LocalTime.now();
			this.opponentPlayer.outputStream.writeUTF(StreamDataType.SEND_MESSAGE_IN_MATCH + "/" + "Hệ thống" + "/"
					+ time.truncatedTo(ChronoUnit.SECONDS).format(GVAR.DTFormatter) + "/"
					+ "Nước mắt anh rơi, trò chơi kết thúc. Người chơi " + this.playerDTO.getFull_name()
					+ " đã bị mất kết nối!" + " Xin chúc mừng " + this.opponentPlayer.playerDTO.getFull_name()
					+ " đã chiến thắng!");

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
			// everything fine here!
			System.out.println(ex.getMessage());
		}
	}

	public void onLogin(String receivedData) {
		try {
			System.out.println("login: " + receivedData);
			String email = receivedData.split("/")[1];
			String password = receivedData.split("/")[2];
			PlayerDTO player = PlayerDAL.getInstance().getPlayerByLogin(email, password);
			if (player != null) {
				Player cplayer = runServer.playerManager.getPlayerByUID(player.getUser_uid());
				if (cplayer != null && cplayer.match != null) {
					this.outputStream.writeUTF(StreamDataType.LOGIN_FAILED + "/");
					return;
				} else if (cplayer != null && cplayer.match == null) {
					cplayer.outputStream.writeUTF(StreamDataType.OUT_OF_CLIENT_UI + "/");
					cplayer.remove();
				}
				this.playerDTO = player;
				this.outputStream
						.writeUTF(StreamDataType.LOGIN + "/" + "SUCCESSFULLY" + "/" + this.playerDTO.getFull_name() +"/"+ this.playerDTO.getElo_rating_points()+"/" + this.playerDTO.getPlayer_img_path());
			} else {
				this.outputStream.writeUTF(StreamDataType.LOGIN + "/" + "FAILED");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onSignUp(String receivedData) {
		try {
			Instant now = Instant.now();
			String currentTime = now.toString();
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(currentTime.getBytes(StandardCharsets.UTF_8));
			String hashString = String.format("%064x", new BigInteger(1, hashBytes));
			String result = hashString.substring(0, 9);
			String id = "P".concat(result);
			String fullName = receivedData.split("/")[1];
			String email = receivedData.split("/")[2];
			String password = receivedData.split("/")[3];
			String date = receivedData.split("/")[4];
			String gender = receivedData.split("/")[5];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			LocalDate dob;
			try {
				dob = LocalDate.parse(date, formatter);
//			String user_uid, String full_name, String gender, String email, String password
				PlayerDTO player = new PlayerDTO(id, fullName, gender, email, password, dob);
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
		} catch (DateTimeParseException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public void onWatchProfile() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			
			this.outputStream.writeUTF(StreamDataType.WATCH_PROFILE + "/" + this.playerDTO.getUser_uid() + "/"
					+ this.playerDTO.getFull_name() + "/" + this.playerDTO.getGender() + "/" + this.playerDTO.getEmail()
					+ "/" + this.playerDTO.getPassword() + "/" + this.playerDTO.getDob().format(formatter) + "/"
					+ this.playerDTO.getTotal_matches() + "/" + this.playerDTO.getWin_streak_counts() + "/"
					+ this.playerDTO.getWin_matches() + "/" + this.playerDTO.getLost_matches() + "/"
					+ this.playerDTO.getElo_rating_points() + "/" + this.playerDTO.getPlayer_img_path() + "/"
					+ this.playerDTO.getBiography() + "/" + this.playerDTO.getJoined_date().format(formatter) + "/"
					+ this.playerDTO.getRank_id());
		} catch (IOException e) {
			// TODO Auto-generated catch block
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

	public void onEditProfile(String receiveData) {
		try {
			String fullname = receiveData.split("/")[1];
			String gender = receiveData.split("/")[2];
			String dob = receiveData.split("/")[3];
			String img = receiveData.split("/")[4];
			String bio = receiveData.split("/")[5];
			String password = receiveData.split("/")[6];

			LocalDate dobD;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			dobD = LocalDate.parse(dob, formatter);
			this.playerDTO.setFull_name(fullname);
			this.playerDTO.setDob(dobD);
			this.playerDTO.setGender(gender);
			this.playerDTO.setPlayer_img_path(img);
			this.playerDTO.setBiography(bio);
			this.playerDTO.setPassword(password);
			PlayerDAL playerDAL = PlayerDAL.getInstance();
			boolean flag = playerDAL.updateProfilePlayer(playerDTO);
			if (flag) {
				this.outputStream.writeUTF(StreamDataType.EDIT_PROFILE + "/" + "SUCCESSFULLY" + "/"+ this.playerDTO.getFull_name() +"/"+ this.playerDTO.getElo_rating_points()+"/" + this.playerDTO.getPlayer_img_path());
			}
			else {
				this.outputStream.writeUTF(StreamDataType.EDIT_PROFILE + "/" + "FAILED");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public synchronized void onMatching(String receiveData) {
		synchronized (this) {
			for (Player player : runServer.playerManager.getPlayers()) {
				synchronized (player) {
					if (player != this && player.isMatching && this.match == null && player.match == null
							&& this.opponentPlayer == null && player.opponentPlayer == null) {
						try {
							this.isMatching = false;
							player.isMatching = false;
							this.move = MOVE.X_MOVE;
							this.outputStream.writeUTF(StreamDataType.FIND_MATCH + "/" + this.move + "/" + "/");
							System.out.println("Match created for player one");
							player.move = MOVE.O_MOVE;
							player.outputStream.writeUTF(StreamDataType.FIND_MATCH + "/" + player.move + "/" + "/");
							System.out.println("Match created for player two");
							this.opponentPlayer = player;
							player.opponentPlayer = this;
							Match match = new Match(this, player);
							this.match = match;
							player.match = match;
							Thread.sleep(200);
							match.broadcast(StreamDataType.ACCEPT_MATCH + "/");
							// SEND PRE-MATCH META DATA
							Thread.sleep(200);
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
							Thread.sleep(200);
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
