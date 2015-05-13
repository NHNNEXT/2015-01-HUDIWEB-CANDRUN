package candrun.support.enums;

public enum RelationRequestState {
	REQUESTED (0),
	ACCEPTED (1),
	DENIED (9);
	
	private final int value;
	
	private RelationRequestState(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
