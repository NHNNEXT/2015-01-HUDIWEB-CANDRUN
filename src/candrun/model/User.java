 package candrun.model;

public class User {
	private String email;
	private String nickname;
	private String password;
	private String picPath;
	private int state;

	public User(String email, String nickname, String password, String picPath) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.picPath = picPath;
	}
	public User(String email, String nickname, String password, String picPath, int state) {
		this(email, nickname, password, picPath);
		this.state = state;
	}
	public User(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public int getState() {
		return state;
	}
	
	public String getPicPath() {
		return picPath;
	}
}
