package candrun.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import candrun.dao.UserDAO;
import candrun.model.User;


public class FriendsService {
	private static final Logger logger = LoggerFactory.getLogger(FriendsService.class);

	private UserDAO userDao;
	
	public FriendsService(UserDAO userDao) {
		this.userDao = userDao;
	}

	public List<User> getFriends(String email) {
		List<User> friendsList = new ArrayList<User>(userDao.getFriendsAsRequester(email));
		System.out.println(friendsList);
		friendsList.addAll(userDao.getFriendsAsReciever(email));
		
		for (int i = 0; i < friendsList.size(); i++) {
			logger.debug(friendsList.get(i).toString());
		}
		
		return friendsList;		
	}
}
