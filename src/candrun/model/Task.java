package candrun.model;

public class Task {

	private Integer id;
	private String contents;
	private Integer nudge;
	private Integer praise;
	private Integer combo;
	private Integer successDays;
	private Integer maxCombo;
	private Integer goalId;
	private Integer achievement;

	public Task(String contents, int goalId) {
		this.contents = contents;
		this.goalId = goalId;
	}

	public Task(String contents, int goalId, int id, int nudge) {
		this.contents = contents;
		this.goalId = goalId;
		this.id = id;
		this.nudge = nudge;
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

	public int getPraise() {
		return praise;
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

	@Override
	public String toString() {
		return "Task [id=" + id + ", goalId=" + goalId + ", contents="
				+ contents + "]";
	}

}
