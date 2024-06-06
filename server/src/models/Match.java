package models;

import java.util.Arrays;

import global.GVAR;
import global.MOVE;
import global.StreamDataType;

public class Match {
	
	Integer[][] boardValues;
	
	Player fPlayer = null;
	Player sPlayer = null;
	
	public Match(Player fPlayer, Player sPlayer) {
		this.boardValues = new Integer[GVAR.MATRIX_SIZE][GVAR.MATRIX_SIZE];
		for (int i = 0; i < GVAR.MATRIX_SIZE; ++i) {
			Arrays.fill(boardValues[i], MOVE.NONE_MOVE);
		}
		this.fPlayer = fPlayer;
		this.sPlayer = sPlayer;
	}
	
	public void move(int x, int y, Integer move) {
		if (boardValues[x][y] == MOVE.NONE_MOVE) {			
			boardValues[x][y] = move;
			this.broadcast(StreamDataType.GAME_EVENT_MOVE + "/" + x + "/" + y + "/" + move);
		}
	}
	
	public void broadcast(String data) {
		try {
			fPlayer.outputStream.writeUTF(data);
			sPlayer.outputStream.writeUTF(data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Boolean winChecking() {
		return false;
	}
}