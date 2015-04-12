package candrun.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;

@Controller
public class AddNudgeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddNudgeController.class);
	
	@Autowired
	TaskDAO taskDao;
	
	@Autowired
	GoalDAO goalDao;

	@RequestMapping(value="/addNudge.cdr", method=RequestMethod.POST)
	protected String doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int tasksId = Integer.parseInt(req.getParameter("tasksId"));
		LOGGER.debug("taskId: {}", tasksId);
				
		try {
			taskDao.addNudge(tasksId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "showGoalAndTasks";
	}

}
