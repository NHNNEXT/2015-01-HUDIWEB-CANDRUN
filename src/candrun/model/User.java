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
