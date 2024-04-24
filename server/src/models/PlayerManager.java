package models;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
	
	private List<Player> players;
	
	public PlayerManager() {
		super();
		this.players = new ArrayList<Player>();
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}
}
