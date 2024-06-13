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

	public void broadcast(String sentData) {
		try {
			for (Player player : this.players) {
				player.outputStream.writeUTF(sentData);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public Player getPlayerByUID(String UID) {
		for (int i = 0; i < this.players.size(); ++i) {
			try {
				if (this.players.get(i).playerDTO.getUser_uid().equals(UID)) {
					return this.players.get(i);
				}
			} catch (Exception ex) {

			}
		}
		return null;
	}
}