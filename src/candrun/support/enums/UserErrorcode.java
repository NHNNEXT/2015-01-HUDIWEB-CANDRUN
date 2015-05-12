package candrun.support.enums;

/*
 * @see candrun.model.User
 * @see candrun.model.UserTest
 * @see candrun.service.UserService
 * 
 * @see candrun.support.enums.Value
 * @see candrun.support.enums.CommonInvar
 * 
 * enum interfcae를 구성하여 지정하면 Vaule타입으로 통합될 수 있습니
 */ 

//public enum UserErrorcode implements Value { 
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
