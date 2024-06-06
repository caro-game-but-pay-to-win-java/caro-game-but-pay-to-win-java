package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Models.CurrentAccount;
import Socket.StreamDataType;
import client_test.Test;
import client_test.runClient;
import client_test.Dialog.MatchingDialog;

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
					// vòng lặp đăng ký cái là cái lồn gì hả Vinh????
					System.out.println("vong lap dang ky: " + receivedData);
					onReceivedSigup(receivedData);
				} else if (streamDataType == StreamDataType.START_MATCHING) {
					onReceivedMatchSignal(receivedData);
				} else if (streamDataType == StreamDataType.ACCEPT_MATCH) {
					
				} else if (streamDataType == StreamDataType.GAME_EVENT_MOVE) {
					onReceivedGameEventMove(receivedData);
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
			JOptionPane.showConfirmDialog(new JFrame(), "Dang ky thanh cong");

		} else {
			JOptionPane.showConfirmDialog(new JFrame(), "Dang ky khong thanh cong");
		}
	}

	public void onReceivedMessage(String receivedData) {
		String data = receivedData.split("/")[1];
		System.out.println(receivedData);
		Test.Paint(data);
	}

	public void onReceivedLogin(String receivedData) {
		String data = receivedData;
		if (data.split("/")[1].equals("SUCCESSFULLY")) {
			CurrentAccount.getInstance().setEmail(data.split("/")[2]);
			runClient.onLoginSuccess();
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
			runClient.matchingDialog.isCancel = true;
			runClient.matchingDialog.dispose();
			runClient.onMatchAccepted(playerMoveMark);
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
			runClient.caroboard.paint(x, y, move);
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
}