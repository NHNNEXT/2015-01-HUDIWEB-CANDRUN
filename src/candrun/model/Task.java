package candrun.model;

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

	public Task(String contents, int goalId) {
		this.contents = contents;
		this.goalId = goalId;
	}

	public Task(int id, String contents, int nudge, int combo, int successDays,
			int maxCombo, int goalId, int achievement, boolean complete) {
		this(contents, id);
		this.nudge = nudge;
		this.combo = combo;
		this.successDays = successDays;
		this.maxCombo = maxCombo;
		this.goalId = goalId;
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

	@Override
	public String toString() {
		return "Task [id=" + id + ", goalId=" + goalId + ", contents="
				+ contents + "]";
	}

}
