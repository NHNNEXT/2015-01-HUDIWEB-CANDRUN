package candrun.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import candrun.service.UserService;
import candrun.support.enums.CommonError;
import candrun.support.enums.CommonInvar;

@RequestMapping("/auth")
@RestController
public class AuthController {
	public static final String SESSION_email = "email";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AuthController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public Map<String, String> signIn(HttpSession session, @RequestParam("email") String email,
			@RequestParam("password") String password) {
		
		Map<String, String> returnMsg = new HashMap<String, String>();
		String msg;

		try {
			msg = userService.login(email, password, session);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			msg = CommonError.SERVER.getValue();
		}
		LOGGER.debug(msg);
		
		returnMsg.put(CommonInvar.RETURNMSG.getValue(), msg);
		return returnMsg;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public void signOut(HttpSession session) {
		session.removeAttribute("email");
	}
}