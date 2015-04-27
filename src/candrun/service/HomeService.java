package candrun.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.dao.UserDAO;
import candrun.model.Goal;
import candrun.model.User;

public class HomeService {
	private static final Logger logger = LoggerFactory
			.getLogger(HomeService.class);

	private UserDAO userDao;
	private GoalDAO goalDao;
	private TaskDAO taskDao;

	public HomeService(UserDAO userDao, GoalDAO goalDao, TaskDAO taskDao) {
		this.userDao = userDao;
		this.goalDao = goalDao;
		this.taskDao = taskDao;
	}

	public void setInitModel(Model model, String email) {
		List<Goal> goals = goalDao.findRecentGoalsByEmail(email);
		Map<String, List<User>> friendsListGroupByGoal = new HashMap<String, List<User>>();
		for (int i = 0; i < goals.size(); i++) {
			friendsListGroupByGoal.put("friends" + i,
					(userDao.findUsersByGoalId(goals.get(i).getId(), email)));
		}
		model.addAttribute("goals", goals);
		model.addAttribute("tasks",
				taskDao.findTasksByGoalId(goals.get(0).getId()));
		model.addAllAttributes(friendsListGroupByGoal);
	}
}
