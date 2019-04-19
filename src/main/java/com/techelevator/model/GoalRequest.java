package com.techelevator.model;

import java.time.LocalDate;

public class GoalRequest {
	
	private long goalId;
	private String nameOfGoal;
	private String startDate;
	private int numberOfDays;
	private String description;
	private int minutesToReachGoal;
	private Long personId;
	
	public long getGoalId() {
		return goalId;
	}
	public void setGoalId(long goalId) {
		this.goalId = goalId;
	}
	public String getNameOfGoal() {
		return nameOfGoal;
	}
	public void setNameOfGoal(String nameOfGoal) {
		this.nameOfGoal = nameOfGoal;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public int getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMinutesToReachGoal() {
		return minutesToReachGoal;
	}
	public void setMinutesToReachGoal(int minutesToReachGoal) {
		this.minutesToReachGoal = minutesToReachGoal;
	}

	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	

}
