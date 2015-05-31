package candrun.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import candrun.dao.UserDAO;
import candrun.model.GoalRelation;

public class HomeService {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeService.class);
	private UserDAO userDao;
	private GoalService goalService;
	private TaskService taskService;
	private FriendsService friendsService;

	public HomeService(UserDAO userDao, GoalService goalService,
			TaskService taskService, FriendsService friendsService) {
		this.userDao = userDao;
		this.goalService = goalService;
		this.taskService = taskService;
		this.friendsService = friendsService;
	}

	public void setInitModel(Model model, String email) {
		// 현재 로긴한 유저 로드
		model.addAttribute("user", userDao.getByEmail(email));
		// 현재 유효한 골 관계 로드
		List<GoalRelation> goalRelations = goalService.getGoalRelations(email);
		model.addAttribute("goalRelations", goalRelations);
		if (goalRelations.isEmpty())
			return;
		// 첫번째 goal의 tasks 로드
		model.addAttribute(
				"tasks",
				taskService.getTasksByGoalId(goalRelations.get(0).getMyGoal()
						.getId()));
		// 친구목록 로드
		model.addAttribute("friends", friendsService.getFriends(email));

		logger.debug("goalrelations: {}", goalRelations);
		logger.debug(
				"taskes: {}",
				taskService.getTasksByGoalId(goalRelations.get(0).getMyGoal()
						.getId()));
	}
}
