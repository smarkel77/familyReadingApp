package com.techelevator.model.DAO;

import com.techelevator.model.Signup;
import com.techelevator.model.User;

public interface UserDAO {

	public void saveUser(String userName, String password, long familyId);

	public boolean searchForUsernameAndPassword(String userName, String password);

	public void updatePassword(String userName, String password);

	public Object getUserByUserName(String userName);
	
	public Long getFamilyByUser(User user);

}
