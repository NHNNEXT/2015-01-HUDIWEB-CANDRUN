package candrun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import candrun.service.GoalService;
import candrun.service.TaskService;

@RequestMapping("/tasks")
@RestController
/*
 * 일반적으로 Controller 레벨에서
 * 비지니스 로직을 처리하거나, repository 를 바로 불러오지 않습니다.
 * 
 * controller는 의미그대로 '흐름'만 명확하게 가지는게 좋습니다.
 */
public class TasksController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);
	
	//@Autowired TaskDAO taskDao;
	@Autowired
	private TaskService taskService;
	
	//@Autowired GoalDAO goalDao;
	@Autowired
	private GoalService goalService;

	@RequestMapping(method = RequestMethod.POST)
	/*
	 * 아시겠지만 필요없는 HttpServletRequest, HttpServletResponse는 포함하지 않아도 될것 같아요.
	 * 대신 @RequestParam을 사용하는게 좋겠네요.
	 * 
	public Task update(HttpServletRequest req, HttpServletResponse resp) {
	 */
	public Task update(@RequestParam("tasksId") int taskId) {
		//LOGGER.info("add Nudge: task Id = { }", req.getParameter("tasksId"));
		LOGGER.info("add Nudge: task Id = { }", taskId);
		//int taskId = Integer.parseInt(req.getParameter("tasksId"));
//		taskDao.addNudge(taskId);
		taskService.addNudge(taskId);
		
//		return taskDao.findTaskByTaskId(taskId);
		return taskService.findTaskByTaskId(taskId);
	}
}
