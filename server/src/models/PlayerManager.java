package models;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
	
	private List<Player> players;
	
	public PlayerManager() {
		this.players = new ArrayList<Player>();
	}
	
	public boolean addPlayer(Player player) {
		if (!this.players.contains(player)) {			
			this.players.add(player);
			return true;
		}
		return false;
	}
	
	public void remove(Player player) {
		this.players.remove(player);
	}
}