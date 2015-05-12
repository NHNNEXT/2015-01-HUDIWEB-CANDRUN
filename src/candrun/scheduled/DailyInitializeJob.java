package candrun.scheduled;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import candrun.dao.TaskDAO;

/*
 * 스프링3 @Scheduled 어노테이션으로 job을 지원하고 있습니다 ㅠㅠ 
 * - 참고: http://jsonobject.tistory.com/101
 * - 주의사항: xml에 task 설정시 task namespace 꼭 확인 바랍니다.
 */
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
