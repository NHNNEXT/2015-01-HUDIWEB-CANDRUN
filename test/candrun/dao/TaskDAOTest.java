package candrun.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import candrun.model.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
public class TaskDAOTest {
	
	@Autowired
	TaskDAO taskDao;
	
	@Before
	public void setUp() throws SQLException{
	}
	
	@Test
	public void findTaskByGoalId() throws SQLException {
		taskDao.addTask(new Task("taskOne", 1));
		taskDao.addTask(new Task("taskTwo", 1));
		taskDao.addTask(new Task("taskThree", 2));		
		List<Task> dbTasks = taskDao.getTasksByGoalId(1);
		
		assertEquals(2, dbTasks.size());
	}
	
	@Test
	public void addNudge() throws SQLException{
		taskDao.addTask(new Task("task", 2));	
		List<Task> dbTasks = taskDao.getTasksByGoalId(2);
		int nudgeCount = dbTasks.get(0).getNudge();
		int taskId = dbTasks.get(0).getId();
		
		taskDao.addNudge(taskId);	
		List<Task> dbTasksAfterNudge = taskDao.getTasksByGoalId(2);
		int nudgeCountAfterNudge = dbTasksAfterNudge.get(0).getNudge();
		
		assertEquals(nudgeCount+1, nudgeCountAfterNudge);
	}
}