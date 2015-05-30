package candrun.model;

import java.util.List;

public class Task {

	private int id;
	private String contents;
	private int nudge;
	private int combo;
	private int successDays;
	private int maxCombo;
	private int goalId;
	private int achievement;
	private Boolean complete;
	private List<TaskLog> taskLogs;
	
	public Task(String contents, int goalId) {
		this.contents = contents;
		this.goalId = goalId;
	}

	public Task(int id, String contents, int nudge, int combo, int successDays,
			int maxCombo, int goalId, int achievement, boolean complete) {
		this(contents, goalId);
		this.nudge = nudge;
		this.combo = combo;
		this.successDays = successDays;
		this.maxCombo = maxCombo;
		this.id = id;
		this.achievement = achievement;
		this.complete = complete;
	}

	public int getId() {
		return id;
	}

	public String getContents() {
		return contents;
	}

	public int getNudge() {
		return nudge;
	}

	public int getCombo() {
		return combo;
	}

	public int getSuccessDays() {
		return successDays;
	}

	public int getMaxCombo() {
		return maxCombo;
	}

	public int getGoalId() {
		return goalId;
	}

	public int getAchievement() {
		return achievement;
	}

	public boolean isComplete() {
		return complete;
	}

	public List<TaskLog> getTaskLogs() {
		return taskLogs;
	}

	public void setTaskLogs(List<TaskLog> taskLogs) {
		this.taskLogs = taskLogs;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", contents=" + contents + ", nudge=" + nudge + ", combo=" + combo + ", successDays="
				+ successDays + ", maxCombo=" + maxCombo + ", goalId=" + goalId + ", achievement=" + achievement
				+ ", complete=" + complete + ", taskLogs=" + taskLogs + "]";
	}
}
