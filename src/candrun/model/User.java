 package candrun.model;

import candrun.dao.UserDAO;
import candrun.exception.PasswordMismatchException;
import candrun.exception.UserNotFoundException;

public class User {
	private String email;
	private String nickname;
	private String password;
	private int state;

	public static boolean login(String email, String password) throws UserNotFoundException, PasswordMismatchException
			{
		
		/*
		 * 지금 User객체 내부에서 UserDao를 가져와서 사용하고 있는데 좋지 않은 방법 같습니다.
		 * 
		 * UserDao는 Repository 레벨인데 POJO인 User를 사용하려면 무조건 Repository와 엮기게 되는 상황이 발생하기 때문입니다.
		 * 따라서 login을 처리하는 로직을 다른 방식으로 처리해야할 것 같아요.
		 */
		UserDAO userDAO = new UserDAO();
		User user = userDAO.findByEmail(email);
		userDAO.findByEmail(email);

		if (user == null) {
			throw new UserNotFoundException();
		}

		if (!user.matchPassword(password)) {
			throw new PasswordMismatchException();
		}
		return true;

	}

	private boolean matchPassword(String newPassword) {
		return this.password.equals(newPassword);
	}

	public User(String email, String nickname, String password) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
	}
	public User(String email, String nickname, String password, int state) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.state = state;
	}
	public User(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public int getState() {
		return state;
	}
}
