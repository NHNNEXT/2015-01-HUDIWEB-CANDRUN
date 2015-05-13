package candrun.model;

import java.util.List;

public class GoalWithTasks {
	private Goal goal;
	private List<Task> tasks;
	
	public GoalWithTasks(Goal goal, List<Task> tasks) {
		super();
		this.goal = goal;
		this.tasks = tasks;
	}

	public Goal getGoal() {
		return goal;
	}

	public List<Task> getTasks() {
		return tasks;
	}
	
}
