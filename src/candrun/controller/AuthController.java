package candrun.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import candrun.exception.PasswordMismatchException;
import candrun.exception.UserNotFoundException;
import candrun.model.User;

@RequestMapping("/auth")
@RestController
public class AuthController {
	public static final String SESSION_email = "email";
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@RequestMapping(method = RequestMethod.POST)
	public String signIn(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {

		try {
			User.login(email, password);
			session.setAttribute("email", email);
			return "redirect: home";
		} catch (UserNotFoundException e) {
			LOGGER.debug("존재하지 않는 사용자 입니다. 다시 로그인하세요.");
			model.addAttribute("errorMessage", "존재하지 않는 사용자 입니다. 다시 로그인하세요.");
			return "signIn";
		} catch (PasswordMismatchException e) {
			LOGGER.debug("비밀번호가 틀립니다. 다시 로그인하세요.");
			model.addAttribute("errorMessage", "비밀번호가 틀립니다. 다시 로그인하세요.");
			return "signIn";
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public String signOut(HttpSession session) throws IOException {
		session.removeAttribute("email");
		return "redirect: signIn";
	}
}