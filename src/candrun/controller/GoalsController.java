package candrun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;
import candrun.model.Task;

@RestController("/goals")
public class GoalsController {

	@Autowired
	GoalDAO goalDao;

	@Autowired
	TaskDAO taskDao;

	// goal 추가 후, 다음 페이지로 연결해주어야 한다.
	@RequestMapping(method = RequestMethod.POST)
	public void create(HttpServletRequest req, HttpServletResponse resp){

		String goalContents = req.getParameter("goal_contents");
		String taskContents = req.getParameter("task_contents");
		String[] arrTaskContents = taskContents.split(",");

		Goal goal = new Goal(goalContents, "email");
		int returnedId = goalDao.addGoal(goal);
		
		for (int i = 0; i < arrTaskContents.length; i++) {
			taskDao.addTask(new Task(arrTaskContents[i], returnedId));
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	protected String list(HttpServletRequest req, HttpServletResponse resp) {
		
		Goal topGoal = goalDao.findRecentGoal();

		// forward하여 내용을 jsp에 뿌린다.
		req.setAttribute("goal", topGoal);
		req.setAttribute("tasks", taskDao.findTasksByGoalId(topGoal.getId()));

		return "showGoalAndTasks";
	}
}
