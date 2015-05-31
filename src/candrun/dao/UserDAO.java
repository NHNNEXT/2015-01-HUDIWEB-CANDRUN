package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import candrun.model.User;
import candrun.support.enums.RelationRequestState;
import candrun.support.enums.UserState;

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
	
	private RowMapper<User> rowMapperForFriends = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) {
			try {
				return new User(rs.getString("email"),
						rs.getString("nickname"),
						rs.getString("pic_path"));
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

	public User getByEmail(String email) throws EmptyResultDataAccessException {
		String sql = "SELECT * FROM user WHERE email = ?";
		return getJdbcTemplate().queryForObject(sql, rowMapper, email);
	}

	public void changeState(String email, UserState userState) {
		String sql = "UPDATE user SET state = ? WHERE email = ?";
		getJdbcTemplate().update(sql, userState.getValue(), email);
	}

	public List<User> getUsersByGoalId(int goalId, String email) {
		String sql = "SELECT DISTINCT u.* FROM (SELECT gg.requester, gg.receiver, g.id, g.user_email FROM goal_has_goal gg INNER JOIN goal g ON (gg.receiver = g.id || gg.requester = g.id)) temp INNER JOIN user u ON u.email = user_email WHERE (requester = ? || receiver = ?) AND !(email = ?)";

		return getJdbcTemplate().query(sql, rowMapper, goalId, goalId, email);
	}

	/**
	 * requester기준으로 현재 친구 상태인 (상태값이 1인) 친구 리스트를 불러온다.
	 * 
	 * @param email
	 * @return 친구 리스트
	 */
	public List<User> getFriendsAsRequester(String email) {
		String sql = "SELECT * FROM (SELECT u.*, uu.requester , uu.receiver, uu.state AS 'uu_state' FROM user_has_user uu INNER JOIN user u ON uu.receiver = u.email WHERE uu.state = 1) temp WHERE requester = ?";
		return getJdbcTemplate().query(sql, rowMapperForFriends, email);
	}

	/**
	 * receiver기준으로 현재 친구 상태인 (상태값이 1인) 친구 리스트를 불러온다.
	 * 
	 * @param email
	 * @return 친구 리스트
	 */
	public List<User> getFriendsAsReciever(String email) {
		String sql = "SELECT * FROM (SELECT u.*, uu.requester , uu.receiver, uu.state AS 'uu_state' FROM user_has_user uu INNER JOIN user u ON uu.requester = u.email WHERE uu.state = 1) temp WHERE receiver = ?";
		return getJdbcTemplate().query(sql, rowMapperForFriends, email);
	}

	/**
	 * 자신이 친구신청을 했으나 아직 승인을 하지 않은 친구 리스트 반환
	 * 
	 * @param email
	 *            자신의 email
	 * @see candrun.dao.UserDAO#getRequesters(String)
	 */
	public List<User> getRequestingFriends(String email) {
		String sql = "SELECT * FROM (SELECT u.*, uu.requester , uu.receiver, uu.state AS 'uu_state' FROM user_has_user uu INNER JOIN user u ON uu.receiver = u.email WHERE uu.state = 0) temp WHERE requester = ?";
		return getJdbcTemplate().query(sql, rowMapperForFriends, email);
	}

	/**
	 * 자신에게 친구신청을 했으나 아직 승인을 하지 않은 친구 리스트 반환
	 * 
	 * @param email
	 *            자신의 email
	 * @see candrun.dao.UserDAO#getRequestingFriends(String)
	 */
	public List<User> getRequesters(String email) {
		String sql = "SELECT * FROM (SELECT u.*, uu.requester , uu.receiver, uu.state AS 'uu_state' FROM user_has_user uu INNER JOIN user u ON uu.requester = u.email WHERE uu.state = 0) temp WHERE receiver = ?";
		return getJdbcTemplate().query(sql, rowMapperForFriends, email);
	}

	/**
	 * 친구 신청
	 * 
	 * @param reqEmail
	 *            친구를 신청하는 사람의 이메일
	 * @param rcvEmail
	 *            친구 신청을 받는 사람의 이메일
	 */
	public void requestToBeFriend(String reqEmail, String rcvEmail) {
		String sql = "INSERT INTO user_has_user(requester, receiver) VALUES(?, ?)";
		getJdbcTemplate().update(sql, reqEmail, rcvEmail);
	}

	/**
	 * 친구 신청 이력 확인
	 * 
	 * @param reqEmail
	 *            친구를 신청한 사람의 이메일
	 * @param rcvEmail
	 *            친구 신청을 받은 사람의 이메일
	 * @return true이면 친구 신청 이력이 없음, false이면 친구 신청 이력 존재
	 */
	public boolean isNewRequestToBeFriend(String reqEmail, String rcvEmail) {
		RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
		String sql = "SELECT id FROM user_has_user WHERE (requester = ? AND receiver = ?) OR (requester = ? AND receiver = ?)";
		getJdbcTemplate().query(sql, countCallback, reqEmail, rcvEmail,
				rcvEmail, reqEmail);
		int rowCount = countCallback.getRowCount();
		return rowCount == 0;
	}

	/**
	 * 친구 신청이 수락되었는지 확인
	 * @param reqEmail 친구를 신청한 사람의 이메일
	 * @param rcvEmail 친구 신청을 받은 사람의 이메일
	 * @return true이면 신청을 수락한 상태
	 */
	public boolean isAcceptedRequestToBeFriend(String reqEmail, String rcvEmail) {
		RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
		String sql = "SELECT id FROM user_has_user WHERE (requester = ? AND receiver = ? AND state = ?) OR (requester = ? AND receiver = ? AND state = ?)";
		getJdbcTemplate().query(sql, countCallback, reqEmail, rcvEmail,
				RelationRequestState.ACCEPTED, rcvEmail, reqEmail,
				RelationRequestState.ACCEPTED);
		int rowCount = countCallback.getRowCount();
		return rowCount != 0;
	}

	/**
	 * 기 신청 상태인지 확인
	 * @param reqEmail 친구를 신청한 사람의 이메일
	 * @param rcvEmail 친구 신청을 받은 사람의 이메일
	 * @return true이면 기신청 상태
	 */
	public boolean isRequestedRequestToBeFriend(String reqEmail, String rcvEmail) {
		RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
		String sql = "SELECT id FROM user_has_user WHERE (requester = ? AND receiver = ? AND state = ?) OR (requester = ? AND receiver = ? AND state = ?)";
		getJdbcTemplate().query(sql, countCallback, reqEmail, rcvEmail,
				RelationRequestState.REQUESTED, rcvEmail, reqEmail,
				RelationRequestState.REQUESTED);
		int rowCount = countCallback.getRowCount();
		return rowCount != 0;
	}

	/**
	 * 친구 신청을 수락
	 * 
	 * @param reqEmail
	 *            친구를 신청한 사람의 이메일
	 * @param rcvEmail
	 *            친구 신청을 받은 사람의 이메일
	 */
	public void acceptRequestToBeFriend(String reqEmail, String rcvEmail) {
		String sql = "UPDATE user_has_user SET state = ?, complete_date = NOW() WHERE requester = ? AND receiver = ?";
		getJdbcTemplate().update(sql, RelationRequestState.ACCEPTED.getValue(),
				reqEmail, rcvEmail);
	}

	/**
	 * 친구 신청을 거절
	 * 
	 * @param reqEmail
	 *            친구 신청을 한 사람의 이메일
	 * @param rcvEmail
	 *            친구 신청을 받은 사람의 이메일
	 */
	public void denyRequestToBeFriend(String reqEmail, String rcvEmail) {
		String sql = "UPDATE user_has_user SET state = ?, complete_date = NOW() WHERE requester = ? AND receiver = ?";
		getJdbcTemplate().update(sql, RelationRequestState.DENIED.getValue(),
				reqEmail, rcvEmail);
	}
}