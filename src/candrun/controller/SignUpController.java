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

@WebServlet("/addUser.cdr")
public class SignUpController extends HttpServlet{ 
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String email  = req.getParameter("email");
		String nickname = req.getParameter("nickname");
		String password = req.getParameter("password");
		
		User user = new User(email, nickname, password);		
		UserDAO userDao = new UserDAO();
		try {
			userDao.addUser(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 


		//System.out.println(email + " " + nickname + " " + password);
		
		//Connection con = userDAO.getConnection();
		resp.sendRedirect("signIn.jsp");

		
	}
}
