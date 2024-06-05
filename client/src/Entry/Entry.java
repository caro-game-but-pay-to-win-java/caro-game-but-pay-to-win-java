package Entry;

import Diaglog.MatchingDialog;
import Socket.SocketHandler;
import View.LobbyFrame;
import View.Login;
import View.SignUp;
import music.MusicUtils;

public class Entry {
	public static Login login;
//	public static Test test;
	public static SocketHandler socketHandler;
	public static MatchingDialog matchingDialog;
	public static SignUp signUp;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		socketHandler = new SocketHandler();
		socketHandler.connect();
		login = new Login();
		login.setVisible(true);
	}

	public static void onLoginSuccess() {
		// test = new Test();
		login.dispose();
		// test.setVisible(true);
		LobbyFrame mainF = new LobbyFrame();
		mainF.setVisible(true);
	}

	public static void onLogin() {
		signUp.dispose();
		login = new Login();
		login.setVisible(true);
	}

	public static void onMatchAccepted() {

	}

}
