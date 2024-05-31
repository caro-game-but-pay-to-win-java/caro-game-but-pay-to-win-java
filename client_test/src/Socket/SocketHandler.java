package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import Socket.StreamDataType;
import client_test.Test;

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
			System.out.println("WTF");
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
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				isRunning = false;
			}
		}
	}
	
	public void onReceiveMessage(String receivedData) {
		String data = receivedData.split("/")[2];
		Test.Paint(data);
	}
	
	public void sendMessage(String sentData) {
		try {
			String sendingString = StreamDataType.SEND_MESSAGE + "/" + sentData;
			System.out.println("SENDING OUT DATA: " + sendingString);
			this.outputStream.writeUTF(sendingString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}