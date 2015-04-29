package candrun.support.enums;

public enum UserState {
	REGISTERED (0),
	CERTIED (1),
	QUITED (9);
	
	private final int value;
	
	private UserState(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
