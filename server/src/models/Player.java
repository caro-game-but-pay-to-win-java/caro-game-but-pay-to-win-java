package models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import database.DTO.PlayerDTO;
import global.GVAR;
import server.runServer;

public class Player implements Runnable {
	
	Socket socket;
	DataInputStream inputStream;
	DataOutputStream outputStream;
	
	PlayerDTO playerDTO;
	Player opponentPlayer;
	
	boolean isMatching = false;
	String matchingStatus = "CANCEL";
	
	public Player(Socket socket) throws IOException {
		this.socket = socket;
		this.inputStream = new DataInputStream(socket.getInputStream());
		this.outputStream = new DataOutputStream(socket.getOutputStream());
	}
	
	@Override
	public void run() {
		String dataReceived;
		Boolean isClientRunning = true;
		
		while (!runServer.isShutdown) {
			try {
				
				dataReceived = inputStream.readUTF();
				Thread.sleep(1000);
				System.out.println("HEHE");
				GVAR.StreamDataType streamDataType = GVAR.getTypeFromData(dataReceived);
				
				
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
}
