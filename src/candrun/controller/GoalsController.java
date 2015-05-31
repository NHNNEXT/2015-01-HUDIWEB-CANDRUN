package candrun.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.model.Goal;
import candrun.model.GoalWithTasks;
import candrun.service.GoalService;
import candrun.service.TaskService;
import candrun.support.enums.CommonInvar;
import candrun.support.enums.RelationRequestState;

@RequestMapping("/goals")
@RestController
public class GoalsController {
	@Autowired
	GoalDAO goalDao;
	@Autowired
	TaskDAO taskDao;
	@Autowired
	GoalService goalService;
	@Autowired
	TaskService taskService;

	@RequestMapping(method = RequestMethod.POST)
	public Object create(@RequestParam("goal_contents") String goalContents,
			@RequestParam("taskContents[]") String[] taskContents, HttpSession session) {
		String userEmail = (String) session.getAttribute("email");

		// TODO: 친구추가하는 기능과 합쳐서 GoalRelation 객체로 리턴해주어야 한다.
		Goal newGoal = goalService.addGoalAndTasks(goalContents, taskContents, userEmail);
		return newGoal;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public GoalWithTasks read(@PathVariable("id") int goalId) {
		return new GoalWithTasks(goalService.getGoal(goalId), taskService.getTasks(goalId));
	}

	@RequestMapping(value = "/relation", method = RequestMethod.POST)
	public Map<String, Integer> createRelation(@RequestParam("requesterId") String requesterId,
			@RequestParam("receiverId") String receiverId) {
		Map<String, Integer> returnMsg = new HashMap<String, Integer>();
		RelationRequestState msg = goalService.addGoalRelation(requesterId, receiverId);

		returnMsg.put(CommonInvar.RETURNMSG.getValue(), msg.getValue());
		return returnMsg;
	}
}
