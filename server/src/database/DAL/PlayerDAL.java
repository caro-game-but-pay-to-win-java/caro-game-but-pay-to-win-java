package database.DAL;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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
			ResultSet resultSet = connector
					.query("SELECT * FROM PLAYERS WHERE EMAIL='" + email + "' AND PASSWORD='" + password + "'");
			return ObjectMapping.mapFirstPlayer(resultSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public PlayerDTO updateCurrentPlayerDTOByUserUID(String user_uid) {
		try {
			Connector connector = new Connector();
			ResultSet resultSet = connector.query("SELECT * FROM PLAYERS WHERE USER_UID = '" + user_uid + "'");
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

	public boolean createPlayer(PlayerDTO player) {
		try {
			Connector connector = new Connector();
			String sql = "INSERT INTO players (user_uid, full_name, gender, email, password, dob, total_matches, win_streak_counts, win_matches, lost_matches, elo_rating_points, player_img_file_path, biography, joined_date, rank_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, 0, 0, 0, 0, 500, '/HinhAnh.png', ' ', ?, 1)";

			PreparedStatement ps = connector.getConnection().prepareStatement(sql);
			ps.setString(1, player.getUser_uid());
			ps.setString(2, player.getFull_name());
			ps.setString(3, player.getGender());
			ps.setString(4, player.getEmail());
			ps.setString(5, player.getPassword());

			LocalDate joinedDate = LocalDate.now();
			System.out.println("Joined date: " + joinedDate);

			LocalDate dob = player.getDob();
			ps.setDate(6, Date.valueOf(dob));
			ps.setDate(7, Date.valueOf(joinedDate));

			int rowsInserted = ps.executeUpdate();
			return rowsInserted > 0;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void updateElo(PlayerDTO player, int newElo) {
		try {
			Connector connector = new Connector();
			String sql = "UPDATE players SET elo_rating_points = elo_rating_points + " + newElo + " WHERE USER_UID = '"
					+ player.getUser_uid() + "'";
			connector.executeNonQuery(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deletePlayerById(Long id) {

	}

	public boolean updateProfilePlayer(PlayerDTO player) {
		try {
			Connector connector = new Connector();
			LocalDate dob = player.getDob();
			String sql = "UPDATE players SET gender = ?, dob = ?, full_name = ?, player_img_file_path = ?, biography = ?, password = ? WHERE user_uid = ?";

			PreparedStatement ps = connector.getConnection().prepareStatement(sql);
			ps.setString(1, player.getGender());
			ps.setDate(2, Date.valueOf(dob));
			ps.setString(3, player.getFull_name());
			ps.setString(4, player.getPlayer_img_path());
			ps.setString(5, player.getBiography());
			ps.setString(6, player.getPassword());
			ps.setString(7, player.getUser_uid());

			int rowSelected = ps.executeUpdate();
			return rowSelected > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
