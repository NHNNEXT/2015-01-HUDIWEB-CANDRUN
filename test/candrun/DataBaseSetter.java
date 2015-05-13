package candrun;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.dao.UserDAO;
import candrun.model.Goal;
import candrun.model.Task;
import candrun.model.User;
import candrun.service.SecurityService;
import candrun.support.enums.RelationRequestState;
import candrun.support.enums.UserState;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"classpath:candrun-servlet.xml" })
public class DataBaseSetter {

	@Autowired
	private UserDAO userDao;
	@Autowired
	private TaskDAO taskDao;
	@Autowired
	private GoalDAO goalDao;
	@Autowired
	private BasicDataSource dataSource;
	@Value("${database.driverClassName}")
	private String drviverClassName;

	@Test
	public void initDataBase() throws Exception {
		ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
		rdp.addScript(new ClassPathResource("local-properties/db-init-java.sql"));
		rdp.populate(dataSource.getConnection());
		int maxUsers = 10;
		for (int i = 0; i < maxUsers; i++) {
			userDao.addUser(new User("asdf" + i + "@asdf.com", "qwer" + i,
					SecurityService.encrypt("asdf", "asdf"), "pic" + i + ".jpg"));
		}
		for (int i = 1; i < maxUsers; i++) {
			userDao.changeState("asdf" + i + "@asdf.com", UserState.CERTIED);
		}
		for (int i = 2; i < maxUsers; i++) {
			for (int j = i + 1; j < maxUsers; j++) {
				userDao.requestToBeFriend("asdf" + i + "@asdf.com", "asdf" + j
						+ "@asdf.com");
			}
		}
		for (int i = maxUsers - 2; i > 2; i--) {
			for (int j = i - 1; j > 1; j--) {
				userDao.acceptRequestToBeFriend("asdf" + j + "@asdf.com",
						"asdf" + i + "@asdf.com");
			}
		}

		int maxGoals = 3;
		int maxTasks = 3;
		int goalIdx = 10;
		int taskIdx = 10;
		for (int i = 1; i < maxUsers; i++) {
			for (int j = 0; j < maxGoals - 1; j++) {
				int goalId = goalDao.addGoal(new Goal("asdfqwer" + goalIdx++, "asdf"
						+ i + "@asdf.com"));
				for (int k = 0; k < maxTasks; k++) {
					taskDao.addTask(new Task("asdfqwer" + taskIdx++, goalId));
				}
			}
			for (int j = maxGoals; j < maxGoals + 5; j++) {
				int goalId = goalDao.addGoal(new Goal("asdfqwer" + goalIdx++, "asdf"
						+ i + "@asdf.com"));
				goalDao.finishGoal(goalId);
				for (int k = 0; k < maxTasks; k++) {
					taskDao.addTask(new Task("asdfqwer" + taskIdx++, goalId));
				}
			}
			int goalId = goalDao.addGoal(new Goal("asdfqwer" + goalIdx++,
					"asdf" + i + "@asdf.com"));
			goalDao.startGoal(goalId);
			for (int k = 0; k < maxTasks; k++) {
				taskDao.addTask(new Task("asdfqwer" + taskIdx++, goalId));
			}
		}
		
		int taskLimit = maxUsers * 15;
		for (int i = 1; i < taskLimit; i += 3) {
			taskDao.completeTask(i);
		}

		// goal_has_goal의 데이터 수 가운트
		int count = 1;
		for (int i = 9; i < (maxUsers - 5) * 8; i += 5) {
			int k = i + 3;
			for (; i < k; i++) {
				for (int j = i + 8; j < i + (3 * 8) + 1; j += 8) {
					goalDao.requestConnectGoal(i, j);
					count++;
				}
			}
		}
		for (int i = 1; i < count; i++) {
			if (i % 5 == 0)
				continue;
			goalDao.changeState(i, RelationRequestState.ACCEPTED);
		}
	}
}
