package gamelogic;

import java.time.Duration;
import java.util.Random;

import global.GVAR;
import global.MOVE;
import models.Match;

public class Game {
	
	private Match match;
	private Integer[][] matrix;
	private Integer currentMove;
	private Duration time;
	private Integer Id;
	
	public Game() {
		this.match = new Match();
		this.matrix = new Integer[GVAR.MATRIX_SIZE][GVAR.MATRIX_SIZE];
		
		Random rand = new Random();
		if (rand.nextInt() % 2 == 0) {
			currentMove = MOVE.O_MOVE;
		} else {
			currentMove = MOVE.X_MOVE;
		}
	}
	
	private void init() {
		for (int i = 0; i < GVAR.MATRIX_SIZE; ++i) {
			for (int j = 0; j < GVAR.MATRIX_SIZE; ++j) {
				this.matrix[i][j] = MOVE.NONE_MOVE;
			}
		}
	}
	
	public Boolean isGameFinished(int x) {
		if (1 == x) {
			return true;
		}
		return false;
	}
	
	public void move(int X, int Y, Integer move) {
		this.matrix[X][Y] = move;
	}
}
