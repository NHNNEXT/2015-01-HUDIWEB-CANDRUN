package candrun.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;
import candrun.model.GoalWithTasks;
import candrun.service.GoalService;
import candrun.service.TaskService;

@RequestMapping("/goals")
@RestController
public class GoalsController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TasksController.class);
	private static final int maxTasksNumber = 5;
	@Autowired
	GoalDAO goalDao;
	@Autowired
	TaskDAO taskDao;
	@Autowired
	GoalService goalService;
	@Autowired
	TaskService taskService;

	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestParam("goal_contents") String goalContents,
			HttpServletRequest req, HttpSession session) {
		String userEmail = (String) session.getAttribute("email");

		ArrayList<String> tasks = new ArrayList<String>();

		for (int i = 0; i < maxTasksNumber; i++) {
			String taskContents = req.getParameter("task_contents_" + i);
			if (taskContents == null) break;
			tasks.add(taskContents);
		}
		
		//TODO: 친구추가하는 기능과 합쳐서 GoalRelation 객체로 리턴해주어야 한다. 
		Goal newGoal = goalService.addGoalAndTasks(goalContents, tasks, userEmail);
		return newGoal;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public GoalWithTasks read(@PathVariable("id") int goalId) {
		return new GoalWithTasks(goalService.getGoal(goalId), taskService.getTasks(goalId));
	}
}
