package utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DTO.PlayerDTO;

/**
 * ObjectMapping class. Providing static methods for Mapping ResultSet to List
 * object.
 * 
 * @author deethesaint
 */
public class ObjectMapping {

	/**
	 * {@code mapPlayers} function convert a {@code ResultSet} of a full `players`
	 * query (select * from `players`) into {@code List<PlayerDTO>}@
	 */
	public static List<PlayerDTO> mapPlayers(ResultSet resultSet) {
		List<PlayerDTO> players = new ArrayList<PlayerDTO>();
		try {
			while (resultSet.next()) {
				PlayerDTO player = new PlayerDTO(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7), resultSet.getInt(8), resultSet.getInt(9),
						resultSet.getInt(10), resultSet.getInt(11), resultSet.getInt(12));
				players.add(player);
			}
			return players;
		} catch (Exception ex) {
			ex.printStackTrace();
			return players;
		}
	}
	public static PlayerDTO mapFirstPlayer(ResultSet resultSet) {
		try {
			if (resultSet.next()) {			
			Date dob = resultSet.getDate(7);
			Date joinDate = resultSet.getDate(15);
				return new PlayerDTO(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), dob.toLocalDate(), resultSet.getInt(8), resultSet.getInt(9),
						resultSet.getInt(10), resultSet.getInt(11), resultSet.getInt(12),resultSet.getString(13),resultSet.getString(14),joinDate.toLocalDate(),resultSet.getInt(16));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}
}
