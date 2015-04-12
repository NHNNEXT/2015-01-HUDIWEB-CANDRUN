package candrun.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import candrun.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/test-applicationContext.xml")
public class UserDAOTest {

	@Autowired
	private UserDAO userDao;
	
	@Test
	public void addUser() throws Exception {
		User user = new User("candy@test.com", "nickname", "password");
		userDao.addUser(user);
		User dbUser = userDao.findByEmail("candy@test.com");
		assertEquals(user,dbUser);
	}

	@Test
	public void addPreliminaryUser() throws Exception {
		User user = new User("chocolate@test.com", "nickname", "password", "verifyKey");
		userDao.addPreliminaryUser(user);
		User dbUser = userDao.findByVerifyKey("verifyKey");
		assertEquals(user,dbUser);
	}
	
	
	
	
	
}
