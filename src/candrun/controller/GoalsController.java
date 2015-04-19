package candrun.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;
import candrun.model.Task;

@RequestMapping("/goals")
@Controller
public class GoalsController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NudgesController.class);

	@Autowired
	GoalDAO goalDao;

	@Autowired
	TaskDAO taskDao;

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
		List<Task> task = taskDao.findTasksByGoalId(topGoal.getId());
		req.setAttribute("goal", topGoal);
		req.setAttribute("tasks", taskDao.findTasksByGoalId(topGoal.getId()));
		for(int i=0; i< task.size();i++){
			System.out.println(task.get(i)+"nbhhhhhhhh");
		}
		return "showGoalAndTasks";
	}
}
