package candrun.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import candrun.dao.GoalDAO;

@Service
public class GoalService {
	
	@Autowired
	private GoalDAO goalDao;

}
