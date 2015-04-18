package candrun.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;
import candrun.model.Task;

@RequestMapping("/")
@Controller
public class MainController {


	@Autowired
	GoalDAO goalDao;

	@Autowired
	TaskDAO taskDao;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String list(HttpServletRequest req, HttpServletResponse resp) {
		
		Goal topGoal = goalDao.findRecentGoal();
		List<Task> task = taskDao.findTasksByGoalId(topGoal.getId());
		req.setAttribute("goal", topGoal);
		req.setAttribute("tasks", taskDao.findTasksByGoalId(topGoal.getId()));

		return "home";
	}
}
