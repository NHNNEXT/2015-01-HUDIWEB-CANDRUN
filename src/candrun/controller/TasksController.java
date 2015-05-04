package candrun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Task;

@RequestMapping("/tasks")
@RestController
public class TasksController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);
	
	@Autowired TaskDAO taskDao;
	
	@Autowired GoalDAO goalDao;

	@RequestMapping(method = RequestMethod.POST)
	public Task update(HttpServletRequest req, HttpServletResponse resp) {
		int taskId = Integer.parseInt(req.getParameter("tasksId"));
		taskDao.addNudge(taskId);
		return taskDao.getTaskByTaskId(taskId);
	}
}
