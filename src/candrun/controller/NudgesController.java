package candrun.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;

@RequestMapping("/nudges")
@RestController
public class NudgesController {
	private static final Logger LOGGER = LoggerFactory.getLogger(NudgesController.class);
	
	@Autowired
	TaskDAO taskDao;
	
	@Autowired
	GoalDAO goalDao;

	@RequestMapping( method = RequestMethod.POST)
	public String update(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println(req.getParameter("tasksId"));
		int taskId = Integer.parseInt(req.getParameter("tasksId"));
		LOGGER.debug("taskId: {}", taskId);	
		taskDao.addNudge(taskId);

		return model;
	}
}
