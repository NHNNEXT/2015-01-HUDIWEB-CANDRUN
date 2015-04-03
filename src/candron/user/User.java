package candron.user;

public class User {
	private String email;
	private String nickname;
	private String password;
	
	public User(String userID, String password, String name, String email) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}
}
