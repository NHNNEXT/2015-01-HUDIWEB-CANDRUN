package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import candrun.user.User;

public class UserDAO extends JdbcDaoSupport{
	
	private static final class UserMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				return new User(
						rs.getString("email"), 
						rs.getString("nickname"),
						rs.getString("password"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(User.class, e.getMessage(), e);
			}
		}
		
	}
		
	public void addPreliminaryUser(User user) {
		String sql ="INSERT INTO preliminary_user(email, nickname, password, verify_key) VALUES(?, ?, ?, ?)";
		getJdbcTemplate().update(sql,user.getEmail(), user.getNickname(), user.getPassword(), user.getVerifyKey());
	}
	
	public void addUser(User user) {
		String sql ="INSERT INTO user(email, nickname, password) VALUES(?, ?, ?)";
		getJdbcTemplate().update(sql,user.getEmail(), user.getNickname(), user.getPassword());
	}

	public User findByEmail(String email) {
		String sql = "SELECT * FROM user WHERE email = ?";
		return getJdbcTemplate().queryForObject(sql, new UserMapper(), email);
	}

	//TODO: nickname으로 verifyKey 생성시 중복 가능성이 있다.
	public User findByVerifyKey(String verifyKey) {
		String sql = "SELECT * FROM preliminary_user WHERE verify_key = ?";
		RowMapper<User> rowMapper = new RowMapper<User>() {

			public User mapRow(ResultSet rs, int rowNum) {
				try {
					return new User(
							rs.getString("email"), 
							rs.getString("nickname"),
							rs.getString("password"),
							rs.getString("verify_key"));
				} catch (SQLException e) {
					throw new BeanInstantiationException(User.class, e.getMessage(), e);
				}
			}
		};
		return getJdbcTemplate().queryForObject(sql, rowMapper, verifyKey);
	}

	public List<User> findUsersByGoalId(int goalId, String email) {
//		String sql = "select * from (select * from goal_has_user inner join user on goal_has_user.email = user.email) where NOT(email = ?) AND goal_id = ? LIMIT 5";
		String sql = "select * from (select * from goal_has_user inner join user on goal_has_user.user_email = user.email) temp where NOT(email = ?) AND goal_id = ? LIMIT 5";
		return getJdbcTemplate().query(sql, new UserMapper(), email, goalId);
	}
}