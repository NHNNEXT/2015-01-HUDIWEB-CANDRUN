package candrun.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;

@RequestMapping("/tasks")
@RestController
public class TasksController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);
	
	@Autowired
	TaskDAO taskDao;
	
	@Autowired
	GoalDAO goalDao;

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public String update(@PathVariable String id) {
		int taskId = Integer.parseInt(id);
		LOGGER.debug("taskId: {}", taskId);	
		taskDao.addNudge(taskId);

		return "showGoalAndTasks";
	}
}
