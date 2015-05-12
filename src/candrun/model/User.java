 package candrun.model;




public class User {
	private String email;
	private String nickname;
	private String password;
	private String picPath;
	private int state;

	public User() { }
	
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
	
	/* 
	 * @see candrun.model.User
	 * @see candrun.model.UserTest
	 * @see candrun.service.UserService
	 * 
	 * @see candrun.support.enums.Value
	 * @see candrun.support.enums.UserErrorcode
	 * @see candrun.support.enums.CommonInvar 
	 * 

	이때 주의할 점은 같은 타입으로 반환해야하는데요.
	제 생각엔 enum에 value타입을 지정하여 통일시켜주는게 좋을것 같아요.
	
	public Value getEnumState() {
		
		switch (state) {
		case 0 :
			return UserErrorcode.NOT_YET_CERTI;
		case 1 :
			return CommonInvar.SUCCESS;
		default :
			return CommonInvar.DEFAULT;
		}
	}
	*/
	
	public String getPicPath() {
		return picPath;
	}
}
