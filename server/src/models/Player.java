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

import database.DAL.PlayerDAL;
import database.DTO.PlayerDTO;
import global.GVAR;
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

	public Player(Socket socket) throws IOException {
		this.socket = socket;
		this.inputStream = new DataInputStream(socket.getInputStream());
		this.outputStream = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		String receivedData;
		Boolean isClientRunning = true;

		while (!runServer.isShutdown) {
			try {
				receivedData = inputStream.readUTF();
				Integer streamDataType = StreamDataType.getTypeFromData(receivedData);
				System.out.println(receivedData);
				if (streamDataType == StreamDataType.LOGIN) {
					onLogin(receivedData);
				} else if (streamDataType == StreamDataType.SIGNUP) {
					onSignUp(receivedData);
				} else if (streamDataType == StreamDataType.GAME_EVENT) {
					onGameEvent(receivedData);
				} else if (streamDataType == StreamDataType.SEND_MESSAGE) {
					onSendMessage(receivedData);
					System.out.println("SERVER: RECEIVED DATA:" + receivedData);
				} else if (streamDataType == StreamDataType.FIND_MATCH) {
					onMatching(receivedData);
				} else if (streamDataType == StreamDataType.START_MATCHING) {
					onStartMatching();
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
			System.out.println("login: "+receivedData);
			String email = receivedData.split("/")[1];
			String password = receivedData.split("/")[2];
			PlayerDTO player = PlayerDAL.getInstance().getPlayerByLogin(email, password);
			if (player != null) {
				this.playerDTO = player;
				this.outputStream.writeUTF(StreamDataType.LOGIN + "/" + "SUCCESSFULLY" + "/" + this.playerDTO.getEmail());
			} else {
				this.outputStream.writeUTF(StreamDataType.LOGIN + "/" + "FAILED");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onSignUp(String receivedData) {
			try {
			System.out.println("on sigup from player: "+ receivedData);
			String id = "P0000000002";
			String fullName = receivedData.split("/")[1];
			String email = receivedData.split("/")[2];
			String password =receivedData.split("/")[3];
			String date = receivedData.split("/")[4];
			String gender = receivedData.split("/")[5];
			System.out.println(id + " " + fullName + " " + gender);
			  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		        LocalDate dob;
		        try {
		            dob = LocalDate.parse(date, formatter);
		        
//			String user_uid, String full_name, String gender, String email, String password
			PlayerDTO player = new PlayerDTO("P002", fullName,gender, email,password,dob);
			PlayerDAL playerDAL = PlayerDAL.getInstance();
			boolean flag =playerDAL.createPlayer(player);
			if(flag)
			{
				this.playerDTO = player;
				this.outputStream.writeUTF(StreamDataType.SIGNUP + "/" + "SUCCESSFULLY" + "/" + this.playerDTO.getEmail());
			}
			else {
				this.outputStream.writeUTF(StreamDataType.SIGNUP+"/"+"FAILED");
			}
			} catch (Exception e) {
			}
			} catch (DateTimeParseException e) {
	            e.printStackTrace();
	            // Xử lý lỗi phân tích cú pháp ngày tháng
	            return;
	        }
	}

	public void onGameEvent(String receivedData) {

	}
	
	public void onSendMessage(String receivedData) {
		String user = receivedData.split("/")[1];
		String data = receivedData.split("/")[2];
		LocalTime time = LocalTime.now();
		runServer.playerManager.broadcast(StreamDataType.SEND_MESSAGE + "/" + user + " - " + time.truncatedTo(ChronoUnit.SECONDS).format(GVAR.DTFormatter) + ": " + data);
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
	
	public void onMatching(String receiveData) {
		for (Player player : runServer.playerManager.getPlayers()) {
			if (player != this && player.isMatching) {
				try {
					synchronized (this) {
						this.isMatching = false;
						this.outputStream.writeUTF(StreamDataType.FIND_MATCH + "/" + "Đã tìm thấy trận!");
						System.out.println("Match created for player one");
						synchronized (player) {						
							player.isMatching = false;
							player.outputStream.writeUTF(StreamDataType.FIND_MATCH + "/" + "Đã tìm thấy trận!");
							System.out.println("Match created for player two");
							Match match = new Match(this, player);
							match.broadcast(StreamDataType.ACCEPT_MATCH + "/");
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				break;
			}
		}
	}
}
