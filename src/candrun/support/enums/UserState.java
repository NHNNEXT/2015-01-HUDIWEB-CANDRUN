package candrun.support.enums;

public enum UserState {
	REGISTERED (0),
	CERTIED (1),
	MAKABLE_NONAVAILABLE (2),
	MAKABLE_AVAILABLE (3),
	MAX_NONAVAILABLE (4),
	MAX_AVAILABLE (5),
	QUITED (9);
	
	private final int value;
	
	private UserState(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
