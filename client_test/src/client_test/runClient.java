package client_test;

import Socket.SocketHandler;
import client_test.Dialog.MatchingDialog;

public class runClient {
	public static Login login;
	public static Test test;
	public static SocketHandler socketHandler;
	public static MatchingDialog matchingDialog;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		socketHandler = new SocketHandler();
		socketHandler.connect();
		login = new Login();
		login.setVisible(true);
	}
	
	public static void onLoginSuccess() {
		test = new Test();
		login.dispose();
		test.setVisible(true);
	}

	public static void onMatchAccepted() {
		
	}

}