package com.techelevator.model;

import java.time.LocalDate;

public class Session {
	
	private long id;
	private LocalDate dateOfReading;
	private int minutesRead;
	private String typeOfReading;
	private long peopleBookId;
	
	public long getPeopleBookId() {
		return peopleBookId;
	}
	public void setPeopleBookId(long peopleBookId) {
		this.peopleBookId = peopleBookId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public LocalDate getDateOfReading() {
		return dateOfReading;
	}
	public void setDateOfReading(LocalDate dateOfReading) {
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
