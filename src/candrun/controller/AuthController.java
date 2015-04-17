package candrun.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import candrun.exception.PasswordMismatchException;
import candrun.exception.UserNotFoundException;
import candrun.user.User;


@RequestMapping("/auth")
@RestController
public class AuthController {
	public static final String SESSION_email = "email";
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(method = RequestMethod.POST)
	protected String signIn(HttpServletRequest request, HttpServletResponse response) {
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
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void signOut(HttpServletRequest req, HttpServletResponse response) throws IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("email");
		response.sendRedirect("/signIn.jsp");
	}
}