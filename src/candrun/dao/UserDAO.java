package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import candrun.user.User;

public class UserDAO extends JdbcDaoSupport{
		
	private RowMapper<User> rowMapper = new RowMapper<User>() {
		
		public User mapRow(ResultSet rs, int rowNum) {
			try {
				return new User(
						rs.getString("email"), 
						rs.getString("nickname"),
						rs.getString("password"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(User.class, e.getMessage(), e);
			}
		}
	};
	
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
		return getJdbcTemplate().queryForObject(sql, rowMapper, email);
	}

//	user를 가져온다, status = 1이고, 내가 requester일 때, user_has_user에서 responser를 가져온다.
//	그것으로 user table에서 email로 검색해서 user를 가져온다.
	public List<User> findFriendsAsRequester(String email) {
		String sql = "SELECT * from (SELECT * FROM user_has_user INNER JOIN user ON user_has_user.responser = user.email)temp WHERE requester = ? and status = 1"; 
		
		return getJdbcTemplate().query(sql, rowMapper, email);
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
	
	
}
