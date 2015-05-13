package candrun.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import candrun.model.GoalWithTasks;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:candrun-servlet.xml"})
public class GoalsControllerTest {
	private static final Logger logger = LoggerFactory
			.getLogger(GoalsControllerTest.class);
	@Autowired
	GoalsController goalsController;

	@Test
	public void testRead() {
		GoalWithTasks goalWithTasks = goalsController.read(17);
		goalWithTasks.getTasks().forEach((task) -> (logger.debug(task.getContents())));
	}

}
