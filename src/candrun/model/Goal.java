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
	private int state;

	public Goal(String contents, String email) {
		this.contents = contents;
		this.email = email;
	}
	
	public Goal(int id, String contents, String email, Timestamp startDate,
			Timestamp createdDate, Timestamp modDate, int successDays,
			int combo, int maxCombo, int achievement, int state) {
		this(contents, email);
		this.id = id;
		this.startDate = startDate;
		this.createdDate = createdDate;
		this.modDate = modDate;
		this.successDays = successDays;
		this.combo = combo;
		this.maxCombo = maxCombo;
		this.achievement = achievement;
		this.state = state;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}