package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import candrun.model.User;

public class UserDAO extends JdbcDaoSupport {
	private RowMapper<User> rowMapper = new RowMapper<User>() {

		public User mapRow(ResultSet rs, int rowNum) {
			try {
				return new User(rs.getString("email"), rs.getString("nickname"), rs.getString("password"),
						rs.getInt("state"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(User.class, e.getMessage(), e);
			}
		}
	};

	public void addUser(User user) {
		String sql = "INSERT INTO user(email, nickname, password) VALUES(?, ?, ?)";
		getJdbcTemplate().update(sql, user.getEmail(), user.getNickname(), user.getPassword());
	}

	public User findByEmail(String email) {
		String sql = "SELECT * FROM user WHERE email = ?";
		return getJdbcTemplate().queryForObject(sql, rowMapper, email);
	}
	
	public List<User> findUsersByGoalId(int goalId, String email) {
		String sql = "select * from (select g.id as goal_id, u.email as email, u.nickname as nickname, u.state as state, gg.receiver as receiver from goal_has_goal gg inner join goal g inner join user u on (gg.receiver = g.id) AND g.user_email = u.email) temp  where !(temp.goal_id = ?) and !(temp.email = ?)";
		RowMapper<User> rowMapper = new RowMapper<User>() {

			public User mapRow(ResultSet rs, int rowNum) {
				try {
					return new User(rs.getString("email"), rs.getString("nickname"));
				} catch (SQLException e) {
					throw new BeanInstantiationException(User.class, e.getMessage(), e);
				}
			}
		};
		
		return getJdbcTemplate().query(sql, rowMapper, goalId, email);
	}

	public void changeState(String email) {
		String sql = "UPDATE user SET state = state + 1 WHERE email = ?";
		// TODO: state의 상한이 있을 시 처리해주어야한다.
		getJdbcTemplate().update(sql, email);
	}
}
