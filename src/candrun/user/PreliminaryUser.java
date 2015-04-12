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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((verifyKey == null) ? 0 : verifyKey.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreliminaryUser other = (PreliminaryUser) obj;
		if (verifyKey == null) {
			if (other.verifyKey != null)
				return false;
		} else if (!verifyKey.equals(other.verifyKey))
			return false;
		return true;
	}
	
}
