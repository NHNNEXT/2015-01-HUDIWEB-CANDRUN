package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import candrun.dao.UserDAO;
import candrun.email.EmailService;
import candrun.email.SHA256Encrypt;
import candrun.user.PreliminaryUser;
import candrun.user.User;

@Controller("/addUser.cdr")
public class SignUpController { 
	
	@Autowired
	UserDAO userDao;
	//이미 존재하는 회원일 때 exception처리 해주어야 한다. 
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String email  = req.getParameter("email");
		String nickname = req.getParameter("nickname");
		String password = req.getParameter("password");
		String verifyKey = SHA256Encrypt.encrypt(nickname);

		User user = new PreliminaryUser(email, nickname, password, verifyKey);		
		try {
			userDao.addPreliminaryUser((PreliminaryUser) user);
			//interface로 바꾸어야 한다. 캐스팅은 좋지 않은 것 같다.
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 
		
		EmailService emailservice = new EmailService();
		emailservice.sendEmail(this.getClass().getSimpleName(), verifyKey, email);
		
		resp.sendRedirect("pleaseVerifyEmail.jsp");	
	}
	
	
}
