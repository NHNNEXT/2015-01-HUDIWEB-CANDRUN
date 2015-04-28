package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import candrun.model.User;

public class UserDAO extends JdbcDaoSupport {
	private RowMapper<User> rowMapper = new RowMapper<User>() {

		public User mapRow(ResultSet rs, int rowNum) {
			try {
				return new User(rs.getString("email"),
						rs.getString("nickname"), rs.getString("password"),
						rs.getString("pic_path"), rs.getInt("state"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(User.class,
						e.getMessage(), e);
			}
		}
	};

	public void addUser(User user) throws DuplicateKeyException {
		String sql = "INSERT INTO user(email, nickname, password, pic_path) VALUES(?, ?, ?, ?)";
		getJdbcTemplate().update(sql, user.getEmail(), user.getNickname(),
				user.getPassword(), user.getPicPath());
	}

	public User findByEmail(String email) throws EmptyResultDataAccessException {
		String sql = "SELECT * FROM user WHERE email = ?";
		return getJdbcTemplate().queryForObject(sql, rowMapper, email);
	}

	public List<User> findUsersByGoalId(int goalId, String email) {
		String sql = "SELECT DISTINCT u.* FROM (SELECT gg.requester, gg.receiver, g.id, g.user_email FROM goal_has_goal gg INNER JOIN goal g ON (gg.receiver = g.id || gg.requester = g.id)) temp INNER JOIN user u ON u.email = user_email WHERE (requester = ? || receiver = ?) AND !(email = ?)";

		return getJdbcTemplate().query(sql, rowMapper, goalId, goalId, email);
	}

	// user를 가져온다, status = 1이고, 내가 requester일 때, user_has_user에서 responser를
	// 가져온다.
	// 그것으로 user table에서 email로 검색해서 user를 가져온다.
	public List<User> findFriendsAsRequester(String email) {
		String sql = "SELECT * FROM (SELECT u.*, uu.requester , uu.receiver FROM user_has_user uu INNER JOIN user u ON uu.receiver = u.email) temp WHERE requester = ?";
		return getJdbcTemplate().query(sql, rowMapper, email);
	}

	public void changeState(String email) {
		String sql = "UPDATE user SET state = state + 1 WHERE email = ?";
		// TODO: state의 상한이 있을 시 처리해주어야한다.
		getJdbcTemplate().update(sql, email);
	}

}