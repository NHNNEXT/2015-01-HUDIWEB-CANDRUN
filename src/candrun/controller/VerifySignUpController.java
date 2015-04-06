package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import candrun.dao.UserDAO;
import candrun.user.User;

@WebServlet("/VerifySignUp.cdr")
public class VerifySignUpController extends HttpServlet {

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
		
		resp.sendRedirect("showGoalAndTasks.jsp");	
	}
}
