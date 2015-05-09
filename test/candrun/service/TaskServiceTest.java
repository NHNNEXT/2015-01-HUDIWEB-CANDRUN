package candrun.service;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import candrun.dao.TaskDAO;
import candrun.model.Task;

public class TaskServiceTest {

	TaskService taskService;
	TaskDAO taskDao = mock(TaskDAO.class);
	static final int DUMMY_TASK_ID = 1; 
	
	@Before
	public void setUp(){
		taskService = new TaskService(taskDao);
	}
	
	@Test
	public void HandleTaskRequestWhenUserIsGoalOwner() {
		String userEmail = "myEmail@email.com";
		String goalOwnerEmail = "myEmail@email.com";
		
		Task task = taskService.handleTaskRequest(DUMMY_TASK_ID, userEmail, goalOwnerEmail);
		
		verify(taskDao, times(1)).completeTask(DUMMY_TASK_ID);
		verify(taskDao, times(0)).addNudge(DUMMY_TASK_ID);
	}
	
	@Test
	public void HandleTaskRequestWhenUserIsNotGoalOwner() {
		String userEmail = "myEmail@email.com";
		String goalOwnerEmail = "friendEmail@email.com";

		Task task = taskService.handleTaskRequest(DUMMY_TASK_ID, userEmail, goalOwnerEmail);
		
		verify(taskDao, times(0)).completeTask(DUMMY_TASK_ID);
		verify(taskDao, times(1)).addNudge(DUMMY_TASK_ID);
	}

}
