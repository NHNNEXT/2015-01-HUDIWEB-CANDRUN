package candrun.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.reflect.Field;

import org.junit.Test;

import candrun.support.enums.CommonInvar;
import candrun.support.enums.UserErrorcode;

public class UserTest {
/*
	@Test
	public void getEnumState_S_state가_0일때() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// defualt 생성자 필요
		User user = new User();
		Field field = User.class.getDeclaredField("state");
		field.setAccessible(true);
		field.set(user, 0);
		
		assertThat(user.getEnumState(), equalTo(UserErrorcode.NOT_YET_CERTI));
	}
	
	@Test
	public void getEnumState_S_state가_1일때() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// defualt 생성자 필요
		User user = new User();
		Field field = User.class.getDeclaredField("state");
		field.setAccessible(true);
		field.set(user, 1);
		
		assertThat(user.getEnumState(), equalTo(CommonInvar.SUCCESS));
	}
	
	@Test
	public void getEnumState_S_state가_0_1이_아닐때() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// defualt 생성자 필요
		User user = new User();
		Field field = User.class.getDeclaredField("state");
		field.setAccessible(true);
		field.set(user, 1000);
		
		assertThat(user.getEnumState(), equalTo(CommonInvar.DEFAULT));
	}
*/

}
