package models;

import java.util.ArrayList;
import java.util.List;

import global.StreamDataType;

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
	
	public void broadcast(String sentData) {
		try {			
			for (Player player : this.players) {
				player.outputStream.writeUTF(StreamDataType.SEND_MESSAGE + "/" + "USER" + "/" + sentData);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}