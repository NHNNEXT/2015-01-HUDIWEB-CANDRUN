package candrun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;

@Controller
public class ShowGoalController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShowGoalController.class);

	@Autowired
	GoalDAO goalDao;

	@Autowired
	TaskDAO taskDao;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	protected String show(HttpServletRequest req, HttpServletResponse resp) {
		
		Goal topGoal = goalDao.findRecentGoal();

		// forward하여 내용을 jsp에 뿌린다.
		req.setAttribute("goal", topGoal);
		req.setAttribute("tasks", taskDao.findTasksByGoalId(topGoal.getId()));

		return "showGoalAndTasks";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		int tasksId = Integer.parseInt(req.getParameter("tasksId"));
		LOGGER.debug("taskId: {}", tasksId);

		taskDao.addNudge(tasksId);
	}
}