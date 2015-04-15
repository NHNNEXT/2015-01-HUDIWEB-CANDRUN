package candrun.model;

public class User {
	private String email;
	private String nickname;
	
	public User(String email, String nickname) {
		super();
		this.email = email;
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}