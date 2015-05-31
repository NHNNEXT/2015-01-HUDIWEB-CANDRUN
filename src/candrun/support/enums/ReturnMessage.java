package candrun.support.enums;

public enum ReturnMessage {
	REQUESTED ("이미 친구 신청을 한 상태 입니다."),
	ACCEPTED ("이미 친구 입니다."),
	MAKEREQUEST ("친구 신청을 하였습니다."),
	NULL ("우리 서비스에 가입한 친구가 아닙니다.");

	private final String value;
	
	private ReturnMessage(String msg) {
		this.value =  msg;
	}
	
	public String getValue() {
		return this.value;
	}
}
