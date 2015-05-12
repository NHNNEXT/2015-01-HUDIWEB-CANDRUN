package candrun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import candrun.model.Task;

/*
 * applicationContext.xml에서 component-scan을 걸어두었다면
 * @Repository, @Service, @Component 등의 어노테이션을 바탕으로 bean으로 읽게됩니다.
 * 
 * 보통 JdbcTemplate을 사용하는 Dao의 경우
 * 1. JdbcTemplate 내부에 dataSource의 참조를 가지고 있으므로 Dao에 직접 set할 필요는 없습니다.
 * 2. JdbcTemplate은 의미대로 템플릿이므로 하나의 객체만 있으면 충분합니다.
 *    따라서 field에 참조를 두더라도 thread-safe 합니다.
 *    
 * 아래는 위 내용으로 재구성한 dao의 모습입니다.
 * (반드시 해당 패키지를 component-scan 해야만 합니다)
 * 

@Repository
public class TaskDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void addTask(Task task) {
		String sql = "INSERT INTO tasks(contents, goal_id) VALUES(?, ?)";
		this.jdbcTemplate.update(sql, task.getContents(), task.getGoalId());
	}

	// 같은 goal아래 tasks을 불러온다
	public List<Task> getTasksByGoalId(int goalId) {
		String sql = "SELECT * FROM tasks WHERE goal_id = ? LIMIT 3";
		return this.jdbcTemplate.query(sql, new TaskMapper(), goalId);
	}
 */

public class TaskDAO extends JdbcDaoSupport {

	private static final class TaskMapper implements RowMapper<Task> {

		@Override
		public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
			try {
				return new Task(rs.getBigDecimal("id").intValue(), rs.getString("contents"),
						rs.getInt("nudge"), rs.getInt("combo"),
						rs.getInt("success_days"), rs.getInt("max_combo"),
						rs.getInt("goal_id"), rs.getInt("achievement"),
						rs.getBoolean("complete"));
			} catch (SQLException e) {
				throw new BeanInstantiationException(Task.class,
						e.getMessage(), e);
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
	
	public void initializeNudgeCount(){
		String sql = "UPDATE tasks SET nudge = 0";
		getJdbcTemplate().update(sql);
	}
	
	public void saveNudgeCount(){
		String sql = "INSERT INTO nudge (tasks_id, count) SELECT id, nudge From tasks";
		getJdbcTemplate().update(sql);
	}
}
