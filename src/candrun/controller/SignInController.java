package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.user.PasswordMismatchException;
import candrun.user.User;
import candrun.user.UserNotFoundException;

@Controller
public class SignInController {
	public static final String SESSION_email = "email";
	private static final Logger LOGGER = LoggerFactory.getLogger(SignInController.class);

	@RequestMapping(value = "/signin.cdr", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			User.login(email, password);
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			response.sendRedirect("/showGoalAndTasks.jsp");
		} catch (UserNotFoundException e) {
			LOGGER.debug("존재하지 않는 사용자 입니다. 다시 로그인하세요.");
			forwardJSP(request, response, "존재하지 않는 사용자 입니다. 다시 로그인하세요.");
		} catch (PasswordMismatchException e) {
			LOGGER.debug("비밀번호가 틀립니다. 다시 로그인하세요.");
			forwardJSP(request, response, "비밀번호가 틀립니다. 다시 로그인하세요.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void forwardJSP(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws ServletException, IOException {
		request.setAttribute("errorMessage", errorMessage);
		RequestDispatcher rd = request.getRequestDispatcher("/signIn.jsp");
		rd.forward(request, response);
	}

}