package candrun.model;

public class Task {

	private int id;
	private String contents;
	private int nudge;
	private int praise;
	private int combo;
	private int successDays;
	private int maxCombo;
	private int goalId;
	private int achievement;

	public Task(String contents, int goalId) {
		this.contents = contents;
		this.goalId = goalId;
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
}
