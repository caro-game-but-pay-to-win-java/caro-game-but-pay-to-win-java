package models;

import java.util.Arrays;

import global.GVAR;
import global.MOVE;
import global.StreamDataType;

public class Match {

	Integer[][] boardValues;

	Player fPlayer = null;
	Player sPlayer = null;

	int nextMove = 1;

	public Match(Player fPlayer, Player sPlayer) {
		this.boardValues = new Integer[GVAR.MATRIX_SIZE][GVAR.MATRIX_SIZE];
		for (int i = 0; i < GVAR.MATRIX_SIZE; ++i) {
			Arrays.fill(boardValues[i], MOVE.NONE_MOVE);
		}
		this.fPlayer = fPlayer;
		this.sPlayer = sPlayer;
	}

	public Boolean move(int x, int y, Integer move) {
		if (boardValues[x][y] == MOVE.NONE_MOVE) {
			boardValues[x][y] = move;
			this.broadcast(StreamDataType.GAME_EVENT_MOVE + "/" + x + "/" + y + "/" + move);
			return true;
		}
		return false;
	}

	public void broadcast(String data) {
		try {
			fPlayer.outputStream.writeUTF(data);
			sPlayer.outputStream.writeUTF(data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Boolean isMatchEnded(int x, int y, Integer move) {
		if (checkVertical(x, y, move) || checkHorizontal(x, y, move) || checkMainDiagonal(x, y, move)
				|| checkSecondaryDiagonal(x, y, move))
			return true;
		else
			return false;
	}

	private Boolean checkVertical(int x, int y, Integer move) {
		int count = 0;
		for (int i = 0; i < GVAR.MATRIX_SIZE; ++i) {
			if (boardValues[i][y] == move) {
				++count;
			} else {
				count = 0;
			}
			if (count == 5)
				return true;
		}
		return false;
	}

	private Boolean checkHorizontal(int x, int y, Integer move) {
		int count = 0;
		for (int i = 0; i < GVAR.MATRIX_SIZE; ++i) {
			if (boardValues[x][i] == move) {
				++count;
			} else {
				count = 0;
			}
			if (count == 5)
				return true;
		}
		return false;
	}

	private Boolean checkMainDiagonal(int x, int y, Integer move) {
		int count = 0;
		int tX = x;
		int tY = y;
		if (tX < tY) {
			tY = tY - tX;
			tX = 0;
		} else if (tX == tY) {
			tX = tY = 0;
		} else {
			tX = tX - tY;
			tY = 0;
		}
		for (int i = 0; i < GVAR.MATRIX_SIZE; ++i) {
			if (tX < GVAR.MATRIX_SIZE && tY < GVAR.MATRIX_SIZE && tX >= 0 && tY >= 0
					&& boardValues[tX++][tY++] == move) {
				++count;
				if (count == 5)
					return true;
			} else {
				count = 0;
			}
		}
		return false;
	}

	private Boolean checkSecondaryDiagonal(int x, int y, Integer move) {
		int count = 0;
		int tX = x;
		int tY = y;
		if (tX + tY < GVAR.MATRIX_SIZE) {
			tY = tY + tX;
			tX = 0;
		} else if (tX + tY == GVAR.MATRIX_SIZE) {
			tY = GVAR.MATRIX_SIZE - 1;
			tX = 0;
		} else {
			tX = tX - tY;
			tY = GVAR.MATRIX_SIZE - 1;
		}
		for (int i = 0; i < GVAR.MATRIX_SIZE; ++i) {
			if (tX < GVAR.MATRIX_SIZE && tY < GVAR.MATRIX_SIZE && tX >= 0 && tY >= 0
					&& boardValues[tX++][tY--] == move) {
				++count;
				if (count == 5)
					return true;
			} else {
				count = 0;
			}
		}
		return false;
	}
}