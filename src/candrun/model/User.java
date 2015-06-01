 package candrun.model;

public class User {
	private String email;
	private String nickname;
	private String password;
	private String picPath;
	private int state;


	/**
	 * 회원가입
	 * @param email
	 * @param nickname
	 * @param password
	 * @param picPath
	 */
	public User(String email, String nickname, String password, String picPath) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.picPath = picPath;
	}
	/**
	 * 로그인
	 * @param email
	 * @param nickname
	 * @param password
	 * @param picPath
	 * @param state
	 */
	public User(String email, String nickname, String password, String picPath, int state) {
		this(email, nickname, password, picPath);
		this.state = state;
	}
	/**
	 * 프렌즈 리스트 생성용
	 * @param email
	 * @param nickname
	 * @param picPath
	 * @param state
	 */
	public User(String email, String nickname, String picPath, int state) {
		this.email = email;
		this.nickname = nickname;
		this.picPath = picPath;
		this.state = state;
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
