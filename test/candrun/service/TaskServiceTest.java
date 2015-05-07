package candrun.service;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import candrun.dao.TaskDAO;
import candrun.model.Task;

public class TaskServiceTest {

	TaskService taskService;
	TaskDAO taskDao = mock(TaskDAO.class);
	
	@Before
	public void setUp(){
		taskService = new TaskService(taskDao);
	}
	
	@Test
	public void HandleTaskRequestWhenUserIsGoalOwner() {
		String userEmail = "myEmail@email.com";
		String goalOwnerEmail = "myEmail@email.com";
		int taskId = 1;
		
		Task task = taskService.handleTaskRequest(taskId, userEmail, goalOwnerEmail);
		
		verify(taskDao, times(1)).completeTask(taskId);
		verify(taskDao, times(0)).addNudge(taskId);
	}
	
	@Test
	public void HandleTaskRequestWhenUserIsNotGoalOwner() {
		String userEmail = "myEmail@email.com";
		String goalOwnerEmail = "friendEmail@email.com";
		int taskId = 1;

		Task task = taskService.handleTaskRequest(taskId, userEmail, goalOwnerEmail);
		
		verify(taskDao, times(0)).completeTask(taskId);
		verify(taskDao, times(1)).addNudge(taskId);
	}

}
