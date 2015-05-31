package candrun.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;

import candrun.dao.UserDAO;
import candrun.model.User;
import candrun.support.enums.CommonInvar;
import candrun.support.enums.ReturnMessage;


public class FriendsService {

	private UserDAO userDao;
	
	public FriendsService(UserDAO userDao) {
		this.userDao = userDao;
	}

	public Map<String, List<User>> getFriends(String email) {
		Map<String, List<User>> friendsList = new HashMap<String, List<User>>();
		List<User> acceptedFriends = userDao.getFriendsAsReciever(email);
		acceptedFriends.addAll(userDao.getFriendsAsRequester(email));
		
		friendsList.put(CommonInvar.ACCEPTEDFRIENDS.getValue(), acceptedFriends);
		friendsList.put(CommonInvar.REQUESTEDFRIENDS.getValue(), userDao.getRequesters(email));
		
		return friendsList;
	}
	
	public void acceptRequest(String reqEmail, String rcvEmail) {
		userDao.acceptRequestToBeFriend(reqEmail, rcvEmail);
	}
	
	public String addFriend(String reqEmail, String rcvEmail) {
		try {
			userDao.getByEmail(rcvEmail);
		}catch(EmptyResultDataAccessException e) {
			return ReturnMessage.NULL.getValue();
		}
		if (userDao.isAcceptedRequestToBeFriend(reqEmail, rcvEmail))
			return ReturnMessage.ACCEPTED.getValue();
		if (userDao.isRequestedRequestToBeFriend(reqEmail, rcvEmail))
			return ReturnMessage.REQUESTED.getValue();
		userDao.requestToBeFriend(reqEmail, rcvEmail);
		return ReturnMessage.MAKEREQUEST.getValue();
	}
}
