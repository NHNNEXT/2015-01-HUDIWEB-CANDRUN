package candrun.support.enums;

public enum CommonError {
	SERVER ("serverError");
	
	private final String value;
	
	private CommonError(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
