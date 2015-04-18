package candrun.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.UserDAO;
import candrun.mail.MailService;
import candrun.mail.SHA256Encrypt;
import candrun.user.User;

@RequestMapping("/users")
@RestController
public class UsersController { 	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	UserDAO userDao;
	//이미 존재하는 회원일 때 exception처리 해주어야 한다. 

	@Autowired
	MailService mailService;
	
	@RequestMapping(method = RequestMethod.POST)
	public String create(@RequestParam("email") String email, @RequestParam("nickname") String nickname, @RequestParam("password") String password) {

		String verifyKey = SHA256Encrypt.encrypt(nickname);
		User user = new User(email, nickname, password, verifyKey);		

		//TODO: user 테이블에서 user를 상태값에 따라 관리하도록 바꾼다. 
		userDao.addPreliminaryUser(user);
		mailService.sendMail(email, verifyKey);
		
		return "pleaseVerifyEmail";	
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String verify(@RequestParam("verify_key") String verifyKey) {

		User user = userDao.findByVerifyKey(verifyKey);

		if (user != null) {
			userDao.addUser(user);
			LOGGER.info("user table으로 회원등록 완료.");
		}
		// 로직이 불완전하다. userDB에 있을 때 등의 예외처리가 필요
		return "signIn";
	}
}
