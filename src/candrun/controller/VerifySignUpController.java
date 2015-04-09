package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.dao.UserDAO;
import candrun.user.User;

@Controller
public class VerifySignUpController {

	@Autowired
	UserDAO userDao;

	@RequestMapping(value="/VerifySignUp.cdr", method=RequestMethod.GET)
	protected String doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String verifyKey = req.getParameter("verify_key");
		
		try {
			User user = userDao.findByVerifyKey(verifyKey);
			if(user==null){
				userDao.addUser(user);
			}
			//로직이 불완전하다. userDB에 있을 때 등의 예외처리가 필요
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "signIn.jsp";	
	}
}
