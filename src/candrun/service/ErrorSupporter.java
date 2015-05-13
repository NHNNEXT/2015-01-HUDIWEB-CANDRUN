package candrun.service;

import javax.servlet.http.HttpSession;

import candrun.support.enums.CommonInvar;
import candrun.support.enums.UserErrorcode;

public class ErrorSupporter {

	private ErrorSupporter() {
	};

	public static UserErrorcode getUserErrorcode(HttpSession session) {
		Object errorCode = null;
		errorCode = session.getAttribute(CommonInvar.ERRORCODE.getValue());
		resetSession(session);
		return (UserErrorcode) errorCode;
	}

	public static String getReturMsg(UserErrorcode errorcode) {
		if (errorcode != null) {
			return errorcode.getValue();
		}
		return CommonInvar.SUCCESS.getValue();
	}
	
	public static String getReturnMsg(HttpSession session) {
		return getReturMsg(getUserErrorcode(session));
	}

	private static void resetSession(HttpSession session) {
		session.removeAttribute(CommonInvar.ERRORCODE.getValue());
	}
}
