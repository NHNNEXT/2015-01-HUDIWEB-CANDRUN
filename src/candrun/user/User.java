package candrun.user;

import candrun.dao.UserDAO;
import candrun.exception.PasswordMismatchException;
import candrun.exception.UserNotFoundException;

public class User {
	private String email;
	private String nickname;
	private String password;
	private String verifyKey;

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

	public User(String email, String nickname, String password, String verifyKey) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.verifyKey = verifyKey;
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

	public String getVerifyKey() {
		return verifyKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((verifyKey == null) ? 0 : verifyKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (verifyKey == null) {
			if (other.verifyKey != null)
				return false;
		} else if (!verifyKey.equals(other.verifyKey))
			return false;
		return true;
	}
}
