package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import candrun.user.User;

public class UserDAO extends JdbcDaoSupport{
		
	@PostConstruct
	public void initialize(){
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		DatabasePopulatorUtils.execute(populator, getDataSource());
	}
	
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
