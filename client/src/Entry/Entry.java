package Entry;

import Diaglog.MatchingDialog;
import Socket.SocketHandler;
import View.CaroBoard;
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
	public static LobbyFrame lobbyFrame;
	public static CaroBoard caroboard;
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
		lobbyFrame= new LobbyFrame();
		lobbyFrame.setVisible(true);
	}

	public static void onLogin() {
		signUp.dispose();
		login = new Login();
		login.setVisible(true);
	}

	public static void onMatchAccepted() {

	}
	public static void onMatchingClicked() {
		matchingDialog = new MatchingDialog();
		matchingDialog.setLocationRelativeTo(lobbyFrame);
		matchingDialog.setModal(true);
		matchingDialog.setVisible(true);
	}

	public static void onMatchAccepted(Integer playerMoveMark) {
		caroboard = new CaroBoard();
		caroboard.setMoveMark(playerMoveMark);
		caroboard.setVisible(true);
		lobbyFrame.setVisible(false);
	}
	
	public static void onMatchEnd() {
		caroboard.dispose();
		lobbyFrame.setVisible(true);
	}

}
