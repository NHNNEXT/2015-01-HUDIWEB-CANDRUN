package candrun.service.user;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import candrun.dao.UserDAO;
import candrun.model.User;
import candrun.support.enums.CommonInvar;
import candrun.support.enums.UserErrorcode;

public class UserService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);

	private UserDAO userDAO;

	public UserService(UserDAO userDao) {
		this.userDAO = userDao;
	}
	
	public String login(String email, String pw, HttpSession session) {
		User user;
		int userState;
		try {
			user = userDAO.findByEmail(email);
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.toString());
			return UserErrorcode.EMPTY.getValue();
		}
		if (!(user.getPassword().equals(pw))) {
			return UserErrorcode.WRONG_PW.getValue();
		}
		
		userState = user.getState();
		if (userState == 0) {
			return UserErrorcode.NOT_YET_CERTI.getValue();
		}
		if (userState == 1) {
			logger.debug("password is right.");
			session.setAttribute(email, email);
			return CommonInvar.SUCCESS.getValue();
		}
		return CommonInvar.DEFAULT.getValue();
	}
	
	public String register(String email, String nick, String pw,
			HttpSession session) {
		try {
			userDAO.addUser(new User(email, nick, pw));
		} catch (DuplicateKeyException e) {
			logger.error(e.toString());
			return UserErrorcode.DUP.getValue();
		}
		return CommonInvar.SUCCESS.getValue();
	}
}
