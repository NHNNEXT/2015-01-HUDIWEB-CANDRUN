package candrun.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("/logout.cdr")
public class LogoutController {

	@RequestMapping(method=RequestMethod.GET)
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.removeAttribute("email");
		response.sendRedirect("/signIn.jsp");
	}
}