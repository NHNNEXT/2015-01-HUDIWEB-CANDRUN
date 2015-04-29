package candrun;

import static org.junit.Assert.*;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import candrun.dao.GoalDAO;
import candrun.dao.TaskDAO;
import candrun.dao.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:candrun-servlet.xml"})
public class DataBaseSetter {
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private TaskDAO taskDao;
	@Autowired
	private GoalDAO goalDao;
	@Autowired
	private BasicDataSource dataBase;

	@Test
	public void initDataBase() {
		
	}

}
