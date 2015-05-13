package candrun.support.enums;

public enum Security {
	RSA_PRI_KEY ("rsaPriKey"),
	RSA_PUB_KEY ("rsaPubKey");
	
	private final String value;
	
	private Security(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
