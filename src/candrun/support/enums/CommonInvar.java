package candrun.support.enums;

/*
 * @see candrun.model.User
 * @see candrun.model.UserTest
 * @see candrun.service.UserService
 * 
 * @see candrun.support.enums.Value
 * @see candrun.support.enums.UserErrorcode
 * 
 * enum interfcae를 구성하여 지정하면 Vaule타입으로 통합될 수 있습니다
 */

//public enum CommonInvar implements Value {
public enum CommonInvar {
	ERRORCODE ("errorCode"),
	RETURNMSG ("returnMsg"),
	SUCCESS ("success"),
	DEFAULT ("default");
	
	private final String value;
	
	private CommonInvar(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
