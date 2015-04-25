package candrun.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import candrun.model.Goal;
import candrun.model.Task;
import candrun.model.User;

@RequestMapping("/")
@Controller
public class MainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired GoalDAO goalDao;

	@Autowired TaskDAO taskDao;
	
	@Autowired UserDAO userDao;
	
	@RequestMapping(method = RequestMethod.GET)

	public String list(Model model, HttpSession session) {
		
		//TODO: 로그인까지 기능하면 session에서 email정보를 받아온다.
		//String email = (String) session.getAttribute("email");
		String email = "wq1021@naver.com";
		
		List<Goal> goals = goalDao.findRecentGoalsByEmail(email);
		
		//recent goals and tasks
		if(goals.size() > 0){
			Goal topGoal = goals.get(0);
			List<Task> tasks = taskDao.findTasksByGoalId(topGoal.getId());
			model.addAttribute("tasks", tasks);
		}
		
		//friends in nav  
		Map<String, List<User>> friendsListGroupByGoal = new HashMap<String, List<User>>();
		for(int i=0 ; i<goals.size();i++){
			Goal goal = goals.get(i);
			LOGGER.info("recent goal's id: {}",goal.getId());
			friendsListGroupByGoal.put("friends"+i, (userDao.findUsersByGoalId(goal.getId(),email)));
		}
		model.addAllAttributes(friendsListGroupByGoal);
		model.addAttribute("goals", goals);		
		
		return "home";		
	}
}
