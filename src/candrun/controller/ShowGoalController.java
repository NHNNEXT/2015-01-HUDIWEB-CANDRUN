package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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

@Controller("/")
public class ShowGoalController {

	@Autowired
	GoalDAO goalDao;
	
	@Autowired
	TaskDAO taskDao;

	@RequestMapping(method=RequestMethod.GET)
	protected String show(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//db에 있는 goal의 내용을 모두 불러온다.
		Goal topGoal= null;
		ArrayList <Task> tasks;
		try {
			topGoal= goalDao.findRecentGoal();
			tasks = taskDao.findTaskByGoalId(topGoal.getId());
			//forward하여 내용을 jsp에 뿌린다.
			req.setAttribute("goal",topGoal);
			req.setAttribute("tasks", tasks);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "showGoalAndTasks";
	}

	
	@RequestMapping(method=RequestMethod.POST)
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int tasksId = Integer.parseInt(req.getParameter("tasksId"));
		System.out.println(tasksId + "");
		TaskDAO taskDao = new TaskDAO();
		try {
			taskDao.addNudge(tasksId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
} 