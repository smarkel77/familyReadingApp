package com.techelevator.model.DAO;

import java.util.List;

import com.techelevator.model.Goal;
import com.techelevator.model.Session;

public interface SessionDAO {
	
	public void addSession(Session newSession);
	
	public List<Session> getListOfSessionsByPerson(long personId);
	
	public List<Session> getListOfSessionsByFamily(long familyId);
	

}
