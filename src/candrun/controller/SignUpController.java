package candrun.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.dao.UserDAO;
import candrun.mail.MailService;
import candrun.mail.SHA256Encrypt;
import candrun.user.User;

@Controller
public class SignUpController { 
	
	@Autowired
	UserDAO userDao;
	//이미 존재하는 회원일 때 exception처리 해주어야 한다. 

	@Autowired
	MailService mailService;
	
	@RequestMapping(value="/addUser.cdr", method=RequestMethod.POST)
	protected String doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String email  = req.getParameter("email");
		String nickname = req.getParameter("nickname");
		String password = req.getParameter("password");
		String verifyKey = SHA256Encrypt.encrypt(nickname);

		User user = new User(email, nickname, password, verifyKey);		
		userDao.addPreliminaryUser(user);
	
		mailService.sendMail(email, verifyKey);
		
		return "pleaseVerifyEmail";	
	}
}
