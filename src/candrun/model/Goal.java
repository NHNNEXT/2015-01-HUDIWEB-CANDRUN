package candrun.model;

import java.sql.Timestamp;

import org.joda.time.DateTime;

public class Goal {
	private String contents;
	private String email;
	private Timestamp startDate;
	private Timestamp createdDate;
	private Timestamp modDate;
	private int successDays;
	private int combo;
	private int maxCombo;
	private int achievement;

	DateTime dateTime = new DateTime();

	public Goal(String contents, String email) {
		this.contents = contents;
		this.email = email;
	}

	public void setCreatedDate() {
		createdDate = new Timestamp(dateTime.getMillis());
	}

	public void setModDate() {
		modDate = new Timestamp(dateTime.getMillis());
	}

	public void setStart() {
		startDate = new Timestamp(dateTime.getMillis());
	}

	public String getContents() {
		return contents;
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

	public DateTime getDateTime() {
		return dateTime;
	}

	public String getEmail() {
		return email;
	}

}