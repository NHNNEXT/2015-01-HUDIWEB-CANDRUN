package candrun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import candrun.model.User;

/**
 * show() 메서드는 실제 서비스에서는 사라질 메서드 입니다.
 * 현재 있는 friends.jsp 는 includ 방식으로 다른 jsp에 포함되어야 하고
 * 각 페이제에서 이벤트로 getJson()을 호출하는 방식으로 사용될 것 입니다.
 * 해당 작업시 getJson() 메서드의 requestmapping은 "/"로 수정해야 합니다.
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
