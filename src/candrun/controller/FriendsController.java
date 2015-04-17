package candrun.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import candrun.model.User;

/**
 * Servlet implementation class GetFriendsController
 */

@RequestMapping("/friends")
@Controller
public class FriendsController {
  
	@RequestMapping(value="/getFriends.cdr", method=RequestMethod.GET)
	@ResponseBody
	protected User[] getJson() {
		User user1 = new User("test01@test.com", "nick01");
		User user2 = new User("test02@test.com", "nick02");
		User user3 = new User("test03@test.com", "nick03");
		User user4 = new User("test04@test.com", "nick04");
		User user5 = new User("test05@test.com", "nick05");
		User user6 = new User("test06@test.com", "nick06");
		User[] users = {user1, user2, user3, user4, user5, user6};
		
		return users;
	}
	@RequestMapping(value="/friends")
	protected String show() {
		return "friends";
	}
}
