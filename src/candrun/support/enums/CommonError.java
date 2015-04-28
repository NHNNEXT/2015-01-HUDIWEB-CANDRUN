package candrun.support.enums;

public enum CommonError {
	SERVER ("serverError"),
	UPLOAD ("uploadError");
	
	private final String value;
	
	private CommonError(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
