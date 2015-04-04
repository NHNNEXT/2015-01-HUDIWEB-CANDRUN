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

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;
import candrun.model.Task;

@WebServlet("/showGoal.cdr")
public class ShowGoalController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//db에 있는 goal의 내용을 모두 불러온다.
		Goal topGoal= null;
		ArrayList <Task> tasks;
		GoalDAO goalDao = new GoalDAO();
		TaskDAO taskDao = new TaskDAO();
		try {
			topGoal= goalDao.findRecentGoal();
			tasks = taskDao.findTaskByGoalId(topGoal.getId());
			//forward하여 내용을 jsp에 뿌린다.
			req.setAttribute("goal",topGoal);
			req.setAttribute("tasks", tasks);
			
			RequestDispatcher rd = req.getRequestDispatcher("/showGoalAndTasks.jsp");
			rd.forward(req, resp);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		int tasksId = Integer.parseInt(req.getParameter("tasksId"));
//		System.out.println(tasksId + "");
//		TaskDAO taskDao = new TaskDAO();
//		try {
//			taskDao.addNudge(tasksId);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
} 