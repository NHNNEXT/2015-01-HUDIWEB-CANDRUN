package candrun.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import candrun.dao.UserDAO;

import candrun.model.User;
import candrun.service.FriendsService;


/**
 * show() 메서드는 실제 서비스에서는 사라질 메서드 입니다.
 * 현재 있는 friends.jsp 는 includ 방식으로 다른 jsp에 포함되어야 하고
 * 각 페이제에서 이벤트로 getJson()을 호출하는 방식으로 사용될 것 입니다.
 * 해당 작업시 getJson() 메서드의 requestmapping은 "/"로 수정해야 합니다.
 */

@RequestMapping("/friends")
@Controller
public class FriendsController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FriendsController.class);
	@Autowired
	UserDAO userDao;
	@Autowired
	FriendsService friendsService;
	
	@RequestMapping(value="/getFriends.cdr", method=RequestMethod.GET)
	@ResponseBody
	public List<User> showfriends(HttpSession session) { 
		String userEmail = (String) session.getAttribute("email");
		List<User> friendsList = friendsService.getFriends(userEmail);
		
		for (int i = 0; i < friendsList.size(); i++) {
		    System.out.println(friendsList.get(i));
		}
		
		return friendsList; //친구 list 반환 
	}

}


