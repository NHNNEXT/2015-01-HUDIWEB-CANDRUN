package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

@Controller
public class ShowGoalController {

	@Autowired
	GoalDAO goalDao;
	
	@Autowired
	TaskDAO taskDao;

	@RequestMapping(value="/", method=RequestMethod.GET)
	protected String show(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//db에 있는 goal의 내용을 모두 불러온다.
		try {
			Goal topGoal= goalDao.findRecentGoal();

			//forward하여 내용을 jsp에 뿌린다.
			req.setAttribute("goal", topGoal);
			req.setAttribute("tasks", taskDao.findTaskByGoalId(topGoal.getId()));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "showGoalAndTasks";
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int tasksId = Integer.parseInt(req.getParameter("tasksId"));
		System.out.println(tasksId + "");
		try {
			taskDao.addNudge(tasksId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
} 