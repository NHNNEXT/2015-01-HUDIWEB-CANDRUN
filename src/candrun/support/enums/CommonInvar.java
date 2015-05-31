package candrun.support.enums;

public enum CommonInvar {
	ERRORCODE ("errorCode"),
	RETURNMSG ("returnMsg"),
	SUCCESS ("success"),
	DEFAULT ("default"),
	ACCEPTEDFRIENDS ("acceptedFriends"),
	REQUESTEDFRIENDS ("requestesFriends");
	
	private final String value;
	
	private CommonInvar(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
