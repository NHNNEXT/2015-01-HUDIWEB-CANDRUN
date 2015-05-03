package candrun.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import candrun.model.GoalRelation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"classpath:aopContext.xml", "classpath:candrun-servlet.xml" })
public class GoalServiceTest {

	private static final Logger logger = LoggerFactory
			.getLogger(GoalServiceTest.class);

	@Autowired
	private GoalService goalService;

	@Test
	public void testGetGoalRelations() throws Exception {
		List<GoalRelation> goalRelations = goalService
				.getGoalRelations("asdf2@asdf.com");
		goalRelations.forEach((gr) -> {
			logger.debug(gr.getMyGoal().getEmail());
			logger.debug(gr.getMyGoal().getContents());
		});
	}
	
	@Test
	public void testGetGoalRelationsWhenEmpty() throws Exception {
		List<GoalRelation> goalRelations = goalService
				.getGoalRelations("unitimes@naver.com");
		assertTrue(goalRelations.isEmpty());
	}
}
