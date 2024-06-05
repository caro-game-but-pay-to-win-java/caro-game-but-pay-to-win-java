package Models;

public class CurrentAccount {
	
	private Long id;
	private String user_uid;
	private String full_name;
	private String gender;
	private String email;
	
	private static CurrentAccount instance;
	
	private CurrentAccount() {
		
	}
	
	public static CurrentAccount getInstance() {
		if (instance == null) {
			instance = new CurrentAccount();
		}
		return instance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser_uid() {
		return user_uid;
	}

	public void setUser_uid(String user_uid) {
		this.user_uid = user_uid;
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
}
