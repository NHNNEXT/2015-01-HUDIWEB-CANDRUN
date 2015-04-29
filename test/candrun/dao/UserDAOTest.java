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
@ContextConfiguration(locations = { "classpath:applicationContext.xml",
		"classpath:candrun-servlet.xml" })
public class UserDAOTest {

	@Autowired
	private UserDAO userDao;
	
	@Test
	public void findUser() throws SQLException {
		User user = new User("asdf0@asdf.com", "qwer0", "asdf", "pic0.jpg");
		userDao.addUser(user);
		User dbUser = userDao.findByEmail("candy@test.com");
		assertEquals(user,dbUser);
	}
	
	@Test(expected=DuplicateKeyException.class)
	public void addUserByDuplicateKey(){
		User user = new User("asdf0@asdf.com", "qwer0", "asdf", "pic0.jpg");
		userDao.addUser(user);
		userDao.addUser(user);
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void findUserByNotExistKey() throws SQLException {
		userDao.findByEmail("honey@test.com");
	}
	
	@Test
	public void testIsNewRequestToBeFriend() throws SQLException {
		assertFalse(userDao.isNewRequestToBeFriend("asdf2@asdf.com", "asdf3@asdf.com"));
		assertFalse(userDao.isNewRequestToBeFriend("asdf3@asdf.com", "asdf2@asdf.com"));
		assertTrue(userDao.isNewRequestToBeFriend("asdf1@asdf.com", "asdf3@asdf.com"));
	}
}
