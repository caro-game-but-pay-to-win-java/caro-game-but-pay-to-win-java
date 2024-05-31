package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import global.GVAR;
import models.Player;
import models.PlayerManager;

public class runServer {

	public static ServerSocket serverSocket;
	public static Boolean isShutdown = false;
	public static volatile PlayerManager playerManager;
	
	public runServer() {
		try {
			serverSocket = new ServerSocket(GVAR.SERVER_PORT);
			playerManager = new PlayerManager();
			System.out.println("Server is running at port " + GVAR.SERVER_PORT + ".");
			
			ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(8));
			
			while (!isShutdown) {
				try {					
					Socket clientSocket = serverSocket.accept();
					Player player = new Player(clientSocket);
					Boolean newPlayerLoggedIn = playerManager.addPlayer(player);
					System.out.println("New Player Connected");
					executor.execute(player);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new runServer();
	}

}
