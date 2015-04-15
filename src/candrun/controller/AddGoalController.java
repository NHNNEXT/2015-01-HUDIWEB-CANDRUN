package candrun.controller;

import java.io.IOException;

import javax.servlet.ServletException;
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

@Controller
public class AddGoalController {

	@Autowired
	GoalDAO goalDao;

	@Autowired
	TaskDAO taskDao;

	// goal 추가 후, 다음 페이지로 연결해주어야 한다.
	@RequestMapping(value = "/addGoal.cdr", method = RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String goalContents = req.getParameter("goal_contents");
		String taskContents = req.getParameter("task_contents");
		String[] arrTaskContents = taskContents.split(",");

		Goal goal = new Goal(goalContents, "email");
		int returnedId = goalDao.addGoal(goal);
		
		for (int i = 0; i < arrTaskContents.length; i++) {
			taskDao.addTask(new Task(arrTaskContents[i], returnedId));
		}
	}
}
