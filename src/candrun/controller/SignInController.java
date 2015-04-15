package candrun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.exception.PasswordMismatchException;
import candrun.exception.UserNotFoundException;
import candrun.user.User;

@Controller
public class SignInController {
	public static final String SESSION_email = "email";
	private static final Logger LOGGER = LoggerFactory.getLogger(SignInController.class);

	@RequestMapping(value = "/signin.cdr", method = RequestMethod.POST)
	protected String doPost(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			User.login(email, password);
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			return "redirect: showGoalAndTasks";
		} catch (UserNotFoundException e) {
			LOGGER.debug("존재하지 않는 사용자 입니다. 다시 로그인하세요.");
			request.setAttribute("errorMessage", "존재하지 않는 사용자 입니다. 다시 로그인하세요.");
			return "signIn";
		} catch (PasswordMismatchException e) {
			LOGGER.debug("비밀번호가 틀립니다. 다시 로그인하세요.");
			request.setAttribute("errorMessage", "비밀번호가 틀립니다. 다시 로그인하세요.");
			return "signIn";
		}
	}
}