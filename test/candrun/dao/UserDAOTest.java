package candrun.dao;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;

import candrun.user.User;

public class UserDAOTest {

	private UserDAO userDao;
	
	@Before
	public void setup() {
		userDao = new UserDAO();
	}
	
// spring test로 바꾸어야 한다. 
//	@Test
//	public void connection() {
//		Connection con = userDao.getConnection();
//		assertNotNull(con);
//	}
	
	@Test
	public void addUser() throws Exception {
		User user = new User("email3", "nickname3", "password3");
		userDao.addUser(user);	 
	}
	
	
	
}
