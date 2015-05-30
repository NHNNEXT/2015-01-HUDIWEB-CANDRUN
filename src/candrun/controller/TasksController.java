package candrun.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Task;
import candrun.model.TaskLog;
import candrun.service.TaskService;

@RequestMapping("/tasks")
@RestController
public class TasksController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

	@Autowired
	TaskDAO taskDao;
	@Autowired
	GoalDAO goalDao;
	@Autowired
	TaskService taskService;

	@RequestMapping(method = RequestMethod.POST)
	public Task update(@RequestParam("tasksId") String tasksId, @RequestParam("goalOwnerEmail") String goalOwnerEmail,
			HttpSession session) {

		int taskId = Integer.parseInt(tasksId);
		String userEmail = (String) session.getAttribute("email");
		taskService.handleTaskRequest(taskId, userEmail, goalOwnerEmail);
		Task updatedTask = taskDao.getTaskByTaskId(taskId);

		return updatedTask;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<List<TaskLog>> logList(@RequestParam("taskIds[]") String[] taskIds) {

		List<List<TaskLog>> logList = new ArrayList<List<TaskLog>>();
		List<TaskLog> taskLog = null;
		for (String strTaskId : taskIds) {
			taskLog = taskDao.getTaskLogsByTaskId(Integer.parseInt(strTaskId));
			if (!taskLog.isEmpty()) {
				logList.add(taskLog);
			}
		}
		return logList;
	}
}
