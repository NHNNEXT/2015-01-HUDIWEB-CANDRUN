package candrun.service.user;

import javax.servlet.http.HttpSession;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import candrun.dao.UserDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserServiceTest {
	
	@Autowired
	private UserDAO userDao;
	
	@Test
	public void checkEmpty() {
		UserService userService = new UserService(userDao);
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.doNothing().when(session).setAttribute(Mockito.anyString(), Mockito.anyObject());
		userService.login("as@as.com", "asdf", session);
	}
	
	@Test
	public void checkDup() {
		UserService userService = new UserService(userDao);
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.doNothing().when(session).setAttribute(Mockito.anyString(), Mockito.anyObject());
		userService.register("asdf0@asdf.net", "asdf", "asdf", session);
	}
}
