package com.techelevator.model;

import java.time.LocalDate;

public class Competition {
	
	private long competitionId;
	private String nameOfCompetition;
	private LocalDate startDate;
	private LocalDate endDate;
	private String description;
	private int minutesToFinish;
	private Long familyId;
	
	
	
	public Long getFamilyId() {
		return familyId;
	}
	public void setFamilyId(Long familyId) {
		this.familyId = familyId;
	}
	public long getCompetitionId() {
		return competitionId;
	}
	public void setCompetitionId(long competitionId) {
		this.competitionId = competitionId;
	}
	public String getNameOfCompetition() {
		return nameOfCompetition;
	}
	public void setNameOfCompetition(String nameOfCompetition) {
		this.nameOfCompetition = nameOfCompetition;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMinutesToFinish() {
		return minutesToFinish;
	}
	public void setMinutesToFinish(int minutesToFinish) {
		this.minutesToFinish = minutesToFinish;
	}
	
	

}
