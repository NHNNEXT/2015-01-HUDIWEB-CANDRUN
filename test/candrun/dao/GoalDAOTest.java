package candrun.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import candrun.model.Goal;
import candrun.support.enums.GoalState;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class GoalDAOTest {
	private static final Logger logger = LoggerFactory
			.getLogger(GoalDAOTest.class);

	@Autowired
	private GoalDAO goalDao;

	@Test
	public void findGoal() throws SQLException {
		Goal goal = new Goal("goalContents", "asdf0@asdf.net");
		int goalId = goalDao.addGoal(goal);
		Goal dbGoal = goalDao.getGoalById(goalId);

		assertEquals(dbGoal.getId(), goalId);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void findUserByNotExistKey() throws SQLException {
		goalDao.getGoalById(-1);
	}

	@Test
	public void testModigyGoal() throws Exception {
		String contents = "modified";
		int id = 1;
		goalDao.modifyGoal(contents, id);
		Goal goal = goalDao.getGoalById(id);
		assertEquals(contents, goal.getContents());
		logger.debug(goal.getModDate().toString());
	}

	@Test
	public void testGetGoals() throws Exception {
		String email = "asdf1@asdf.com";
		GoalState state = GoalState.FINISHED;
		List<Goal> goals = goalDao.getGoals(email, state);
		logger.debug(goals.size() + "");
		goals.forEach((goal) -> assertEquals(state.getValue(), goal.getState()));
	}
}
