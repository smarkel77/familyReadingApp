package com.techelevator.model;

public class SessionRequest {

	private String dateOfReading;
	private int minutesRead;
	private String typeOfReading;
	private long personId;
	private long isbn;
	private boolean finished;
	private long goalId;
	
	public long getGoalId() {
		return goalId;
	}
	public void setGoalId(long goalId) {
		this.goalId = goalId;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public long getIsbn() {
		return isbn;
	}
	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}
	public String getDateOfReading() {
		return dateOfReading;
	}
	public void setDateOfReading(String dateOfReading) {
		this.dateOfReading = dateOfReading;
	}
	public int getMinutesRead() {
		return minutesRead;
	}
	public void setMinutesRead(int minutesRead) {
		this.minutesRead = minutesRead;
	}
	public String getTypeOfReading() {
		return typeOfReading;
	}
	public void setTypeOfReading(String typeOfReading) {
		this.typeOfReading = typeOfReading;
	}
}
