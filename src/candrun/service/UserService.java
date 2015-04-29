package candrun.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import candrun.dao.UserDAO;
import candrun.model.User;
import candrun.support.enums.CommonError;
import candrun.support.enums.CommonInvar;
import candrun.support.enums.UserErrorcode;

public class UserService {
	private static final Logger logger = LoggerFactory
			.getLogger(UserService.class);

	private UserDAO userDao;

	public UserService(UserDAO userDao) {
		this.userDao = userDao;
	}

	public String login(String email, String pw, HttpSession session)
			throws Exception {
		User user;
		int userState;

		try {
			user = userDao.findByEmail(email);
		} catch (EmptyResultDataAccessException e) {
			logger.error(e.toString());
			return UserErrorcode.EMPTY.getValue();
		}
		if (!(user.getPassword().equals(SecurityService.encrypt(pw, pw)))) {
			return UserErrorcode.WRONG_PW.getValue();
		}

		userState = user.getState();
		if (userState == 0) {
			return UserErrorcode.NOT_YET_CERTI.getValue();
		}
		if (userState == 1) {
			session.setAttribute("email", email);
			return CommonInvar.SUCCESS.getValue();
		}
		return CommonInvar.DEFAULT.getValue();
	}

	public String register(String email, String pw, String nick,
			MultipartFile file, HttpServletRequest req) {
		String picPath = "";
		try {
			picPath = makePicPath(email, file, req);
			userDao.addUser(new User(email, nick, SecurityService.encrypt(pw,
					pw), picPath));
		} catch (DuplicateKeyException e) {
			logger.error(e.toString());
			return UserErrorcode.DUP.getValue();
		} catch (IOException e) {
			logger.error(e.toString());
			return CommonError.UPLOAD.getValue();
		} catch (Exception e) {
			logger.error(e.toString());
			return CommonError.SERVER.getValue();
		}
		return CommonInvar.SUCCESS.getValue();
	}

	public String makePicPath(String email, MultipartFile file,
			HttpServletRequest req) throws IOException {
		String originalFilename = file.getOriginalFilename();
		String extension = originalFilename.substring(
				originalFilename.lastIndexOf('.'), originalFilename.length());
		String pathName = IoService.makePicPathName(email, extension, req);
		IoService.saveMultipartFile(file, pathName);
		return email + extension;
	}

	public boolean isLogedIn(Model model, HttpSession session)
			throws GeneralSecurityException {
		if (session.getAttribute("email") == null
				|| session.getAttribute("email") == "") {
			SecurityService.setRAS(session, model);
			return false;
		}
		return true;
	}
}
