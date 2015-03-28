package candrun.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
	}
}
