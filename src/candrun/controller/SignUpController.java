package candrun.controller;

import java.io.IOException;


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
		
		UserDAO userDAO = new UserDAO();
		User user = new User(email, nickname, password);		

		//System.out.println(email + " " + nickname + " " + password);
		
		//Connection con = userDAO.getConnection();
		resp.sendRedirect("signIn.jsp");


	}
}
