package candrun.service;

import java.util.ArrayList;
import java.util.List;

import candrun.dao.TaskDAO;
import candrun.model.Task;
import candrun.model.TaskLog;

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

	public List<Task> getTasksByGoalId(int goalId) {
		List<Task> tasks = taskDao.getTasksByGoalId(goalId);
		List<TaskLog> taskLogs = new ArrayList<TaskLog>();
		
		for(Task task : tasks){
			taskLogs = taskDao.getTaskLogsByTaskId(task.getId());
			if(!taskLogs.isEmpty()){
				task.setTaskLogs(taskLogs); 
			}
		}
		return tasks;
	}
}
