package candrun.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.UserDAO;
import candrun.mail.CryptoUtil;
import candrun.mail.MailService;
import candrun.model.User;
import candrun.service.user.UserService;
import candrun.support.enums.CommonInvar;

@RequestMapping("/users")
@Controller
public class UsersController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UsersController.class);

	@Autowired
	private UserDAO userDao;
	// 이미 존재하는 회원일 때 exception처리 해주어야 한다.

	@Autowired
	private MailService mailService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(@RequestParam("email") String email,
			@RequestParam("nickname") String nickname,
			@RequestParam("password") String password, HttpSession session) {

		Map<String, String> returnMsg = new HashMap<String, String>();
		String msg;
		
		msg = userService.register(email, nickname, password, session);
		LOGGER.debug(msg);
		
		mailService.putMailBodyElement(email);
		mailService.sendMail(email);
		
		returnMsg.put(CommonInvar.RETURNMSG.getValue(), msg);
		return returnMsg;
	}

	// @RequestMapping(method = RequestMethod.POST)
	// public String create(@RequestParam("email") String email,
	// @RequestParam("nickname") String nickname,
	// @RequestParam("password") String password) {
	//
	// userDao.addUser(new User(email, nickname, password));
	//
	// try {
	// String key = CryptoUtil.encrypt(email);
	// mailService.sendMail(email, key);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return "pleaseVerifyEmail";
	// }
	//
	@RequestMapping(value = "/{key}/verify", method = RequestMethod.GET)
	public String verify(@PathVariable("key") String key, HttpSession session) {

		String email = null;
		try {
			email = CryptoUtil.decrypt(key);
			LOGGER.info("{} 회원등록 완료", email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User user = userDao.findByEmail(email);

		if (user != null && user.getState() == 0) {
			userDao.changeState(user.getEmail());
			session.setAttribute("verified", "success");
		}
		// user의 state 값 1로 증가
		// 로직이 불완전하다. userDB에 있을 때 등의 예외처리가 필요

		return "redirect";
	}

}
