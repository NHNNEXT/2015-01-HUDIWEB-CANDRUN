package candrun.controller;

import java.security.GeneralSecurityException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.dao.UserDAO;
import candrun.service.GoalService;
import candrun.service.HomeService;
import candrun.service.UserService;
import candrun.support.enums.Security;

@RequestMapping("/")
@Controller
public class MainController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MainController.class);

	@Autowired
	HomeService homeService;
	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String firstPage(Model model, HttpSession session) {

		try {
			if (!userService.isLogedIn(model, session)) {
				return "welcome";
			}
		} catch (GeneralSecurityException e) {
			LOGGER.error(e.toString());
			e.printStackTrace();
		}

		// 로긴/회원가입 시 사용했던 비공개 키 삭제
		session.removeAttribute(Security.RSA_PRI_KEY.getValue());
		
		LOGGER.debug("Login Successed.");
		
		String email = (String) session.getAttribute("email");
		homeService.setInitModel(model, email);
		
		return "home";
	}
}
