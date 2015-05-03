package candrun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import candrun.exception.PreparedStatementException;
import candrun.model.Goal;
import candrun.support.enums.GoalState;
import candrun.support.enums.RelationRequestState;

public class GoalDAO extends JdbcDaoSupport {
	// connection을 만든다
	private RowMapper<Goal> rowMapper = new RowMapper<Goal>() {
		public Goal mapRow(ResultSet rs, int rowNum) {
			try {
				return new Goal(rs.getInt("id"), rs.getString("contents"),
						rs.getString("user_email"),
						rs.getTimestamp("start_date"),
						rs.getTimestamp("created_date"),
						rs.getTimestamp("mod_date"), rs.getInt("success_days"),
						rs.getInt("combo"), rs.getInt("max_combo"),
						rs.getInt("achievement"), rs.getInt("state"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(Goal.class,
						e.getMessage(), e);
			}
		}
	};
	private RowMapper<Integer> rowMapperForGettingGoalIdsFromGoalHasGoal = new RowMapper<Integer>() {
		public Integer mapRow(ResultSet rs, int rowNum) {
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
				throw new BeanInstantiationException(Goal.class,
						e.getMessage(), e);
			}
		}
	};

	// 입력받은 goal을 db에 넣는다.
	public int addGoal(Goal goal) {
		String sql = "INSERT INTO goal(contents, user_email) VALUES(?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		getJdbcTemplate().update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(
					Connection connection) {
				PreparedStatement ps = null;
				try {
					ps = connection.prepareStatement(sql, new String[] { "id" });
					ps.setString(1, goal.getContents());
					ps.setString(2, goal.getEmail());

					return ps;
				} catch (SQLException e) {
					throw new PreparedStatementException();
				}
			}
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	public void modifyGoal(String contents, int id) {
		String sql = "UPDATE goal SET contents = ?, mod_date = NOW() WHERE id = ?";
		getJdbcTemplate().update(sql, contents, id);
	}

	public void startGoal(int id) {
		String sql = "UPDATE goal SET state = ?, start_date = NOW() WHERE id = ?";
		getJdbcTemplate().update(sql, GoalState.STARTED.getValue(), id);
	}

	public void finishGoal(int id) {
		String sql = "UPDATE goal SET state = ?, end_date = NOW() WHERE id = ?";
		getJdbcTemplate().update(sql, GoalState.FINISHED.getValue(), id);
	}

	// goalId와 일치하는 goal을 불러온다
	public Goal getGoalById(int goalId) {
		String sql = "SELECT * FROM goal WHERE id = ?";
		return getJdbcTemplate().queryForObject(sql, rowMapper, goalId);
	}

	// 가장 최근 넣은 goal 5개까지 불러온다.
	public List<Goal> getRecentGoalsByEmail(String email) {
		String sql = "SELECT * FROM goal WHERE user_email = ? ORDER BY created_date DESC LIMIT 5";
		return getJdbcTemplate().query(sql, rowMapper, email);
	}

	/**
	 * 최신 순으로 지정한 수만큼 Goal list를 반환
	 * 
	 * @param email
	 *            자신의 email
	 * @param max
	 *            가져올 goal의 갯수
	 * @see candrun.dao.GoalDAO#getRecentGoalsByEmail(String)
	 */
	public List<Goal> getRecentGoalsByEmail(String email, int max) {
		String sql = "SELECT * FROM goal WHERE user_email = ? ORDER BY created_date DESC LIMIT ?";
		return getJdbcTemplate().query(sql, rowMapper, email, max);
	}

	public List<Goal> getGoals(String email, GoalState goalState) {
		String sql = "SELECT * FROM goal WHERE user_email = ? AND state = ?";
		return getJdbcTemplate().query(sql, rowMapper, email,
				goalState.getValue());
	}

	/**
	 * 함께 관리할 골끼리 연결
	 * 
	 * @param req
	 *            요청하는 골의 id
	 * @param rcv
	 *            요청을 받는 골의 id
	 */
	public void requestConnectGoal(int req, int rcv) {
		String sql = "INSERT INTO goal_has_goal(requester, receiver) VALUES(?, ?)";
		getJdbcTemplate().update(sql, req, rcv);
	}

	public void changeState(int id, RelationRequestState rrs) {
		String sql = "UPDATE goal_has_goal SET state = ? WHERE id = ?";
		getJdbcTemplate().update(sql, rrs.getValue(), id);
	}

	public List<Integer> getRelatedGoalIdsByRequester(int goalId,
			RelationRequestState rrs) {
		String sql = "SELECT receiver FROM goal_has_goal WHERE requester = ? AND state = ?";
		return getJdbcTemplate().query(sql,
				rowMapperForGettingGoalIdsFromGoalHasGoal, goalId,
				rrs.getValue());
	}

	public List<Integer> getRelatedGoalIdsByReceiver(int goalId,
			RelationRequestState rrs) {
		String sql = "SELECT requester FROM goal_has_goal WHERE receiver = ? AND state = ?";
		return getJdbcTemplate().query(sql,
				rowMapperForGettingGoalIdsFromGoalHasGoal, goalId,
				rrs.getValue());
	}

	/**
	 * 특정 골과 관련 있는 골아이디의 리스트 반환
	 * 
	 * @param goalId
	 *            관련 골을 찾으려는 골의 id
	 * @param rrs
	 *            찾으려는 관계 값
	 */
	public List<Integer> getRelatedGoals(int goalId, RelationRequestState rrs) {
		List<Integer> relatedGoals = getRelatedGoalIdsByReceiver(goalId, rrs);
		relatedGoals.addAll(getRelatedGoalIdsByRequester(goalId, rrs));
		return relatedGoals;
	}
}