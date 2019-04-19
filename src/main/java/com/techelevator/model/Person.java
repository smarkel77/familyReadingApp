package com.techelevator.model;

public class Person {
	
	private String name;
	private boolean parent;
	private long peopleId;
	private long familyId;
	private boolean inactive;
	
	
	
	
	
	public boolean isInactive() {
		return inactive;
	}
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isParent() {
		return parent;
	}
	public void setParent(boolean parent) {
		this.parent = parent;
	}
	public long getPeopleId() {
		return peopleId;
	}
	public void setPeopleId(long peopleId) {
		this.peopleId = peopleId;
	}
	public long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(long familyId) {
		this.familyId = familyId;
	}
	
	

}
