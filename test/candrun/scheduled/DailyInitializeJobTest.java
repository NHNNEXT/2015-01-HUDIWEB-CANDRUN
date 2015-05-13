package candrun.scheduled;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.CronExpression;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:candrun-servlet.xml"})
public class DailyInitializeJobTest {
	
	@Autowired
	ApplicationContext context;
	
	@Test
	public void testCronExpression() throws ParseException {
		CronTriggerImpl bean = (CronTriggerImpl)context.getBean("cronTrigger");
		CronExpression cronExpression = new CronExpression(bean.getCronExpression());
		
		Date nextValidDate1 = cronExpression.getNextValidTimeAfter(new Date("May 12, 2015 10:10:10"));
		Date nextValidDate2 = cronExpression.getNextValidTimeAfter(nextValidDate1);
	 
		assertEquals("Tue May 12 23:59:00 KST 2015", nextValidDate1.toString());
		assertEquals("Wed May 13 23:59:00 KST 2015", nextValidDate2.toString());
	}

}
