package candrun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;

import candrun.dao.UserDAO;
import candrun.model.User;
import candrun.support.enums.CommonInvar;
import candrun.support.enums.ReturnMessage;
import candrun.support.enums.UserState;


public class FriendsService {

	private UserDAO userDao;
	
	public FriendsService(UserDAO userDao) {
		this.userDao = userDao;
	}

	public Map<String, List<User>> getFriends(String email) {
		Map<String, List<User>> friendsList = new HashMap<String, List<User>>();
		Map<Integer, List<User>> friendsLists = new HashMap<Integer, List<User>>();
		List<User> availableFriends = new ArrayList<User>();
		List<User> nonavailableFriends = new ArrayList<User>();
		friendsLists.put(UserState.CERTIED.getValue(), nonavailableFriends);
		friendsLists.put(UserState.MAKABLE_NONAVAILABLE.getValue(), nonavailableFriends);
		friendsLists.put(UserState.MAX_NONAVAILABLE.getValue(), nonavailableFriends);
		friendsLists.put(UserState.MAKABLE_AVAILABLE.getValue(), availableFriends);
		friendsLists.put(UserState.MAX_AVAILABLE.getValue(), availableFriends);
		
		List<User> acceptedFriends = userDao.getFriendsAsReciever(email);
		acceptedFriends.addAll(userDao.getFriendsAsRequester(email));
		
		acceptedFriends.forEach(f -> friendsLists.get(f.getState()).add(f));
		
		friendsList.put(CommonInvar.REQUESTEDFRIENDS.getValue(), userDao.getRequesters(email));
		friendsList.put(CommonInvar.AVAILABLEFRIENDS.getValue(), availableFriends);
		friendsList.put(CommonInvar.NONAVAILABLEFRIENDS.getValue(), nonavailableFriends);
		
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
