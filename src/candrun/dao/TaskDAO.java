package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import candrun.model.Task;
import candrun.model.TaskLog;

public class TaskDAO extends JdbcDaoSupport {

	private static final class TaskMapper implements RowMapper<Task> {

		@Override
		public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				return new Task(rs.getBigDecimal("id").intValue(), rs.getString("contents"), rs.getInt("nudge"),
						rs.getInt("combo"), rs.getInt("success_days"), rs.getInt("max_combo"), rs.getInt("goal_id"),
						rs.getInt("achievement"), rs.getBoolean("complete"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(Task.class, e.getMessage(), e);
			}
		}
	}

	private static final class TaskLogMapper implements RowMapper<TaskLog> {

		@Override
		public TaskLog mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				return new TaskLog(rs.getInt("id"), rs.getTimestamp("date"), rs.getInt("tasks_id"),
						rs.getBoolean("complete"), rs.getInt("count"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(Task.class, e.getMessage(), e);
			}
		}
	}

	public void addTask(Task task) {
		String sql = "INSERT INTO tasks(contents, goal_id) VALUES(?, ?)";
		getJdbcTemplate().update(sql, task.getContents(), task.getGoalId());
	}

	// 같은 goal아래 tasks을 불러온다
	public List<Task> getTasksByGoalId(int goalId) {
		String sql = "SELECT * FROM tasks WHERE goal_id = ? LIMIT 3";
		return getJdbcTemplate().query(sql, new TaskMapper(), goalId);
	}

	// TaskId로 해당 태스크를 가져온다.
	public Task getTaskByTaskId(int taskId) {
		String sql = "SELECT * FROM tasks WHERE id = ?";
		return getJdbcTemplate().queryForObject(sql, new TaskMapper(), taskId);
	}

	// taskId를 갖고 와서 해당 task의 넛지 수를 하나 올려준다
	public void addNudge(int taskId) {
		String sql = "UPDATE tasks SET nudge=nudge+1 WHERE id=?";
		getJdbcTemplate().update(sql, taskId);
	}

	public void completeTask(int taskId) {
		String sql = "UPDATE tasks SET complete = ? WHERE id = ?";
		getJdbcTemplate().update(sql, true, taskId);
	}

	public void initializeNudgeCount() {
		String sql = "UPDATE tasks SET nudge = 0";
		getJdbcTemplate().update(sql);
	}

	public void saveTaskLog() {
		String sql = "INSERT INTO log (tasks_id, count, complete) SELECT id, nudge, complete From tasks";
		getJdbcTemplate().update(sql);
	}

	public List<TaskLog> getTaskLogsByTaskId(int taskId) {
		String sql = "SELECT * FROM log WHERE tasks_id = ? ORDER BY date DESC LIMIT 6";
		return getJdbcTemplate().query(sql, new TaskLogMapper(), taskId);
	}
}
