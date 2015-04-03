package candrun.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;
import candrun.model.Task;

@WebServlet("/addGoal.cdr")
public class AddGoalController extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String goalContents = req.getParameter("goal_contents");
		String taskContents = req.getParameter("task_contents");
		String[] arrTaskContents = taskContents.split(",");
		
		GoalDAO goalDAO = new GoalDAO();
		TaskDAO taskDAO = new TaskDAO();
		Goal goal = new Goal(goalContents, "email");		
		
		try {
			
			int returnedId = goalDAO.addGoal(goal);
			for(int i=0; i<arrTaskContents.length;i++){
				taskDAO.addTask(new Task(arrTaskContents[i],returnedId));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
