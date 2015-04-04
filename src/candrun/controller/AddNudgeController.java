package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import candrun.dao.TaskDAO;

@WebServlet("/addNudge.cdr")
public class AddNudgeController extends HttpServlet{

	@Override
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
		System.out.println("redirect!!");
		resp.sendRedirect("/");
	}
}
