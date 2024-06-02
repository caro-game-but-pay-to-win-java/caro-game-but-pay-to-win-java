package database.DAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.Connector;
import database.DTO.PlayerDTO;
import utils.ObjectMapping;

public class PlayerDAL {
	
	private static PlayerDAL instance = null;
	
	private PlayerDAL() {
		
	}
	
	public static PlayerDAL getInstance() {
		if (instance == null) {
			instance = new PlayerDAL();
		}
		return instance;
	}
	
	public PlayerDTO getPlayerByLogin(String email, String password) {
		try {
			Connector connector = new Connector();
			ResultSet resultSet = connector.query("SELECT * FROM PLAYERS WHERE EMAIL='" + email + "' AND PASSWORD='" + password + "'");
			return ObjectMapping.mapFirstPlayer(resultSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<PlayerDTO> getAllPlayers() {
		try {
			Connector connector = new Connector();
			ResultSet resultSet = connector.query("SELECT * FROM PLAYERS");
			return ObjectMapping.mapPlayers(resultSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public void createPlayer() {
		
	}
	
	public void updatePlayerById(Long id) {
		
	}
	
	public void deletePlayerById(Long id) {
		
	}
}
