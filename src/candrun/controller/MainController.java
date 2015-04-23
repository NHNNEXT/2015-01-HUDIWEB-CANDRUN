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
import candrun.user.User;

@RequestMapping("/")
@Controller
public class MainController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Autowired
	GoalDAO goalDao;

	@Autowired
	TaskDAO taskDao;
	
	@Autowired
	UserDAO userDao;
	
	@RequestMapping(method = RequestMethod.GET)

	public String list(Model model, HttpSession session) {
		
		String email = (String) session.getAttribute("email");
		email = "test@email.com";
		
		List<Goal> goals = goalDao.findRecentGoalsByEmail(email);
		Goal topGoal = goals.get(0);
		
		List<Task> tasks = taskDao.findTasksByGoalId(topGoal.getId());
		Map<String, List<User>> friendsListGroupByGoal = new HashMap<String, List<User>>();
		
		for( int i=0 ; i<goals.size();i++){
			Goal goal = goals.get(i);
			LOGGER.info("{}",goal.getId());
			
			friendsListGroupByGoal.put("friends"+i, (userDao.findUsersByGoalId(goal.getId(),email)));
		}
	
		model.addAllAttributes(friendsListGroupByGoal);
		model.addAttribute("goals", goals);
		LOGGER.info("size: {}", tasks.size());
		model.addAttribute("tasks", tasks);

		return "home";		
	}
}
