package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.dao.UserDAO;
import candrun.user.User;

@Controller("/VerifySignUp.cdr")
public class VerifySignUpController {

	@RequestMapping(method=RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String verifyKey = req.getParameter("verify_key");
		
		UserDAO userDao = new UserDAO();
		User user = null;
		try {
			user = userDao.findByVerifyKey(verifyKey);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(user==null){
			//exception처리 해주어야 한다.
		} else {
			try {
				userDao.addUser(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		resp.sendRedirect("signIn.jsp");	
	}
}
