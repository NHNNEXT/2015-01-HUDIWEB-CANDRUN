package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import candrun.user.User;

public class UserDAO extends JdbcDaoSupport{
		
	public void addPreliminaryUser(User user) throws SQLException{
		String sql ="INSERT INTO preliminary_user(email, nickname, password, verify_key) VALUES(?, ?, ?, ?)";
		getJdbcTemplate().update(sql,user.getEmail(), user.getNickname(), user.getPassword(), user.getVerifyKey());
	}
	
	public void addUser(User user) throws SQLException{
		String sql ="INSERT INTO user(email, nickname, password) VALUES(?, ?, ?)";
		getJdbcTemplate().update(sql,user.getEmail(), user.getNickname(), user.getPassword());
	}

	public User findByEmail(String email) throws SQLException {
		String sql = "SELECT * FROM user WHERE email = ?";
		RowMapper<User> rowMapper = new RowMapper<User>() {

			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new User(
						rs.getString("email"), 
						rs.getString("nickname"),
						rs.getString("password"));
			}
		};
		return getJdbcTemplate().queryForObject(sql, rowMapper, email);
	}

	//TODO: nickname으로 verifyKey 생성시 중복 가능성이 있다.
	public User findByVerifyKey(String verifyKey) throws SQLException {
		String sql = "SELECT * FROM preliminary_user WHERE verify_key = ?";
		RowMapper<User> rowMapper = new RowMapper<User>() {

			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new User(
						rs.getString("email"), 
						rs.getString("nickname"),
						rs.getString("password"),
						rs.getString("verify_key"));
			}
		};
		return getJdbcTemplate().queryForObject(sql, rowMapper, verifyKey);
	}
}
