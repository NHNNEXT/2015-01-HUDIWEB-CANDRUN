package candrun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import candrun.model.User;
import candrun.service.FriendsService;
import candrun.support.enums.CommonInvar;

/**
 * show() 메서드는 실제 서비스에서는 사라질 메서드 입니다. 현재 있는 friends.jsp 는 includ 방식으로 다른 jsp에
 * 포함되어야 하고 각 페이제에서 이벤트로 getJson()을 호출하는 방식으로 사용될 것 입니다. 해당 작업시 getJson() 메서드의
 * requestmapping은 "/"로 수정해야 합니다.
 */

@RequestMapping("/friends")
@Controller
public class FriendsController {
	private static final Logger logger = LoggerFactory
			.getLogger(FriendsController.class);

	@Autowired
	FriendsService friendsService;

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Map<String, List<User>> getFriends(HttpSession session) {
		return friendsService
				.getFriends((String) session.getAttribute("email"));
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Map<String, String> request(HttpSession session,
			@RequestParam("rcvEmail") String rcvEmail) {
		Map<String, String> returnMsg = new HashMap<String, String>();
		returnMsg.put(CommonInvar.RETURNMSG.getValue(), friendsService
				.addFriend((String) session.getAttribute("email"), rcvEmail));
		return returnMsg;
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Map<String, List<User>> acceptRequest(HttpSession session, @RequestBody User friend) {
		friendsService.acceptRequest(friend.getEmail(), (String) session.getAttribute("email"));
		return friendsService.getFriends((String) session.getAttribute("email"));
	}
}
