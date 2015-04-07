package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import candrun.dao.UserDAO;
import candrun.email.EmailService;
import candrun.email.SHA256Encrypt;
import candrun.user.PreliminaryUser;
import candrun.user.User;

@WebServlet("/addUser.cdr")
public class SignUpController extends HttpServlet{ 
	
	//이미 존재하는 회원일 때 exception처리 해주어야 한다. 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String email  = req.getParameter("email");
		String nickname = req.getParameter("nickname");
		String password = req.getParameter("password");
		String verifyKey = SHA256Encrypt.encrypt(nickname);

		User user = new PreliminaryUser(email, nickname, password, verifyKey);		
		UserDAO userDao = new UserDAO();
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
