package candrun.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import candrun.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserDAOTest {

	@Autowired
	private UserDAO userDao;
	
	@Test
	public void findUser() throws SQLException {
		User user = new User("candy@test.com", "nickname", "password");
		userDao.addUser(user);
		User dbUser = userDao.findByEmail("candy@test.com");
		assertEquals(user,dbUser);
	}
	
	@Test(expected=DuplicateKeyException.class)
	public void addUserByDuplicateKey(){
		User user = new User("caramel@test.com", "nickname", "password");
		userDao.addUser(user);
		userDao.addUser(user);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findUserByNotExistKey() throws SQLException {
		userDao.findByEmail("honey@test.com");
	}
	
}
