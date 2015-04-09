package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import candrun.user.PasswordMismatchException;
import candrun.user.User;
import candrun.user.UserNotFoundException;

@WebServlet("/signin.cdr")
public class SignInController {
	public static final String SESSION_email = "email";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			User.login(email, password);
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			response.sendRedirect("/showGoalAndTasks.jsp");
		} catch (UserNotFoundException e) {
			System.out.println("존재하지 않는 사용자 입니다. 다시 로그인하세요.");
			forwardJSP(request, response, "존재하지 않는 사용자 입니다. 다시 로그인하세요.");
		} catch (PasswordMismatchException e) {
			System.out.println("비밀번호가 틀립니다. 다시 로그인하세요.");
			forwardJSP(request, response, "비밀번호가 틀립니다. 다시 로그인하세요.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void forwardJSP(HttpServletRequest request,
			HttpServletResponse response, String errorMessage) throws ServletException, IOException {
		request.setAttribute("errorMessage", errorMessage);
		RequestDispatcher rd = request.getRequestDispatcher("/signIn.jsp");
		rd.forward(request, response);
	}


}