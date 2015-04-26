package candrun.support.enums;

public enum UserErrorcode {
	EMPTY ("empty"),
	DUP ("dup"),
	WRONG_PW ("wrongPw"),
	NOT_YET_CERTI ("notYetCerti");
	
	private final String value;
	
	private UserErrorcode(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
