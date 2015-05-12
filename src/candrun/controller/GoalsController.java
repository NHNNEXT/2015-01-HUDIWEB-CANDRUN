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
	
	/*
	 * 특별한 이유가 없는한 private을 붙여주는게 좋습니다.
	 */
	@Autowired
	private GoalDAO goalDao;
	@Autowired
	private TaskDAO taskDao;
	@Autowired
	private GoalService goalService;
	@Autowired
	private TaskService taskService;

	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestParam("goal_contents") String goalContents,
			HttpServletRequest req, HttpSession session) {
		String userEmail = (String) session.getAttribute("email");

		ArrayList<String> tasks = new ArrayList<String>();

		for (int i = 0; i < maxTasksNumber; i++) {
			/*
			 * 클라이언트에서 값을 전달할 때부터 배열이면 좋을 것 같아요.
			 *  - 클라참고: http://goo.gl/BORzx6
			 *  - 서버참고: http://goo.gl/rCZ3Jq
			 */
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
