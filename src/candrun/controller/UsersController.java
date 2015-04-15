package candrun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.UserDAO;
import candrun.mail.MailService;
import candrun.mail.SHA256Encrypt;
import candrun.user.User;

@RestController("/users")
public class UsersController { 	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	UserDAO userDao;
	//이미 존재하는 회원일 때 exception처리 해주어야 한다. 

	@Autowired
	MailService mailService;
	
	@RequestMapping(method = RequestMethod.POST)
	protected String create(HttpServletRequest req, HttpServletResponse resp) {
		
		String email  = req.getParameter("email");
		String nickname = req.getParameter("nickname");
		String password = req.getParameter("password");
		String verifyKey = SHA256Encrypt.encrypt(nickname);

		User user = new User(email, nickname, password, verifyKey);		
		userDao.addPreliminaryUser(user);
		mailService.sendMail(email, verifyKey);
		
		return "pleaseVerifyEmail";	
	}
	
	@RequestMapping(method = RequestMethod.GET)
	protected String verify(HttpServletRequest req, HttpServletResponse resp) {
		String verifyKey = req.getParameter("verify_key");

		User user = userDao.findByVerifyKey(verifyKey);

		if (user != null) {
			userDao.addUser(user);
			LOGGER.info("user table으로 회원등록 완료.");
		}
		// 로직이 불완전하다. userDB에 있을 때 등의 예외처리가 필요
		return "signIn";
	}
}
