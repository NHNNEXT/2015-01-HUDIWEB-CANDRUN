package candrun.model;

import java.util.Map;

public class GoalRelation {

	private Goal myGoal;
	private Map<User, Goal> relation;
	
	public GoalRelation(Goal myGoal, Map<User, Goal> relation) {
		this.myGoal = myGoal;
		this.relation = relation;
	}

	public Goal getMyGoal() {
		return myGoal;
	}

	public void setMyGoal(Goal myGoal) {
		this.myGoal = myGoal;
	}

	public Map<User, Goal> getRelation() {
		return relation;
	}

	public void setRelation(Map<User, Goal> relation) {
		this.relation = relation;
	}
}
