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

public class GoalDAO extends JdbcDaoSupport {
	// connection을 만든다
	private RowMapper<Goal> rowMapper = new RowMapper<Goal>() {
		public Goal mapRow(ResultSet rs, int rowNum) {
			try {
				return new Goal(rs.getInt("id"), rs.getString("contents"),
						rs.getTimestamp("start_date"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(Goal.class, e.getMessage(), e);   
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
						ps = connection.prepareStatement(sql,
								new String[] { "id" });
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
	
	public void modifyGoal(Goal goal) {
		String sql = "UPDATE goal SET contents = ?, mod_date = NOW() WHERE id = ?";
		getJdbcTemplate().update(sql, goal.getContents(), goal.getId());
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
	public Goal findGoalById(int goalId) {
		String sql = "SELECT * FROM goal WHERE id = ?";
		return getJdbcTemplate().queryForObject(sql, rowMapper, goalId);
	}
		

	// 가장 최근 넣은 goal 5개까지 불러온다.
	public List<Goal> findRecentGoalsByEmail(String email) {
		String sql = "SELECT * FROM goal WHERE user_email = ? ORDER BY created_date DESC LIMIT 5";
		return getJdbcTemplate().query(sql, rowMapper, email);
	}
	/**
	 * 최신 순으로 지정한 수만큼 Goal list를 반환 
	 * @param email 자신의 email 
	 * @param max 가져올 goal의 갯수
	 * @see candrun.dao.GoalDAO#findRecentGoalsByEmail(String)
	 */
	public List<Goal> findRecentGoalsByEmail(String email, int max) {
		String sql = "SELECT * FROM goal WHERE user_email = ? ORDER BY created_date DESC LIMIT ?";
		return getJdbcTemplate().query(sql, rowMapper, email, max);
	}
}     