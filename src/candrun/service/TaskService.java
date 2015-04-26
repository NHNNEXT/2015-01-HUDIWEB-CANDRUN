package candrun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import candrun.dao.TaskDAO;
import candrun.model.Task;

@Service
public class TaskService {
	
	@Autowired
	private TaskDAO taskDao;

	public void addNudge(int taskId) {
		taskDao.addNudge(taskId);
	}

	public Task findTaskByTaskId(int taskId) {
		
		return taskDao.findTaskByTaskId(taskId);
	}

}
