package candrun.scheduled;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import candrun.dao.TaskDAO;

public class DailyInitializeJob extends QuartzJobBean{
	
	TaskDAO taskDao;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		taskDao.saveNudgeCount();
		taskDao.initializeNudgeCount();
	}

	public void setTaskDao(TaskDAO taskDao) {
		this.taskDao = taskDao;
	}

}
