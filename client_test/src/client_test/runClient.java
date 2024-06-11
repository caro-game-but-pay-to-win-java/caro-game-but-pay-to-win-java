package client_test;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Socket.SocketHandler;
import client_test.Dialog.MatchingDialog;

public class runClient {
	public static Login login;
	public static Test test;
	public static SocketHandler socketHandler;
	public static MatchingDialog matchingDialog;
	public static Signup sigup;
	public static CaroBoard caroboard;

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

	public static void onOutOfClientUI() {
		socketHandler = new SocketHandler();
		socketHandler.connect();
		test.dispose();
		;
		login = new Login();
		login.setVisible(true);
	}

	public static void onDisconnectedToServer() {
		// code
	}

	public static void onMatchingClicked() {
		matchingDialog = new MatchingDialog();
		matchingDialog.setLocationRelativeTo(test);
		matchingDialog.setModal(true);
		matchingDialog.setVisible(true);
	}

	public static void onMatchAccepted(Integer playerMoveMark) {
		caroboard = new CaroBoard();
		caroboard.setMoveMark(playerMoveMark);
		caroboard.setVisible(true);
		test.setVisible(false);
	}

	public static void onMatchEnd() {
		caroboard.dispose();
		test.setVisible(true);
	}
}
