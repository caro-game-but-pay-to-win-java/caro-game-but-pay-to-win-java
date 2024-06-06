package models;

import java.util.Arrays;

import global.GVAR;
import global.MOVE;

public class Match {
	
	Integer[][] boardValues;
	
	public Match() {
		this.boardValues = new Integer[GVAR.MATRIX_SIZE][GVAR.MATRIX_SIZE];
		for (int i = 0; i < GVAR.MATRIX_SIZE; ++i) {
			Arrays.fill(boardValues[i], MOVE.NONE_MOVE);
		}
	}

	
}
