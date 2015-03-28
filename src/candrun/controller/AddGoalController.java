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
import candrun.model.Goal;

@WebServlet("/addGoal.cdr")
public class AddGoalController extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String goalContents = req.getParameter("goal_contents");
		String taskContents = req.getParameter("inputValues");
		String[] taskContentsList = taskContents.split(",");
		
		
		System.out.println("goal: "+goalContents);
		for(int i=0; i<taskContentsList.length; i++){
			System.out.println(taskContentsList[i]);
		}
		
		Goal goal = new Goal(goalContents, "email");
		
		//여기서 dao를 가져와서 dao 에게 넘겨준다. 
		
		GoalDAO goalDAO = new GoalDAO();
		Connection con = goalDAO.getConnection();
		try {
			goalDAO.addGoal(goal);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
