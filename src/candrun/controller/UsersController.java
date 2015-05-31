package candrun.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import candrun.dao.UserDAO;
import candrun.mail.CryptoUtil;
import candrun.mail.MailService;
import candrun.model.User;
import candrun.service.UserService;
import candrun.support.enums.CommonInvar;
import candrun.support.enums.UserState;

@RequestMapping("/users")
@Controller
public class UsersController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UsersController.class);

	@Autowired
	private UserDAO userDao;
	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> create(HttpSession session,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("nickname") String nickname,
			@RequestParam("pic") MultipartFile file, HttpServletRequest request)
			throws Exception {
		Map<String, String> returnMsg = new HashMap<String, String>();
		String msg;
		
		msg = userService.register(email, password, nickname, file, request);
		LOGGER.debug(msg);
		
		if(msg==CommonInvar.SUCCESS.getValue()){
			mailService.putMailBodyElement(email);
			mailService.setMailReceiver(email);
			taskExecutor.execute(mailService);
		}

		returnMsg.put(CommonInvar.RETURNMSG.getValue(), msg);
		return returnMsg;
	}

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
		User user = userDao.getByEmail(email);

		if (user != null && user.getState() == 0) {
			userDao.changeState(user.getEmail(), UserState.CERTIED);
			session.setAttribute("verified", "success");
		}
		// user의 state 값 1로 증가
		// 로직이 불완전하다. userDB에 있을 때 등의 예외처리가 필요

		return "redirect";
	}
}
