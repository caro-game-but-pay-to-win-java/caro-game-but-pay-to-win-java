package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Socket.StreamDataType;
import client_test.CurrentAccount;
import client_test.Test;
import client_test.runClient;

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

			this.socket = new Socket("127.0.0.1", 6543);

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
					onReceiveMessage(receivedData);
				} else if (streamDataType == StreamDataType.LOGIN) {
					onReceiveLogin(receivedData);
				} else if (streamDataType == StreamDataType.FIND_MATCH) {
					onReceivedMatchFound(receivedData);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				isRunning = false;
			}
		}
	}

	public void onReceiveMessage(String receivedData) {
		String data = receivedData.split("/")[1];
		Test.Paint(data);
	}

	public void onReceiveLogin(String receivedData) {
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
		if (!runClient.matchingDialog.isCancel) {			
			String data = receivedData;
			runClient.matchingDialog.isCancel = true;
			runClient.matchingDialog.dispose();
			JOptionPane.showMessageDialog(new JFrame(), data.split("/")[1]);
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

	public void sendMatchingRequest() {
		try {
			String sendingString = StreamDataType.FIND_MATCH + "/";
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}