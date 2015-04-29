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
import candrun.support.enums.FriendRequestState;
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
	// Accepted 된 친구만 불러오도록 수정
	public List<User> findFriendsAsRequester(String email) {
		String sql = "SELECT * FROM (SELECT u.*, uu.requester , uu.receiver, uu.state AS 'uu_state' FROM user_has_user uu INNER JOIN user u ON uu.receiver = u.email WHERE uu.state = 1) temp WHERE requester = ?";
		return getJdbcTemplate().query(sql, rowMapper, email);
	}

	public List<User> findFriendsAsReciever(String email) {
		String sql = "SELECT * FROM (SELECT u.*, uu.requester , uu.receiver, uu.state AS 'uu_state' FROM user_has_user uu INNER JOIN user u ON uu.requester = u.email WHERE uu.state = 1) temp WHERE receiver = ?";
		return getJdbcTemplate().query(sql, rowMapper, email);
	}
	/**
	 * 자신이 친구신청을 했으나 아직 승인을 하지 않은 친구 리스트 반환
	 * @param email 자신의 email
	 * @see candrun.dao.UserDAO#findRequesters(String)
	 */
	public List<User> findRequestingFriends(String email) {
		String sql = "SELECT * FROM (SELECT u.*, uu.requester , uu.receiver, uu.state AS 'uu_state' FROM user_has_user uu INNER JOIN user u ON uu.receiver = u.email WHERE uu.state = 0) temp WHERE requester = ?";
		return getJdbcTemplate().query(sql, rowMapper, email);
	}
	/**
	 * 자신에게 친구신청을 했으나 아직 승인을 하지 않은 친구 리스트 반환
	 * @param email 자신의 email
	 * @see candrun.dao.UserDAO#findRequestingFriends(String)
	 */
	public List<User> findRequesters(String email) {
		String sql = "SELECT * FROM (SELECT u.*, uu.requester , uu.receiver, uu.state AS 'uu_state' FROM user_has_user uu INNER JOIN user u ON uu.requester = u.email WHERE uu.state = 0) temp WHERE receiver = ?";
		return getJdbcTemplate().query(sql, rowMapper, email);
	}

	// 친구 신청 및 승인, 거절과 관련된 메서드들 시작////////////////////////////////////////////
	public void requestToBeFriend(String reqEmail, String rcvEmail) {
		String sql = "INSERT INTO user_has_user(requester, receiver) VALUES(?, ?)";
		getJdbcTemplate().update(sql, reqEmail, rcvEmail);
	}

	public boolean isNewRequestToBeFriend(String reqEmail, String rcvEmail) {
		RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
		String sql = "SELECT id FROM user_has_user WHERE (requester = ? AND receiver = ?) OR (requester = ? AND receiver = ?)";
		getJdbcTemplate().query(sql, countCallback, reqEmail, rcvEmail,
				rcvEmail, reqEmail);
		int rowCount = countCallback.getRowCount();
		return rowCount == 0;
	}

	public void acceptRequestToBeFriend(String reqEmail, String rcvEmail) {
		String sql = "UPDATE user_has_user SET state = ?, complete_date = NOW() WHERE requester = ? AND receiver = ?";
		getJdbcTemplate().update(sql, FriendRequestState.ACCEPTED.getValue(),
				reqEmail, rcvEmail);
	}

	public void denyRequestToBeFriend(String reqEmail, String rcvEmail) {
		String sql = "UPDATE user_has_user SET state = ?, complete_date = NOW() WHERE requester = ? AND receiver = ?";
		getJdbcTemplate().update(sql, FriendRequestState.DENIED.getValue(),
				reqEmail, rcvEmail);
	}

	// 친구 신청 및 승인, 거절과 관련된 메서드들 끝////////////////////////////////////////////
	public void changeState(String email, UserState userState) {
		int state;

		switch (userState) {
		case REGISTERED:
			state = UserState.REGISTERED.getValue();
			break;
		case CERTIED:
			state = UserState.CERTIED.getValue();
			break;
		case QUITED:
			state = UserState.QUITED.getValue();
			break;
		default:
			state = 0;
			break;
		}
		String sql = "UPDATE user SET state = ? WHERE email = ?";
		getJdbcTemplate().update(sql, state, email);
	}

}