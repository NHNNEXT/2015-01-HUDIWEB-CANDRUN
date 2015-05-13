package candrun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.dao.UserDAO;
import candrun.model.Goal;
import candrun.model.GoalRelation;
import candrun.model.Task;
import candrun.model.User;
import candrun.support.enums.GoalState;
import candrun.support.enums.RelationRequestState;

public class GoalService {
	private static final Logger logger = LoggerFactory
			.getLogger(GoalService.class);

	private GoalDAO goalDao;
	private UserDAO userDao;
	private TaskDAO taskDao;

	public GoalService(GoalDAO goalDao, UserDAO userDao, TaskDAO taskDao) {
		this.goalDao = goalDao;
		this.userDao = userDao;
		this.taskDao = taskDao;
	}

	public List<GoalRelation> getGoalRelations(String email) {
		List<GoalRelation> goalRelations = new ArrayList<GoalRelation>();
		List<Goal> myGoals = getCurrentDisplayGoals(email);
		myGoals.forEach((goal) -> goalRelations.add(getGoalRelation(goal)));
		logger.debug(TransactionSynchronizationManager
				.isActualTransactionActive() + "");
		return goalRelations;
	}

	public List<Goal> getCurrentDisplayGoals(String email) {
		List<Goal> goals = goalDao.getGoals(email, GoalState.REGISTERED);
		goals.addAll(goalDao.getGoals(email, GoalState.STARTED));
		return goals;
	}
	
	public Goal getGoal(int goalId) {
		return goalDao.getGoalById(goalId);
	}

	private GoalRelation getGoalRelation(Goal myGoal) {
		List<Goal> acceptedRelationGoals = new ArrayList<Goal>();
		Map<User, Goal> relation = new HashMap<User, Goal>();
		List<Integer> acceptedRelationGoalIds = goalDao.getRelatedGoals(
				myGoal.getId(), RelationRequestState.ACCEPTED);

		acceptedRelationGoalIds.forEach((goalId) -> acceptedRelationGoals
				.add(goalDao.getGoalById(goalId)));
		acceptedRelationGoals.forEach((goal) -> relation.put(
				userDao.getByEmail(goal.getEmail()), goal));
		return new GoalRelation(myGoal, relation);
	}

	public Goal addGoalAndTasks(String goalContents, String[] tasks, String userEmail) {
		
		Goal goal = new Goal(goalContents, userEmail);
		int returnedId = goalDao.addGoal(goal);

		for (String task : tasks) {
			taskDao.addTask(new Task(task, returnedId));
		}
		return goal;
	}
}
