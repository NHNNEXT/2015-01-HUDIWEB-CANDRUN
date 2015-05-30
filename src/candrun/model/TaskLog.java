package candrun.model;

import java.sql.Timestamp;

public class TaskLog {

	private int id;
	private Timestamp date;
	private int taskId;
	private Boolean complete;
	private int count;
	
	public TaskLog(int id, Timestamp date, int taskId, Boolean complete, int count) {
		this.id = id;
		this.date = date;
		this.taskId = taskId;
		this.complete = complete;
		this.count = count;
	}
	
	public int getId() {
		return id;
	}
	public Timestamp getDate() {
		return date;
	}
	public int getTaskId() {
		return taskId;
	}
	public Boolean getComplete() {
		return complete;
	}
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "TaskLog [id=" + id + ", date=" + date + ", taskId=" + taskId + ", complete=" + complete + ", count="
				+ count + "]";
	}
}
