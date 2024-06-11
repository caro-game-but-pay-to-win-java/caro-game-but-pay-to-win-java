package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Entry.Entry;
import Models.CurrentAccount;

import View.LobbyFrame;

public class SocketHandler {
	Socket socket;
	DataOutputStream outputStream;
	DataInputStream inputStream;

	String name;
	String room;

	Thread thread = null;

	public SocketHandler() {
		// TODO Auto-generated constructor stub
	}

	public Boolean connect() {
		try {
			// InetAddress inetAddress = InetAddress.getByName();

			// this.socket = new Socket();

			this.socket = new Socket("127.0.0.1", 9123);

			this.outputStream = new DataOutputStream(this.socket.getOutputStream());
			this.inputStream = new DataInputStream(this.socket.getInputStream());

			if (this.thread != null && thread.isAlive()) {
				this.thread.interrupt();
			}

			this.thread = new Thread(this::listen);
			this.thread.start();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void listen() {
		Boolean isRunning = true;
		String receivedData;
		while (isRunning) {
			try {
				receivedData = inputStream.readUTF();
				Integer streamDataType = StreamDataType.getTypeFromData(receivedData);
				if (streamDataType == StreamDataType.SEND_MESSAGE) {
					onReceivedMessage(receivedData);
				} else if (streamDataType == StreamDataType.LOGIN) {
					onReceivedLogin(receivedData);
				} else if (streamDataType == StreamDataType.FIND_MATCH) {
					onReceivedMatchAccepted(receivedData);
					System.out.println("WON'T RUN?");
				} else if (streamDataType == StreamDataType.SIGNUP) {
					onReceivedSigup(receivedData);
				} else if (streamDataType == StreamDataType.START_MATCHING) {
					onReceivedMatchSignal(receivedData);
				} else if (streamDataType == StreamDataType.ACCEPT_MATCH) {

				} else if (streamDataType == StreamDataType.GAME_EVENT_MOVE) {
					onReceivedGameEventMove(receivedData);
				} else if (streamDataType == StreamDataType.GAME_EVENT_ABLE_TO_MOVE) {
					onReceivedGameEventAbleToMove(receivedData);
				} else if (streamDataType == StreamDataType.GAME_EVENT_UNABLE_TO_MOVE) {
					onReceivedGameEventUnableToMove(receivedData);
				} else if (streamDataType == StreamDataType.PREMATCH_META_DATA) {
					onReceivedPrematchMetaData(receivedData);
				} else if (streamDataType == StreamDataType.GAME_EVENT_WIN) {
					onReceivedMatchWin(receivedData);
				} else if (streamDataType == StreamDataType.GAME_EVENT_LOST) {
					onReceivedMatchLost(receivedData);
				} else if (streamDataType == StreamDataType.SEND_MESSAGE_IN_MATCH) {
					onReceivedMessageInMatch(receivedData);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				isRunning = false;
			}
		}
	}

	public void onReceivedSigup(String receivedData) {
		String data = receivedData;
		System.out.println("socket " + data);
		if (data.split("/")[1].equals("SUCCESSFULLY")) {
			JOptionPane.showConfirmDialog(new JFrame(), "Đăng ký thành công");

		} else {
			JOptionPane.showConfirmDialog(new JFrame(), "Đăng ký không thành công");
		}
	}

	public void onReceivedMessage(String receivedData) {
		String data = receivedData.split("/")[1];
		System.out.println(receivedData);
	//	Test.Paint(data);
	}

	public void onReceivedLogin(String receivedData) {
		String data = receivedData;
		if (data.split("/")[1].equals("SUCCESSFULLY")) {
			CurrentAccount.getInstance().setEmail(data.split("/")[2]);
			Entry.onLoginSuccess();
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Sai mật khẩu hoặc tên đăng nhập!", "Thông báo",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void onReceivedMatchFound(String receivedData) {

		// JOptionPane.showMessageDialog(new JFrame(), data.split("/")[1]);
	}

	public void onReceivedMatchSignal(String reiceivedData) {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onReceivedMatchAccepted(String receivedData) {
		try {
			Integer playerMoveMark = Integer.valueOf(receivedData.split("/")[1]);
			Entry.matchingDialog.isCancel = true;
			Entry.matchingDialog.dispose();
			Entry.onMatchAccepted(playerMoveMark);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onReceivedGameEventMove(String receivedData) {
		try {
			System.out.println("PAINTING...");
			int x = Integer.valueOf(receivedData.split("/")[1]);
			int y = Integer.valueOf(receivedData.split("/")[2]);
			Integer move = Integer.valueOf(receivedData.split("/")[3]);
			Entry.caroboard.paint(x, y, move);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onReceivedGameEventAbleToMove(String receivedData) {
		try {
			Entry.caroboard.setAbleToMove(true);
			Entry.caroboard.resetPTimer();
			Entry.caroboard.blockOTimer();
			System.out.println("ABLE TO MOVE: true");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onReceivedGameEventUnableToMove(String receivedData) {
		try {
			Entry.caroboard.setAbleToMove(false);
			Entry.caroboard.resetOTimer();
			Entry.caroboard.blockPTimer();
			System.out.println("ABLE TO MOVE: false");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onReceivedPrematchMetaData(String receivedData) {
		try {
			Entry.caroboard.setMetaData(receivedData);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onReceivedMatchWin(String receivedData) {
		try {
			String currentElo = receivedData.split("/")[1];
			Entry.caroboard.blockPTimer();
			Entry.caroboard.blockOTimer();
			Entry.caroboard.blockMatchTimer();
			String gainElo = receivedData.split("/")[2];
			JOptionPane.showMessageDialog(new JFrame(), "Bạn đã thắng!\nElo của bạn: " + currentElo + " + " + gainElo);
			Entry.onMatchEnd();
		} catch (Exception ex) {
			
		}
	}

	public void onReceivedMatchLost(String receivedData) {
		try {
			String currentElo = receivedData.split("/")[1];
			String gainElo = receivedData.split("/")[2];
			Entry.caroboard.blockPTimer();
			Entry.caroboard.blockOTimer();
			Entry.caroboard.blockMatchTimer();
			JOptionPane.showMessageDialog(new JFrame(), "Bạn đã thua!\nElo của bạn: " + currentElo + " - " + gainElo);
			Entry.onMatchEnd();
		} catch (Exception ex) {
			
		}
	}
	
	public void onReceivedMessageInMatch(String receivedData) {
		try {
			String name = receivedData.split("/")[1];
			String time = receivedData.split("/")[2];
			String message = name + " - " + time + ": " + receivedData.split("/")[3];
			Entry.caroboard.printMessage(message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendMessage(String sentData) {
		try {
			String sendingString = StreamDataType.SEND_MESSAGE + "/" + CurrentAccount.getInstance().getEmail() + "/"
					+ sentData;
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendLoginInformation(String email, String password) {
		try {
			String sendingString = StreamDataType.LOGIN + "/" + email + "/" + password;
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendSigupInformation(String name, String email, String password, String date, String gender) {
		try {
			String sendingString = StreamDataType.SIGNUP + "/" + name + "/" + email + "/" + password + "/" + date + "/"
					+ gender;
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void sendMatchingSignal() {
		try {
			String sendingString = StreamDataType.START_MATCHING + "/";
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendMatchingRequest() {
		try {
			String sendingString = StreamDataType.FIND_MATCH + "/";
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void sendGameEventMove(int x, int y, Integer move) {
		try {
			String sendingString = StreamDataType.GAME_EVENT_MOVE + "/" + x + "/" + y + "/" + move;
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendMessageInMatch(String message) {
		try {
			String sendingString = StreamDataType.SEND_MESSAGE_IN_MATCH + "/" + message;
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendTimeOutSignal() {
		try {
			String sendingString = StreamDataType.GAME_EVENT_TIMEOUT + "/";
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}