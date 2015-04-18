package candrun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;
import candrun.model.Task;

@RequestMapping("/")
@Controller
public class MainController {


	@Autowired
	GoalDAO goalDao;

	@Autowired
	TaskDAO taskDao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model) {
		
		Goal topGoal = goalDao.findRecentGoal();
		List<Task> tasks = taskDao.findTasksByGoalId(topGoal.getId());
		model.addAttribute("goal", topGoal);
		model.addAttribute("tasks", tasks);

//		모델을 이용하여 attribute 더해준다. 아래는 이전 코드
//		req.setAttribute("goal", topGoal);
//		req.setAttribute("tasks", taskDao.findTasksByGoalId(topGoal.getId()));

		return "home";
	}
}
