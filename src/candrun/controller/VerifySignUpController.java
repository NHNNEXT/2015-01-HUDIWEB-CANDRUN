package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.dao.UserDAO;
import candrun.user.User;

@Controller
public class VerifySignUpController {
	private static final Logger LOGGER = LoggerFactory.getLogger(VerifySignUpController.class);

	@Autowired
	UserDAO userDao;

	@RequestMapping(value="/VerifySignUp.cdr", method=RequestMethod.GET)
	protected String doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String verifyKey = req.getParameter("verify_key");
		
		try {
			User user = userDao.findByVerifyKey(verifyKey);
			
			if(user!=null){
				userDao.addUser(user);
				LOGGER.info("user table으로 회원등록 완료.");
			}
			//로직이 불완전하다. userDB에 있을 때 등의 예외처리가 필요
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "signIn";	
	}
}
