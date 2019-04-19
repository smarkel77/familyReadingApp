package com.techelevator.model.DAO;

import java.util.List;

import com.techelevator.model.Person;

public interface PersonDAO {
	
	public void addPerson(long familyId, String name, boolean isParent, boolean isInactive);
	
	public void deletePerson(long personId);
	
	public List<Person> getListOfPeopleInFamily(long familyId);
	
}
