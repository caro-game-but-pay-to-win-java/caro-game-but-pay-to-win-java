package database.DTO;

import java.time.LocalDate;

public class PlayerDTO {
	private Long id;

	private String user_uid;
	private String full_name;
	private String gender;
	private String email;
	private String password;
	private LocalDate dob;
	private Integer total_matches;
	private Integer win_streak_counts;
	private Integer win_matches;
	private Integer lost_matches;
	private Integer elo_rating_points;
	private String player_img_path;
	private String biography;
	private LocalDate joined_date;
	private Integer rank_id;

	public PlayerDTO(Long id, String user_uid, String full_name, String gender, String email, String password,
			LocalDate dob, Integer total_matches, Integer win_streak_counts, Integer win_matches, Integer lost_matches,
			Integer elo_rating_points, String player_img_path, String biography, LocalDate joined_date,
			Integer rank_id) {
		this.id = id;
		this.user_uid = user_uid;
		this.full_name = full_name;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.total_matches = total_matches;
		this.win_streak_counts = win_streak_counts;
		this.win_matches = win_matches;
		this.lost_matches = lost_matches;
		this.elo_rating_points = elo_rating_points;
		this.player_img_path = player_img_path;
		this.biography = biography;
		this.joined_date = joined_date;
		this.rank_id = rank_id;
	}

	public PlayerDTO(Long id, String user_uid, String full_name, String gender, String email, String password) {
		this.id = id;
		this.user_uid = user_uid;
		this.full_name = full_name;
		this.gender = gender;
		this.email = email;
		this.password = password;
	}

	public PlayerDTO(String user_uid, String full_name, String gender, String email, String password, LocalDate dob) {
		this.user_uid = user_uid;
		this.full_name = full_name;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.dob = dob;
	}
	
	public PlayerDTO(Long id, String user_uid, String full_name, String gender, String email, String password, String dob, Integer totalMatches, Integer winStreakCounts, Integer winMatches, Integer lostMatches, Integer eloRatingPoints) {
		this.user_uid = user_uid;
		this.full_name = full_name;
		this.gender = gender;
		this.email = email;
		this.password = password;
//		this.dob = dob;
		this.total_matches = totalMatches;
		this.win_streak_counts = winStreakCounts;
		this.win_matches = winMatches;
		this.lost_matches = lostMatches;
		this.elo_rating_points = eloRatingPoints;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Integer getTotal_matches() {
		return total_matches;
	}

	public void setTotal_matches(Integer total_matches) {
		this.total_matches = total_matches;
	}

	public Integer getWin_streak_counts() {
		return win_streak_counts;
	}

	public void setWin_streak_counts(Integer win_streak_counts) {
		this.win_streak_counts = win_streak_counts;
	}

	public Integer getWin_matches() {
		return win_matches;
	}

	public void setWin_matches(Integer win_matches) {
		this.win_matches = win_matches;
	}

	public Integer getLost_matches() {
		return lost_matches;
	}

	public void setLost_matches(Integer lost_matches) {
		this.lost_matches = lost_matches;
	}

	public Integer getElo_rating_points() {
		return elo_rating_points;
	}

	public void setElo_rating_points(Integer elo_rating_points) {
		this.elo_rating_points = elo_rating_points;
	}

	public String getPlayer_img_path() {
		return player_img_path;
	}

	public void setPlayer_img_path(String player_img_path) {
		this.player_img_path = player_img_path;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public LocalDate getJoined_date() {
		return joined_date;
	}

	public void setJoined_date(LocalDate joined_date) {
		this.joined_date = joined_date;
	}

	public Integer getRank_id() {
		return rank_id;
	}

	public void setRank_id(Integer rank_id) {
		this.rank_id = rank_id;
	}

	public String getUser_uid() {
		return user_uid;
	}

	public void setUser_uid(String user_uid) {
		this.user_uid = user_uid;
	}
}
