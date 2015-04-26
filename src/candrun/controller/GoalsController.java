package candrun.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;
import candrun.model.Task;

@RequestMapping("/goals")
@RestController
public class GoalsController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TasksController.class);

	@Autowired GoalDAO goalDao;

	@Autowired TaskDAO taskDao;
	
	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestParam("goal_contents") String goalContents, HttpServletRequest req) {

		ArrayList<String> arrTaskContents = new ArrayList<String>();

		// TODO: 요청 보내는 front코드와 함께 리팩토링 필요,
		for (int i = 0; i < 5; i++) {
			String taskContents = req.getParameter("task_contents_" + i);
			LOGGER.info("task: {}",taskContents);
			if (taskContents == null) {
				break;
			}
			
			arrTaskContents.add(taskContents);
		}
		Goal goal = new Goal(goalContents, "wq1021@naver.com");
		int returnedId = goalDao.addGoal(goal);

		for (int i = 0; i < arrTaskContents.size(); i++) {
			taskDao.addTask(new Task(arrTaskContents.get(i), returnedId));
		}
		return goal;
	}
}
