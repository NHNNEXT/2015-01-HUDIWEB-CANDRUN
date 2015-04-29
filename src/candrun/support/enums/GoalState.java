package candrun.support.enums;

public enum GoalState {
	REGISTERED (0),
	STARTED (1),
	FINISHED (9);
	
	private final int value;
	
	private GoalState(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
