package candrun.user;

public class PreliminaryUser extends User{

	String verifyKey;
	public PreliminaryUser(String email, String nickname, String password, String verifyKey) {
		super(email, nickname, password);
		this.verifyKey = verifyKey;
	}
	public String getVerifyKey() {
		return verifyKey;
	}
	
}
