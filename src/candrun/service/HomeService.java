package candrun.service;

import java.util.List;

import org.springframework.ui.Model;

import candrun.dao.TaskDAO;
import candrun.dao.UserDAO;
import candrun.model.GoalRelation;

public class HomeService {

	private UserDAO userDao;
	private TaskDAO taskDao;
	private GoalService goalService;

	public HomeService(UserDAO userDao, TaskDAO taskDao, GoalService goalService) {
		this.userDao = userDao;
		this.taskDao = taskDao;
		this.goalService = goalService;
	}

	public void setInitModel(Model model, String email) {
		// 현재 로긴한 유저 로드
		model.addAttribute("user", userDao.getByEmail(email));
		// 현재 유효한 골 관계 로드
		List<GoalRelation> goalRelations = goalService.getGoalRelations(email);
		model.addAttribute("goalRelations", goalRelations);
		// 첫번째 goal의 tasks 로드
		model.addAttribute(
				"tasks",
				taskDao.getTasksByGoalId(goalRelations.get(0).getMyGoal()
						.getId()));
	}
}
