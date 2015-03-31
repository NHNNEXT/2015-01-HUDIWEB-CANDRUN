package candrun.model;

import java.sql.Timestamp;

public class Goal {
	
	private int id;
	private String contents;
	private String email;
	private Timestamp startDate;
	private Timestamp createdDate;
	private Timestamp modDate;
	private int successDays;
	private int combo;
	private int maxCombo;
	private int achievement;

	public Goal(String contents, String email) {
		this.contents = contents;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}

	public String getContents() {
		return contents;
	}
	
	public String getEmail() {
		return email;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public Timestamp getModDate() {
		return modDate;
	}

	public int getSuccessDays() {
		return successDays;
	}

	public int getCombo() {
		return combo;
	}

	public int getMaxCombo() {
		return maxCombo;
	}

	public int getAchievement() {
		return achievement;
	}
}