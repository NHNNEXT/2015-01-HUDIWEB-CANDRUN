package candrun.service;

import java.util.List;

import candrun.dao.TaskDAO;
import candrun.model.Task;

public class TaskService {
	
	private TaskDAO taskDao;
	
	public TaskService(TaskDAO taskDao) {
		this.taskDao = taskDao;
	}
	
	public List<Task> getTasks(int goalId) {
		return taskDao.getTasksByGoalId(goalId);
	}

	public Task handleTaskRequest(int taskId, String userEmail, String goalOwnerEmail) {
		
		if(userEmail.equals(goalOwnerEmail)){
			taskDao.completeTask(taskId);
		}else {
			taskDao.addNudge(taskId);
		}
		
		return taskDao.getTaskByTaskId(taskId);
	}
}
